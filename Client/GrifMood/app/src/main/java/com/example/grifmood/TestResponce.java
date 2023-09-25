package com.example.grifmood;

public class TestResponce {
    int id_test ;
    String test_name;
    String description;
    int test_completion_time;
    String image;
    int count_question;

    public TestResponce(int id_test, String test_name, String description, int test_completion_time, String image, int count_question) {
        this.id_test = id_test;
        this.test_name = test_name;
        this.description = description;
        this.test_completion_time = test_completion_time;
        this.image = image;
        this.count_question = count_question;
    }


    @Override
    public String toString() {
        return "{" +
                "\"id_test=\": "+ id_test +
                ", \"test_name\": \"" + test_name + '\"' +
                ", \"description\": \"" + description + '\"' +
                "\"test_completion_time=\": "+ test_completion_time +
                "\"image=\": "+ image +
                "\"count_question=\": "+ count_question +
                '}';
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCount_question() {
        return count_question;
    }

    public void setCount_question(int count_question) {
        this.count_question = count_question;
    }

    public int getId_test() {
        return id_test;
    }

    public void setId_test(int id_test) {
        this.id_test = id_test;
    }

    public String getTest_name() {
        return test_name;
    }

    public void setTest_name(String test_name) {
        this.test_name = test_name;
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
}
