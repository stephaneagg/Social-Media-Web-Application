import Post from "../post/Post";

export default function PostList({ posts, loadPosts }) {
  return (
    <div className="feed">
      {posts.map(post => (
        <Post post={post} loadPosts={loadPosts} key={post.id} />
      ))}
    </div>
  );
}