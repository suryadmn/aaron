package com.simpleMan.aaron;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import java.util.ArrayList;

public class bookmarkAdapter extends RecyclerView.Adapter<bookmarkAdapter.BookmarkViewHolder> {

    private ArrayList<bookmarkItem> mBookmarkList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteCLick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public class BookmarkViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public ImageView mImageDelete;
        public TextView mTxt1;
        public TextView mTxt2;
        public TextView mTxt3;
        public TextView mTxt4;

        public BookmarkViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.imageView);
            mImageDelete = itemView.findViewById(R.id.img_delete);
            mTxt1 = itemView.findViewById(R.id.textView1);
            mTxt2 = itemView.findViewById(R.id.textView2);
            mTxt3 = itemView.findViewById(R.id.textView3);
            mTxt4 = itemView.findViewById(R.id.textView4);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            mImageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onDeleteCLick(position);
                            saveData(view);

                            //Info
                            Log.i("Info array",""+saveData(view));
                        }
                    }
                }
            });
        }
    }

    public bookmarkAdapter(ArrayList<bookmarkItem> bookmarkList){
        mBookmarkList = bookmarkList;
    }

    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_item, parent, false);
        BookmarkViewHolder bvh = new BookmarkViewHolder(view, mListener);
        return bvh;
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder holder, int position) {
        bookmarkItem currentItem = mBookmarkList.get(position);
        String dataPosition = String.valueOf(mBookmarkList.get(position).getmTxt2());

        holder.mImageView.setImageResource(currentItem.getmImageResource());
        holder.mTxt1.setText(currentItem.getmTxt1());
        holder.mTxt2.setText(dataPosition);
        holder.mTxt3.setText(currentItem.getmTxt3());
        holder.mTxt4.setText(currentItem.getmTxt4());
    }

    @Override
    public int getItemCount() {
        return mBookmarkList.size();
    }

    public String saveData(View view){
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("Shared Preferences Bookmark", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mBookmarkList);
        editor.putString("key", json);
        editor.apply();
        return json;
    }

}
