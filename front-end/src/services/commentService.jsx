const API_URL = "http://localhost:8080/comments"



export async function getComments(postId) {
  const token = localStorage.getItem("token");

  const res = await fetch(`${API_URL}/post/${postId}`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`
    },
  });

  if (!res.ok) {
    const data = await res.text().then(t => {
      try { return JSON.parse(t); } catch { return t; }
    });

    throw new Error(data.message || data);
  }

  return await res.json() // [ {id, userId, postId, content, createdAt} , ... ]
}


export async function createComment(postId, content){
  const token = localStorage.getItem("token");

  const res = await fetch(`${API_URL}/post/${postId}`, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-type": "application/json"
    },
    body: JSON.stringify({ content })
  });

  if (!res.ok) {
    const data = await res.text().then(t => {
      try { return JSON.parse(t); } catch { return t; }
    });

    throw new Error(data.message || data);
  }

  return await res.json() // {id, userId, postId, content, createdAt}
}


export async function editComment(commentId, content){
  const token = localStorage.getItem("token");

  const res = await fetch(`${API_URL}/${commentId}`, {
    method: "PUT",
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-type": "application/json"
    },
    body: JSON.stringify({ content })
  });

  if (!res.ok) {
    const data = await res.text().then(t => {
      try { return JSON.parse(t); } catch { return t; }
    });

    throw new Error(data.message || data);
  }

  return await res.json() // {id, userId, postId, content, createdAt}
}

export async function deleteComment(commentId) {
  const token = localStorage.getItem("token");

  const res = await fetch(`${API_URL}/${commentId}`, {
    method: "DELETE",
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

  return res.status // 204s
}