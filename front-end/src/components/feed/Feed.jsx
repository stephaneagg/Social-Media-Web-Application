import Post from "../post/Post.jsx"
import PostList from "../post/PostList";
import "./feed.scss"

import { useState, useEffect, useCallback } from "react";
import { getFeed } from "../../services/feedService"

export default function Feed() {


  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const loadFeed = useCallback(async () => {
    try {
      setLoading(true);
      const data = await getFeed();
      setPosts(data);
    } catch (err) {
      setError(err);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    loadFeed();
  }, [loadFeed]);

  return (
    <div className="feed">
      {loading ? <p>Loading feed...</p> : null}

      {error ? <p>Failed to load feed.</p> : null}

      {/* {!loading && !error && posts.map((post) => (
        <PostList posts={posts} />
      ))} */}

      {!loading && !error &&
        <PostList posts={posts} loadPosts={loadFeed}/>
      }
    </div>
  );
}