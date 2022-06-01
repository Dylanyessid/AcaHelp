package com.example.acahelp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.acahelp.MyPosts;
import com.example.acahelp.MyPremiumQuestions;
import com.example.acahelp.R;
import com.example.acahelp.interfaces.userAPI;
import com.example.acahelp.models.User;
import com.example.acahelp.utilites.Constants;

import org.w3c.dom.Text;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SharedPreferences preferences;
    private User currentUser;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static userAPI userAPI = Constants.retrofit.create(userAPI.class);
    private static Call<User> call;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        preferences = getContext().getSharedPreferences( getString(R.string.sharedP), Context.MODE_PRIVATE);
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView tVName = view.findViewById(R.id.tVCurrentUserName);
        TextView tVUserType = view.findViewById(R.id.tVUserType);
        getUserInfo(tVName, tVUserType);


        Button btnGoToMyQuestions = view.findViewById(R.id.btnGoToMyQuestions);
        Button btnGoToMyAnswers = view.findViewById(R.id.btnGoToMyAnswers);
        Button btnGoToPremiumQuestions = view.findViewById(R.id.btnGoToPremiumQuestions);
        Button btnLogOut = view.findViewById(R.id.btnLogOut);

        btnGoToPremiumQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyPremiumQuestions.class);
                startActivity(intent);
            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        btnGoToMyQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MyPosts.class);
                intent.putExtra("type", "questions");
                startActivity(intent);
            }
        });
        btnGoToMyAnswers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MyPosts.class);
                intent.putExtra("type", "answers");
                startActivity(intent);
            }
        });
        return view;
    }

    private void getUserInfo(TextView tVName, TextView tVType){
        call = userAPI.getUserName(preferences.getString(getString(R.string.sharedP),"PRVT"));
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code()==200){
                   tVName.setText(response.body().getName() + " " + response.body().getSurname());
                   if(response.body().isQualified()){
                       tVType.setText("Usuario cualificado");
                   }
                   else{
                       tVType.setText("Usuario normal");
                   }
                }
                setCurrentUser(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

    private void setCurrentUser(User body) {
        this.currentUser = body;
    }
}