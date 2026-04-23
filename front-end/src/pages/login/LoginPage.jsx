import LoginForm from "../../components/forms/LoginForm.jsx"
import {Link} from "react-router-dom"
import { useContext } from "react";
import { AuthContext } from "../../context/authContext.jsx";
import "./login.scss"


export default function LoginPage() {

    const {login} = useContext(AuthContext);

    function handleLogin() {
        login();
    }

    return (
        <div className="login">
            <div className="card">
                <div className="left">
                    <h1>Hello World</h1>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                    <span>Don't have an account?</span>
                    <Link to="/register">
                        <button>Register</button>
                    </Link>
                </div>
                <div className="right">
                    <h1>Login</h1>
                    <LoginForm handleLogin={handleLogin}/>
                </div>
            </div>
        </div>
    )
}