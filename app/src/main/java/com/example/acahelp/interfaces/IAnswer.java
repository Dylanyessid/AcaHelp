package com.example.acahelp.interfaces;

import com.example.acahelp.models.Answer;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IAnswer {
    @GET("answers/getAnswers/{id}")
    Call<ArrayList<Answer>> getAnswers(@Path("id") String _id);
}
