package com.cw.mad.a00003741.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;


public class ExpandableAdapter extends BaseExpandableListAdapter {

    Context context;
    List<News> listOfNews;
    public ExpandableAdapter(Context context, List<News> listOfNews){
        this.context = context;
        this.listOfNews = listOfNews;
    }

    @Override
    public int getGroupCount() {
        return listOfNews.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listOfNews.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listOfNews.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expandable_list_group,null);
        }

        if(Data.showImage){
            ImageView img = (ImageView)convertView.findViewById(R.id.group_item_img);
            img.setImageBitmap(listOfNews.get(groupPosition).icon);
        }

        TextView textTitle = (TextView)convertView.findViewById(R.id.group_item_title);
        TextView textDate = (TextView)convertView.findViewById(R.id.group_item_date);

        textTitle.setText(listOfNews.get(groupPosition).title);
        textDate.setText(listOfNews.get(groupPosition).publishDate);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expandable_list_child,null);
        }

        TextView textDescription = (TextView)convertView.findViewById(R.id.group_item_child_description);
        textDescription.setText(listOfNews.get(groupPosition).description);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
