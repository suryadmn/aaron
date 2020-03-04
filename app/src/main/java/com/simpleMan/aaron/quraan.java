package com.simpleMan.aaron;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class quraan extends Fragment {

    private View view;
    private ImageView slideImages;
    private ArrayList<bookmarkItem> mBookmarkList  = new ArrayList<>();

    private SliderAdapter sliderAdapter;
    private ViewPager viewPager;
    private bookmarkAdapter mAdapter;

    private int mPager;
    private String strJuz;
    private String strSurah;

    public quraan() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**OnCreateView*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_quraan, container, false);

        //Initialize
        viewPager = (ViewPager) view.findViewById(R.id.slideViewPager);
        slideImages = (ImageView) view.findViewById(R.id.slideImages);

        //Call adapter slider
        sliderAdapter = new SliderAdapter(getContext());
        viewPager.setAdapter(sliderAdapter);
        viewPager.setRotationY(180);

        //Call adapter bookmark
        mAdapter = new bookmarkAdapter(mBookmarkList);

        //Load data arrayList
        loadData();

        //Set position of item quraan
        if (getArguments() == null) {
            //Do nothing when arguments was null
        }else{
            int intData = getArguments().getInt("data");
            viewPager.setCurrentItem(intData);
        }

        sliderAdapter.setOnPagerClickListener(new SliderAdapter.OnPagerClickListener() {
            @Override
            public void onPager(int position) {
                //Insert data to array mBookmarkList
                insertItem(0, position);

                //Save all data to sharedPref
                saveData();
            }
        });


        //Return view from inflater
        return view;
    }

    /**Insert data*/
    public void insertItem(int position, int pager){
        //For pager
        mPager = pager;
        mPager += 1;

        //Get string Txt Juz
        if (pager <= 20) {
            strJuz = "1";
        }else if (pager <= 40) {
            strJuz = "2";
        }else if (pager <= 60) {
            strJuz = "3";
        }

        //Get string Txt surah
        if (pager <= 0) {
            strSurah = "Al-Faatiha";
        }else if (pager <= 48){
            strSurah = "Al-Baqara";
        }

        //Insert data item
        mBookmarkList.add(position, new bookmarkItem(R.drawable.ic_bookmark_white_24dp, "Page", mPager, "|    juz  "+strJuz, "|   "+strSurah));
        mAdapter.notifyItemInserted(position);
    }

    /**Save data to SharedPref*/
    public String saveData(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Shared Preferences Bookmark", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mBookmarkList);
        editor.putString("key", json);
        editor.apply();
        return json;
    }

    /**Load data from SharedPef*/
    public void loadData(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Shared Preferences Bookmark", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("key", null);
        Type type = new TypeToken<ArrayList<bookmarkItem>>() {}.getType();
        mBookmarkList = gson.fromJson(json, type);

        if (mBookmarkList == null){
            mBookmarkList = new ArrayList<>();
        }
    }
}
