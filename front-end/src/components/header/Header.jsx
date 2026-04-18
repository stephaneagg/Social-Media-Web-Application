import "./header.scss"
import HomeIcon from '@mui/icons-material/Home';
import DarkModeIcon from '@mui/icons-material/DarkMode';
import AppsIcon from '@mui/icons-material/Apps';
import SearchIcon from '@mui/icons-material/Search';
import PersonIcon from '@mui/icons-material/Person';
import NotificationsIcon from '@mui/icons-material/Notifications';

import { Link } from "react-router-dom"

export default function Header() {
    return (
        <div className="header">
            <div className="left">
                <Link to="/" style={{textDecoration:"none"}}>
                <span>Steph's Social</span>
                </Link>
                <HomeIcon />
                <DarkModeIcon />
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