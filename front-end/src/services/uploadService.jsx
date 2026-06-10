const API_URL = "http://localhost:8080/upload/"

export async function uploadUserPhoto(file) {
  const token = localStorage.getItem("token")

  const formData = new FormData();
  formData.append("file", file);

  const res = await fetch(`${API_URL}user`, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${token}`,
    },
    body: formData
  });

  if (!res.ok) {
    throw new Error("Upload failed");
  }

  return await res.json(); // {imageUrl}
}

export async function uploadPostPhoto(file) {
  const token = localStorage.getItem("token")

  const formData = new FormData();
  formData.append("file", file);

  const res = await fetch(`${API_URL}post`, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${token}`,
    },
    body: formData
  });

  if (!res.ok) {
    throw new Error("Upload failed");
  }

  return await res.json(); // {imageUrl}
}