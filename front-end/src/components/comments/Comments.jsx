import "./comments.scss"

import { useContext, useState, useEffect, useCallback, useRef } from "react";
import { AuthContext } from "../../context/authContext";
import { getComments, createComment, deleteComment } from "../../services/commentService"
import { timeAgo } from "../../utils/formatDate";
import MoreHorizIcon from "@mui/icons-material/MoreHoriz";

export default function Comments(props) {

  const {currentUser} = useContext(AuthContext);
  const [comments, setComments] = useState([]);
  const [newComment, setNewComment] = useState("");
  const [loading, setLoading] = useState(null);
  const [error, setError] = useState(null);
  // indicates if the more menu is open. null if closed, commentId if open (to keep track of the comment).
  const [menuOpen, setMenuOpen] = useState(null);

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

  const handleDeleteComment = async (commentId) => {
    const confirmed = window.confirm("Are you sure you want to delete this comment? \nThis cannot be undone...");
    if (confirmed) {
      try {
        await deleteComment(commentId);
        loadComments();
      } catch (err) {
      console.log(err);
      alert("Failed to delete comment. Please try again.");
      }
    }
  }

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (
        !event.target.closest(".commentMenu") &&
        !event.target.closest(".menuButton")
      ) {
        setMenuOpen(null);
      }
    };

    document.addEventListener("click", handleClickOutside);

    return () => {
      document.removeEventListener("click", handleClickOutside);
    };
  }, []);

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


          {currentUser.id === comment.userId ?
            <div
              className="menuButton"
              onClick={() => setMenuOpen(menuOpen === comment.id ? null : comment.id)}
            >
              <MoreHorizIcon />
            </div>
            : null
          }
          {menuOpen === comment.id ?
            <div className="commentMenu">
              <>
                <button onClick={() => {
                    setMenuOpen(null);
                  }}
                >
                  Edit Comment
                </button>

                <button onClick={() => {
                    handleDeleteComment(comment.id);
                    setMenuOpen(null);
                  }}
                >
                  Delete Comment
                </button>
              </>
            </div>
            : null
          }

          <span className="date">{timeAgo(comment.createdAt)}</span>
        </div>
      ))}
    </div>
  );
};