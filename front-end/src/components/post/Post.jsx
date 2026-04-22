
import {Link} from "react-router-dom"
import MoreHorizIcon from '@mui/icons-material/MoreHoriz';
import AddCommentIcon from '@mui/icons-material/AddComment';

import "./post.scss"

export default function Post(props) {
  return (
    <div className="post">
      <div className="container">
        <div className="user">
            <div className="userInfo">
              <img src={props.post.profilePic} alt="" />
              <div className="details">
                <Link
                  to={`profile/${props.post.userId}`}
                  style={{textDecoration:"none", color:"inheret"}}
                >
                  <span className="name">{props.post.name}</span>
                </Link>
                <span className="date">1 min ago</span>
              </div>
            </div>
            <MoreHorizIcon />
        </div>
        <div className="content">
          <p>{props.post.desc}</p>
          <img src="src/resources/tempPostPic.jpg" alt="" />
        </div>
        <div className="info">
          <div className="item">
            <AddCommentIcon />
            12 Comments
          </div>
        </div>
      </div>
    </div>
  )
}