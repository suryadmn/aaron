package com.simpleMan.aaron;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

public class tajwid extends Fragment {

    private View view;
    private TajwidSliderAdapter tajwidSliderAdapter;
    private ViewPager viewPager;

    private Button popup1;
    public ImageView slideImages;
    private ImageView mTajwid1;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;

    private int a, b;

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
        popup1 = view.findViewById(R.id.btnDialog);
        mTajwid1 = view.findViewById(R.id.slideViewPager);


        //Call adapter slider
        tajwidSliderAdapter = new TajwidSliderAdapter(getContext());
        viewPager.setAdapter(tajwidSliderAdapter);
        viewPager.setRotationY(180);

        //get position pager from adapter
        tajwidSliderAdapter.setOnTawjidClickListener(new TajwidSliderAdapter.OnTajwidClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTajwid(int position) {

            }
        });

        return view;
    }

    /**Function handle popup*/
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showAlertDialog(int layout){
        dialogBuilder = new AlertDialog.Builder(getContext());
        View layoutView = getLayoutInflater().inflate(layout, null);
        Button dialogButton = layoutView.findViewById(R.id.btnDialog);
        dialogBuilder.setView(layoutView);
        dialogBuilder.create();
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //alertDialog.dismiss();
            }
        });
    }
}
