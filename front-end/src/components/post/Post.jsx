
import {Link} from "react-router-dom"
import { useState } from "react";
import Comments from "../comments/Comments"
import MoreHorizIcon from '@mui/icons-material/MoreHoriz';
import AddCommentIcon from '@mui/icons-material/AddComment';

import "./post.scss"

export default function Post(props) {

  const [commentOpen, setCommentOpen] = useState(false)

  return (
    <div className="post">
      <div className="container">
        <div className="user">
            <div className="userInfo">
              <img src={`http://localhost:8080${props.post.userProfileImageUrl}`} alt="" />
              <div className="details">
                <Link
                  to={`profile/${props.post.authorId}`}
                  style={{textDecoration:"none", color:"inherit"}}
                >
                  <span className="name">{props.post.authorName}</span>
                </Link>
                <span className="date">{props.post.createdAt}</span> {/* TODO: Format the date */}
              </div>
            </div>
            <MoreHorizIcon />
        </div>
        <div className="content">
          <p>{props.post.content}</p>
          <img src={`http://localhost:8080${props.post.imageUrl}`} alt="" />
        </div>
        <div className="info">
          <div className="item" onClick={()=>setCommentOpen(!commentOpen)}>
            <AddCommentIcon />
            {props.post.commentCount} Comments
          </div>
        </div>
        {commentOpen ? <Comments postId={props.post.id} />: null}
      </div>
    </div>
  )
}