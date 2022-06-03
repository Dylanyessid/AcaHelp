package com.example.acahelp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.acahelp.adapters.PremiumQuestionAdapter;
import com.example.acahelp.interfaces.IQuestion;
import com.example.acahelp.models.Question;
import com.example.acahelp.utilites.Constants;
import com.example.acahelp.utilites.SpacingItemDecorator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsignedPremiumQuestions extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView tVNoAssignedQuestions;
    SharedPreferences preferences;
    ArrayList<Question> questions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = getApplicationContext().getSharedPreferences( getString(R.string.sharedP), Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asigned_premium_questions);
        recyclerView = findViewById(R.id.recyclerAssigned);
        tVNoAssignedQuestions = findViewById(R.id.tVNoAssignedQuestions);
        tVNoAssignedQuestions.setVisibility(View.GONE);
        getAssignedQuestions(preferences.getString(getString(R.string.sharedP),"PRVT"));
    }

    private void getAssignedQuestions(String user) {
        IQuestion iQuestion = Constants.retrofit.create(IQuestion.class);
        Call<ArrayList<Question>> call = iQuestion.getAssignedQuestions(user);
        call.enqueue(new Callback<ArrayList<Question>>() {
            @Override
            public void onResponse(Call<ArrayList<Question>> call, Response<ArrayList<Question>> response) {
                questions = response.body();
                if(questions.size()==0){
                    tVNoAssignedQuestions.setVisibility(View.VISIBLE);
                    return;
                }
               PremiumQuestionAdapter adapter= new PremiumQuestionAdapter(questions, getApplicationContext().getApplicationContext());
                SpacingItemDecorator spacingItemDecorator = new SpacingItemDecorator(30);
                recyclerView.addItemDecoration(spacingItemDecorator);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Question>> call, Throwable t) {

            }
        });
    }

}