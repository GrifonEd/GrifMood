package com.example.grifmood;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ConditionServicePut {
    @PUT(BuildConfig.MYURL+"/condition/update")
    Call<ConditionResponsePost> putConditions(@Body ConditionResponsePost conditionResponse);
}
