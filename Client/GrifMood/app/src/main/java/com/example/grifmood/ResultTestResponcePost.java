package com.example.grifmood;

public class ResultTestResponcePost {
    Integer id;
    int test;
    int profile;
    String description;
    int test_completion_time;
    int score;
    String date;

    public ResultTestResponcePost(Integer id, int test, String description, int test_completion_time, int score,int profile, String date) {
        this.id = id;
        this.test = test;
        this.description = description;
        this.test_completion_time = test_completion_time;
        this.score = score;
        this.profile=profile;
        this.date=date;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id=\": "+ id +
                "\"profile=\": "+ profile +
                ", \"description\": \"" + description + '\"' +
                "\"test_completion_time=\": "+ test_completion_time +
                "\"score=\": "+ score +
                "\"test=\": "+ test +
                ", \"date\": \"" + date + '\"' +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTest_completion_time() {
        return test_completion_time;
    }

    public void setTest_completion_time(int test_completion_time) {
        this.test_completion_time = test_completion_time;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
