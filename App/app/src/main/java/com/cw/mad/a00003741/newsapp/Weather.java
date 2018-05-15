package com.cw.mad.a00003741.newsapp;


import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Weather {
    public String country;
    public String city;
    public String morning;
    public String day;
    public String evening;
    public String night;
    public String date;
    public String main;
    public String description;
    public int icon;

    public Weather(String country,
                   String city,
                   int morning,
                   int day,
                   int evening,
                   int night,
                   long date,
                   String main,
                   String description,
                   int icon) {

        this.country = country;
        this.city = city;
        this.day = day + "\u2103";
        this.night = night +"\u2103";
        this.morning = morning +"\u2103";
        this.evening = evening +"\u2103";
        this.main = main;
        this.description = description;
        this.icon = icon;

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM  EEEE");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date*1000);
        this.date = formatter.format(calendar.getTime());

    }
}