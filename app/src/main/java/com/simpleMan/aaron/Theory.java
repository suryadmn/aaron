package com.simpleMan.aaron;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simpleMan.aaron.Theory_Menus.Theory_Huruf_Activity;


public class Theory extends Fragment {
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

        //Call method ClickedCardView
        ClickedAtCardView(view);

        return view;
    }


    /**
     * Function for Card View
     * It's handle initialize variable and Clicking method
     */
    public void ClickedAtCardView(View view){
        //Initialize obj
        theoryHuruf = view.findViewById(R.id.card1);
        theoryHarakat = view.findViewById(R.id.card2);
        basicAdvancedTajwid = view.findViewById(R.id.card3);
        exercise = view.findViewById(R.id.card4);

        //When click at card view
        theoryHuruf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Theory_Huruf_Activity.class);
                startActivity(intent);
            }
        });
    }
}
