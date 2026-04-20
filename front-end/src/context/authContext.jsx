import { createContext, useState, useEffect } from "react";

export const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [darkMode, setDarkMode] = useState(() => {
    return JSON.parse(localStorage.getItem("darkMode")) || false;
  });

const toggle = () => {
    setDarkMode(prev => !prev);
  };

  useEffect(() => {
    localStorage.setItem("darkMode", JSON.stringify(darkMode));

    document.body.classList.toggle("theme-dark", darkMode);
  }, [darkMode]);

  return (
    <AuthContext.Provider value={{ darkMode, toggle }}>
      {children}
    </AuthContext.Provider>
  );
}