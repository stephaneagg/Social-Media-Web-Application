const API_URL = "http://localhost:8080/"

export async function register(username, email, password) {
    const res = await fetch (`${API_URL}api/v1/auth/register`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({username, email, password})
    })

    if (!res.ok) {
        throw new Error("Registration failed");
    }

    return await res.json(); // {access_token}
}

export async function login(login,password) {
    const res = await fetch(`${API_URL}api/v1/auth/authenticate`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ login, password }),
    });

    if (!res.ok) {
        throw new Error("Login Failed");
    }

    return await res.json(); // {access_token}
}

export async function getCurrentUser(token) {
    const res = await fetch(`${API_URL}users/me`, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });

    if (!res.ok) {
        return new Error("Failed to fetch user");
    }

    return res.json(); // {id, username, email, displayName, profileImageUrl}
}