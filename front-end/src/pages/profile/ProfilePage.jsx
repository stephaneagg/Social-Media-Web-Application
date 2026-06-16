import "./profile.scss"
import MoreHorizIcon from '@mui/icons-material/MoreHoriz';
import Feed from "../../components/feed/Feed.jsx"
import PostList from "../../components/post/PostList";
import CreatePost from "../../components/post/CreatePost.jsx"
import EditUserModal from "../../components/profile/EditUserModal"

import { useParams } from "react-router-dom";
import { useContext, useEffect, useState, useCallback, useRef } from "react";
import { AuthContext } from "../../context/authContext.jsx";
import { getUser } from "../../services/userService"
import { getUsersPosts } from "../../services/postService"
import { getFollowers, getFollowees, follow, unfollow } from "../../services/followService"


export default function ProfilePage() {

  const { id } = useParams();
  const { currentUser } = useContext(AuthContext)

  // indicates if this profile belongs to the current user. true if yes false if no
  const isOwnProfile = currentUser.id == id;


  const [user, setUser] = useState(null);
  const [posts, setPosts] = useState([]);
  const [followerInfo, setFollowerInfo] = useState([]);
  const [followeeInfo, setFolloweeInfo] = useState([]);
  const [menuOpen, setMenuOpen] = useState(false);
  const [editing, setEditing] = useState(false);
  const [copied, setCopied] = useState(false);
  const [loading, setLoading] = useState(true);

  // Refs for the menu dropdown and trigger button, used to detect outside clicks
  const menuRef = useRef(null);
  const buttonRef = useRef(null);

  // indicates if the current user follows the owner of this profile page. true if current user follows the owner. false if current user does not follow the owner OR if current user is the owner
  const isFollower = followerInfo.some(user => user.id === currentUser.id);

  // Callback functions

  const loadUser = useCallback(async () => {
    const data = await getUser(id);
    setUser(data);
  }, [id]);

  const loadPosts = useCallback(async () => {
    const data = await getUsersPosts(id);
    setPosts(data);
    }, [id]);

  const loadFollowers = useCallback(async () => {
    const data = await getFollowers(id);
    setFollowerInfo(data);
  }, [id])

  // Helper Functions

  const handleUnfollow = async () => {
    setLoading(true);
    try {
      await unfollow(id);
    } catch (err) {
      console.error(err);
    } finally {
      loadFollowers()
      setLoading(false);
    }
  }

  const handleFollow = async () => {
    setLoading(true);
    try {
      await follow(id);
    } catch (err) {
      console.error(err);
    } finally {
      loadFollowers()
      setLoading(false);
    }
  }

  function handleLink() {
    navigator.clipboard.writeText(`http://localhost:5173/profile/${id}`);
    setCopied(true);
    setTimeout(() => {
      setCopied(false);
      setMenuOpen(false);
    }, 2000);
  }

  const profileMenu = (<div className="profileMenu">
    {isOwnProfile &&
      <button onClick={() => {
        setEditing(true);
        setMenuOpen(false);
      }}
      >
        Edit Profile
      </button>}

      <button onClick={() => {
        handleLink()
      }}
      >
        {copied ? "Link Copied!" : "Copy Link"}
      </button>
    </div>)


  // Conditionally render a follow button
  let followButton = null
  if (!isOwnProfile) {
    if (isFollower) {
      followButton = <button className="unfollowButton"
        onClick={handleUnfollow}
        disabled={loading}
        >Unfollow</button>;
    } else {
      followButton = <button className="followButton"
       onClick={handleFollow}
       disabled={loading}
       >Follow</button>;
    }
  }

  // User Info
  useEffect( () => {
    loadUser()
  }, [loadUser]);

  // Load posts on inital render and when the callback function is recreated
  useEffect( () => {
    loadPosts()
  }, [loadPosts]);

  // Load list of followers in inital render and when the callback function is recreated
  useEffect( () => {
    loadFollowers()
  }, [loadFollowers]);

  // List of Followees
    useEffect( () => {
    const loadFollowees = async () => {
      const data = await getFollowees(id)
      setFolloweeInfo(data);
    };
    loadFollowees()
  }, [id]);

  return (
    <div className="profile">
      <div className="images">
        <img
          src={user && user.coverImageUrl != null ?
            `http://localhost:8080${user.coverImageUrl}`
            :
            "/resources/tempCoverPic.jpg"}
          alt="Cover Picture"
          className="cover"
        />
        <img
          src={user && user.profileImageUrl != null ?
            `http://localhost:8080${user.profileImageUrl}`
            :
            "/resources/tempProfileIcon.jpg"}
          alt="Profile Picture"
          className="profilePic"/>
      </div>

      <div className="profileContainer">
        <div className="uInfo">

          <div className="topName">
            {user && <span>{user.displayName}</span>}
          </div>

          <div className="middle">
            <div className="left">
              <div className="post-count">
                <span>{posts.length}</span>
                <span>posts</span>
              </div>
              <div className="follower-count">
                <span>{followerInfo.length}</span>
                <span>followers</span>
              </div>
              <div className="following-count">
                <span>{followeeInfo.length}</span>
                <span>following</span>
              </div>
            </div>

            <div
              ref={buttonRef}
              className="menuButton"
              onClick={() => setMenuOpen((prev) => !prev)}
            >
              <MoreHorizIcon />
            </div>

            {menuOpen && profileMenu}

            {editing && (
              <EditUserModal
                user={user}
                onClose={() => setEditing(false)}
                onUpdate={() => {loadUser(); loadPosts()}}
              />
            )}

          </div>


          <div className="center">
            {/* Render followButton when user is not null */}
            {user && followButton}
          </div>

          <div className="bio">
            {user && <span>{user.bio}</span>}
          </div>

        </div>
        {isOwnProfile && <CreatePost loadPosts={loadPosts}/>}
        <PostList posts={posts} loadPosts={loadPosts}/>
      </div>
    </div>
  )
}