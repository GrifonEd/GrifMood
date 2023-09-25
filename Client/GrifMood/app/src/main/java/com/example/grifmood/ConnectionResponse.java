package com.example.grifmood;

import android.util.Log;

public class ConnectionResponse {
    private ProfileResponse profileWatching;
    private ProfileResponse profileShare;
    private Integer id;

    public ConnectionResponse() {

    }

    public ProfileResponse getProfileWatching() {
        return profileWatching;
    }

    public void setProfileWatching(ProfileResponse profileWatching) {
        this.profileWatching = profileWatching;
    }

    public ProfileResponse getProfileShare() {
        return profileShare;
    }

    public void setProfileShare(ProfileResponse profileShare) {
        this.profileShare = profileShare;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
