package com.streamstech.droid.mshtb.fragement;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shafi on 4/16/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> title = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }

    public void set(int position, Fragment fragment){
        mFragmentList.set(position, fragment);
//        notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (title.isEmpty()) {
            return  "";
        } else return title.get(position);
    }
}