package com.cw.mad.a00003741.newsapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class Pager extends FragmentStatePagerAdapter{
    int tabCount;

    public Pager(FragmentManager fm, int tabCount){
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                 FragmentSignIn signIn = new FragmentSignIn();
                return  signIn;
            case 1:
                FragmentSignUp signUp = new FragmentSignUp();
                return signUp;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
