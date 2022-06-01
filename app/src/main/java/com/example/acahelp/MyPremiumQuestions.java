package com.example.acahelp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.acahelp.adapters.PremiumQuestionAdapter;
import com.example.acahelp.interfaces.IQuestion;
import com.example.acahelp.models.Question;
import com.example.acahelp.utilites.Constants;
import com.example.acahelp.utilites.SpacingItemDecorator;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPremiumQuestions extends AppCompatActivity {

    private static PremiumQuestionAdapter adapter;
    private static RecyclerView recyclerView;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getApplicationContext().getSharedPreferences( getString(R.string.sharedP), Context.MODE_PRIVATE);
        setContentView(R.layout.activity_my_premium_questions);
        recyclerView = findViewById(R.id.recyclerMyPremiumQuestions);
        recyclerView.setVisibility(View.INVISIBLE);
        getMyPremiumQuestions();
    }

    private void getMyPremiumQuestions(){
        IQuestion IQuestion = Constants.retrofit.create(IQuestion.class);
        Call<ArrayList<Question>> call = IQuestion.getUserPremiumQuestions(preferences.getString(getString(R.string.sharedP),"PRVT"));
        call.enqueue(new Callback<ArrayList<Question>>() {
            @Override
            public void onResponse(Call<ArrayList<Question>> call, Response<ArrayList<Question>> response) {

                adapter= new PremiumQuestionAdapter(response.body(), getApplicationContext());
                SpacingItemDecorator spacingItemDecorator = new SpacingItemDecorator(30);
                recyclerView.addItemDecoration(spacingItemDecorator);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<ArrayList<Question>> call, Throwable t) {

            }
        });
    }
}