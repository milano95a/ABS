package com.cw.mad.a00003741.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class WeatherAdapter extends ArrayAdapter<Weather>{

    private Context mContext;
    private List<Weather> weatherList;

    public WeatherAdapter(Context context, List<Weather> weatherList){
        super(context,R.layout.weather_item,weatherList);

        mContext = context;
        this.weatherList = weatherList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.weather_item,null);

            ImageView wether_item_img = (ImageView)convertView.findViewById(R.id.weather_item_img);
            TextView weather_item_weekday = (TextView)convertView.findViewById(R.id.weather_item_weekday);
            TextView weather_item_day = (TextView)convertView.findViewById(R.id.weather_item_day);
            TextView weather_item_even = (TextView)convertView.findViewById(R.id.weather_item_night);


            weather_item_weekday.setText(String.valueOf(weatherList.get(position).date));
            weather_item_day.setText(String.valueOf(weatherList.get(position).day));
            weather_item_even.setText(String.valueOf(weatherList.get(position).evening));
            wether_item_img.setImageResource(weatherList.get(position).icon);

        return convertView;
    }

}
