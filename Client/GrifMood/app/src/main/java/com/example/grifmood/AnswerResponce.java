package com.example.grifmood;

public class AnswerResponce {
    Integer id;
    String answer;
    Float points;

    public AnswerResponce(Integer id, String answer, Float points) {
        this.id = id;
        this.answer = answer;
        this.points = points;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id=\": "+ id +
                ", \"answer\": \"" + answer + '\"' +
                "\"points=\": "+ points +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Float getPoints() {
        return points;
    }

    public void setPoints(Float points) {
        this.points = points;
    }
}
