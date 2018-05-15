package com.cw.mad.a00003741.newsapp;

import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class Data {
    public static ArrayList<News> listOfNews = new ArrayList<>();

    public static ArrayList<News> OfflinelistOfNews = new ArrayList<>();

    public static ViewPager viewPager;

    public static int selectedIndex = 0;

    public static boolean showImage = false;

    public static String current_publisher = "top";

    public static int drawerSelectedItem = 0;

    public static boolean cloearFragment = false;

    public static String username;

    public static String password;

    public static int userId;

    public static String weather = "http://api.openweathermap.org/data/2.5/forecast/daily?q=Tashkent&units=metric&cnt=7&appid=5840fa9fea603b7feea6b1068efbbcdd";

    public static LinearLayout noConnection;

    public static LinearLayout noConnectionWeather;
}
