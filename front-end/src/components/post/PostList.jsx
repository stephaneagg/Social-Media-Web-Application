import Post from "../post/Post";

export default function PostList({ posts }) {
  return (
    <div className="feed">
      {posts.map(post => (
        <Post post={post} key={post.id} />
      ))}
    </div>
  );
}