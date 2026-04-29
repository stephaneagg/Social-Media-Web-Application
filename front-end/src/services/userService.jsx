const API_URL = "http://localhost:8080/users/"

export async function getUser(userId) {
  const token = localStorage.getItem("token")

  const res = await fetch(`${API_URL}/${userId}`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!res.ok) {
    throw new Error("Failed to create user");
  }

  return await res.json(); // {id, displayName, bio, profileImageUrl}
}

export async function editUser(userId, displayName, bio, profileImageUrl) {
  const token = localStorage.getItem("token")

  const body = {};

  if (displayName != null) body.displayName = displayName;
  if (bio != null) body.bio = bio;
  if (profileImageUrl != null) body.profileImageUrl = profileImageUrl;

  const res = await fetch(`${API_URL}/${userId}`, {
    method: "PUT",
    headers: {
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify(body),
  });

  if (!res.ok) {
    throw new Error("Failed to create user");
  }

  return await res.json(); // {id, displayName, bio, profileImageUrl}
}

// Think about if this function is even needed
export async function deleteUser(userId) {s
  const token = localStorage.getItem("token")

  const res = await fetch(`${API_URL}/${userId}`, {
    method: "DELETE",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!res.ok) {
    throw new Error("Failed to create user");
  }

  return await res
}
