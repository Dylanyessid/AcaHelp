package com.example.acahelp.utilites;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class Constants {
     private static String URI = "http://192.168.1.153:4000";

    public static String getURI() {
        return URI;
    }

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.getURI())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
