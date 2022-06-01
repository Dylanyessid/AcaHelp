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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.acahelp.R;
import com.example.acahelp.adapters.AreaAdapter;
import com.example.acahelp.interfaces.IRequest;
import com.example.acahelp.models.Request;
import com.example.acahelp.utilites.Constants;
import com.example.acahelp.utilites.SpacingItemDecorator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PremiumRequest#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PremiumRequest extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static ArrayList<String> areas;
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.getURI())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private SharedPreferences preferences;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PremiumRequest() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PremiumRequest.
     */
    // TODO: Rename and change types and number of parameters
    public static PremiumRequest newInstance(String param1, String param2) {
        PremiumRequest fragment = new PremiumRequest();
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
        areas = new ArrayList<String>();
        View view = inflater.inflate(R.layout.fragment_premium_request, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerAreas);
        Button addArea = view.findViewById(R.id.btnAddArea);
        Button sendRequest = view.findViewById(R.id.btnSendRequest);
        EditText area = view.findViewById(R.id.eTArea);
        SpacingItemDecorator spacingItemDecorator = new SpacingItemDecorator(30);
        recyclerView.addItemDecoration(spacingItemDecorator);

        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Request request = new Request(preferences.getString(getString(R.string.sharedP), "xd"),areas);
                IRequest iRequest = retrofit.create(IRequest.class);
                Call<Request> requestCall = iRequest.createRequest(request);
                requestCall.enqueue(new Callback<Request>() {
                    @Override
                    public void onResponse(Call<Request> call, Response<Request> response) {
                        if(response.code()==200){
                            Toast.makeText(getContext(), "Solicitud enviada con éxito.", Toast.LENGTH_SHORT).show();
                        }
                        System.out.println(response.body());
                    }

                    @Override
                    public void onFailure(Call<Request> call, Throwable t) {

                    }
                });

                for (int i = 0; i <areas.size() ; i++) {
                    System.out.println(areas.get(i));
                }
            }
        });
        addArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(areas.size()<5){
                    if(areas.contains(area.getText().toString())){
                        Toast.makeText(getContext(),"Ya tienes agregada el área de: " + area.getText().toString(), Toast.LENGTH_SHORT).show();

                    }
                    else{
                        areas.add(area.getText().toString());
                        AreaAdapter adapter = new AreaAdapter(areas);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        adapter.notifyItemInserted((areas.size()-1));
                        recyclerView.setAdapter(adapter);
                        Toast.makeText(getContext(),area.getText().toString() + " agregado con éxito.", Toast.LENGTH_SHORT).show();


                    }

                }else{
                    Toast.makeText(getContext(),"No puedes asignarte más de 5 áreas diferentes.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }

    private void getPremiumRequestStatus(){
        //getContext().getSharedPreferences( getString(R.string.sharedP), Context.MODE_PRIVATE);
        IRequest iRequest = retrofit.create(IRequest.class);
        Call<Request> requestCall = iRequest.getRequest(preferences.getString(getString(R.string.sharedP), "xd"));
        requestCall.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                switch (response.code()){
                    case 200:
                       Request userRequest = response.body();
                       if(userRequest.getStatus().equals("Waiting Response")){

                       }
                        break;
                }
            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {

            }
        });
    }
}