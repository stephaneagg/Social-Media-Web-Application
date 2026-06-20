
import { useState, useContext, useCallback, useEffect } from "react"
import { useParams } from "react-router-dom";
import { AuthContext } from "../../context/authContext.jsx";

import { getUser } from "../../services/userService"

import EditUserModal from "../../components/profile/EditUserModal"
import ChangePasswordForm from "../../components/forms/ChangePasswordForm"
import DeleteUserModal from "../../components/profile/DeleteUserModal"

import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import ExpandLessIcon from '@mui/icons-material/ExpandLess';

import "./settingsPage.scss"

export default function SettingsPage() {

  const { id } = useParams();
  const { currentUser } = useContext(AuthContext)

  const [user, setUser] = useState(null);
  const [expandProfileInfo, setExpandProfileInfo] = useState(false);
  const [profileInfoMenu, setProfileInfoMenu] = useState(false);
  const [expandPassword, setExpandPassword] = useState(false);
  const [expandDeleteUser, setExpandDeleteUser] = useState(false);
  const [deletingUser, setDeletingUser] = useState(false);


  const loadUser = useCallback(async () => {
    const data = await getUser(id);
    setUser(data);
  }, [id]);

  useEffect( () => {
    loadUser()
  }, [loadUser]);

  function handleSubmit() {

  }


  return (
    <div className="settings">


      <h3 onClick={() => setExpandProfileInfo((prev) => !prev)}>
        {expandProfileInfo ?
          <>Profile Info <ExpandLessIcon /></> :
          <>Profile Info <ExpandMoreIcon /></>
        }
      </h3>
      {expandProfileInfo && (
        <div className="profileInfo">
          <p onClick={() => setProfileInfoMenu((prev) => !prev)}>Edit Profile Info</p>
          {profileInfoMenu && (
            <EditUserModal user={user} onClose={() => setProfileInfoMenu(false)} onUpdate={() => loadUser()} />
          )}
        </div>
      )}


      <h3 onClick={() => setExpandPassword((prev) => !prev)}>
        {expandPassword ?
          <>Change Password <ExpandLessIcon /></> :
          <>Change Password <ExpandMoreIcon /></>
        }
      </h3>


      {expandPassword && (
        <div className="password">
          <ChangePasswordForm userId={id}/>
        </div>
      )}


      <h3 onClick={() => setExpandDeleteUser((prev) => !prev)}>
        {expandDeleteUser ?
          <>Delete User <ExpandLessIcon /></> :
          <>Delete User <ExpandMoreIcon /></>
        }
      </h3>
      {expandDeleteUser && (
        <div className="deleteUser">
          <button onClick={() => setDeletingUser(true)}>Delete User</button>
        </div>
      )}
      {deletingUser && <DeleteUserModal userId={id} onClose={() => setDeletingUser(false)}/>}

    </div>
  )
}