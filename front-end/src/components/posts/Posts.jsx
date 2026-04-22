import Post from "../post/Post.jsx"
import "./posts.scss"

export default function Posts() {

  // TEMP DATA
  const posts = [ {
      id:1,
      name:"James Doe",
      userId:1,
      profilePic:"src/resources/tempProfileIcon.jpeg",
      desc:"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
      img:"src/resources/tempPostPic.jpg"
    },
    {
      id:2,
      name:"Jordan Doe",
      userId:2,
      profilePic:"src/resources/tempProfileIcon.jpeg",
      desc:"Lorem ipsum dolor sit amet consectetur adipiscing elit. Sit amet consectetur adipiscing elit quisque faucibus ex. Adipiscing elit quisque faucibus ex sapien vitae pellentesque.",
      img:"src/resources/tempPostPic.jpg"
    }
  ]



  return (
    <div className="posts">
      {posts.map( post => (
        <Post post={post} key={post.id}/>
      ))}
    </div>
  )
}