package com.example.grifmood;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TestDescriptionServiceGet {
    @GET(BuildConfig.MYURL+"/descriptionTest/{id_test}/{points}")
    Call<ResultDescriptionResponce> getDescription(@Path("id_test")Integer value, @Path("points")Integer value1);
}
