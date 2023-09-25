package com.example.grifmood;

import android.util.Log;

public class AuthorizationResponse {
    private String Authorization;
    private Integer id;

    public AuthorizationResponse(String authorization) {
        Authorization = "Bearer "+authorization;
        Log.d("Norm111111",Authorization);
    }

    @Override
    public String toString() {
        return
                "Authorization: Bearer " + Authorization;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthorization() {
        return Authorization;
    }

    public void setAuthorization(String authorization) {
        Authorization = authorization;
    }
}
