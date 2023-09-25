package com.example.grifmood;

public class MessageResponse {
    int id ;
    private ProfileResponse profileSend;
    private ProfileResponse profileReceive ;
    private String message;
    private String date_created;

    public MessageResponse() {
    }

    public MessageResponse(int id, ProfileResponse profileSend, ProfileResponse profileReceive, String message, String date_created) {
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

    public ProfileResponse getProfileSend() {
        return profileSend;
    }

    public void setProfileSend(ProfileResponse profileSend) {
        this.profileSend = profileSend;
    }

    public ProfileResponse getProfileReceive() {
        return profileReceive;
    }

    public void setProfileReceive(ProfileResponse profileReceive) {
        this.profileReceive = profileReceive;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
