package com.example.grifmood;

public class ProfileResponse {
    private  int id;
    private int age;
    private String first_name;
    private String second_name;
    private String sex;
    private  String uuid;

    public ProfileResponse(int age, String first_name, String second_name, String sex,String uuid) {
        this.age = age;
        this.first_name = first_name;
        this.second_name = second_name;
        this.sex = sex;
        this.uuid=uuid;
    }

    public ProfileResponse() {
    }

    @Override
    public String toString() {
        return "ProfileResponse{" +
                "age=" + age +
                ", first_name='" + first_name + '\'' +
                ", second_name='" + second_name + '\'' +
                ", sex='" + sex + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
}
