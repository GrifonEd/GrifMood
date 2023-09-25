package com.example.grifmood;

public class ResultTestResponce {
    Integer id;
    TestResponce test;
    String description;
    int test_completion_time;
    int score;
    String date;

    public ResultTestResponce(Integer id, TestResponce test, String description, int test_completion_time, int score, String date) {
        this.id = id;
        this.test = test;
        this.description = description;
        this.test_completion_time = test_completion_time;
        this.score = score;
        this.date=date;
    }
    public ResultTestResponce(Integer id,  String description, int test_completion_time, int score,String date) {
        this.id = id;
        this.description = description;
        this.test_completion_time = test_completion_time;
        this.score = score;
        this.date=date;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id=\": "+ id +
                ", \"description\": \"" + description + '\"' +
                "\"test_completion_time=\": "+ test_completion_time +
                "\"score=\": "+ score +
                ", \"date\": \"" + date + '\"' +
                "\"test=\": "+
                "{" +
                "\"id_test=\": "+ test.id_test +
                ", \"test_name\": \"" + test.test_name + '\"' +
                ", \"description\": \"" + test.description + '\"' +
                "\"test_completion_time=\": "+ test.test_completion_time +
                "\"image=\": "+ test.image +
                "\"count_question=\": "+ test.count_question +
                '}'+

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

    public TestResponce getTest() {
        return test;
    }

    public void setTest(TestResponce test) {
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
