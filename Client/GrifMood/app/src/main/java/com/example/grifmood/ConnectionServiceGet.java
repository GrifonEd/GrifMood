package com.example.grifmood;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ConnectionServiceGet {
    @GET(BuildConfig.MYURL+"/connection/{profile_id}")
    Call<ConnectionResponse> ConnectionServiceGet(@Path("profile_id")Integer value);
}
