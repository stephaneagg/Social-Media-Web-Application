import Header from "../../components/header/Header.jsx"
import Feed from "../../components/feed/Feed.jsx"
import CreatePost from "../../components/post/CreatePost.jsx"
import "./home.scss"

export default function Home() {
    return (
        <div className="home">
            <CreatePost loadPosts={() => {window.location.reload()}}/>
            <Feed />
        </div>

    )
}