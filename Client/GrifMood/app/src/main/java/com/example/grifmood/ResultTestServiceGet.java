package com.example.grifmood;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ResultTestServiceGet {
    @GET(BuildConfig.MYURL+"/resultTest/{id_test}/{id_user}")
    Call<ResultTestResponce> getResult (@Path("id")Integer value,@Path("id_user")Integer value1);
}
