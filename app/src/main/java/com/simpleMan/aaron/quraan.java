package com.simpleMan.aaron;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class quraan extends Fragment {

    private View view;
    private ImageView slideImages;
    private OnGetPositionListener listener;

    SliderAdapter sliderAdapter;
    ViewPager viewPager;

    int intBookmark;

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

        //Long click listener


        //Set position of item quraan
        if (getArguments() == null) {
            //Do nothing when arguments was null
        }else{
            int intData = getArguments().getInt("data");
            viewPager.setCurrentItem(intData);
        }

        //For bookmark icons, that display just on quraan fragment
        setHasOptionsMenu(true);

        //Return view from inflater
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.mymenu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }
}
