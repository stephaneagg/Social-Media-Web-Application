
const API_URL = "http://localhost:8080/follows/"

export async function getFollowers(userId) {
  const token = localStorage.getItem()

  const res = await fetch(`${API_URL}/followers/${userId}`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!res.ok) {
    throw new Error("Failed to fetch followers");
  }
}

export async function getFollowees(userId) {
  const token = localStorage.getItem()

  const res = await fetch(`${API_URL}/following/${userId}`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!res.ok) {
    throw new Error("Failed to fetch followees");
  }
}

export async function follow(userId) {
  const token = localStorage.getItem()

  const res = await fetch(`${API_URL}/${userId}`, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!res.ok) {
    throw new Error(`Failed to follow userId: ${userId}`);
  }
}

export async function unfollow(userId) {
  const token = localStorage.getItem()

  const res = await fetch(`${API_URL}/${userId}`, {
    method: "DELETE",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!res.ok) {
    throw new Error(`Failed to unfollow userId: ${userId}`);
  }
}

