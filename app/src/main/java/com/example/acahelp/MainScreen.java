package com.example.acahelp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.acahelp.interfaces.IQuestion;
import com.example.acahelp.models.Question;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainScreen extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Question> questions;
    Call<ArrayList<Question>> call;
    PostAdapter adapter;
    SharedPreferences preferences;
     String _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        preferences = getApplicationContext().getSharedPreferences( getString(R.string.sharedP), Context.MODE_PRIVATE);
        System.out.println("KEY A VER SI ESTA BIEN : " + preferences.getString(getString(R.string.sharedP),"PRVT"));


        recyclerView= findViewById(R.id.recyclerQuestions);
        getQuestions();


    }



    private void getQuestions(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.66:4000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IQuestion IQuestion = retrofit.create(IQuestion.class);
        call = IQuestion.getQuestions();
        call.enqueue(new Callback<ArrayList<Question>>() {
            @Override
            public void onResponse(Call<ArrayList<Question>> call, Response<ArrayList<Question>> response) {
                questions = response.body();
                if(!response.isSuccessful()){

                }

                adapter= new PostAdapter(questions, getApplicationContext());
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<ArrayList<Question>> call, Throwable t) {

            }
        });

    }
}
