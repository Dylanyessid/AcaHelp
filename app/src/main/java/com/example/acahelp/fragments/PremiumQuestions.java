package com.example.acahelp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.acahelp.R;
import com.example.acahelp.adapters.PremiumQuestionAdapter;
import com.example.acahelp.adapters.QuestionAdapter;
import com.example.acahelp.interfaces.IQuestion;
import com.example.acahelp.models.Question;
import com.example.acahelp.utilites.SpacingItemDecorator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PremiumQuestions#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PremiumQuestions extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static ArrayList<Question> questions;
    private static PremiumQuestionAdapter adapter;
    private static RecyclerView recyclerView;
    private static SharedPreferences preferences;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PremiumQuestions() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PremiumQuestions.
     */
    // TODO: Rename and change types and number of parameters
    public static PremiumQuestions newInstance(String param1, String param2) {
        PremiumQuestions fragment = new PremiumQuestions();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        preferences = getContext().getSharedPreferences( getString(R.string.sharedP), Context.MODE_PRIVATE);
        View view = inflater.inflate(R.layout.fragment_premium_questions, container, false);
        recyclerView = view.findViewById(R.id.recyclerPremiumQuestions);
        getQuestions();
        return view;
    }

    private void getQuestions(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://back.dylanlopez1.repl.co")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IQuestion IQuestion = retrofit.create(IQuestion.class);
        Call<ArrayList<Question>> call = IQuestion.getPrivateQuestions(preferences.getString(getString(R.string.sharedP),"PRVT"));
        call.enqueue(new Callback<ArrayList<Question>>() {
            @Override
            public void onResponse(Call<ArrayList<Question>> call, Response<ArrayList<Question>> response) {
                questions = response.body();
                if(!response.isSuccessful()){

                }
                adapter= new PremiumQuestionAdapter(questions, getActivity().getApplicationContext());
                SpacingItemDecorator spacingItemDecorator = new SpacingItemDecorator(30);
                recyclerView.addItemDecoration(spacingItemDecorator);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<ArrayList<Question>> call, Throwable t) {

            }
        });

    }
}