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
import com.example.acahelp.utilites.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewAnswer extends AppCompatActivity {

    EditText eTAnswer;
    boolean isPrivate;
    Button btnSendAnswer;
    SharedPreferences preferences ;
    String _id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        isPrivate = intent.getBooleanExtra("isPrivate", false);
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

    private void close(){
        Intent returnIntent = new Intent();
        setResult(100, returnIntent);
        finish();
    }


    private void postAnswer(){
        Answer answer = new Answer(_id, preferences.getString(getString(R.string.sharedP),"PRVT"), eTAnswer.getText().toString());
        IAnswer iAnswer = Constants.retrofit.create(IAnswer.class);

        if(!isPrivate){
            Call<Answer> call = iAnswer.postAnswer(answer);
            call.enqueue(new Callback<Answer>() {
                @Override
                public void onResponse(Call<Answer> call, Response<Answer> response) {
                    Toast.makeText(NewAnswer.this, "Creado el comentario con éxito.", Toast.LENGTH_SHORT).show();
                    close();
                }

                @Override
                public void onFailure(Call<Answer> call, Throwable t) {

                }
            });
        }else{
            Call<Answer> call = iAnswer.postAnswerOfPremiumQuestion(preferences.getString(getString(R.string.sharedP),"PRVT"), _id, answer);
            call.enqueue(new Callback<Answer>() {
                @Override
                public void onResponse(Call<Answer> call, Response<Answer> response) {
                    switch (response.code()){
                        case 200:
                            Toast.makeText(NewAnswer.this, "Creada la respuesta con éxito.", Toast.LENGTH_SHORT).show();
                            close();
                            break;
                    }
                }

                @Override
                public void onFailure(Call<Answer> call, Throwable t) {

                }
            });
        }

    }
}