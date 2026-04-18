import { BrowserRouter, Routes, Route, Outlet, Navigate } from "react-router-dom";
import LoginPage from "./pages/login/LoginPage";
import RegisterPage from "./pages/register/RegisterPage";
import HomePage from "./pages/home/HomePage"
import ProfilePage from "./pages/profile/ProfilePage"
import Header from "./components/header/Header"
import LeftBar from "./components/leftBar/LeftBar"
import RightBar from "./components/rightBar/RightBar"


function App() {

  const currentUser = true;

  const Layout = () => {
    return (
      <div>
        <Header/>
        <div style={{display:"flex"}}>
          <LeftBar />
          <div style={{flex:6}}>
            <Outlet />
          </div>
          <RightBar />
        </div>
      </div>
    )
  }

  // Every componenet wrapped in <ProtectedRoute/> should ensure the user is logged in before loading the page
  const ProtectedRoute = ({children}) => {
    if (!currentUser) {
      return <Navigate to="/login"/>
    }
    return children
  }

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<ProtectedRoute> <Layout /> </ProtectedRoute>}>
          <Route index element={<HomePage />} />
          <Route path="profile/:id" element={<ProfilePage />} />
        </Route>

        <Route path="/login" element={<LoginPage/>} />
        <Route path="/register" element={<RegisterPage/>} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
