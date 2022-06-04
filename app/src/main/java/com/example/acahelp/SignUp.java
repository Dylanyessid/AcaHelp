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

import com.example.acahelp.interfaces.userAPI;
import com.example.acahelp.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Button signUp;
    EditText eTName, eTSurname, eTemail, eTPass, eTConfirmPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        eTName = findViewById(R.id.eTName);
        eTSurname = findViewById(R.id.eTSurname);
        eTemail = findViewById(R.id.eTEmailSignUp);
        eTPass = findViewById(R.id.eTPasswordSignUp);
        eTConfirmPass = findViewById(R.id.eTConfirmPassword);
        signUp = findViewById(R.id.btnSignUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }});
    };

    private void signUp(){

        if(eTName.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"El nombre NO debe estar vacío;", Toast.LENGTH_SHORT).show();
            return;
        }
        if(eTSurname.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"El apellido NO debe estar vacío;", Toast.LENGTH_SHORT).show();
            return;
        }
        if(eTemail.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"El correo NO debe estar vacío;", Toast.LENGTH_SHORT).show();
            return;
        }
        if(eTPass.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"La contraseña NO debe estar vacía;", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!eTPass.getText().toString().equals(eTConfirmPass.getText().toString())){
            Toast.makeText(getApplicationContext(),"Las contraseñas NO coinciden", Toast.LENGTH_SHORT).show();
            return;
        }


        if(eTPass.getText().toString().equals(eTConfirmPass.getText().toString())){
            User user = new User(
                    eTName.getText().toString(),
                    eTSurname.getText().toString(),
                    eTemail.getText().toString(),
                    eTPass.getText().toString()
            );
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://back.dylanlopez1.repl.co")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            userAPI userAPI = retrofit.create(userAPI.class);
            Call<User> call = userAPI.signUp(user);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.code()==200){
                        Intent mainIntent = new Intent(SignUp.this, MainScreen.class);
                        User res = response.body();
                        preferences = getApplicationContext().getSharedPreferences( getString(R.string.sharedP), Context.MODE_PRIVATE);
                        editor = preferences.edit();
                        editor.putString( getString(R.string.sharedP),res.getId());
                        editor.apply();
                        mainIntent.putExtra("name", res.getName() + " " + res.getSurname());
                        startActivity(mainIntent);
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });

        }

    }
}