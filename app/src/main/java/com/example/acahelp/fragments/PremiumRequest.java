package com.example.acahelp.fragments;

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
import com.example.acahelp.utilites.SpacingItemDecorator;

import java.util.ArrayList;

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

        areas = new ArrayList<String>();
        View view = inflater.inflate(R.layout.fragment_premium_request, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerAreas);
        Button addArea = view.findViewById(R.id.btnAddArea);
        EditText area = view.findViewById(R.id.eTOtherArea);
        SpacingItemDecorator spacingItemDecorator = new SpacingItemDecorator(30);
        recyclerView.addItemDecoration(spacingItemDecorator);
        addArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(areas.size()<=5){
                    areas.add(area.getText().toString());
                    AreaAdapter adapter = new AreaAdapter(areas);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adapter);
                }else{
                    Toast.makeText(getContext(),"No puedes asignarte más de 5 áreas diferentes.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}