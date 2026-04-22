import { useContext } from "react";
import { AuthContext } from "../../context/authContext";
import "./comments.scss"

export default function Comments() {

  const {currentUser} = useContext(AuthContext)

  // Temporary comment data
  const comments = [
    {
      id:1,
      desc:"Lorem ipsum dolor sit amet consectetur adipiscing elit.",
      name:"Janette Doe",
      userId:3,
      profilePic:"src/resources/tempProfileIcon.jpeg"
    },
    {
      id:2,
      desc:"Lorem ipsum dolor sit amet consectetur adipiscing elit quisque.",
      name:"Jerry Doe",
      userId:5,
      profilePic:"src/resources/tempProfileIcon.jpeg"
    }
  ]

  return (
    <div className="comments">
      <div className="write">
        <img src={currentUser.profilePic} alt="" />
        <input type="text" placeholder="write a comment" />
        <button>Comment</button>
      </div>

      {comments.map( (comment) => (
        <div className="comment">
          <img src={comment.profilePic} alt="" />
          <div className="comment-info">
            <span>{comment.name}</span>
            <p>{comment.desc}</p>
          </div>
          <span className="date">1 hour ago</span>
        </div>
      ))}
    </div>
  );
};