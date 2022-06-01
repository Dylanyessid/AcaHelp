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

import androidx.appcompat.app.AppCompatDelegate;

import com.example.acahelp.interfaces.*;

import com.example.acahelp.models.User;
import com.example.acahelp.utilites.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    Button btnLogin, btnSignUp;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        btnSignUp = findViewById(R.id.btnGoToSignUp);
        btnLogin  = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpIntent = new Intent(MainActivity.this, SignUp.class);
                startActivity(signUpIntent);
            }
        });
    }

    private void login(){
        btnLogin.setEnabled(false);
        Intent mainIntent = new Intent(MainActivity.this, MainScreen.class);
        EditText eTEmail = findViewById(R.id.eTEmail);
        EditText eTPass = findViewById(R.id.eTPass);

        User user = new User(eTEmail.getText().toString(), eTPass.getText().toString());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.getURI())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userAPI userAPI = retrofit.create(userAPI.class);
        Call<User> call = userAPI.login(user);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                switch (response.code()){
                    case 200:
                        Toast.makeText(getApplicationContext(),"Ingreso exitoso", Toast.LENGTH_SHORT).show();
                        User res = (User) response.body();
                        preferences = getApplicationContext().getSharedPreferences( getString(R.string.sharedP),Context.MODE_PRIVATE);
                        editor = preferences.edit();
                        editor.putString( getString(R.string.sharedP),res.getId());
                        editor.apply();
                        mainIntent.putExtra("name", res.getName() + " " + res.getSurname());
                        startActivity(mainIntent);
                        break;
                    case 400:
                        Toast.makeText(getApplicationContext(), "Ocurrió un error al intentar iniciar sesión." , Toast.LENGTH_SHORT).show();

                        break;
                    case 401:
                        Toast.makeText(getApplicationContext(), "Correo electrónico y/o contraseña no válido(s)." , Toast.LENGTH_SHORT).show();

                        break;
                    default:

                        break;
                }
                btnLogin.setEnabled(true);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                btnLogin.setEnabled(true);
                System.out.println( t.getCause());
                Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    }
