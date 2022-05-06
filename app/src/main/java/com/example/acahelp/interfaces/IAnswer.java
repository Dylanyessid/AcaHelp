package com.example.acahelp.interfaces;

import com.example.acahelp.models.Answer;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IAnswer {
    @GET("answers/getAnswers/{id}")
    Call<ArrayList<Answer>> getAnswers(@Path("id") String _id);

    @POST("answers/createAnswer")
    Call<Answer> postAnswer(@Body Answer answer);
}
