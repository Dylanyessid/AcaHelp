package com.example.acahelp.interfaces;

import com.example.acahelp.models.Question;
import com.example.acahelp.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface userAPI {
    @POST("users/login")
    Call<User> login(@Body User user);

    @GET ("users/getName/{id}")
    Call<User> getUserName(@Path("id") String _id);

}
