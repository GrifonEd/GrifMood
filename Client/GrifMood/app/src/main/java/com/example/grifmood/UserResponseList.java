package com.example.grifmood;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponseList {
    private String email;
    private int id;
    private String password;
    private String re_password;
    private List<String> username;
    private int age;
    private String first_name;
    private String second_name;
    private String sex;

    public UserResponseList(String email, int id, String password, String re_password, List<String> username, int age, String first_name, String second_name, String sex) {
        this.email = email;
        this.id = id;
        this.password = password;
        this.re_password = re_password;
        this.username = username;
        this.age = age;
        this.first_name = first_name;
        this.second_name = second_name;
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRe_password() {
        return re_password;
    }

    public void setRe_password(String re_password) {
        this.re_password = re_password;
    }

    public List<String> getUsername() {
        return username;
    }

    public void setUsername(List<String> username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @SerializedName("body")

    @Override
    public String toString() {
        return "{" +
                "\"id\": "+ id +
                ", \"email\": \"" + email + '\"' +
                ", \"password\": \"" + password + '\"' +
                ", \"age\": "+ age +
                ", \"re_password\": \"" + re_password + '\"' +
                ", \"username\": \"" + username + '\"' +
                ", \"first_name\": \"" + first_name + '\"' +
                ", \"second_name\": \"" + second_name + '\"' +
                ", \"sex\": \"" + sex + '\"' +
                '}';
    }

}
