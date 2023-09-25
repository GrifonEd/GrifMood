package com.example.grifmood;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AllConnectionServiceGet {
    @GET(BuildConfig.MYURL+"/connection/profile/{profile_id}")
    Call<List<ConnectionResponse>> AllConnectionServiceGet(@Path("profile_id")Integer value);
}
