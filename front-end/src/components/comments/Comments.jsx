import "./comments.scss"

import { useContext, useState, useEffect, useCallback } from "react";
import { AuthContext } from "../../context/authContext";
import { getComments, createComment } from "../../services/commentService"
import { timeAgo } from "../../utils/formatDate";

export default function Comments(props) {

  const {currentUser} = useContext(AuthContext);
  const [comments, setComments] = useState([]);
  const [newComment, setNewComment] = useState("");
  const [loading, setLoading] = useState(null);
  const [error, setError] = useState(null);

  const loadComments = useCallback(async () => {
    const data = await getComments(props.postId);
    setComments(data);
  }, [props.postId]);

  const handleAddComment = async (e) => {
    e.preventDefault();

    if (!newComment) return;

    try {
      setLoading(true);
      setError(null);
      // call commentService's createComment(postId, content<newComment>)
      await createComment({
        postId: props.postId, content: newComment
      });
      // reset newComment
      setNewComment("");
    } catch (err) {
      console.error(err);
      setError("Failed to create comment. Please try again.");
    } finally {
      // reset loading
      setLoading(false);
    }
    // reload comments
    loadComments();
  }

  useEffect( () => {
    loadComments();
  }, [loadComments]);



  return (
    <div className="comments">
      <div className="write">
        <img src={`http://localhost:8080${currentUser.profileImageUrl}`} alt="" />
        <form onSubmit={handleAddComment}>
          <input
            type="text"
            placeholder="write a comment"
            value={newComment}
            onChange= {(e) => setNewComment(e.target.value)}
          />
          <button type="submit" disabled={loading}>
            {loading ? "Commenting..." : "Comment"}
          </button>
        </form>

      </div>

      {comments.map( (comment) => (
        <div className="comment" key={comment.id}>
          <img src={`http://localhost:8080${comment.profilePictureUrl}`} alt="" />
          <div className="comment-info">
            <span>{comment.displayName}</span>
            <p>{comment.content}</p>
          </div>
          <span className="date">{timeAgo(comment.createdAt)}</span>
        </div>
      ))}
    </div>
  );
};