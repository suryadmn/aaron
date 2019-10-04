package com.simpleMan.aaron;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class bookmarkAdapter extends RecyclerView.Adapter<bookmarkAdapter.BookmarkViewHolder> {

    private ArrayList<bookmarkItem> mBookmarkList;

    public static class BookmarkViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mTxt1;
        public TextView mTxt2;
        public TextView mTxt3;

        public BookmarkViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.imageView);
            mTxt1 = itemView.findViewById(R.id.textView1);
            mTxt2 = itemView.findViewById(R.id.textView2);
            mTxt3 = itemView.findViewById(R.id.textView3);
        }
    }

    public bookmarkAdapter(ArrayList<bookmarkItem> bookmarkList){

        mBookmarkList = bookmarkList;

    }

    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_item, parent, false);
        BookmarkViewHolder bvh = new BookmarkViewHolder(view);
        return bvh;
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder holder, int position) {

        bookmarkItem currentItem = mBookmarkList.get(position);

        holder.mImageView.setImageResource(currentItem.getmImageResource());
        holder.mTxt1.setText(currentItem.getmTxt1());
        holder.mTxt2.setText(currentItem.getmTxt2());
        holder.mTxt3.setText(currentItem.getmTxt3());
    }

    @Override
    public int getItemCount() {
        return mBookmarkList.size();
    }

}
