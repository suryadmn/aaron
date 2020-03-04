package com.simpleMan.aaron;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.simpleMan.aaron.Bantuan_Menus.ExplanationForMenu;

import java.util.ArrayList;


public class Theory extends Fragment {
    private View view;
    private CardView theoryHuruf, theoryHarakat, basicAdvancedTajwid, exercise;


    public Theory() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_theory, container, false);

        //Initialize obj
        theoryHuruf = view.findViewById(R.id.card1);
        theoryHarakat = view.findViewById(R.id.card2);
        basicAdvancedTajwid = view.findViewById(R.id.card3);
        exercise = view.findViewById(R.id.card4);

        //Call method ClickedCardView
        ClickedAtCardView(view);

        return view;
    }


    /**
     * Fun for item ListView
     * It's handle something, when item on arrayList clicked
     */
    public void ClickedAtCardView(View view){
        //When click at card view
        /*howtoUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), HowtoUseAppActivity.class);
                getActivity().startActivity(myIntent);
            }
        });*/
    }
}
