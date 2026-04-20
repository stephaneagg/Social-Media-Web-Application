import { createContext, useState, useEffect } from "react";

export const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [currentUser, setCurrentUser] = useState(() => {
    return JSON.parse(localStorage.getItem("user")) || null;
  });

const login = () => {
    // TODO
    setCurrentUser({id:1, name:"Janes Doe", profilePic:"src/resources/tempProfileIcon.jpeg"})
  };

  useEffect(() => {
    localStorage.setItem("user", JSON.stringify(currentUser));
  }, [currentUser]);

  return (
    <AuthContext.Provider value={{ currentUser, login }}>
      {children}
    </AuthContext.Provider>
  );
}