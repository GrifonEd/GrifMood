package com.example.grifmood;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AnalysisServiceGet {
    @GET(BuildConfig.MYURL+"/analysis/{id_user}/{case}")
    Call<AnalysisResponce> getAnalysis(@Path("id_user")Integer value, @Path("case")String value1);
}
