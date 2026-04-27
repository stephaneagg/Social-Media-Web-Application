import { useState, useContext } from "react";
import {Link, useNavigate} from "react-router-dom"
import { AuthContext } from "../../context/authContext"

export default function LoginForm() {
  const { login } = useContext(AuthContext);

  const [loginInput, setLoginInput] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await login(loginInput, password);
      navigate("/")
    } catch (err) {
      setError("Invalid credentials");
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        type="text"
        placeholder="Email or Username"
        value={loginInput}
        onChange={(e) => setLoginInput(e.target.value)}
      />

      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />

      <button type="submit">Login</button>

      {error && <p>{error}</p>}
    </form>
  );
}