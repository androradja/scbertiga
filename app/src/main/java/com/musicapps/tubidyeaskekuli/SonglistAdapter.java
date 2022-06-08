package com.musicapps.tubidyeaskekuli;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.musicapps.tubidyeaskekuli.databinding.ItemSong1Binding;

import java.util.ArrayList;
import java.util.List;

public class SonglistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SongModel> listitems = new ArrayList<>();
    private Activity context;
    int selectedKey = -1;
    boolean padding=false;
    int maxsize;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onMoreClick(SongModel songModel);
    }

    int menu;

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public SonglistAdapter(List<SongModel> listitems, Activity context,int maxsize) {
        this.listitems = listitems;
        this.context = context;
        this.maxsize=maxsize;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MyViewHolder(ItemSong1Binding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));


    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ItemSong1Binding item;

        public MyViewHolder(ItemSong1Binding item) {
            super(item.getRoot());
            this.item = item;
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final MyViewHolder view = (MyViewHolder) holder;
        SongModel songModel = listitems.get(position);

        view.item.title.setText(songModel.getTitle());
        view.item.artist.setText(songModel.getArtist());
        view.item.duration.setText(MusicUtills.milliSecondsToTimerstatic(Long.parseLong(songModel.getDuration())));

        if (songModel.getArtist().equals("null")){
            view.item.artist.setText(songModel.getTitle());
        }


        view.item.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ads ads= new Ads(context);
                ads.showinters();
                ads.setadsListener(new Ads.adsListener() {
                    @Override
                    public void onAdsfinish() {
                        mOnItemClickListener.onItemClick(position);

                    }
                });
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
        if (listitems.size()>maxsize){
            return maxsize;
        }
        else return listitems.size();

    }

}