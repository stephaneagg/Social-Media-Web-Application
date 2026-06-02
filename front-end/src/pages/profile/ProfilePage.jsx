import "./profile.scss"
import MoreHorizIcon from '@mui/icons-material/MoreHoriz';
import Feed from "../../components/feed/Feed.jsx"
import PostList from "../../components/post/PostList";

import { useParams } from "react-router-dom";
import { useContext, useEffect, useState } from "react";
import { AuthContext } from "../../context/authContext.jsx";
import { getUser } from "../../services/userService"
import { getUsersPosts } from "../../services/postService"
import { getFollowers, getFollowees } from "../../services/followService"


export default function ProfilePage() {

  const { id } = useParams();
  const { currentUser } = useContext(AuthContext)
  const isOwnProfile = currentUser.id == id;

  const [user, setUser] = useState(null);
  const [posts, setPosts] = useState([]);
  const [followerInfo, setFollowerInfo] = useState([]);
  const [followeeInfo, setFolloweeInfo] = useState([]);
  const [loading, setLoading] = useState(true);

  // User Info
  useEffect( () => {
    const loadUser = async () => {
      const data = await getUser(id);
      setUser(data)
    };
    loadUser()
  }, [id]);

  // List of Posts
  useEffect( () => {
    const loadPosts = async () => {
      const data = await getUsersPosts(id);
      setPosts(data);
    };

    loadPosts()
  }, [id]);

  // List of Followers
  useEffect( () => {
    const loadFollowers = async () => {
      const data = await getFollowers(id)
      setFollowerInfo(data);
    };
    loadFollowers()
  }, [id]);

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
        <img src="/resources/tempCoverPic.jpg" alt="" className="cover"/>
        <img src={user ? `http://localhost:8080${user.profileImageUrl}` : ""} alt="" className="profilePic"/>
      </div>

      <div className="profileContainer">
        <div className="uInfo">

          <div className="topName">
            {user ? <span>{user.displayName}</span> : null}
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

            <div className="right">
              <MoreHorizIcon />
            </div>
          </div>


          <div className="center">
            {isOwnProfile ? null : <button>Follow</button>}
          </div>

          <div className="bio">
            {user ? <span>{user.bio}</span> : null}
          </div>

        </div>
        <PostList posts={posts} />
      </div>
    </div>
  )
}