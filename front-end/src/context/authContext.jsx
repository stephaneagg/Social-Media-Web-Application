import { createContext, useState, useEffect } from "react";
import * as authService from "../services/authService";

export const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [currentUser, setCurrentUser] = useState(() => {
    return JSON.parse(localStorage.getItem("user")) || null;
  });

  const loginUser = async (loginInput, password) => {
    // 1. get token
    const data = await authService.login(loginInput, password);
    const token = data.access_token;

    // 2. store token
    localStorage.setItem("token", data.token);

    // 3. fetch user
    const user = await authService.getCurrentUser(token);

    // 4. store user
    setCurrentUser(user);
  };

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    setCurrentUser(null);
  }

  // persist user
  useEffect(() => {
    localStorage.setItem("user", JSON.stringify(currentUser));
  }, [currentUser]);

  // auto-login on refresh
  useEffect( () => {
    localStorage.setItem("user", JSON.stringify(currentUser));
  }, [currentUser]);

  return (
    <AuthContext.Provider value={{ currentUser, login: loginUser, logout }}>
      {children}
    </AuthContext.Provider>
  );
}