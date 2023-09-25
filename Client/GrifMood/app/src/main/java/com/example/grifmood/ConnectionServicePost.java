package com.example.grifmood;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ConnectionServicePost {
    @POST(BuildConfig.MYURL+"/connection/{profile_id}/{uuid}")
    Call<ConnectionResponse> ConnectionServicePost(@Path("profile_id")Integer value,@Path("uuid")String value1);
}
