import { useState } from "react";
import { editPost } from "../../services/postService";
import { uploadPostPhoto } from "../../services/uploadService";

import "./editPostModal.scss";

export default function EditPostModal({ post, onClose, onUpdate }) {
  const [text, setText] = useState(post.content);
  const [image, setImage] = useState(null);
  const [preview, setPreview] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  function handleImageChange(e) {
    const file = e.target.files[0];
    if (file) {
      setImage(file);
      setPreview(URL.createObjectURL(file));
    }
  }

  async function handleSubmit() {
    setLoading(true);
    setError(null);
    try {
      let newImageUrl = null;

      if (image) {
        const uploaded = await uploadPostPhoto(image);
        newImageUrl = uploaded.imageUrl;
      }

      await editPost(post.id, text, newImageUrl);
      onUpdate();
      onClose();
    } catch (err) {
      console.error(err);
      setError("Failed to update post. Please try again.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="modal-overlay">
      <div className="modal">
        <h2>Edit Post</h2>

        <textarea
          value={text}
          onChange={(e) => setText(e.target.value)}
          disabled={loading}
        />

        {preview ? <img src={preview} alt="post preview" /> : null}

        <input
          type="file"
          accept="image/*"
          onChange={handleImageChange}
          disabled={loading}
        />

        {error && <p className="error">{error}</p>}

        <div className="modal-actions">
          <button onClick={onClose} disabled={loading}>Cancel</button>
          <button onClick={handleSubmit} disabled={loading}>
            {loading ? "Saving..." : "Save"}
          </button>
        </div>
      </div>
    </div>
  );
}