package com.example.grifmood;

public class MessageResponsePost {
    int id ;
    private int profileSend;
    private int profileReceive ;
    private String message;
    private String date_created;

    public MessageResponsePost() {
    }

    public MessageResponsePost(int id, int profileSend, int profileReceive, String message, String date_created) {
        this.id = id;
        this.profileSend = profileSend;
        this.profileReceive = profileReceive;
        this.message = message;
        this.date_created = date_created;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProfileSend() {
        return profileSend;
    }

    public void setProfileSend(int profileSend) {
        this.profileSend = profileSend;
    }

    public int getProfileReceive() {
        return profileReceive;
    }

    public void setProfileReceive(int profileReceive) {
        this.profileReceive = profileReceive;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
