package com.example.grifmood;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface QuestionServiceGet {
    @GET(BuildConfig.MYURL+"/question/{id_test}/{number_question}")
    Call<QuestionResponce> getQuestion(@Path("id_test")Integer value,@Path("number_question")Integer value1);
}
