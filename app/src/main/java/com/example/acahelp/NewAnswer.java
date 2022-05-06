package com.example.acahelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.acahelp.interfaces.IAnswer;
import com.example.acahelp.models.Answer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewAnswer extends AppCompatActivity {

    EditText eTAnswer;
    Button btnSendAnswer;
    SharedPreferences preferences ;
    String _id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        _id = intent.getStringExtra("id");
        setContentView(R.layout.activity_new_answer);
        preferences = getApplicationContext().getSharedPreferences( getString(R.string.sharedP), Context.MODE_PRIVATE);
        eTAnswer = findViewById(R.id.eTAnswer);
        btnSendAnswer = findViewById(R.id.btnSendAnswer);
        btnSendAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postAnswer();

            }
        });
    }

    private void postAnswer(){
        Answer answer = new Answer(_id, preferences.getString(getString(R.string.sharedP),"PRVT"), eTAnswer.getText().toString());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.66:4000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IAnswer iAnswer = retrofit.create(IAnswer.class);
        Call<Answer> call = iAnswer.postAnswer(answer);
        call.enqueue(new Callback<Answer>() {
            @Override
            public void onResponse(Call<Answer> call, Response<Answer> response) {
                Toast.makeText(NewAnswer.this, "Creado el comentario con éxito.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Answer> call, Throwable t) {

            }
        });
    }
}