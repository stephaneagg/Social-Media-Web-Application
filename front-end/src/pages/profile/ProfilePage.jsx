import "./profile.scss"
import MoreHorizIcon from '@mui/icons-material/MoreHoriz';
import Feed from "../../components/feed/Feed.jsx"

import { useParams } from "react-router-dom";
import { useContext } from "react";
import { AuthContext } from "../../context/authContext.jsx";


export default function ProfilePage() {

  const { id } = useParams();
  const { currentUser } = useContext(AuthContext)
  const isOwnProfile = currentUser.id == id;
  console.log(currentUser.id)
  console.log(id)
  console.log(isOwnProfile)

  return (
    <div className="profile">
      <div className="images">
        <img src="/resources/tempCoverPic.jpg" alt="" className="cover"/>
        <img src="/resources/tempProfileIcon.jpeg" alt="" className="profilePic"/>
      </div>

      <div className="profileContainer">
        <div className="uInfo">

          <div className="topName">
            <span>{id}</span>
          </div>

          <div className="middle">
            <div className="left">
              <div className="post-count">
                <span>3</span>
                <span>posts</span>
              </div>
              <div className="follower-count">
                <span>223</span>
                <span>followers</span>
              </div>
              <div className="following-count">
                <span>510</span>
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



        </div>
        <Feed />
      </div>
    </div>
  )
}