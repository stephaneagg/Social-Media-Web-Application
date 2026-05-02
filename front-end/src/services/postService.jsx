const API_URL = "http://localhost:8080/posts"

export async function getPost(postId) {
  const token = localStorage.getItem("token");

  const res = await fetch(`${API_URL}/${postId}`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!res.ok) {
  const data = await res.text().then(t => {
    try { return JSON.parse(t); } catch { return t; }
  });

  throw new Error(data.message || data);
}

  return await res.json(); // {id, authorId, authorName, content, userProfileImageUrl, imageUrl, createdAt}
}

export async function getUsersPosts(userId) {
  const token = localStorage.getItem("token");

  const res = await fetch(`${API_URL}/user/${userId}`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!res.ok) {
    const data = await res.text().then(t => {
      try { return JSON.parse(t); } catch { return t; }
    });

    throw new Error(data.message || data);
  }

  return await res.json(); // [{id, authorId, authorName, content, userProfileImageUrl, imageUrl, createdAt}, ...]
}

export async function createPost(content, imageUrl) {
  const token = localStorage.getItem("token");

  const res = await fetch(`${API_URL}`, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-type": "application/json",
    },
    body: JSON.stringify({ content, imageUrl })
  })

  if (!res.ok) {
    const data = await res.text().then(t => {
      try { return JSON.parse(t); } catch { return t; }
    });

    throw new Error(data.message || data);
  }

  return await res.json(); // {id, authorId, authorName, content, userProfileImageUrl, imageUrl, createdAt}
}

export async function editPost(postId, content, imageUrl) {
  const token = localStorage.getItem("token");

  const body = {}

  if (content != null) body.content = content;
  if (imageUrl != null) body.imageUrl = imageUrl;

  const res = await fetch(`${API_URL}/${postId}`, {
    method: "PUT",
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-type": "application/json",
    },
    body: JSON.stringify(body),
  });

  if (!res.ok) {
    const data = await res.text().then(t => {
      try { return JSON.parse(t); } catch { return t; }
    });

    throw new Error(data.message || data);
  }

  return await res.json(); // {id, authorId, authorName, content, userProfileImageUrl, imageUrl, createdAt}
}

export async function deletePost(postId) {
  const token = localStorage.getItem("token");

  const res = await fetch(`${API_URL}/${postId}`, {
    method: "DELETE",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!res.ok) {
    const data = await res.text().then(t => {
      try { return JSON.parse(t); } catch { return t; }
    });

    throw new Error(data.message || data);
  }

  return res.status; // 204
}