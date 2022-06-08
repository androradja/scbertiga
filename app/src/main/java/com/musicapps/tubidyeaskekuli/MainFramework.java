package com.musicapps.tubidyeaskekuli;


import static com.musicapps.tubidyeaskekuli.Static.ACTIONNAME;
import static com.musicapps.tubidyeaskekuli.Static.MUSICCONTROLL;
import static com.musicapps.tubidyeaskekuli.Static.NEXT_BUTTON;
import static com.musicapps.tubidyeaskekuli.Static.PLAY_BUTTON;
import static com.musicapps.tubidyeaskekuli.Static.PREV_BUTTON;
import static com.musicapps.tubidyeaskekuli.Static.SEEK_BUTTON;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.musicapps.tubidyeaskekuli.notifservice.CreateNotification;
import com.musicapps.tubidyeaskekuli.ui.DownloadedFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

public class MainFramework {
    public static void setBottomnav(BottomNavigationView nav, FragmentManager fm, Fragment homefragment, Fragment allsongfragment, Fragment genrefragment, Fragment searchfragment , DownloadedFragment downloadedFragment){
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            ConstraintSet constraintSet = new ConstraintSet();

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_menu:
                        Route.gotofragmentbotnav(fm,homefragment);
                        return true;
                    case R.id.allsong:
                        Route.gotofragmentbotnav(fm, allsongfragment);
                        return true;
                    case R.id.genre:
                        Route.gotofragmentbotnav(fm, genrefragment);
                        return true;

                    case R.id.search_menu:
                        Route.gotofragmentbotnav(fm, searchfragment);
                        return true;
                    case R.id.downloaded:
                        Route.gotofragmentbotnav(fm, downloadedFragment);
                        return true;


                }
                return false;
            }
        });

    }

    public static void  setMiniPlayer(View miniPlayer, IndicatorSeekBar seekbar, Context context, ImageButton play, ImageButton next, ImageButton prev, TextView title, TextView artist, ImageView cover, ProgressBar progressBar){

        miniPlayer.setVisibility(View.GONE);
        seekbar.setProgress(0);
        seekbar.setMax(MusicUtills.MAX_PROGRESS);
        seekbar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                if(seekParams.fromUser){
                    seekbar.setProgress(seekParams.progress);
                    double currentseek = ((double) seekParams.progress/(double)MusicUtills.MAX_PROGRESS);
                    int totaldura= (int) MusicService.totalduration;
                    int seek= (int) (totaldura*currentseek);
                    Intent intent = new Intent(MUSICCONTROLL);
                    intent.putExtra(ACTIONNAME, SEEK_BUTTON);
                    intent.putExtra("seekto",seek);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });


        BroadcastRes broadcastRes= new BroadcastRes();
        broadcastRes.MusicState(context);
        broadcastRes.setOnMusicStateChange(new BroadcastRes.OnMusicStateChange() {
            @Override
            public void onPlaying() {
                play.setImageResource(R.drawable.ic_pause);
                title.setText(MusicService.currentSongModel.getTitle());
                artist.setText(MusicService.currentSongModel.getArtist());
                progressBar.setVisibility(View.INVISIBLE);
                play.setVisibility(View.VISIBLE);

                Helper.displayImage(context,cover, MusicService.currentSongModel.getImage(),R.drawable.defaultimage);
            }

            @Override
            public void onNext() {
                play.setImageResource(R.drawable.ic_pause);
                title.setText(MusicService.currentSongModel.getTitle());
                artist.setText(MusicService.currentSongModel.getArtist());

                Helper.displayImage(context,cover, MusicService.currentSongModel.getImage(),R.drawable.defaultimage);
            }

            @Override
            public void onPrev() {
                play.setImageResource(R.drawable.ic_pause);
                title.setText(MusicService.currentSongModel.getTitle());
                artist.setText(MusicService.currentSongModel.getArtist());

                Helper.displayImage(context,cover, MusicService.currentSongModel.getImage(),R.drawable.defaultimage);
            }

            @Override
            public void onPrepare() {
                miniPlayer.setVisibility(View.VISIBLE  );
                progressBar.setVisibility(View.VISIBLE);
                play.setVisibility(View.INVISIBLE);


            }

            @Override
            public void onPause() {
                play.setImageResource(R.drawable.ic_play);

            }

            @Override
            public void onStop() {

            }

            @Override
            public void onResume() {
                play.setImageResource(R.drawable.ic_pause);

            }

            @Override
            public void onGetduration(String currentduration, int progress) {
                play.setImageResource(R.drawable.ic_pause);
                seekbar.setProgress(progress);
            }
        });



        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.musicControl(context,PLAY_BUTTON);


            }
        });

      next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.musicControl(context,NEXT_BUTTON);

            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.musicControl(context,PREV_BUTTON);

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CreateNotification.CHANNEL_ID,
                    "Music App", NotificationManager.IMPORTANCE_LOW);
            NotificationManager  notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }


    }


    public static void setOnbackpress(FragmentManager fm, Context context, Activity activity){
        if (fm.getBackStackEntryCount() > 1) {
            fm.popBackStack();
        } else {
            Dialog.showExitDialog(context,activity);
        }
    }








}
