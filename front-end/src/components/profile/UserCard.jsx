import { Link } from "react-router-dom";
import "./userCard.scss";
import { API_BASE_URL } from "../../config";

export default function UserCard({ user }) {
  return (
    <Link to={`/profile/${user.id}`} className="userCard">
      <img
        src={user.profileImageUrl}
        alt=""
      />
      <span>{user.displayName}</span>
    </Link>
  );
}