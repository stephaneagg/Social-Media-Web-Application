import RegisterForm from "../../components/RegisterForm.jsx"
import {Link} from "react-router-dom"
import "./register.scss"

export default function RegisterPage() {
    return (
        <div className="register">
            <div className="card">
                <div className="left">
                    <h1>Register</h1>
                    <RegisterForm />
                </div>
                <div className="right">
                    <h1>Social Media</h1>
                    <p> Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin fermentum sodales elit, ultricies posuere tortor porttitor bibendum. Duis ullamcorper quam in fermentum porttitor. Pellentesque varius volutpat varius. Proin vel tempus sapien. Praesent justo diam, pharetra at congue sit amet, eleifend vel quam.</p>
                    <span>Do you have an account?</span>
                    <Link to="/login">
                        <button>Login</button>
                    </Link>
                </div>
            </div>
        </div>
    )
}