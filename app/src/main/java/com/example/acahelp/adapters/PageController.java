package com.example.acahelp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.acahelp.PremiumRequest;
import com.example.acahelp.fragments.CreateQuestionFragment;
import com.example.acahelp.fragments.ProfileFragment;
import com.example.acahelp.fragments.QuestionsFragment;

public class PageController extends FragmentPagerAdapter {

    int numTabs;
    public PageController(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.numTabs = behavior;


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
                return new PremiumRequest();

        }
        return null;
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
