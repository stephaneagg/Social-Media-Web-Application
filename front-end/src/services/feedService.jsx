const API_URL = "http://localhost:8080/feed"

export async function getFeed(){
  const token = localStorage.getItem("token");

  const res = await fetch(`${API_URL}`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`
    }
  });

  if (!res.ok) {
    const data = await res.text().then(t => {
      try { return JSON.parse(t); } catch { return t; }
    });

    throw new Error(data.message || data);
  }

  return await res.json() // [ {{id, authorId, authorName, content, userProfileImageUrl, imageUrl, createdAt}}, ... ]
}