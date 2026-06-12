import {useState} from "react";
import {createPost} from "../../services/postService";
import {uploadPostPhoto} from "../../services/uploadService";
import "./createPost.scss";


export default function CreatePost() {

  const [text, setText] = useState("");
  const [file, setFile] = useState(null);
  const [preview, setPreview] = useState(null);
  const [loading, setLoading] = useState(null);


  const removeImage = () => {
    setFile(null);
    setPreview(null);
  };

  const handleFileChange = (e) => {
    const selected = e.target.files[0];
    setFile(selected);

    if (selected) {
      setPreview(URL.createObjectURL(selected));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!text && !file) return;

    try {
      setLoading(true);

      let imageUrl = null;

      // STEP 1: upload image (if exists)
      if (file) {
        const uploadRes = await uploadPostPhoto(file);
        imageUrl = uploadRes.imageUrl;
      }

      // STEP 2: create post
      const newPost = await createPost({
        content: text,
        imageUrl
      });

      // reset
      setText("");
      setFile(null);
      setPreview(null);

    } finally {
      setLoading(false);
      window.location.reload()
    }
  };


  return (
    <div className="createPost">
      <form onSubmit={handleSubmit} >

        <div className="top">
          <input
            type="text"
            placeholder="What's on your mind?"
            value={text}
            onChange= { (e) => setText(e.target.value)}
          />
        </div>

        {preview ?
          <div className="preview">
            <img src={preview} alt="preview" />
            <button type="button" onClick={removeImage} className="remove">
              ✕
            </button>
          </div>
        :
          null
        }

        <div className="bottom">
          <label className="upload">
            Add Photo
            <input
              type="file"
              accept="image/*"
              hidden
              onChange={handleFileChange}
            />
          </label>

          <button type="submit" disabled={loading}>
            {loading ? "Posting...'" : "Post"}
          </button>

        </div>

      </form>
    </div>
  )
}