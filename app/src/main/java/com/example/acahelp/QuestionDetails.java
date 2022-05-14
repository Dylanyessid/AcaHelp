package com.example.acahelp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.acahelp.adapters.AnswerAdapter;
import com.example.acahelp.interfaces.IAnswer;
import com.example.acahelp.models.Answer;
import com.example.acahelp.utilites.SpacingItemDecorator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionDetails extends AppCompatActivity {

    TextView eTTitle, eTUsername, eTDesc;
    RecyclerView recyclerView;
    ArrayList<Answer> answers;
    Call<ArrayList<Answer>> call;
    String _id;
    Button btnAnswer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_details);
        Intent intent = getIntent();
        eTTitle = findViewById(R.id.eTTitle);
        eTDesc = findViewById(R.id.eTDesc);
        eTTitle.setText(intent.getStringExtra("title"));
        eTDesc.setText(intent.getStringExtra("desc"));
        _id = (intent.getStringExtra("questionId"));
        recyclerView = findViewById(R.id.recyclerAnswers);
        getAnswers();

        btnAnswer = findViewById(R.id.btnAnswer);
        btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewAnswer.class);
                intent.putExtra("id", _id);
                startActivity(intent);

            }
        });
    }

    private void getAnswers(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://back.dylanlopez1.repl.co")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IAnswer answersAPI = retrofit.create(IAnswer.class);
        call = answersAPI.getAnswers(_id);
        call.enqueue(new Callback<ArrayList<Answer>>() {
            @Override
            public void onResponse(Call<ArrayList<Answer>> call, Response<ArrayList<Answer>> response) {
                answers = response.body();
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
}