package com.example.grifmood;

import java.util.List;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TestsServiceGetOne {
    @GET(BuildConfig.MYURL+"/test/{id_test}")
    Call<TestResponce> getTest(@Path("id_test")Integer value);
}
