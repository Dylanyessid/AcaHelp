package com.example.acahelp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acahelp.interfaces.questionAPI;
import com.example.acahelp.models.Question;

import java.util.ArrayList;
import java.util.List;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        recyclerView= findViewById(R.id.recyclerQuestions);
        getQuestions();


    }



    private void getQuestions(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.52:4000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        questionAPI questionAPI = retrofit.create(questionAPI.class);
        call = questionAPI.getQuestions();
        call.enqueue(new Callback<ArrayList<Question>>() {
            @Override
            public void onResponse(Call<ArrayList<Question>> call, Response<ArrayList<Question>> response) {
                questions = response.body();
                System.out.println(" dsa" + questions.get(0).getDescription());
                if(!response.isSuccessful()){

                }

                adapter= new PostAdapter(questions);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Question>> call, Throwable t) {

            }
        });

    }
}
