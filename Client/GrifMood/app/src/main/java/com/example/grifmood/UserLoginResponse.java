package com.example.grifmood;

public class UserLoginResponse {
    private String username;
    private String password;
    private String refresh;
    private String access;

    public UserLoginResponse(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    @Override
    public String toString() {
        return "{" +
                ", \"username\": \"" + username + '\"' +
                ", \"password\": \"" + password + '\"' +
                ", \"refresh\": \"" + refresh + '\"' +
                ", \"access\": \"" + access + '\"' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
