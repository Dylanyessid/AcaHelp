package com.example.acahelp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.acahelp.R;
import com.example.acahelp.interfaces.IQuestion;
import com.example.acahelp.models.Question;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateQuestionFragment extends Fragment {

    private EditText eTTitle, eTDescription;
    private SharedPreferences preferences;
    private Button btnSendQuestion;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateQuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateQuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateQuestionFragment newInstance(String param1, String param2) {
        CreateQuestionFragment fragment = new CreateQuestionFragment();
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

        View view = inflater.inflate(R.layout.fragment_create_question, container, false);
        eTTitle =view.findViewById(R.id.eTQuestionTitle);
        eTDescription = view.findViewById(R.id.eTQuestionDescription);
        btnSendQuestion = view.findViewById(R.id.btnSendNewQuestion);
        preferences = getContext().getSharedPreferences( getString(R.string.sharedP), Context.MODE_PRIVATE);
        btnSendQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postNewQuestion();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void postNewQuestion(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://back.dylanlopez1.repl.co")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Question question = new Question(eTTitle.getText().toString(),eTDescription.getText().toString(), preferences.getString(getString(R.string.sharedP),"PRVT"));
        IQuestion IQuestion = retrofit.create(IQuestion.class);
        Call<Question> call = IQuestion.postNewQuestion(question);
        call.enqueue(new Callback<Question>() {
            @Override
            public void onResponse(Call<Question> call, Response<Question> response) {
                if(response.code()==200){
                    Toast.makeText(getContext(), "Pregunta formulada y enviada con éxito", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Question> call, Throwable t) {

            }
        });
    }
}