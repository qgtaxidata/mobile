package com.example.taxidata.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class AreaAnalyzeViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> areaAnalyzeList = new ArrayList<>();

    public AreaAnalyzeViewPagerAdapter(@NonNull FragmentManager fragmentManager, List<Fragment> list) {
        super(fragmentManager);
        this.areaAnalyzeList = list;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return areaAnalyzeList.get(position);
    }

    @Override
    public int getCount() {
        return areaAnalyzeList.size();
    }

}
