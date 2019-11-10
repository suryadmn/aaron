package com.simpleMan.aaron;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class TajwidSliderAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;

    public TajwidSliderAdapter(Context context) {
        this.context = context;
    }

    //Array images
    public int[] slider_Images = {
      R.drawable.tajwid1,
      R.drawable.tajwid2,
      R.drawable.tajwid3,
      R.drawable.tajwid4,
      R.drawable.tajwid5
    };

    @Override
    public int getCount() {
        return slider_Images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_tajwid_layout, container, false);

        ImageView slideImageView = view.findViewById(R.id.slideTajwidImages);
        slideImageView.setImageResource(slider_Images[position]);
        slideImageView.setRotationY(180);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
