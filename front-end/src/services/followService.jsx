
const API_URL = "http://localhost:8080/follows/"

export async function getFollowers(userId) {
  const token = localStorage.getItem("token")

  const res = await fetch(`${API_URL}/followers/${userId}`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!res.ok) {
    throw new Error("Failed to fetch followers");
  }

  return await res.json(); // {[userProfileDTO,...]}
}

export async function getFollowees(userId) {
  const token = localStorage.getItem("token")

  const res = await fetch(`${API_URL}/following/${userId}`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!res.ok) {
    throw new Error("Failed to fetch followees");
  }

  return await res.json(); // {[{id, displayName, bio, profileImageUrl},...]}
}

export async function follow(userId) {
  const token = localStorage.getItem("token")

  const res = await fetch(`${API_URL}/${userId}`, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!res.ok) {
    throw new Error(`Failed to follow userId: ${userId}`);
  }

  return await res
}

export async function unfollow(userId) {
  const token = localStorage.getItem("token")

  const res = await fetch(`${API_URL}/${userId}`, {
    method: "DELETE",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!res.ok) {
    throw new Error(`Failed to unfollow userId: ${userId}`);
  }
  return await res
}

