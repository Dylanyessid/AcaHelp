package com.example.acahelp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.acahelp.adapters.PageController;
import com.example.acahelp.adapters.QuestionAdapter;
import com.example.acahelp.models.Question;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import retrofit2.Call;

public class MainScreen extends AppCompatActivity {
    SharedPreferences preferences;
     TabLayout tabLayout;
     ViewPager viewPager;
     PageController pageController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        preferences = getApplicationContext().getSharedPreferences( getString(R.string.sharedP), Context.MODE_PRIVATE);
        TextView eTUsername = findViewById(R.id.eTUsernameMain);
        eTUsername.setText(getIntent().getStringExtra("name"));
        System.out.println("KEY A VER SI ESTA BIEN : " + preferences.getString(getString(R.string.sharedP),"PRVT"));
        tabLayout = findViewById(R.id.tabLayoutMain);
        viewPager = findViewById(R.id.pagerMain);

        pageController = new PageController(getSupportFragmentManager(),tabLayout.getTabCount());

        viewPager.setAdapter(pageController);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()){
                    case 0:
                    case 1:
                    case 2:
                        pageController.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));



    }




}
