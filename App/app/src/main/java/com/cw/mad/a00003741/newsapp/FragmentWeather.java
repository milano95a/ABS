package com.cw.mad.a00003741.newsapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FragmentWeather extends Fragment{

    ArrayList<Weather> weathers;
    ListView listView;
    WeatherAdapter weatherAdapter;

    ImageView imgToday;
    TextView txtToday;
    TextView morning;
    TextView day;
    TextView evening;
    TextView night;
    TextView description;
    FrameLayout progress;
    LinearLayout weatherLinear;
    Button btnTry;
    TextView gpsStatus;

    boolean isCancelled;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_weather,container,false);

        progress = (FrameLayout)rootview.findViewById(R.id.fragment_weather_progress);

        listView = (ListView)rootview.findViewById(R.id.weather_list);

        imgToday = (ImageView)rootview.findViewById(R.id.fragment_weather_icon);
        txtToday = (TextView)rootview.findViewById(R.id.today);
        morning = (TextView)rootview.findViewById(R.id.fragment_weather_mor);
        day = (TextView)rootview.findViewById(R.id.fragment_weather_day);
        evening = (TextView)rootview.findViewById(R.id.fragment_weather_even);
        night = (TextView)rootview.findViewById(R.id.fragment_weather_night);
        description = (TextView)rootview.findViewById(R.id.description);
        gpsStatus = (TextView)rootview.findViewById(R.id.txt_no_connection);

        weatherLinear = (LinearLayout)rootview.findViewById(R.id.weather_no_connection_layout);

        btnTry = (Button)rootview.findViewById(R.id.weather_btn_try);
        btnTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoadWeather();
            }
        });
        startLoadWeather();

        return rootview;
    }

    private class DownloadFilesTask extends AsyncTask<String,Integer,String> {

        @Override
        protected void onPreExecute() {
            if(isInternetAvailable()){
                isCancelled = false;
                weatherLinear.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
            } else{
                progress.setVisibility(View.GONE);
                isCancelled = true;
                return;
            }
//
//            if(!isGPSAvailable()){
//                isCancelled = true;
//                gpsStatus.setText("GPS is disabled");
//            }

        }

        @Override
        protected String doInBackground(String... params) {
            if(isCancelled) return null;
            String weather = "";
            try{
                URL url = new URL(params[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream is = httpURLConnection.getInputStream();
                BufferedReader r = new BufferedReader(new InputStreamReader(is));
                StringBuilder total = new StringBuilder();
                String line;
                while((line = r.readLine())!= null){
                    total.append(line);
                }
                weather = total.toString();
            }catch (Exception e){
                e.printStackTrace();
            }
            return weather;
        }

        @Override
        protected void onPostExecute(String s) {
            if(isCancelled) return;
//            Log.v("LOG",s);
            toWeather(s);
            try{

                weatherAdapter = new WeatherAdapter(getActivity(),weathers);
                listView.setAdapter(weatherAdapter);

                imgToday.setImageResource(weathers.get(0).icon);
                txtToday.setText(weathers.get(0).day);
                morning.setText(weathers.get(0).morning);
                day.setText(weathers.get(0).day);
                evening.setText(weathers.get(0).evening);
                night.setText(weathers.get(0).night);
                description.setText(weathers.get(0).description);
                progress.setVisibility(View.GONE);
            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }

    public void toWeather(String json){
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(json);

        if(element.isJsonObject()){

            JsonObject jsonObject = element.getAsJsonObject();
            JsonObject cityObject = jsonObject.get("city").getAsJsonObject();
            String countryName = cityObject.get("country").getAsString();
            String cityName = cityObject.get("name").getAsString();

            JsonArray weatherList = jsonObject.get("list").getAsJsonArray();

            for(int i = 0, k = weatherList.size(); i < k; i++){

                JsonObject weatherObject = weatherList.get(i).getAsJsonObject();

                long date = weatherObject.get("dt").getAsLong();

                JsonObject temperatureObject = weatherObject.get("temp").getAsJsonObject();
                JsonArray weatherDescriptionArray = weatherObject.get("weather").getAsJsonArray();

                String main = weatherDescriptionArray.get(0).getAsJsonObject().get("main").getAsString();
                String description = weatherDescriptionArray.get(0).getAsJsonObject().get("description").getAsString();
                int morning = temperatureObject.get("morn").getAsInt();
                int day = temperatureObject.get("day").getAsInt();
                int evening = temperatureObject.get("eve").getAsInt();
                int night = temperatureObject.get("night").getAsInt();

                int icon = R.drawable.sun;

                if(main.equals("Rain")){
                    icon = R.drawable.rain;
                }

                if(main.equals("Claer")){
                    icon = R.drawable.sun;
                }else if(main.equals("Clouds")){
                    icon = R.drawable.cloud;
                }else if(main.equals("Rain")){
                    icon = R.drawable.rain;
                }else if(main.equals("Snow")){
                    icon = R.drawable.snow;
                }
                Weather weather = new Weather(countryName,
                        cityName,
                        morning,
                        day,
                        evening,
                        night,
                        date,
                        main,
                        description,icon);

                Log.v("LOG",weather.city +"  " +
                        weather.morning + "  " +
                        weather.day + "  " +
                        weather.evening +"  "+
                        weather.night +"  "+
                        weather.night +"  "+
                        weather.date + "  " +
                        weather.main + "  "+
                        weather.description
                );

                weathers.add(weather);
            }
        }
    }


    public void startLoadWeather(){
        weathers = new ArrayList<>();
        new DownloadFilesTask().execute(Data.weather);
    }


    public boolean isInternetAvailable() {
        try {
            ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

            return cm.getActiveNetworkInfo() != null;

        } catch (Exception e) {
            return false;
        }

    }
//
//    public boolean isGPSAvailable(){
//        final LocationManager manager = (LocationManager) getActivity().getSystemService( Context.LOCATION_SERVICE );
//
//        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
//            return false;
//        }else{
//            return true;
//        }
//    }


}
