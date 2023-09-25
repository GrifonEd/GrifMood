package com.example.grifmood;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MessagesServiceGet {
    @GET(BuildConfig.MYURL+"/message/profile/{id_receive}")
    Call<List<MessageResponse>> getMessages(@Path("id_receive")Integer value);
}
