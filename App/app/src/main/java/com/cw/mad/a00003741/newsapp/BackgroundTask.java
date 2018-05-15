package com.cw.mad.a00003741.newsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static com.cw.mad.a00003741.newsapp.Data.showImage;

public class BackgroundTask extends AsyncTask<String,News,List<News>> {


    Context context;
    ExpandableListView expandableListView;
    ExpandableAdapter adapter;
    RelativeLayout progressBar;
    boolean isCancelled;

    public BackgroundTask(Context context, ExpandableListView expandableListView, RelativeLayout progressBar){
        this.context = context;
        this.expandableListView = expandableListView;

        adapter = new ExpandableAdapter(context,Data.listOfNews);

        expandableListView.setAdapter(adapter);
        this.progressBar = progressBar;

    }
    @Override
    protected void onPreExecute() {
        Data.listOfNews.clear();
        adapter.notifyDataSetChanged();
        if(isInternetAvailable()){
            isCancelled = false;
            progressBar.setVisibility(View.VISIBLE);
            Data.noConnection.setVisibility(View.GONE);
            Log.v("Internet Connection", " " + isInternetAvailable());
        }else{
            Log.v("Internet Connection", " " + isInternetAvailable());
            isCancelled = true;
            Data.noConnection.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected List<News> doInBackground(String... params) {

        if (isCancelled) return null;
        InputStream inputStream = null;
        HttpURLConnection connection = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection)url.openConnection();
            inputStream = connection.getInputStream();

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String s;

            while((s = bufferedReader.readLine()) != null){
                stringBuilder.append(s);

            }

            inputStream.close();
            bufferedReader.close();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connection.disconnect();
        }
        return JsonToObject(stringBuilder.toString());
    }

    @Override
    protected void onProgressUpdate(News... values) {
        if(isCancelled) return;

        Log.v("LOOG", "UPDATE");
        progressBar.setVisibility(View.GONE);

        Data.listOfNews.add(values[0]);
        adapter.notifyDataSetChanged();
    }




    public List<News> JsonToObject(String jsonString){
        try {
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(jsonString);

            if(element.isJsonObject()){
                JsonObject jsonObject = element.getAsJsonObject();
                String source = jsonObject.get("source").getAsString();

                JsonArray articles = jsonObject.get("articles").getAsJsonArray();

                for(int i = 0, k = articles.size(); i < k; i++){
                    JsonObject article = articles.get(i).getAsJsonObject();

                    String author = JsonToString(article.get("author"));
                    String title = JsonToString(article.get("title"));
                    String description = JsonToString(article.get("description"));
                    String url = JsonToString(article.get("url"));
                    String urlToImage = JsonToString(article.get("urlToImage"));
                    Bitmap bitmap = null;
                    if(showImage){
                        bitmap = getBitmap(urlToImage);
                    }
                    String date = JsonToString(article.get("publishedAt"));

                    News news = new News(source,author,title,description,url,bitmap,date);
                    publishProgress(news);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.v("LOOOOOG", "I FUCKED UP");
        }

        return Data.listOfNews;
    }



    public String JsonToString(JsonElement element){
        if(!element.isJsonNull()){
            return element.getAsString();
        }
        return "";
    }

    // take  a url
    // download image in the following url
    // return as a bitmap
    public Bitmap getBitmap(String sUrl){
        HttpURLConnection connection = null;
        try {
            URL url = new URL(sUrl);
            connection = (HttpURLConnection)url.openConnection();
//            connection.connect();
            InputStream inputStream = connection.getInputStream();
            return decodeSampledBitmapFromStream(inputStream);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
//            connection.disconnect();
        }
    }

//    public int calculateInSampleSize(BitmapFactory.Options options, int reqHeight){
//        final int height = options.outHeight;
//        final int width = options.outWidth;
//        Log.v("LOG", "height: " + height + "  width: "+ width);
//        int inSampeSize = 1;
//
//        if(height > reqHeight){
//            final int halfHeight = height / 2;
//            final int halfWidth = width / 2;
//
//            while ((halfHeight/ inSampeSize) >= reqHeight){
//                inSampeSize *= 2;
//            }
//        }
//        Log.v("LOG", "inSampleSize: " + inSampeSize);
//        return inSampeSize;
//    }
//
//    public Bitmap decodeSampledBitmapFromStream(InputStream is){
//        try{
//            final BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true;
//            BufferedInputStream buffer = new BufferedInputStream(is);
//            BitmapFactory.decodeStream(buffer,null,options);
//
//            options.inSampleSize  = calculateInSampleSize(options,100);
//
//            options.inJustDecodeBounds = false;
//            return BitmapFactory.decodeStream(buffer,null,options);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return null;
//    }

    public Bitmap decodeSampledBitmapFromStream(InputStream is){
        try{
            Bitmap bitmap = BitmapFactory.decodeStream(is);

            int height = bitmap.getHeight();
            int width = bitmap.getWidth();



            while (height >= 100){
                height /= 2;
                width /= 2;
            }
            Log.v("LOG", "height: " + height + "  width: "+ width);

            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap,width,height,false);
            return resizedBitmap;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean isInternetAvailable() {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            return cm.getActiveNetworkInfo() != null;

        } catch (Exception e) {
            return false;
        }

    }
}

