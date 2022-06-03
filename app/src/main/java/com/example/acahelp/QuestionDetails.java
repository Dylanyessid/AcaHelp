package com.example.acahelp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acahelp.adapters.AnswerAdapter;
import com.example.acahelp.interfaces.IAnswer;
import com.example.acahelp.models.Answer;
import com.example.acahelp.models.Score;
import com.example.acahelp.utilites.Constants;
import com.example.acahelp.utilites.SpacingItemDecorator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionDetails extends AppCompatActivity {

    private TextView eTTitle, eTUsername, eTDesc;
    private RecyclerView recyclerView;
    private ArrayList<Answer> answers;
    private Call<ArrayList<Answer>> call;
    private String _id, user;
    private Button btnAnswer;
    private SharedPreferences preferences;
    private ArrayList<Score> scoredAnswers;
    private boolean isPrivate;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            recyclerView.setVisibility(View.INVISIBLE);
            btnAnswer.setEnabled(false);
            getAnswers();
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_details);
        Intent intent = getIntent();
        isPrivate = intent.getBooleanExtra("isPrivate",false);
        preferences = getApplicationContext().getSharedPreferences( getString(R.string.sharedP), Context.MODE_PRIVATE);
        eTTitle = findViewById(R.id.eTTitle);
        eTDesc = findViewById(R.id.eTDesc);
        eTTitle.setText(intent.getStringExtra("title"));
        eTDesc.setText(intent.getStringExtra("desc"));
        _id = (intent.getStringExtra("questionId"));
        user = intent.getStringExtra("user");

        System.out.println("Veamos,,, " + user +" " + _id);
        recyclerView = findViewById(R.id.recyclerAnswers);
        recyclerView.setVisibility(View.INVISIBLE);
        getScoredAnswers();
        getAnswers();


        btnAnswer = findViewById(R.id.btnAnswer);
        btnAnswer.setVisibility(View.INVISIBLE);

        btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answers.size() < 11){
                    Intent intent = new Intent(getApplicationContext(), NewAnswer.class);
                    intent.putExtra("id", _id);
                    intent.putExtra("isPrivate", isPrivate);
                    startActivityForResult(intent, 100);
                }
            }
        });
    }

    private void getScoredAnswers(){

        IAnswer iAnswer = Constants.retrofit.create(IAnswer.class);
        Call<ArrayList<Score>> call = iAnswer.getScoredAnswers(preferences.getString(getString(R.string.sharedP),"PRVT"),_id);
        call.enqueue(new Callback<ArrayList<Score>>() {
            @Override
            public void onResponse(Call<ArrayList<Score>> call, Response<ArrayList<Score>> response) {
                set(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Score>> call, Throwable t) {

            }
        });

    }

    private void set(ArrayList<Score> t){
        scoredAnswers = t;
    }

    private void getAnswers(){

        IAnswer answersAPI = Constants.retrofit.create(IAnswer.class);
        call = answersAPI.getAnswers(_id);
        call.enqueue(new Callback<ArrayList<Answer>>() {
            @Override
            public void onResponse(Call<ArrayList<Answer>> call, Response<ArrayList<Answer>> response) {
                answers = response.body();
                if(isPrivate && answers.size()==0){
                    if(user.equals(preferences.getString(getString(R.string.sharedP),"PRVT"))){
                        System.out.println("MIRA: " + user);
                        btnAnswer.setEnabled(false);
                        btnAnswer.setText("No puedes enviar mensaje todavía.");
                        btnAnswer.setBackgroundResource(R.drawable.button_options_danger);
                        btnAnswer.setTextColor(getResources().getColor(R.color.danger));
                    }

                }
               if(answers !=null){
                   AnswerAdapter adapter = new AnswerAdapter(answers, getApplicationContext(), scoredAnswers, isPrivate);
                   recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                   SpacingItemDecorator spacingItemDecorator = new SpacingItemDecorator(30);
                   recyclerView.addItemDecoration(spacingItemDecorator);
                   recyclerView.setAdapter(adapter);
                   if(answers.size()==10 && !isPrivate ){
                       btnAnswer.setEnabled(false);
                       btnAnswer.setText("Ya no se puede responder más.");
                       btnAnswer.setBackgroundResource(R.drawable.button_options_danger);
                       btnAnswer.setTextColor(getResources().getColor(R.color.danger));
                   }else{
                       btnAnswer.setEnabled(true);
                   }
                   btnAnswer.setVisibility(View.VISIBLE);
                   recyclerView.setVisibility(View.VISIBLE);
               }
            }

            @Override
            public void onFailure(Call<ArrayList<Answer>> call, Throwable t) {

            }
        });
    }
}