package com.example.grifmood;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ConditionAllServiceGet {
    @GET(BuildConfig.MYURL+"/condition/profileID/{id}")
    Call<List<ConditionResponse>> getConditions(@Path("id")Integer value);
}
