

import { useState } from "react"

import { changePassword } from "../../services/userService"

export default function ChangePasswordForm(props) {

  const [currentPassword, setCurrentPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confNewPassword, setConfNewPassword] = useState("");

  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setLoading(true);
    setSuccess(false);


    if (!currentPassword ||
        !newPassword ||
        !confNewPassword) {
          setError("All fields are required");
          return;
        }

    if (newPassword != confNewPassword) {
      setError("Passwords do not match");
      return;
    }

    if (newPassword === currentPassword) {
      setError("New password must be different from current password");
      return;
    }

    if (newPassword.length < 8) {
      setError("New password must be at least 8 characters");
      return;
    }

    try {
      await changePassword({userId: props.userId, currentPassword, newPassword, confNewPassword});
      setCurrentPassword("");
      setNewPassword("");
      setConfNewPassword("");
      setSuccess("Password updated successfully")
    } catch (err) {
      setError(err.message)
      setSuccess(false);
    } finally {
      setLoading(false);
    }

  }


  return (
    <form onSubmit={handleSubmit}>
      <input
        type="text"
        placeholder="Current Password"
        value={currentPassword}
        onChange={(e) => setCurrentPassword(e.target.value)}
      />

      <input
        type="text"
        placeholder="New Password"
        value={newPassword}
        onChange={(e) => setNewPassword(e.target.value)}
      />

      <input
        type="text"
        placeholder="Confirm New Password"
        value={confNewPassword}
        onChange={(e) => setConfNewPassword(e.target.value)}
      />

      <button type="submit">Submit</button>

      {error && <p style={{color:'#f0544f'}}>{error}</p>}
      {success && <p>{success}</p>}
    </form>
  )
}