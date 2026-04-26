package com.steph.auth;

public class AuthenticationRequest {

    private String login;

    private String password;

    // Constructors //

    public AuthenticationRequest(){}

    public AuthenticationRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    // Getters and Setters //

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
