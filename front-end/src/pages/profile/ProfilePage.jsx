import "./profile.scss"
import MoreHorizIcon from '@mui/icons-material/MoreHoriz';

export default function ProfilePage() {
  return (
    <div className="profile">
      <div className="images">
        <img src="/resources/tempCoverPic.jpg" alt="" className="cover"/>
        <img src="/resources/tempProfileIcon.jpeg" alt="" className="profilePic"/>
      </div>

      <div className="profileContainer">
        <div className="uInfo">
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
          <div className="center">

          </div>
          <div className="right">
            <MoreHorizIcon />
          </div>
        </div>
      </div>
    </div>
  )
}