package com.example.getrequesturl;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AgifyApi {
    @GET("/")
    Call<AgeResponse> getAge(@Query("name") String name);
}
