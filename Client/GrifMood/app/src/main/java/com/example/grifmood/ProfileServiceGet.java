package com.example.grifmood;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProfileServiceGet {
    @GET(BuildConfig.MYURL+"/profile/{id}")
    Call<ProfileResponse> ProfileServiceGet(@Path("id")Integer value);
}
