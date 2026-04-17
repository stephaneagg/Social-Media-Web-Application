import { BrowserRouter, Routes, Route, Outlet } from "react-router-dom";
import LoginPage from "./pages/login/LoginPage";
import RegisterPage from "./pages/register/RegisterPage";
import HomePage from "./pages/home/HomePage"
import ProfilePage from "./pages/profile/ProfilePage"
import Header from "./components/header/Header"
import LeftBar from "./components/leftBar/LeftBar"
import RightBar from "./components/rightBar/RightBar"


function App() {

  const Layout = () => {
    return (
      <div>
        <Header/>
        <div style={{display:"flex"}}>
          <LeftBar />
          <Outlet />
          <RightBar />
        </div>
      </div>
    )
  }

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout/>} />
          <Route path="/" element={<HomePage />} />
          <Route path="profile/:id" element={<ProfilePage />} />
        <Route path="/login" element={<LoginPage/>} />
        <Route path="/register" element={<RegisterPage/>} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
