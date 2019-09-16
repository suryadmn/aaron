package com.simpleMan.aaron;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    //Arrays Images
    public int[] slide_images = {
            R.drawable.page1,
            R.drawable.page2,
            R.drawable.page3,
            R.drawable.page4,
            R.drawable.page5,
            R.drawable.page6,
            R.drawable.page7,
            R.drawable.page8,
            R.drawable.page9,
            R.drawable.page10,
            R.drawable.page11,
            R.drawable.page12,
            R.drawable.page13,
            R.drawable.page14,
            R.drawable.page15,
            R.drawable.page16,
            R.drawable.page17,
            R.drawable.page18,
            R.drawable.page19,
            R.drawable.page20,
            R.drawable.page21,
            R.drawable.page22,
            R.drawable.page23,
            R.drawable.page24,
            R.drawable.page25,
            R.drawable.page26,
            R.drawable.page27,
            R.drawable.page28,
            R.drawable.page29,
            R.drawable.page30,
            R.drawable.page31,
            R.drawable.page32,
            R.drawable.page33,
            R.drawable.page34,
            R.drawable.page35,
            R.drawable.page36,
            R.drawable.page37,
            R.drawable.page38,
            R.drawable.page39,
            R.drawable.page40,
            R.drawable.page41,
            R.drawable.page42,
            R.drawable.page43,
            R.drawable.page44,
            R.drawable.page45,
            R.drawable.page46,
            R.drawable.page47,
            R.drawable.page48,
            R.drawable.page49,
            R.drawable.page50
    };

    @Override
    public int getCount() {
        return slide_images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = view.findViewById(R.id.slideImages);
        slideImageView.setImageResource(slide_images[position]);
        slideImageView.setRotationY(180);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
