package com.cw.mad.a00003741.newsapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

public class FragmentSearchResult extends Fragment {

    ExpandableListView expandableListView;
    ExpandableAdapter expandableAdapter;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main,container,false);

        expandableListView = (ExpandableListView)rootView.findViewById(R.id.expandable_list_view);
        context = getActivity();
        List<News> newsList = new ArrayList<>();
        News news = Data.listOfNews.get(Data.selectedIndex);
        newsList.add(news);
        setUpListView(newsList);

        return rootView;
    }

    public void setUpListView(List<News> newsList){
        expandableAdapter = new ExpandableAdapter(context,newsList);
        expandableAdapter.notifyDataSetChanged();
        expandableListView.setAdapter(expandableAdapter);
    }
}
