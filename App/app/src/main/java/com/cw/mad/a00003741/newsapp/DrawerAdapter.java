package com.cw.mad.a00003741.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawerAdapter extends ArrayAdapter<String>{
    String[] list;
    Context context;
    int[] images;

    public DrawerAdapter(Context context, int resource, String[] objects, int[] images) {
        super(context, resource, objects);
        this.list = objects;
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.drawer_item,null);
        }

        if(position == Data.drawerSelectedItem){
            convertView.setBackgroundColor(context.getResources().getColor(R.color.colorLightGrey));
        }else{
            convertView.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
        }

        if(position == 0 | position == 1){
            convertView.setPadding(10,10,10,10);
        }
        ImageView img = (ImageView)convertView.findViewById(R.id.drawer_item_img);
        img.setImageResource(images[position]);

        TextView textTitle = (TextView)convertView.findViewById(R.id.drawer_item_text);
        textTitle.setText(list[position]);

        return convertView;
    }

}
