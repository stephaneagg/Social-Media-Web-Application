import "./leftBar.scss"


export default function LeftBar() {
  return (
    <div className="leftBar">

      <div className="container">

        <div className="menu">

          <div className="user">
            <img src="src/resources/profileIcon.png" alt="" />
            <span>Jane Doe</span>
          </div>

          <div className="item">
            <img src="src/resources/followers.png" alt=""/>
            <span>Followers</span>
          </div>

          <div className="item">
            <img src="src/resources/groupIcon.png" alt=""/>
            <span>Groups</span>
          </div>

          <div className="item">
            <img src="src/resources/messageIcon.png" alt=""/>
            <span>Messages</span>
          </div>

          <div className="item">
            <img src="src/resources/galleryIcon.png" alt=""/>
            <span>Gallery</span>
          </div>

          <div className="item">
            <img src="src/resources/gameIcon.png" alt=""/>
            <span>Gaming</span>
          </div>

        </div>
      </div>
    </div>
  )
}