const API_URL = "http://localhost:8080/users/"

export async function getUser(userId) {
  const token = localStorage.getItem("token")

  const res = await fetch(`${API_URL}${userId}`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!res.ok) {
    throw new Error("Failed to get user");
  }

  return await res.json(); // {id, displayName, bio, profileImageUrl, coverImageUrl}
}

export async function editUser({userId, displayName, bio, profileImageUrl, coverImageUrl}) {
  const token = localStorage.getItem("token")

  const body = {};

  if (displayName != null) body.displayName = displayName;
  if (bio != null) body.bio = bio;
  if (profileImageUrl != null) body.profileImageUrl = profileImageUrl;
  if (coverImageUrl != null) body.coverImageUrl = coverImageUrl;

  const res = await fetch(`${API_URL}${userId}`, {
    method: "PUT",
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body),
  });

  if (!res.ok) {
    const text = await res.text();
    let message = text;

    try {
      const json = JSON.parse(text);
      message = json.message;
    } catch {}
    throw new Error(message)
  }

  return await res.json(); // {id, displayName, bio, profileImageUrl, coverImageUrl}
}

export async function changePassword({userId, currentPassword, newPassword, confirmPassword}) {
  const token = localStorage.getItem("token");

  const body = {currentPassword: currentPassword,
                newPassword: newPassword,
                confirmPassword: confirmPassword};

  const res = await fetch(`${API_URL}${userId}/password`, {
    method: "PUT",
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body)
  });

  if (!res.ok) {
    const text = await res.text();
    let message = text;

    try {
      const json = JSON.parse(text);
      message = json.message
    } catch {

    }
    throw new Error(message)
  }
  return await res;
}



export async function deleteUser(userId, password) {
  const token = localStorage.getItem("token")

  const body = {password: password}

  const res = await fetch(`${API_URL}${userId}`, {
    method: "DELETE",
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-type": "application/json",
    },
    body: JSON.stringify(body)
  });

  if (!res.ok) {
    const text = await res.text();
    let message = text;

    try {
      const json = JSON.parse(text);
      message = json.message;
    } catch {
    }
    throw new Error(message);
  }

  return await res
}
