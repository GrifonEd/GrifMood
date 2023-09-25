package com.example.grifmood;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserServicePost {
    @POST(BuildConfig.MYURL+"/auth/users/")
    Call<UserResponse> createUser(@Body UserResponse user);
}
