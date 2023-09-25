package com.example.grifmood;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ResultTestServicePost {
    @POST(BuildConfig.MYURL+"/resultTest/create")
    Call<ResultTestResponcePost> postResult (@Body ResultTestResponcePost result);
}
