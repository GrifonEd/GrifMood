package com.example.grifmood;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TestsServiceGetAll {
    @GET(BuildConfig.MYURL+"/test/all")
    Call<List<TestResponce>> getTests();
}
