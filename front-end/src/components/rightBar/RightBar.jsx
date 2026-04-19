import "./rightBar.scss"


export default function RightBar() {
  return (
    <div className="rightBar">

      <div className="container">

        <div className="item">
          <span>Follow Requests</span>

          <div className="user">
            <div className="userInfo">
              <img src="src/resources/tempProfileIcon.jpeg" alt=""/>
              <span>John Doe</span>
            </div>

            <div className="buttons">
              <button>Follow</button>
              <button>Dismiss</button>
            </div>
          </div>
        </div>

        <div className="item">
          <span>Followers</span>


          <div className="user">
            <div className="userInfo">
              <img src="src/resources/tempProfileIcon.jpeg" alt=""/>
              <span>John Doe</span>
            </div>

          </div>
        </div>
      </div>
    </div>
  )
}