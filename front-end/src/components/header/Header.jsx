import "./header.scss"
import { ThemeContext } from "../../context/themeContext.jsx";
import { AuthContext } from "../../context/authContext.jsx";
import { getRecentFollowers } from "../../services/followService"
import { timeAgo } from "../../utils/formatDate";
import AppsDockModal from "./AppsDockModal"
import HomeIcon from '@mui/icons-material/Home';
import DarkModeIcon from '@mui/icons-material/DarkMode';
import LightModeIcon from '@mui/icons-material/LightMode';
import AppsIcon from '@mui/icons-material/Apps';
import SearchIcon from '@mui/icons-material/Search';
import PersonIcon from '@mui/icons-material/Person';
import NotificationsIcon from '@mui/icons-material/Notifications';

import { useContext, useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom"

export default function Header() {
    const { toggle, darkMode } = useContext(ThemeContext);
    const { currentUser } = useContext(AuthContext)
    const [ userMenu, setUserMenu] = useState(false);
    const { logout } = useContext(AuthContext);
    const [searchInput, setSearchInput] = useState("");
    const [showAppsDockModal, setShowAppsDockModal] = useState(false);
    const [showRecentFollowers, setShowRecentFollowers] = useState(false);
    const [recentFollowers, setRecentFollowers] = useState(null);

    const navigate = useNavigate();

    const handleSearchKeyDown = (e) => {
        if (e.key === "Enter" && searchInput.trim()) {
            navigate(`/search?query=${encodeURIComponent(searchInput.trim())}`);
        }
    };

    useEffect(() => {
        const handleClickOutside = (event) => {
        if (
            !event.target.closest(".user") //&&
            // !event.target.closest(".userMenu")
        ) {
            setUserMenu(false);
        }
        };

        document.addEventListener("click", handleClickOutside);

        return () => {
        document.removeEventListener("click", handleClickOutside);
        };
    }, []);

    useEffect(() => {
        const handleClickOutside = (event) => {
        if (
            !event.target.closest(".notifications") //&&
            // !event.target.closest(".follower")
        ) {
            setShowRecentFollowers(false);
        }
        };

        document.addEventListener("click", handleClickOutside);

        return () => {
        document.removeEventListener("click", handleClickOutside);
        };
    }, []);

    useEffect(() => {
        handleGetRecentFollowers()
    },[])

    async function handleGetRecentFollowers() {
        const data = await getRecentFollowers();
        setRecentFollowers(data);
    }

    const goToProfile = (id) => {
        navigate(`/profile/${id}`)
    }

    return (
        <div className="header">
            <div className="left">
                <Link to="/" style={{textDecoration:"none"}}>
                    <span>Social Media Platform</span>
                </Link>

                <Link to="/" style={{textDecoration:"none", color: "inherit" }} >
                    <HomeIcon />
                </Link>

                {darkMode ?
                    <LightModeIcon onClick={toggle}/>
                    : <DarkModeIcon onClick={toggle}/>}

                <div onClick={() => setShowAppsDockModal(true)} style={{textDecoration:"none", color: "inherit" }}>
                    <AppsIcon />
                </div>

                {showAppsDockModal && <AppsDockModal onClose={() => setShowAppsDockModal(false)} />}

                <div className="search">
                    <SearchIcon />
                    <input
                        type="text"
                        placeholder="Search..."
                        onChange={(e) => setSearchInput(e.target.value)}
                        onKeyDown={handleSearchKeyDown}
                    />
                </div>

            </div>

            <div className="right">
                <Link to={`profile/${currentUser.id}`} style={{textDecoration:"none", color: "inherit" }} >
                    <PersonIcon />
                </Link>

                <div className="notifications" onClick={() => {handleGetRecentFollowers(); setShowRecentFollowers((prev) => !prev)}}>
                    <NotificationsIcon />
                    <div className="recentFollowers">
                    {showRecentFollowers && ( recentFollowers.map((follower) => (
                            <div
                                className="follower"
                                key={follower.userId}
                                onClick={() => goToProfile(follower.userId)}
                            >
                                <img src={follower.profileImageUrl ? `http://localhost:8080${follower.profileImageUrl}` : "/resources/tempProfileIcon.jpeg"} alt="" />
                                <span>{`${follower.displayName} followed you ${timeAgo(follower.createdAt)}`}</span>

                            </div>
                        ))
                    )}
                    </div>
                </div>


                <div className="user" onClick={() => setUserMenu((prev) => !prev)}>
                    <img src={`http://localhost:8080${currentUser.profileImageUrl}`} alt=""/>
                    <span>{currentUser.displayName}</span>

                    {userMenu && (
                        <div className="userMenu">
                            <Link
                                to={`profile/${currentUser.id}`}
                                className="userLink"
                            >
                                Profile
                            </Link>
                            <Link
                                to={`profile/${currentUser.id}/settings`}
                                className="settingsLink"
                            >
                                Settings
                            </Link>
                            <button
                                onClick={logout}
                                className="logoutButton"
                            >
                                Logout
                            </button>
                        </div>
                    )}
                </div>


            </div>

        </div>
    )
}