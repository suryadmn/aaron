package com.simpleMan.aaron;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;
    private OnPagerClickListener mListener;

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
            R.drawable.page50,
            R.drawable.page51,
            R.drawable.page52,
            R.drawable.page53,
            R.drawable.page54,
            R.drawable.page55,
            R.drawable.page56,
            R.drawable.page57,
            R.drawable.page58,
            R.drawable.page59,
            R.drawable.page60,
            R.drawable.page61,
            R.drawable.page62
    };

    @Override
    public int getCount() {
        return slide_images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    public interface OnPagerClickListener {
        void onPager(int position);
    }

    public void setOnPagerClickListener(OnPagerClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = view.findViewById(R.id.slideImages);
        slideImageView.setImageResource(slide_images[position]);
        slideImageView.setRotationY(180);
        container.addView(view);

        //getPosition(position);
        slideImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onLongClick(View view) {
                showAlertDialog(R.layout.bookmark_popup, position);
                return false;
            }
        });

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }

    //Function handle popup
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showAlertDialog(int layout, final int position){

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View layoutView = layoutInflater.inflate(layout, null);
        dialogBuilder = new AlertDialog.Builder(layoutView.getContext());
        Button saveButton = layoutView.findViewById(R.id.saveButton);

        dialogBuilder.setView(layoutView);
        dialogBuilder.create();
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                mListener.onPager(position);
                Toast.makeText(context, "Bookmark tersimpan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
