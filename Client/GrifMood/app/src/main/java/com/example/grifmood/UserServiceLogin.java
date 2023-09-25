package com.example.grifmood;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserServiceLogin {
    @POST(BuildConfig.MYURL+"/auth/jwt/create/")
    Call<UserLoginResponse> loginUser(@Body UserLoginResponse user);
}
