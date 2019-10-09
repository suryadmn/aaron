package com.simpleMan.aaron;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class quraan extends Fragment {

    private View view;
    private ImageView slideImages;
    private OnGetPositionListener listener;
    private ArrayList<bookmarkItem> mBookmarkList  = new ArrayList<>();

    private SliderAdapter sliderAdapter;
    private ViewPager viewPager;
    private bookmarkAdapter mAdapter;

    public quraan() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void setOnGetPositionListener(OnGetPositionListener listener){
        this.listener = listener;
        getPagerPosition();
    }

    void getPagerPosition(){
        listener.getPosition(viewPager.getCurrentItem());
    }

    interface OnGetPositionListener{
        void getPosition(int position);
    }

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

        //For bookmark icons, that display just on quraan fragment
        setHasOptionsMenu(true);

        sliderAdapter.setOnPagerClickListener(new SliderAdapter.OnPagerClickListener() {
            @Override
            public void onPager(int position) {
                insertItem(0);
                saveData();


                //info
                String dataInfo = saveData();
                Log.i("Info data ArrayList",""+dataInfo);
                Log.i("info pager",""+position);
            }
        });

        //Return view from inflater
        return view;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.mymenu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    public void insertItem(int position){
        mBookmarkList.add(position, new bookmarkItem(R.drawable.ic_bookmark_white_24dp, "Page  ", "New surah", "New juz"));
        mAdapter.notifyItemInserted(position);
    }

    public String saveData(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Shared Preferences Bookmark", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mBookmarkList);
        editor.putString("key", json);
        editor.apply();
        return json;
    }

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
