import { useState, useContext } from "react";

import { AuthContext } from "../../context/authContext.jsx";
import { editUser } from "../../services/userService";
import { uploadUserPhoto } from "../../services/uploadService";

import "./editUserModal.scss";

export default function EditUserModal({user, onClose, onUpdate }) {

  const [displayName, setDisplayName] = useState(user.displayName);
  const [bio, setBio] = useState(user.bio);
  const [profilePic, setProfilePic] = useState(null);
  const [coverPic, setCoverPic] = useState(null);
  const [profilePicPreview, setProfilePicPreview] = useState(null);
  const [coverPicPreview, setCoverPicPreview] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const { refreshCurrentUser } = useContext(AuthContext);

  function handleImageChange(e, type) {
    const file = e.target.files[0];
    if (file) {
      if (type === "profile") {
        setProfilePic(file);
        setProfilePicPreview(URL.createObjectURL(file));
      } else {
        setCoverPic(file);
        setCoverPicPreview(URL.createObjectURL(file));
      }
    }
  }

  async function handleSubmit() {
    setLoading(true);
    setError(null);
    try {
      let newDisplayName = null
      let newBio = null
      let newProfileImageUrl = null;
      let newCoverImageUrl = null;

      if (displayName !== user.displayName) {
        newDisplayName = displayName;
      }

      if (bio !== user.bio) {
        newBio = bio;
      }

      if (profilePic) {
        const uploadedProfilePic = await uploadUserPhoto(profilePic);
        newProfileImageUrl = uploadedProfilePic.imageUrl;
      }

      if (coverPic) {
        const uploadedCoverPic = await uploadUserPhoto(coverPic);
        newCoverImageUrl = uploadedCoverPic.imageUrl;
      }

      await editUser(
        {
          userId: user.id,
          displayName: newDisplayName,
          bio: newBio,
          profileImageUrl: newProfileImageUrl,
          coverImageUrl: newCoverImageUrl
        }
      );
      await refreshCurrentUser();
      onUpdate();
      onClose();
    } catch (err) {
      console.error(err);
      setError("Failed to update profile. Please try again.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="modal-overlay">
      <div className="modal">

        <h2>Edit Profile</h2>

        <p>Name</p>
        <input // display name
          type="text"
          value={displayName}
          onChange={(e) => setDisplayName(e.target.value)}
          disabled={loading}
        />

        <p>Bio</p>
        <textarea // bio
          value={bio}
          onChange={(e) => setBio(e.target.value)}
          disabled={loading}
        />

        <p>Profile Picture</p>
        {profilePicPreview && <img src={profilePicPreview} alt="profile picture preview" />}
        <input // profile pic
          type="file"
          accept="image/*"
          onChange={(e) => handleImageChange(e, "profile")}
        />

        <p>Cover Image</p>
        {coverPicPreview && <img src={coverPicPreview} alt="cover image preview" />}
        <input // cover image
          type="file"
          accept="image/*"
          onChange={(e) => handleImageChange(e, "cover")}
        />

        {error && <p className="error">{error}</p>}

        <div className="modal-actions">
          <button
            onClick={onClose}
            disabled={loading}
          >
            Cancel
          </button>
          <button
            onClick={handleSubmit}
            disabled={loading}
          >
            {loading?"Saving...": "Save"}
          </button>
        </div>
      </div>
    </div>
  )
}