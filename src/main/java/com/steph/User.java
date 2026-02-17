package com.steph;

import java.util.Objects;

public class User {

    private Integer id;
    private String name;
    private String passw;
    private String email;


    public User() {
    }

    public User(Integer id, String name, String passw, String email) {
        this.id = id;
        this.name = name;
        this.passw = passw;
        this.email = email;
    }

    public Integer getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getPassw() {
        return this.passw;
    }

    public String getEmail() {
        return this.email;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(passw, user.passw) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, passw, email);
    }

}
