package com.example.grifmood;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ConditionDelete {
    @DELETE(BuildConfig.MYURL+"/condition/delete/{id}")
    Call<ConditionResponse> deleteConditions(@Path("id")Integer value);
}
