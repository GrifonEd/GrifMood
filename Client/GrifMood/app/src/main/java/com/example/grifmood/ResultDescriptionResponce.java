package com.example.grifmood;

public class ResultDescriptionResponce {
    TestResponce test ;
    int pointsMin ;
    int pointsMax;
    String description;


    public ResultDescriptionResponce(TestResponce test, int pointsMin, int pointsMax, String description) {
        this.test = test;
        this.pointsMin = pointsMin;
        this.pointsMax = pointsMax;
        this.description = description;
    }

    @Override
    public String toString() {
        return "{" +
                "\"test=\": "+
                "{" +
                "\"id_test=\": "+ test.id_test +
                ", \"test_name\": \"" + test.test_name + '\"' +
                ", \"description\": \"" + test.description + '\"' +
                "\"test_completion_time=\": "+ test.test_completion_time +
                "\"image=\": "+ test.image +
                "\"count_question=\": "+ test.count_question +
                '}'+
                ", \"description\": \"" + description + '\"' +
                "\"pointsMin=\": "+ pointsMin +
                "\"pointsMax=\": "+ pointsMax +
                '}';
    }

    public TestResponce getTest() {
        return test;
    }

    public void setTest(TestResponce test) {
        this.test = test;
    }

    public int getPointsMin() {
        return pointsMin;
    }

    public void setPointsMin(int pointsMin) {
        this.pointsMin = pointsMin;
    }

    public int getPointsMax() {
        return pointsMax;
    }

    public void setPointsMax(int pointsMax) {
        this.pointsMax = pointsMax;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
