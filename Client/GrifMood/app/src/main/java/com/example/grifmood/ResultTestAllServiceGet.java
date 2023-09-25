package com.example.grifmood;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ResultTestAllServiceGet {
    @GET(BuildConfig.MYURL+"/resultTest/all/{id_test}/{id_user}")
    Call<List<ResultTestResponce>> getAllResult (@Path("id_test")Integer value, @Path("id_user")Integer value1);
}
