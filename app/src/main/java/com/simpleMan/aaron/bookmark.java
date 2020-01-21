package com.simpleMan.aaron;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class bookmark extends Fragment {

    private View view;
    private RecyclerView mRecyclerView;
    private bookmarkAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<bookmarkItem> mBookmarkList = null;
    private Bundle bundle;

    public bookmark() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bookmark, container, false);

        //initialize array list
        createBookmarkList();

        //Load data from shared and show it to view
        loadData();

        //Initialize RecyclerView and Stuff
        buildRecyclerView();

        return view;
    }

    /**Remove item from bookmarkList*/
    public void removeItem(int position){
        mBookmarkList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    /**Create object BookmarkList*/
    public void createBookmarkList(){
        mBookmarkList = new ArrayList<>();
    }

    /**Build RecyclerView*/
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
                int mPosition = mBookmarkList.get(position).getmTxt2();
                mPosition -= 1;

                //Send data to bundle quraan
                bundle = new Bundle();
                bundle.putInt("data",mPosition);
                quraan fragmentQuraan = new quraan();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragmentQuraan).commit();
                fragmentQuraan.setArguments(bundle);
                FragmentActivity actionBar = getActivity();
                actionBar.setTitle("Al-Qur'anul Karim");

                //Info
                Log.i("Position mBookmark",""+mPosition);
            }

            @Override
            public void onDeleteCLick(int position) {
             removeItem(position);
            }
        });
    }

    /**Load data from SharedPref*/
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public String loadData(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Shared Preferences Bookmark", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("key", null);
        Type type = new TypeToken<ArrayList<bookmarkItem>>() {}.getType();
        mBookmarkList = gson.fromJson(json, type);

        //Check data array from bookmarkList are empty or null
        if (mBookmarkList == null || mBookmarkList.isEmpty()){
            showAlertDialog(R.layout.no_bookmark_popup);
        }

        return json;
    }

    /**Function handle popup*/
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showAlertDialog(int layout){

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
        View layoutView = layoutInflater.inflate(layout, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(layoutView.getContext());
        Button addButton = layoutView.findViewById(R.id.addButton);

        dialogBuilder.setView(layoutView);
        dialogBuilder.create();
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //Show action when addButton was clicked
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                //Go to fragment quraan when addButton click
                quraan fragmentQuraan = new quraan();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragmentQuraan).commit();
                fragmentQuraan.setArguments(bundle);
                FragmentActivity actionBar = getActivity();
                actionBar.setTitle("Al-Qur'anul Karim");

                //Close popup when addButton clicked
                alertDialog.dismiss();
            }
        });
    }
}
