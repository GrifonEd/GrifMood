package com.example.grifmood;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Authorization {
    @GET(BuildConfig.MYURL+"/auth/users/me/")
    Call<AuthorizationResponse> getloginUser(@Header("Authorization") String header);
}
