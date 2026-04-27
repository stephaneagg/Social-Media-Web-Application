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
    localStorage.setItem("token", data.access_token);

    // 3. fetch user
    const user = await authService.getCurrentUser(token);

    // 4. store user
    setCurrentUser(user);
  };

    const registerUser = async (username, email, password) => {
      // 1. call register endpoint
      await authService.register(username, email, password);
      // 2. log user in
      await loginUser(username, password);
  }

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
  useEffect(() => {
    const token = localStorage.getItem("access_token");

    if (!currentUser && token) {
      authService
        .getCurrentUser(token)
        .then((user) => setCurrentUser(user))
        .catch(() => logout());
    }
  }, []);

  return (
    <AuthContext.Provider value={{ currentUser, login: loginUser, register: registerUser, logout }}>
      {children}
    </AuthContext.Provider>
  );
}