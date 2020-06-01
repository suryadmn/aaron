package com.simpleMan.aaron;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class tajwid extends Fragment {

    private View view;
    private TajwidSliderAdapter tajwidSliderAdapter;
    private ViewPager viewPager;

    private Button popup1;
    public ImageView slideImages;
    private ImageView mTajwid1;

    public tajwid() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tajwid, container, false);

        //Initialize obj for slider
        viewPager = (ViewPager) view.findViewById(R.id.slideTajwidViewPager);
        slideImages = (ImageView) view.findViewById(R.id.slideTajwidImages);

        //Initialize obj from layout
        mTajwid1 = view.findViewById(R.id.slideViewPager);


        //Call adapter slider
        tajwidSliderAdapter = new TajwidSliderAdapter(getContext());
        viewPager.setAdapter(tajwidSliderAdapter);
        viewPager.setRotationY(180);

        return view;
    }
}
