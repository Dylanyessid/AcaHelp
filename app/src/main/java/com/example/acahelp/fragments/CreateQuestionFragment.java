package com.example.acahelp.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.FileUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acahelp.R;
import com.example.acahelp.interfaces.IQuestion;
import com.example.acahelp.models.Question;
import com.example.acahelp.utilites.Constants;

import java.io.File;
import java.net.ResponseCache;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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
    private TextView tVWarningArea, tVAreaTitle;
    private SharedPreferences preferences;
    private ImageView imageView;
    private Uri filePath;
    private Button btnSendQuestion, btnAddFile, btnTakePhoto;
    Switch privateQuestionSwitch;
    private Spinner questionArea;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String[] areas = new String[]{"Matemáticas", "Física", "Biología", "Informática", "Idiomas"};

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

        tVAreaTitle = view.findViewById(R.id.tVAreaTitle);
        tVWarningArea = view.findViewById(R.id.tVWarningArea);
        questionArea = view.findViewById(R.id.spinnerQuestionArea);

        eTTitle =view.findViewById(R.id.eTQuestionTitle);
        eTDescription = view.findViewById(R.id.eTQuestionDescription);

        btnSendQuestion = view.findViewById(R.id.btnSendNewQuestion);

        privateQuestionSwitch= view.findViewById(R.id.switchPrivateQuestion);
        preferences = getContext().getSharedPreferences( getString(R.string.sharedP), Context.MODE_PRIVATE);
        tVWarningArea.setVisibility(View.GONE);
        questionArea.setVisibility(View.GONE);
        tVAreaTitle.setVisibility(View.GONE);
        privateQuestionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!privateQuestionSwitch.isChecked()){

                    tVWarningArea.setVisibility(View.GONE);
                    questionArea.setVisibility(View.GONE);
                    tVAreaTitle.setVisibility(View.GONE);
                }else{

                    questionArea.setVisibility(View.VISIBLE);
                    tVAreaTitle.setVisibility(View.VISIBLE);

                    tVWarningArea.setVisibility(View.VISIBLE);
                }
            }
        });

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

        Question question = new Question(eTTitle.getText().toString(),
                eTDescription.getText().toString(),
                preferences.getString(getString(R.string.sharedP),"PRVT"),
                privateQuestionSwitch.isChecked());
        IQuestion IQuestion = Constants.retrofit.create(IQuestion.class);

        if(!privateQuestionSwitch.isChecked()){

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
        }else{
            question.setArea(questionArea.getSelectedItem().toString());
            Call<Question> call = IQuestion.postNewQuestion(question);
            call.enqueue(new Callback<Question>() {
                @Override
                public void onResponse(Call<Question> call, Response<Question> response) {
                    switch (response.code()){
                        case 200:
                            Toast.makeText(getContext(), "Pregunta formulada y enviada con éxito", Toast.LENGTH_LONG).show();
                            break;
                    }
                }

                @Override
                public void onFailure(Call<Question> call, Throwable t) {

                }
            });

        }


    }





}