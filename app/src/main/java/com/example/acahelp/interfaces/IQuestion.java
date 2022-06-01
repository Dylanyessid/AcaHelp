package com.example.acahelp.interfaces;

import com.example.acahelp.models.Question;
import com.example.acahelp.models.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface IQuestion {

    @GET("questions/getQuestions")
    Call<ArrayList<Question>> getQuestions();

    @GET("questions/getPrivateQuestions/{user}")
    Call<ArrayList<Question>> getPrivateQuestions(@Path("user") String user);


    @POST("questions/createNewQuestion")
    Call<Question> postNewQuestion(@Body Question question);

    @GET("questions/getUserQuestions/{user}")
    Call<ArrayList<Question>> getUserQuestions(@Path("user") String user);

    @GET("questions/getUserPrivateQuestions/{user}")
    Call<ArrayList<Question>> getUserPremiumQuestions(@Path("user") String user);
}
