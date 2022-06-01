package com.example.acahelp.interfaces;

import com.example.acahelp.models.Answer;
import com.example.acahelp.models.Count;
import com.example.acahelp.models.Question;
import com.example.acahelp.models.Score;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IAnswer {
    @GET("answers/getAnswers/{id}")
    Call<ArrayList<Answer>> getAnswers(@Path("id") String _id);

    @GET("answers/getScoredAnswers/{user}/{question}")
    Call<ArrayList<Score>> getScoredAnswers(@Path("user") String user, @Path("question") String question);

    @POST("answers/createAnswer")
    Call<Answer> postAnswer(@Body Answer answer);

    @POST("answers/createAnswerForPrivateQuestion/{user}/{question}")
    Call<Answer> postAnswerOfPremiumQuestion(@Path("user") String user, @Path("question") String question, @Body Answer answer);

    @GET("answers/getUserAnswers/{user}")
    Call<ArrayList<Answer>> getUserAnswers(@Path("user") String user);

    @GET("answers/getScore/{answer}")
    Call<Count> getScore(@Path("answer") String answer);

    @DELETE("answers/removeScore/{answerId}/{user}")
    Call<Score> removeScore(@Path("answerId") String answer, @Path("user") String user);

    @PUT("answers/qualify/{answerId}")
    Call<Answer> qualifyAnswer(@Path("answerId") String answer, @Body Score scoreData);


}
