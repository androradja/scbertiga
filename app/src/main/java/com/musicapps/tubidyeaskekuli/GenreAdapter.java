package com.musicapps.tubidyeaskekuli;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.musicapps.tubidyeaskekuli.databinding.ItemGenreBinding;

import java.util.ArrayList;
import java.util.List;


public class GenreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> listitems = new ArrayList<>();
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    int selected=0;
    public interface OnItemClickListener {
        void onItemClick(int position);

    }


    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public GenreAdapter(List<String> listitems, Context context) {
        this.listitems = listitems;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MyViewHolder(ItemGenreBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));


    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ItemGenreBinding item;
        public MyViewHolder(ItemGenreBinding item) {
            super(item.getRoot());
            this.item = item;
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final MyViewHolder view = (MyViewHolder) holder;
        String genre = listitems.get(position);
        view.item.genre.setText(genre);

        if (selected==position){
            view.item.bg.setImageResource(R.drawable.circle);
            view.item.genre.setTextColor(ContextCompat.getColor(context, R.color.white));
        }
        else {
            view.item.bg.setImageResource(R.drawable.circle1);
            view.item.genre.setTextColor(ContextCompat.getColor(context,R.color.accent));
        }


        view.item.main.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mOnItemClickListener.onItemClick(position);
                selected=position;
                notifyDataSetChanged();
            }
        });






//        try {
//            if (songModel.getId() == MusicService.currentSongModel.getId()) {
//                view.item.option.setImageResource(R.drawable.btn_addsong2);
//                view.item.artist.setTextColor(ContextCompat.getColor(context,R.color.white));
//                view.item.duration.setTextColor(ContextCompat.getColor(context,R.color.white));
//                view.item.separator.setImageResource(R.drawable.separator2);
//                view.item.lirik.setImageResource(R.drawable.label_lyrics2);
//                view.item.animeactive.setVisibility(View.VISIBLE);
//                view.item.active.setVisibility(View.VISIBLE);
//
//
//
//            } else {
//                view.item.option.setImageResource(R.drawable.btn_addsong);
//                view.item.artist.setTextColor(ContextCompat.getColor(context,R.color.abu2));
//                view.item.duration.setTextColor(ContextCompat.getColor(context,R.color.abu1));
//                view.item.separator.setImageResource(R.drawable.separator);
//                view.item.animeactive.setVisibility(View.GONE);
//                view.item.active.setVisibility(View.GONE);
//                view.item.lirik.setImageResource(R.drawable.label_lyrics);
//
//
//
//
//
//            }
//
//        } catch (Exception e) {
//
//        }



    }


    @Override
    public int getItemCount() {
        return listitems.size();

    }

}