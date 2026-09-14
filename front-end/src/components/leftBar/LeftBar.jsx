import "./leftBar.scss"
import { useContext } from "react";
import { useNavigate } from "react-router-dom"
import { AuthContext } from "../../context/authContext.jsx";

export default function LeftBar() {

  const { currentUser } = useContext(AuthContext)

  const navigate = useNavigate()

  return (
    <div className="leftBar">

      <div className="container">

        <div className="menu">

          <button className="user" onClick={() => navigate(`/profile/${currentUser.id}`)} >
            <img src={`http://localhost:8080${currentUser.profileImageUrl}`} alt="" />
            <span>{currentUser.displayName}</span>
          </button>

          <button className="item" onClick={() => navigate(`/followers`)} >
            <img src="/resources/followers.png" alt=""/>
            <span>Followers</span>
          </button>

          <button className="item">
            <img src="/resources/groupIcon.png" alt=""/>
            <span>Groups</span>
          </button>

          <button className="item">
            <img src="/resources/messageIcon.png" alt=""/>
            <span>Messages</span>
          </button>

          <button className="item">
            <img src="/resources/galleryIcon.png" alt=""/>
            <span>Gallery</span>
          </button>

          <button className="item">
            <img src="/resources/gameIcon.png" alt=""/>
            <span>Gaming</span>
          </button>

        </div>
      </div>
    </div>
  )
}