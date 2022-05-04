package com.example.acahelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import com.example.acahelp.interfaces.*;
import com.example.acahelp.models.Question;
import com.example.acahelp.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin  = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
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
                .baseUrl("http://192.168.1.52:4000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userAPI userAPI = retrofit.create(userAPI.class);
        Call<User> call = userAPI.login(user);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                switch (response.code()){
                    case 200:
                        //Toast.makeText(getApplicationContext(),"Ingreso exitoso", Toast.LENGTH_SHORT).show();
                        startActivity(mainIntent);
                        User res = (User) response.body();
                        break;
                    case 400:
                        Toast.makeText(getApplicationContext(), "Ocurrió un error al intentar iniciar sesión." , Toast.LENGTH_SHORT).show();
                        btnLogin.setEnabled(true);
                        break;
                    case 401:
                        Toast.makeText(getApplicationContext(), "Correo electrónico y/o contraseña no válido(s)." , Toast.LENGTH_SHORT).show();
                        btnLogin.setEnabled(true);
                        break;
                    default:
                        btnLogin.setEnabled(true);
                        break;
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                btnLogin.setEnabled(true);
                Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    }