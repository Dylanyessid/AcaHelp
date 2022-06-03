package com.example.acahelp.adapters;

import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.acahelp.fragments.PremiumQuestions;
import com.example.acahelp.fragments.PremiumRequest;
import com.example.acahelp.fragments.CreateQuestionFragment;
import com.example.acahelp.fragments.ProfileFragment;
import com.example.acahelp.fragments.QuestionsFragment;
import com.example.acahelp.fragments.WaitingFragment;
import com.example.acahelp.interfaces.IRequest;
import com.example.acahelp.models.Request;
import com.example.acahelp.utilites.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PageController extends FragmentPagerAdapter {

    int numTabs;
    String user;
    private Request userRequest;
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.getURI())
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public PageController(@NonNull FragmentManager fm, int behavior, String user) {
        super(fm, behavior);
        this.numTabs = behavior;
        this.user = user;
        getPremiumRequestStatus();

    }

    private void getPremiumRequestStatus(){

        IRequest iRequest = retrofit.create(IRequest.class);
        Call<Request> requestCall = iRequest.getRequest(user);
        requestCall.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                switch (response.code()){
                    case 200:
                        userRequest = response.body();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {

            }
        });
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new QuestionsFragment();
            case 1:
                return new CreateQuestionFragment();
            case 2:
                return new ProfileFragment();
            case 3:
                if(userRequest!=null){
                    if(!userRequest.getStatus().equals("Accepted")){
                        if(userRequest.getStatus().equals("Waiting Response")){
                            return new WaitingFragment();
                        }
                        return new PremiumRequest();
                    }else{
                        return new PremiumQuestions(userRequest);
                    }
                }else{
                    return new PremiumRequest();
                }


        }
        return null;
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
