package com.jondhc.cloudoclock;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestClient {
    @GET(".")
    Call<String> getData();
}
