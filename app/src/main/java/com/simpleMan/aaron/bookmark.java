package com.simpleMan.aaron;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class bookmark extends Fragment {

    private View view;
    private RecyclerView mRecyclerView;
    private bookmarkAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<bookmarkItem> mBookmarkList;

    private Button buttonAdd, buttonSave;

    public bookmark() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bookmark, container, false);



        //initialize array list
        createBookmarkList();

        loadData();

        //Initialize RecyclerView and Stuff
        buildRecyclerView();

        //Set Buttons
        setButtons();

        return view;
    }

    public void insertItem(int position){
        mBookmarkList.add(position, new bookmarkItem(R.drawable.ic_bookmark_white_24dp, "Page  ", "New surah", "New juz"));
        mAdapter.notifyItemInserted(position);
    }

    public void removeItem(int position){
        mBookmarkList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void changeItem(int position, String text){
        mBookmarkList.get(position).changeText1(text);
        mAdapter.notifyItemChanged(position);
    }

    public void createBookmarkList(){
        mBookmarkList = new ArrayList<>();
    }


    public void buildRecyclerView() {
        mRecyclerView = view.findViewById(R.id.reyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new bookmarkAdapter(mBookmarkList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new bookmarkAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                changeItem(position, "Clicked");
            }

            @Override
            public void onDeleteCLick(int position) {
             removeItem(position);
            }
        });
    }

    public void setButtons(){
        buttonAdd = view.findViewById(R.id.btnAdd);
        buttonSave = view.findViewById(R.id.btnSave);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = 0;
                insertItem(position);

            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();

                //get info
                String data = saveData();
                Log.i("Info shared",""+data);
            }
        });
    }

    public String saveData(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Shared Preferences Bookmark", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mBookmarkList);
        editor.putString("key", json);
        editor.apply();
        return json;
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Shared Preferences Bookmark", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("key", null);
        Type type = new TypeToken<ArrayList<bookmarkItem>>() {}.getType();
        mBookmarkList = gson.fromJson(json, type);

        if (mBookmarkList == null){
            mBookmarkList = new ArrayList<>();
        }
    }
}
