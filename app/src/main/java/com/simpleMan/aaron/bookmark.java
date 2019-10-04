package com.simpleMan.aaron;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class bookmark extends Fragment {

    private View view;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SharedPreferences sharedPreferences;

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
        ArrayList<bookmarkItem> bookmarkList = new ArrayList<>();
        bookmarkList.add(new bookmarkItem(R.drawable.ic_bookmark_white_24dp, "Page 1", "Al-Fatihaa", "Juz 1"));
        bookmarkList.add(new bookmarkItem(R.drawable.ic_bookmark_white_24dp, "Page 1", "Al-Fatihaa", "Juz 1"));
        bookmarkList.add(new bookmarkItem(R.drawable.ic_bookmark_white_24dp, "Page 1", "Al-Fatihaa", "Juz 1"));
        bookmarkList.add(new bookmarkItem(R.drawable.ic_bookmark_white_24dp, "Page 1", "Al-Fatihaa", "Juz 1"));
        bookmarkList.add(new bookmarkItem(R.drawable.ic_bookmark_white_24dp, "Page 1", "Al-Fatihaa", "Juz 1"));
        bookmarkList.add(new bookmarkItem(R.drawable.ic_bookmark_white_24dp, "Page 1", "Al-Fatihaa", "Juz 1"));
        bookmarkList.add(new bookmarkItem(R.drawable.ic_bookmark_white_24dp, "Page 1", "Al-Fatihaa", "Juz 1"));
        bookmarkList.add(new bookmarkItem(R.drawable.ic_bookmark_white_24dp, "Page 1", "Al-Fatihaa", "Juz 1"));
        bookmarkList.add(new bookmarkItem(R.drawable.ic_bookmark_white_24dp, "Page 1", "Al-Fatihaa", "Juz 1"));
        bookmarkList.add(new bookmarkItem(R.drawable.ic_bookmark_white_24dp, "Page 1", "Al-Fatihaa", "Juz 1"));
        bookmarkList.add(new bookmarkItem(R.drawable.ic_bookmark_white_24dp, "Page 1", "Al-Fatihaa", "Juz 1"));
        bookmarkList.add(new bookmarkItem(R.drawable.ic_bookmark_white_24dp, "Page 1", "Al-Fatihaa", "Juz 1"));
        bookmarkList.add(new bookmarkItem(R.drawable.ic_bookmark_white_24dp, "Page 1", "Al-Fatihaa", "Juz 1"));
        bookmarkList.add(new bookmarkItem(R.drawable.ic_bookmark_white_24dp, "Page 1", "Al-Fatihaa", "Juz 1"));
        bookmarkList.add(new bookmarkItem(R.drawable.ic_bookmark_white_24dp, "Page 1", "Al-Fatihaa", "Juz 1"));
        bookmarkList.add(new bookmarkItem(R.drawable.ic_bookmark_white_24dp, "Page 1", "Al-Fatihaa", "Juz 1"));
        bookmarkList.add(new bookmarkItem(R.drawable.ic_bookmark_white_24dp, "Page 1", "Al-Fatihaa", "Juz 1"));
        bookmarkList.add(new bookmarkItem(R.drawable.ic_bookmark_white_24dp, "Page 1", "Al-Fatihaa", "Juz 1"));
        bookmarkList.add(new bookmarkItem(R.drawable.ic_bookmark_white_24dp, "Page 1", "Al-Fatihaa", "Juz 1"));
        bookmarkList.add(new bookmarkItem(R.drawable.ic_bookmark_white_24dp, "Page 1", "Al-Fatihaa", "Juz 1"));
        bookmarkList.add(new bookmarkItem(R.drawable.ic_bookmark_white_24dp, "Page 1", "Al-Fatihaa", "Juz 1"));

        //Initialize RecylerVIew and Stuff
        mRecyclerView = view.findViewById(R.id.reyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new bookmarkAdapter(bookmarkList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}
