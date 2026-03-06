package com.steph.auth;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

public class AuthenticationResponse {

    @JsonProperty("access_token")
    private String accessToken;

    // Constructors //
    public AuthenticationResponse() {}

    public AuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    // Getters and Setters

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
