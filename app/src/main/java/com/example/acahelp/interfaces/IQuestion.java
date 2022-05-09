package com.example.acahelp.interfaces;

import com.example.acahelp.models.Question;
import com.example.acahelp.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IQuestion {

    @GET("questions/getQuestions")
    Call<ArrayList<Question>> getQuestions();

    @POST("questions/createNewQuestion")
    Call<Question> postNewQuestion(@Body Question question);
}
