package com.example.grifmood;

public class QuestionResponce {
    int id_question ;
    int number ;
    String question;

    public QuestionResponce(int id_question, int number, String question) {
        this.id_question = id_question;
        this.number = number;
        this.question = question;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id_question=\": "+ id_question +
                ", \"question\": \"" + question + '\"' +
                "\"number=\": "+ number +
                '}';
    }

    public int getId_question() {
        return id_question;
    }

    public void setId_question(int id_question) {
        this.id_question = id_question;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
