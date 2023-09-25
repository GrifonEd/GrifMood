package com.example.grifmood;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ConditionAllProfileServiceGet {
    @GET(BuildConfig.MYURL+"/condition/all")
    Call<List<ConditionResponseAllProfile>> getAllProfileConditions();
}
