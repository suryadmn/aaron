package com.simpleMan.aaron;

import android.content.Intent;
import android.os.Bundle;

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

public class help extends Fragment {
    private View view;
    ArrayList<String> arrayLstView;

    public help() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_help, container, false);

        //Init OBJ
        ListView lstView = view.findViewById(R.id.listView);

        //Make array list
        arrayLstView = new ArrayList<>();
        arrayLstView.add("Tentang Menu");
        arrayLstView.add("Penggunaan Navigasi");
        arrayLstView.add("Penggunaan Bookmark");
        arrayLstView.add("Penggunaan Menu Tajwid");
        arrayLstView.add("Kontak Developer");

        //Set array adapter
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, arrayLstView);
        lstView.setAdapter(arrayAdapter);

        //call fun that handle clicked item on List View
        ListViewClicked(lstView);

        return view;
    }

    /**
    *Method for doing click listener at card view
    */
    public void ListViewClicked(ListView listView){

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(getContext(), ExplanationForMenu.class);
                        startActivity(intent);
                        break;
                    default:

                }
            }
        });
    }
}
