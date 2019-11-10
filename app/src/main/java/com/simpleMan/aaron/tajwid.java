package com.simpleMan.aaron;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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

    @SuppressLint("ClickableViewAccessibility")
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

        /*//Get Coordinate
        mTajwid1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                a = (int) event.getX();
                b = (int) event.getY();

                Log.i("Info Coordinate","X : "+a+" Y : "+b);

                return false;
            }
        });*/

        /*//Do something when coordinate is clicked
        mTajwid1.setOnLongClickListener(new View.OnLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onLongClick(View v) {
                doCoordinate();
                return false;
            }
        });*/

        return view;
    }

    /**Setup coordinate*/
   @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
   public void doCoordinate(){
       int X = a, Y = b;

       //If user click at coordinate
       if (X <= 551 && Y <= 275){
           //Do nothing
       } else if (X <= 200 && Y <= 350){
           //Do nothing
       } else if (X <= 551 && Y <= 350){
           //showAlertDialog(R.layout.popup_layout);
           Toast.makeText(getContext(), "Al-Fatihaa | Ayat : 1", Toast.LENGTH_SHORT).show();

       } else if (X <= 551 && Y <= 380){
           //Do nothing
       } else if (X <= 200 && Y <= 450){
           //Do nothing
       } else if (X <= 270 && Y <= 450){
           Toast.makeText(getContext(), "Al-Fatihaa | Ayat : 3", Toast.LENGTH_SHORT).show();
       } else if (X <= 580 && Y <= 450){
           Toast.makeText(getContext(), "Al-Fatihaa | Ayat : 2", Toast.LENGTH_SHORT).show();

       } else if (X <= 430 && Y <= 460) {
           //Do nothing
       } else if (X <= 200 && Y <= 530){
           //Do nothing
       } else if (X <= 429 && Y <= 530) {
           Toast.makeText(getContext(), "Al-Fatihaa | Ayat : 4", Toast.LENGTH_SHORT).show();
       } else if (X <= 580 && Y <= 460) {
           //Do nothing
       } else if (X <= 445 && Y <= 530) {
           //Do nothing
       } else if (X <= 580 && Y <= 530) {
           Toast.makeText(getContext(), "Al-Fatihaa | Ayat : 3", Toast.LENGTH_SHORT).show();

       } else if (X <= 200 && Y <= 560) {
           //Do nothing
       } else if (X <= 200 && Y <= 620) {
           //Do nothing
       } else if (X <= 580 && Y <= 620) {
           Toast.makeText(getContext(), "Al-Fatihaa | Ayat : 5", Toast.LENGTH_SHORT).show();

       } else if (X <= 200  && Y <= 630) {
           //Do nothing
       } else if (X <= 200 && Y <= 710) {
           //Do nothing
       } else if (X <= 300 && Y <= 710) {
           Toast.makeText(getContext(), "Al-Fatihaa | Ayat : 7", Toast.LENGTH_SHORT).show();
       } else if (X <= 580 && Y <= 710) {
           Toast.makeText(getContext(), "Al-Fatihaa | Ayat : 6", Toast.LENGTH_SHORT).show();

       } else if (X <= 200 && Y <= 720) {
           //Do nothing
       } else if (X <= 200  && Y <= 780) {
           //Do nothing
       } else if (X <= 580  && Y <= 780) {
           Toast.makeText(getContext(), "Al-Fatihaa | Ayat : 7", Toast.LENGTH_SHORT).show();

       } else if (X <= 275  && Y <= 790) {
           //Do nothing
       } else if (X <= 275  && Y <= 875) {
           //Do nothing
       } else if (X <= 500 && Y <= 875) {
           Toast.makeText(getContext(), "Al-Fatihaa | Ayat : 7", Toast.LENGTH_SHORT).show();
       }
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
                alertDialog.dismiss();
            }
        });
    }

}
