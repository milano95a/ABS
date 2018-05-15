package com.cw.mad.a00003741.newsapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class HomePager extends FragmentPagerAdapter{

    int tabCount;

    public HomePager(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                FragmentMain top = new FragmentMain();
                Data.current_publisher = "top";
                return  top;
            case 1:
                FragmentWeather latest = new FragmentWeather();
                return latest;
            default:
                Log.v("LOG","ERROR");
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
