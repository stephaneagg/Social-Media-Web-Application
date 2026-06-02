import "./comments.scss"

import { useContext, useState, useEffect } from "react";
import { AuthContext } from "../../context/authContext";
import { getComments } from "../../services/commentService"

export default function Comments(props) {

  const {currentUser} = useContext(AuthContext);
  const [comments, setComments] = useState([]);

  // Temporary comment data
  // const comments = [
  //   {
  //     id:1,
  //     desc:"Lorem ipsum dolor sit amet consectetur adipiscing elit.",
  //     name:"Janette Doe",
  //     userId:3,
  //     profilePic:"/resources/tempProfileIcon.jpeg"
  //   },
  //   {
  //     id:2,
  //     desc:"Lorem ipsum dolor sit amet consectetur adipiscing elit quisque.",
  //     name:"Jerry Doe",
  //     userId:5,
  //     profilePic:"/resources/tempProfileIcon.jpeg"
  //   }
  // ]

  useEffect( () => {
    const loadComments = async () => {
      const data = await getComments(props.postId);
      setComments(data);
      console.log(data)
    };
    loadComments();
  }, [props.postId]);



  return (
    <div className="comments">
      <div className="write">
        <img src={`http://localhost:8080${currentUser.profileImageUrl}`} alt="" />
        <input type="text" placeholder="write a comment" />
        <button>Comment</button>
      </div>

      {comments.map( (comment) => (
        <div className="comment" key={comment.id}>
          <img src={`http://localhost:8080${comment.profilePictureUrl}`} alt="" />
          <div className="comment-info">
            <span>{comment.displayName}</span>
            <p>{comment.content}</p>
          </div>
          <span className="date">{comment.createdAt}</span> {/* TODO: format the date*/}
        </div>
      ))}
    </div>
  );
};