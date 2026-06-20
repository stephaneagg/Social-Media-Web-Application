package com.steph.user.DTOs;

public class DeleteUserDTO {
    private String password;

    public DeleteUserDTO() {
    }

    public DeleteUserDTO(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
