import "./rightBar.scss"
import { useContext, useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"
import { getFollowers, getFollowSuggestions } from "../../services/followService"
import { AuthContext } from "../../context/authContext" // adjust path to match your project structure

export default function RightBar() {
  const { currentUser } = useContext(AuthContext)
  const id = currentUser.id

  const [followers, setFollowers] = useState([])
  const [suggestions, setSuggestions] = useState([])
  const navigate = useNavigate()

  useEffect(() => {
    getFollowers(id)
      .then(setFollowers)
      .catch((err) => console.error(err))

    getFollowSuggestions(5)
      .then(setSuggestions)
      .catch((err) => console.error(err))
  }, [id])

  const goToProfile = (id) => {
    navigate(`/profile/${id}`)
  }

  return (
    <div className="rightBar">

      <div className="container">

        <div className="item">
          <span>Suggestions for you</span>

          {suggestions.map((suggestion) => (
            <div
              className="user"
              key={suggestion.userId}
              onClick={() => goToProfile(suggestion.userId)}
            >
              <div className="userInfo">
                <img src={suggestion.profileImageUrl ? `http://localhost:8080${suggestion.profileImageUrl}` : "/resources/tempProfileIcon.jpeg"} alt="" />
                <div className="userText">
                  <span className="name">{suggestion.displayName}</span>
                  <span className="subtext">
                    {suggestion.reason === "MUTUAL"
                      ? `${suggestion.mutualCount} mutual follower${suggestion.mutualCount === 1 ? "" : "s"}`
                      : "Popular"}
                  </span>
                </div>
              </div>
            </div>
          ))}
        </div>

        <div className="item">
          <span>Followers</span>

          {followers.map((follower) => (
            <div
              className="user"
              key={follower.id}
              onClick={() => goToProfile(follower.id)}
            >
              <div className="userInfo">
                <img src={follower.profileImageUrl ? `http://localhost:8080${follower.profileImageUrl}` : "/resources/tempProfileIcon.jpeg"} alt="" />
                <span>{follower.displayName}</span>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  )
}