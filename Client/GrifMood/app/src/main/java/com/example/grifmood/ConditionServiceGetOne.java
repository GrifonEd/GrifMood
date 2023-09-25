package com.example.grifmood;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ConditionServiceGetOne {
    @GET(BuildConfig.MYURL+"/condition/get/{id}")
    Call<ConditionResponse> getCondition(@Path("id")Integer value);
}
