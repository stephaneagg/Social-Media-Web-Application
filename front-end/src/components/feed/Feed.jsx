import Post from "../post/Post.jsx"
import "./feed.scss"

import { useState, useEffect } from "react";
import { getFeed } from "../../services/feedService"

export default function Feed() {


  const [feed, setFeed] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // TEMP DATA
  // const feed = [ {
  //     id:1,
  //     name:"James Doe",
  //     userId:1,
  //     profilePic:"/resources/tempProfileIcon.jpeg",
  //     desc:"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
  //     img:"/resources/tempPostPic.jpg"
  //   },
  //   {
  //     id:2,
  //     name:"Jordan Doe",
  //     userId:2,
  //     profilePic:"/resources/tempProfileIcon.jpeg",
  //     desc:"Lorem ipsum dolor sit amet consectetur adipiscing elit. Sit amet consectetur adipiscing elit quisque faucibus ex. Adipiscing elit quisque faucibus ex sapien vitae pellentesque.",
  //     img:"/resources/tempPostPic.jpg"
  //   }
  // ]

useEffect(() => {
  const loadFeed = async () => {
    try {
      setLoading(true);
      const data = await getFeed();
      // console.log(data);
      setFeed(data);
    } catch (err) {
      setError(err);
    } finally {
      setLoading(false);
    }

  };

  loadFeed();
}, []);

return (
  <div className="feed">
    {loading ? <p>Loading feed...</p> : null}

    {error ? <p>Failed to load feed.</p> : null}

    {!loading && !error && feed.map((post) => (
      <Post post={post} key={post.id} />
    ))}
  </div>
);
}