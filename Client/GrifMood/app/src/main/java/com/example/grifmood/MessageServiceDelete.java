package com.example.grifmood;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Path;

public interface MessageServiceDelete {
    @DELETE(BuildConfig.MYURL+"/message/get/{id_message}")
    Call<MessageResponse>deleteMessage(@Path("id_message")Integer value);
}
