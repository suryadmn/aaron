package com.simpleMan.aaron;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder > {
    private List<Content> contents = new ArrayList<>();

    @NonNull
    @Override
    public ContentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_item, parent, false);

        return new ContentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentHolder holder, int position) {
        Content currentContent = contents.get(position);
        holder.txtArab.setText(currentContent.getArab());
        holder.txtBahasa.setText(currentContent.getPenjelasan());
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    public void setContents(List<Content> contents){
        this.contents = contents;
        notifyDataSetChanged();
    }

    class ContentHolder extends RecyclerView.ViewHolder {
        private TextView txtArab;
        private TextView txtBahasa;
        private RelativeLayout relativeLayoutHolder;


        public ContentHolder(@NonNull View itemView) {
            super(itemView);

            txtArab = itemView.findViewById(R.id.txtArab);
            txtBahasa = itemView.findViewById(R.id.txtPenjelasan);
            relativeLayoutHolder = itemView.findViewById(R.id.contentHolder);
        }
    }

}
