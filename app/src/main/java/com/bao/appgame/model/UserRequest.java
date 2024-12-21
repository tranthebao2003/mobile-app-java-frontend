package com.bao.appgame.model;

//gửi đi
public class UserRequest {
    private String email;
    private String password;

    public UserRequest(String username, String password) {
        this.email = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

