import { Link } from "react-router-dom";
import "./userCard.scss";

export default function UserCard({ user }) {
  return (
    <Link to={`/profile/${user.id}`} className="userCard">
      <img
        src={`http://localhost:8080${user.profileImageUrl}`}
        alt=""
      />
      <span>{user.displayName}</span>
    </Link>
  );
}