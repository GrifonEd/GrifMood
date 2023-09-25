package com.example.grifmood;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ConditionServicePost {
    @POST(BuildConfig.MYURL+"/condition/create")
    Call<ConditionResponsePost> PostConditions(@Body ConditionResponsePost condition);
}
