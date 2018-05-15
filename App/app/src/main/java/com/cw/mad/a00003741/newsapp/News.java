package com.cw.mad.a00003741.newsapp;

import android.graphics.Bitmap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class News {
    String source = "";
    String author = "";
    String title = "";
    String description = "";
    String url = null;
    Bitmap icon = null;
    String publishDate = "";

    public News(String source, String author,String title, String description, String url, Bitmap icon, String publishDate){
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.icon = icon;
        this.publishDate = convertDate(publishDate);
    }

    private String convertDate(String startDate){


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String now = simpleDateFormat.format(new Date());
        try {

            long publishedDate = simpleDateFormat.parse(startDate).getTime();
            long currentDate = simpleDateFormat.parse(now).getTime();

            long diff = currentDate - publishedDate;

            long hours = TimeUnit.MILLISECONDS.toHours(diff);

            if(hours >= 24){
                long day = hours / 24;
                return day + "d  |  " + source;
            }
            return hours + "h  | " + author;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "recent  |  " + author;

    }
}
