import { Link } from "react-router-dom";
import { AuthContext } from "../../context/authContext.jsx";
import { useState, useContext } from "react";
import followersIcon from "/resources/followers.png"
import groupsIcon from "/resources/groupIcon.png"
import messagesIcon from "/resources/messageIcon.png"
import galleryIcon from "/resources/galleryIcon.png"
import gamingIcon from "/resources/gameIcon.png"
import "./appsDockModal.scss";


export default function AppsDockModal({ onClose }) {

  const { currentUser } = useContext(AuthContext);

  const APPS = [
    { label: "Profile", to: `/profile/${currentUser.id}`, icon:`http://localhost:8080${currentUser.profileImageUrl}`},
    { label: "Followers", to: "", icon:followersIcon},
    { label: "Groups", to: "", icon: groupsIcon },
    { label: "Messages", to: "", icon: messagesIcon },
    { label: "Gallery", to: "", icon: galleryIcon},
    { label: "Gaming", to: "", icon: gamingIcon },
  ];

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal appsDock" onClick={(e) => e.stopPropagation()}>
        <div className="dockGrid">
          {APPS.map((app) => (
            <Link
              key={app.label}
              to={app.to}
              className="dockItem"
              onClick={onClose}
            >
              <img src={app.icon} alt={app.label} className="dockIcon" />
              <span>{app.label}</span>
            </Link>
          ))}
        </div>
      </div>
    </div>
  );
}