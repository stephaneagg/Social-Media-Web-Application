import "./header.scss"
import { ThemeContext } from "../../context/themeContext.jsx";
import { AuthContext } from "../../context/authContext.jsx";
import HomeIcon from '@mui/icons-material/Home';
import DarkModeIcon from '@mui/icons-material/DarkMode';
import LightModeIcon from '@mui/icons-material/LightMode';
import AppsIcon from '@mui/icons-material/Apps';
import SearchIcon from '@mui/icons-material/Search';
import PersonIcon from '@mui/icons-material/Person';
import NotificationsIcon from '@mui/icons-material/Notifications';

import { useContext } from "react";
import { Link } from "react-router-dom"

export default function Header() {
    const { toggle, darkMode } = useContext(ThemeContext);
    const { currentUser } = useContext(AuthContext)

    return (
        <div className="header">
            <div className="left">
                <Link to="/" style={{textDecoration:"none"}}>
                    <span>Steph's Social</span>
                </Link>

                <Link to="/" style={{textDecoration:"none", color: "inherit" }} >
                    <HomeIcon />
                </Link>

                {darkMode ?
                    <LightModeIcon onClick={toggle}/>
                    : <DarkModeIcon onClick={toggle}/>}
                <AppsIcon />

                <div className="search">
                    <SearchIcon />
                    <input type="text" placeholder="Search..."/>
                </div>

            </div>

            <div className="right">
                <Link to={`profile/${currentUser.id}`} style={{textDecoration:"none", color: "inherit" }} >
                    <PersonIcon />
                </Link>

                <NotificationsIcon />
                <div className="user">
                    <Link
                        to={`profile/${currentUser.id}`}
                        className="userLink"
                    >
                        <img src={currentUser.profileImageUrl} alt=""/>
                        <span>{currentUser.displayName}</span>
                    </Link>
                </div>

            </div>

        </div>
    )
}