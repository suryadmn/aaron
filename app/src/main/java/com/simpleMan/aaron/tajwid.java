package com.simpleMan.aaron;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class tajwid extends Fragment {

    private View view;

    Button mark1;
    Button popup1;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

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

        mark1 = view.findViewById(R.id.mark1);
        popup1 = view.findViewById(R.id.btnDialog);

        mark1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                //Calling popup function
                showAlertDialog(R.layout.popup_layout);
                /*Toast.makeText(getContext(), "You Clicked Me!", Toast.LENGTH_LONG).show();*/
            }
        });

        return view;
    }

    //Function handle popup
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
