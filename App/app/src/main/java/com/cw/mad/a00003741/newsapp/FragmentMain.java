package com.cw.mad.a00003741.newsapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.HashMap;

public class FragmentMain extends Fragment{

    Context context;
    ExpandableListView expandableListView;
    HashMap<String,String> hashMap;
    RelativeLayout progressBar;
    LinearLayout noConnection;
    Button btnTry;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main,container,false);
        context = getActivity();
        expandableListView = (ExpandableListView)rootView.findViewById(R.id.expandable_list_view);
        progressBar = (RelativeLayout)rootView.findViewById(R.id.progress_bar);
        hashMap = new HashMap<>();
        noConnection = (LinearLayout)rootView.findViewById(R.id.no_connection_layout);
        Data.noConnection = this.noConnection;
        btnTry = (Button)rootView.findViewById(R.id.btn_try);
        btnTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoadingNews();
            }
        });

        hashMap.put("top","https://newsapi.org/v1/articles?source=the-guardian-uk&sortBy=latest&apiKey=a63f12f0b15941349221166bb09844f6");
        hashMap.put("bbc","https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=a63f12f0b15941349221166bb09844f6");
        hashMap.put("bbc-sport","https://newsapi.org/v1/articles?source=bbc-sport&sortBy=top&apiKey=a63f12f0b15941349221166bb09844f6");
        hashMap.put("business-insider","https://newsapi.org/v1/articles?source=business-insider&sortBy=top&apiKey=a63f12f0b15941349221166bb09844f6");
        hashMap.put("cnn","https://newsapi.org/v1/articles?source=cnn&sortBy=top&apiKey=a63f12f0b15941349221166bb09844f6");
        hashMap.put("google","https://newsapi.org/v1/articles?source=google-news&sortBy=top&apiKey=a63f12f0b15941349221166bb09844f6");
        hashMap.put("national-geographic","https://newsapi.org/v1/articles?source=national-geographic&sortBy=top&apiKey=a63f12f0b15941349221166bb09844f6");
        hashMap.put("the-economist","https://newsapi.org/v1/articles?source=the-economist&sortBy=top&apiKey=a63f12f0b15941349221166bb09844f6");
        hashMap.put("the-verge","https://newsapi.org/v1/articles?source=the-verge&sortBy=top&apiKey=a63f12f0b15941349221166bb09844f6");

        startLoadingNews();

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                for (int i = 0, k = parent.getCount(); i < k; i++){
                    if(i == groupPosition){
                        parent.expandGroup(i);
                    }else{
                        parent.collapseGroup(i);
                    }
                }
                return true;
            }
        });
        return rootView;
    }

    public void startLoadingNews(){
        if(Data.cloearFragment){
            ExpandableAdapter adapter = null;
            expandableListView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else{
            BackgroundTask backgroundTask = new BackgroundTask(context, expandableListView,progressBar);
            backgroundTask.execute(hashMap.get(Data.current_publisher));
        }
    }
}
