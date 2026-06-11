import { useState, useContext, useRef, useEffect } from "react";
import { Link } from "react-router-dom";
import { AuthContext } from "../../context/authContext.jsx";
import { deletePost, editPost } from "../../services/postService"

import Comments from "../comments/Comments";
import MoreHorizIcon from "@mui/icons-material/MoreHoriz";
import AddCommentIcon from "@mui/icons-material/AddComment";
import { timeAgo } from "../../utils/formatDate";

import "./post.scss";

export default function Post(props) {
  const [commentOpen, setCommentOpen] = useState(false);
  const [menuOpen, setMenuOpen] = useState(false);

  const { currentUser } = useContext(AuthContext);
  const isOwner = currentUser.id === props.post.authorId;

  const menuRef = useRef(null);
  const buttonRef = useRef(null);

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (
        menuRef.current &&
        !menuRef.current.contains(event.target) &&
        !buttonRef.current.contains(event.target)
      ) {
        setMenuOpen(false);
      }
    };

    document.addEventListener("click", handleClickOutside);

    return () => {
      document.removeEventListener("click", handleClickOutside);
    };
  }, []);

  async function handleDelete() {
    const confirmed = window.confirm("Are you sure you want to delete this post? \nThis cannot be undone...");
    if (confirmed) {
      try {
        await deletePost(props.post.id);
        window.location.reload();
      } catch (err) {
        console.error(err);
        alert("Failed to delete post. Please try again.");
      }
    }
  }

  function handleEdit() {}

  function handleLink() {}

  return (
    <div className="post">
      <div className="container">
        <div className="user">
          <div className="userInfo">
            <img
              src={`http://localhost:8080${props.post.userProfileImageUrl}`}
              alt=""
            />
            <div className="details">
              <Link
                to={`profile/${props.post.authorId}`}
                style={{ textDecoration: "none", color: "inherit" }}
              >
                <span className="name">{props.post.authorName}</span>
              </Link>
              <span className="date">{timeAgo(props.post.createdAt)}</span>
            </div>
          </div>

          <div
            ref={buttonRef}
            className="menuButton"
            onClick={() => setMenuOpen((prev) => !prev)}
          >
            <MoreHorizIcon />
          </div>

          <div ref={menuRef} className={`postMenu${menuOpen ? " open" : ""}`}>
            {isOwner && (
              <>
                <button
                  onClick={() => {
                    handleEdit();
                    setMenuOpen(false);
                  }}
                >
                  Edit Post
                </button>

                <button
                  onClick={() => {
                    handleDelete();
                    setMenuOpen(false);
                  }}
                >
                  Delete Post
                </button>
              </>
            )}

            <button
              onClick={() => {
                handleLink();
                setMenuOpen(false);
              }}
            >
              Copy Link
            </button>
          </div>
        </div>

        <div className="content">
          <p>{props.post.content}</p>
          <img
            src={`http://localhost:8080${props.post.imageUrl}`}
            alt=""
          />
        </div>

        <div className="info">
          <div
            className="item"
            onClick={() => setCommentOpen(!commentOpen)}
          >
            <AddCommentIcon />
            {props.post.commentCount} Comments
          </div>
        </div>

        {commentOpen && <Comments postId={props.post.id} />}
      </div>
    </div>
  );
}