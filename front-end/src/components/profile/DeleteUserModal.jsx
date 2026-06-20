import { useState } from "react"
import { deleteUser } from "../../services/userService"

import "./deleteUserModal.scss"

export default function DeleteUserModal( { userId, onClose} ) {

  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);

    if (!password) {
      setError("Password is required");
      return;
    }

    try {
      setLoading(true);
      await deleteUser(userId, password);
      // Account deleted — log the user out and redirect
      localStorage.removeItem("token");
      localStorage.removeItem("user");
      window.location.href = "/login";
    } catch (err) {
      console.error(err);
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal">
        <h2>Delete Account</h2>

        <p className="warning">
          This action is permanent and cannot be undone. All your posts, comments, and data will be deleted.
        </p>

        <form onSubmit={handleSubmit}>
          <input
            type="password"
            placeholder="Enter your password to confirm"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            disabled={loading}
            autoFocus
          />

          {error && <p className="error">{error}</p>}

          <div className="modal-actions">
            <button type="button" onClick={onClose} disabled={loading}>
              Cancel
            </button>
            <button type="submit" className="deleteConfirm" disabled={loading}>
              {loading ? "Deleting..." : "Delete Account"}
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}