package com.example.grifmood;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MessageServicePost {
    @POST(BuildConfig.MYURL+"/message/create")
    Call<MessageResponsePost> MessageServicePost(@Body MessageResponsePost result);
}
