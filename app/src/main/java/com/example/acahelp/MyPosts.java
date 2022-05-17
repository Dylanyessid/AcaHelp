package com.example.acahelp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.acahelp.adapters.AnswerAdapter;
import com.example.acahelp.adapters.QuestionAdapter;
import com.example.acahelp.interfaces.IAnswer;
import com.example.acahelp.interfaces.IQuestion;
import com.example.acahelp.models.Answer;
import com.example.acahelp.models.Question;
import com.example.acahelp.utilites.Constants;
import com.example.acahelp.utilites.SpacingItemDecorator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyPosts extends AppCompatActivity {

    SharedPreferences preferences;
    RecyclerView recyclerView;
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.getURI())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = getApplicationContext().getSharedPreferences( getString(R.string.sharedP), Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);
        recyclerView= findViewById(R.id.recyclerMyPosts);

        if(getIntent().getStringExtra("type").equals("questions")){
            getMyQuestions();
        }
        else if(getIntent().getStringExtra("type").equals("answers")){
            getMyAnswers();
        }



    }

    private void getMyAnswers() {
        IAnswer answersAPI = retrofit.create(IAnswer.class);
        Call<ArrayList<Answer>> call = answersAPI.getUserAnswers(preferences.getString(getString(R.string.sharedP),"PRVT"));
        call.enqueue(new Callback<ArrayList<Answer>>() {
            @Override
            public void onResponse(Call<ArrayList<Answer>> call, Response<ArrayList<Answer>> response) {
                ArrayList<Answer> answers = response.body();
                AnswerAdapter adapter = new AnswerAdapter(answers);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                SpacingItemDecorator spacingItemDecorator = new SpacingItemDecorator(30);
                recyclerView.addItemDecoration(spacingItemDecorator);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Answer>> call, Throwable t) {

            }
        });
    }

    private void getMyQuestions(){
        IQuestion IQuestion = retrofit.create(IQuestion.class);
        Call <ArrayList<Question>>call = IQuestion.getUserQuestions(preferences.getString(getString(R.string.sharedP),"PRVT"));
        call.enqueue(new Callback<ArrayList<Question>>() {
            @Override
            public void onResponse(Call<ArrayList<Question>> call, Response<ArrayList<Question>> response) {
                ArrayList<Question> questions = response.body();
                if(!response.isSuccessful()){

                }
                QuestionAdapter adapter= new QuestionAdapter(questions, getApplicationContext());
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<ArrayList<Question>> call, Throwable t) {

            }
        });

    }
}