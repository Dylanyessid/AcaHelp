package com.example.acahelp.interfaces;

import com.example.acahelp.models.Question;
import com.example.acahelp.models.Request;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IRequest {
    @POST("requests/createNewRequest")
    Call<Request> createRequest(@Body Request request);

    @GET("requests/getRequest/{user}")
    Call<Request> getRequest(@Path("user") String user);
}
