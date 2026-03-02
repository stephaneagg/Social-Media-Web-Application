package com.steph.auth;


import com.steph.user.Role;

public class RegisterRequest {

    private String username;
    private String email;
    private String password;
    private Role role;

    // Constructors //

    public RegisterRequest() {}

    public RegisterRequest(String username, String email, String password, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;

    }

    // Getters and Setters //

    public String getUsername() {
        return username;
    }

    public void setUsername(String firstName) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
