package com.simpleMan.aaron;

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
import android.widget.Toast;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class quraan extends Fragment {

    private View view;

    SliderAdapter sliderAdapter;
    ViewPager viewPager;
    GestureDetector tapGestureDetector;

    public quraan() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_quraan, container, false);

        //Initialize
        viewPager = (ViewPager) view.findViewById(R.id.slideViewPager);

        //Call adapter slider
        sliderAdapter = new SliderAdapter(getContext());
        viewPager.setAdapter(sliderAdapter);
        viewPager.setRotationY(180);

        /*viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Click!", Toast.LENGTH_SHORT).show();
            }
        });*/

        //Set position of item quraan
        if (getArguments() == null) {
            //Do nothing when arguments was null
        }else{
            int intData = getArguments().getInt("data");
            viewPager.setCurrentItem(intData);
        }

        //Toast.makeText(getContext(),"Click!", Toast.LENGTH_SHORT).show();

        //Return view from inflater

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.mymenu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }
}
