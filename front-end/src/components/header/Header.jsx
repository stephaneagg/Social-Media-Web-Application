import "./header.scss"
import { ThemeContext } from "../../context/themeContext.jsx";
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

    return (
        <div className="header">
            <div className="left">
                <Link to="/" style={{textDecoration:"none"}}>
                <span>Steph's Social</span>
                </Link>
                <HomeIcon />
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
                <PersonIcon />
                <NotificationsIcon />
                <div className="user">
                    <img src="src/resources/tempProfileIcon.jpeg" alt=""/>
                    <span>Jane Doe</span>
                </div>

            </div>

        </div>
    )
}