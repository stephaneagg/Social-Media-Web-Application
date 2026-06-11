import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { getPost } from "../../services/postService";
import Post from "../../components/post/Post";

import "./singlePostPage.scss"

export default function SinglePostPage() {
  const { id } = useParams();
  const [post, setPost] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    async function fetchPost() {
      try {
        const data = await getPost(id);
        setPost(data);
      } catch (err) {
        setError("Post not found.");
      } finally {
        setLoading(false);
      }
    }

    fetchPost();
  }, [id]);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div className="singlePostPage">
      <div className="container">
        <Post post={post} />
      </div>
    </div>
  )
};