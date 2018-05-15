package com.cw.mad.a00003741.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityMain extends AppCompatActivity implements View.OnClickListener, ListView.OnItemClickListener, TabLayout.OnTabSelectedListener{

    Toolbar toolbar;
    LinearLayout searchLayout;
    boolean isSearchOpen;
    ImageView search_back;
    ImageView search_clear;
    AutoCompleteTextView searchAutoText;
    ActionBar actionBar;
    boolean isKeyboardOpen = false;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    ListView listDrawer;
    DrawerAdapter drawerAdapter;
    String[] listOfPublishers;
    TabLayout tabLayout;
    ViewPager viewPager;
    HomePager adapter;
    FrameLayout fragmentPlaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        setUpSearchLayout();

        tabLayout = (TabLayout)findViewById(R.id.home_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Latest"));
        tabLayout.addTab(tabLayout.newTab().setText("Weather"));

        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);

        viewPager = (ViewPager)findViewById(R.id.home_viewpager);
        fragmentPlaceHolder = (FrameLayout)findViewById(R.id.placeHolderFragment);



        adapter = new HomePager(getSupportFragmentManager(),tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        Data.viewPager = viewPager;

        tabLayout.setOnTabSelectedListener(this);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        Data.showImage = true;

        setUpDrawer();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.arrow_back_button:
                onBackPressed();
                break;
            case R.id.clear_button:
                searchAutoText.setText("");
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.serach:
                isSearchOpen = true;
                searchLayout.setVisibility(View.VISIBLE);
                keyboardToggle(true);
                setUpSearch();
                break;
            case R.id.log_out:
                startActivity(new Intent(this,ActivitySignIn.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(isSearchOpen){
            isSearchOpen = false;
            searchLayout.setVisibility(View.GONE);
            keyboardToggle(false);
        }else{
            super.onBackPressed();
        }
    }

    public void setUpSearchLayout(){
        searchLayout = (LinearLayout)findViewById(R.id.linear_layout_search);
        search_back = (ImageView)findViewById(R.id.arrow_back_button);
        search_back.setOnClickListener(this);
        search_clear = (ImageView)findViewById(R.id.clear_button);
        search_clear.setOnClickListener(this);
        searchAutoText = (AutoCompleteTextView)findViewById(R.id.auto_text_search);
    }

    public void setUpSearch(){
        ArrayList<String> titlesOfNews = new ArrayList<>();
        ArrayList<News> news = Data.listOfNews;

        for(int i = 0, k = news.size(); i < k; i++) {
            titlesOfNews.add(news.get(i).title);
        }

        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.search_item,titlesOfNews);
        searchAutoText.setAdapter(adapter);

        searchAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txt = (TextView) view.findViewById(R.id.search_item_txt);
                String selectedTitle =  (String)txt.getText();
                Toast.makeText(ActivityMain.this,selectedTitle,Toast.LENGTH_SHORT).show();

                for(int i = 0, k = Data.listOfNews.size(); i < k; i++){
                    if(Data.listOfNews.get(i).title.equals(selectedTitle)){
                        Data.selectedIndex = i;
                        startSearchResultActivity();
                    }
                }
            }
        });
    }

    public void setUpFragment(Fragment fragment){
        fragmentPlaceHolder.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);

        Data.cloearFragment = true;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.placeHolderFragment,fragment).commit();
        Data.cloearFragment = false;

        FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
        ft2.replace(R.id.placeHolderFragment,fragment).commit();
    }

    public void startSearchResultActivity(){
        Intent intent = new Intent(this,SearchResultActivity.class);
        startActivity(intent);
    }

    public void keyboardToggle(boolean keyboardState){

        isKeyboardOpen = keyboardState;

//        if(isKeyboardOpen){
        InputMethodManager keyboard = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//            keyboard.showSoftInput(searchAutoText,InputMethodManager.SHOW_FORCED);
        searchAutoText.requestFocus();
        keyboard.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
//        }else{
//            InputMethodManager keyboard = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//            keyboard.hideSoftInputFromWindow(searchAutoText.getWindowToken(),0);
//        }
    }

    public void setUpDrawer(){

        listOfPublishers =  new String[]{
                "Home",
                "User",
                "BBC",
                "BBC-Sport",
                "Business-Insider",
                "CNN",
                "Google News",
                "NATIONAL GEOGRAPHIC",
                "THE ECONOMIST",
                "THE VERGE"};

        int[] images = {
                R.drawable.home,
                R.drawable.user,
                R.drawable.bbc_news_m,
                R.drawable.bbc_sport_m,
                R.drawable.business_insider_m,
                R.drawable.cnn_m,
                R.drawable.google_news_m,
                R.drawable.national_geographic_m,
                R.drawable.the_economist_m,
                R.drawable.the_verge_m
        };

        drawerLayout = (DrawerLayout)findViewById(R.id.activity_main);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        listDrawer = (ListView)findViewById(R.id.left_drawer);
        drawerAdapter = new DrawerAdapter(this,R.layout.drawer_item,listOfPublishers,images);
        listDrawer.setAdapter(drawerAdapter);
        listDrawer.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listDrawer.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                if(position == Data.drawerSelectedItem) break;
                Data.current_publisher = "top";
                setHomePage();
                setItem(position);
                break;
            case 1:
                if(position == Data.drawerSelectedItem) break;
                Data.current_publisher = "user";
                setItem(position);
                startActivity(new Intent(ActivityMain.this,UserProfile.class));
                break;
            case 2:
                if(position == Data.drawerSelectedItem) break;
                Data.current_publisher = "bbc";
                setUpFragment(new FragmentMain());
                setItem(position);
                break;
            case 3:
                if(position == Data.drawerSelectedItem) break;
                Data.current_publisher = "bbc-sport";
                setUpFragment(new FragmentMain());
                setItem(position);
                break;
            case 4:
                if(position == Data.drawerSelectedItem) break;
                Data.current_publisher = "business-insider";
                setUpFragment(new FragmentMain());
                setItem(position);
                break;
            case 5:
                if(position == Data.drawerSelectedItem) break;
                Data.current_publisher = "cnn";
                setUpFragment(new FragmentMain());
                setItem(position);
                break;
            case 6:
                if(position == Data.drawerSelectedItem) break;
                Data.current_publisher = "google";
                setUpFragment(new FragmentMain());
                setItem(position);
                break;
            case 7:
                if(position == Data.drawerSelectedItem) break;
                Data.current_publisher = "national-geographic";
                setUpFragment(new FragmentMain());
                setItem(position);
                break;
            case 8:
                if(position == Data.drawerSelectedItem) break;
                Data.current_publisher = "the-economist";
                setUpFragment(new FragmentMain());
                setItem(position);
                break;
            case 9:
                if(position == Data.drawerSelectedItem) break;
                Data.current_publisher = "the-verge";
                setItem(position);
                setUpFragment(new FragmentMain());
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void setItem(int selected){
        getSupportActionBar().setTitle(listOfPublishers[selected]);
        Data.drawerSelectedItem = selected;
        drawerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostResume() {
        getSupportActionBar().setTitle(listOfPublishers[0]);
        Data.drawerSelectedItem = 0;
        super.onPostResume();
    }

    public void setHomePage(){

        fragmentPlaceHolder.setVisibility(View.GONE);
        viewPager.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);
}


    //region On Tab Selected Listener
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
    //endregion

}

