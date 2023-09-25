package com.example.grifmood;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ConditionServiceGet {
    @GET(BuildConfig.MYURL+"/condition/profile/{id}")
    Call<List<ConditionResponse>> getConditions(@Path("id")Integer value);
}
