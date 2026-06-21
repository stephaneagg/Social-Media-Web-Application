const API_URL = "http://localhost:8080/search"

export async function search(query) {
  const token = localStorage.getItem("token");

  const res = await fetch(`${API_URL}?query=${query}`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!res.ok) {
    const data = await res.text().then(t => {
      try { return JSON.parse(t);} catch {return t;}
    });

    throw new Error(data.message || data);
  }

  // {
  //  users: [ {id, displayName, bio, profileImageUrl, coverImageUrl}, ...],
  //  posts: [ {id, authorId, authorName, content, userProfileImageUrl, imageUrl, commentCount, createdAt}, ...]
  // }
  return await res.json()
}