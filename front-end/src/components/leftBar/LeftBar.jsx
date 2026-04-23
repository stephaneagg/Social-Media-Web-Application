import "./leftBar.scss"
import { useContext } from "react";
import { AuthContext } from "../../context/authContext.jsx";

export default function LeftBar() {

  const { currentUser } = useContext(AuthContext)

  return (
    <div className="leftBar">

      <div className="container">

        <div className="menu">

          <div className="user">
            <img src={currentUser.profilePic} alt="" />
            <span>{currentUser.name}</span>
          </div>

          <div className="item">
            <img src="/resources/followers.png" alt=""/>
            <span>Followers</span>
          </div>

          <div className="item">
            <img src="/resources/groupIcon.png" alt=""/>
            <span>Groups</span>
          </div>

          <div className="item">
            <img src="/resources/messageIcon.png" alt=""/>
            <span>Messages</span>
          </div>

          <div className="item">
            <img src="/resources/galleryIcon.png" alt=""/>
            <span>Gallery</span>
          </div>

          <div className="item">
            <img src="/resources/gameIcon.png" alt=""/>
            <span>Gaming</span>
          </div>

        </div>
      </div>
    </div>
  )
}