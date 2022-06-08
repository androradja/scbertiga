package com.musicapps.tubidyeaskekuli;

import static com.musicapps.tubidyeaskekuli.Static.ACTIONNAME;
import static com.musicapps.tubidyeaskekuli.Static.ACTION_NEXT;
import static com.musicapps.tubidyeaskekuli.Static.ACTION_PAUSE;
import static com.musicapps.tubidyeaskekuli.Static.ACTION_PREPARE;
import static com.musicapps.tubidyeaskekuli.Static.ACTION_PREVIUOS;
import static com.musicapps.tubidyeaskekuli.Static.ACTION_RESUME;
import static com.musicapps.tubidyeaskekuli.Static.ACTION_SET_DURATION;
import static com.musicapps.tubidyeaskekuli.Static.ACTION_START;
import static com.musicapps.tubidyeaskekuli.Static.ACTION_STOP;
import static com.musicapps.tubidyeaskekuli.Static.CLOSE_BUTTON;
import static com.musicapps.tubidyeaskekuli.Static.MUSICCONTROLL;
import static com.musicapps.tubidyeaskekuli.Static.MUSICSTATE;
import static com.musicapps.tubidyeaskekuli.Static.NEXT_BUTTON;
import static com.musicapps.tubidyeaskekuli.Static.PLAY_BUTTON;
import static com.musicapps.tubidyeaskekuli.Static.PLAY_CURRENT_MUSIC;
import static com.musicapps.tubidyeaskekuli.Static.PLAY_CURRENT_VIDEO;
import static com.musicapps.tubidyeaskekuli.Static.PREV_BUTTON;
import static com.musicapps.tubidyeaskekuli.Static.SAVE_DURATION;
import static com.musicapps.tubidyeaskekuli.Static.SEEK_BUTTON;
import static com.musicapps.tubidyeaskekuli.Static.STOP_ALLMUSIC;
import static com.musicapps.tubidyeaskekuli.Static.STOP_SERVICES;
import static com.musicapps.tubidyeaskekuli.Static.TIMER_BUTTON;
import static io.realm.Realm.getApplicationContext;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class BroadcastRes {
    private OnMusicStateChange onMusicStateChange;
    private OnControlButtonClick onControlButtonClick;
    public static final int MAX_PROGRESS = 10000;

    public interface OnMusicStateChange {
        void onPlaying();
        void onNext();
        void onPrev();
        void onPrepare();
        void onPause();
        void onStop();
        void onResume();
        void onGetduration(String currentduration,int progress);
    }


    public interface OnControlButtonClick{
        void onPlay();
        void onPlayCurrentVid();
        void onPlayCurrentMusic();
        void onNext();
        void onPrev();
        void onSeek(int seekto);
        void onTimerSet(Long end);
        void onStopAllMusic();
        void onStopServices();
        void onClosebutton();
        void onSaveCurrentDuration();

    }

    public void setOnMusicStateChange(OnMusicStateChange onMusicStateChange) {
        this.onMusicStateChange = onMusicStateChange;
    }

    public void setOnControlButtonClick(OnControlButtonClick onControlButtonClick) {
        this.onControlButtonClick = onControlButtonClick;
    }

    public void MusicState(Context context){
        LocalBroadcastManager.getInstance(context).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e("onReceive", intent.getExtras().toString());
                String action = intent.getStringExtra(ACTIONNAME);
                if (action.equals(ACTION_START)){
                    onMusicStateChange.onPlaying();
                }
                else if (action.equals(ACTION_STOP)){
                    onMusicStateChange.onStop();
                }
                else if (action.equals(ACTION_RESUME)){
                    onMusicStateChange.onResume();
                }
                else if (!action.equals(ACTION_PAUSE)) {
                    if (action.equals(ACTION_NEXT)){
                        onMusicStateChange.onNext();
                    }
                    else if (action.equals(ACTION_PREVIUOS)){
                        onMusicStateChange.onPrev();
                    }else if (action.equals(ACTION_PREPARE)){
                        onMusicStateChange.onPrepare();
                    }
                    else if (action.equals(ACTION_SET_DURATION)){
                        int cdura=intent.getIntExtra("currentduration",0);
                        String timercurrent=MusicUtills.milliSecondsToTimerstatic(cdura);
                        int progress=intent.getIntExtra("progress",0);
                        // Updating progress bar
                        onMusicStateChange.onGetduration(timercurrent,progress);
                    }
                } else {
                    onMusicStateChange.onPause();
                }
            }

        }  , new IntentFilter(MUSICSTATE));

    }
    public void ControlButton(Context context){
        LocalBroadcastManager.getInstance(context).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getStringExtra(ACTIONNAME);
                if (action.equals(PLAY_BUTTON)){
                    onControlButtonClick.onPlay();
                }
                else if (action.equals(NEXT_BUTTON)){
                    onControlButtonClick.onNext();
                }
                else if (action.equals(PLAY_CURRENT_VIDEO)){
                    onControlButtonClick.onPlayCurrentVid();
                }
                else if (action.equals(PLAY_CURRENT_MUSIC)){
                    onControlButtonClick.onPlayCurrentMusic();
                }
                else if (action.equals(PREV_BUTTON)){
                    onControlButtonClick.onPrev();
                }
                else if (action.equals(STOP_ALLMUSIC)){
                    onControlButtonClick.onStopAllMusic();
                }
                else if (action.equals(STOP_SERVICES)){
                    onControlButtonClick.onStopServices();
                }
                else if (action.equals(CLOSE_BUTTON)){
                    onControlButtonClick.onClosebutton();
                }
                else if (action.equals(SEEK_BUTTON)){
                    int seekto=intent.getIntExtra("seekto",0);
                    onControlButtonClick.onSeek(seekto);
                }
                else if (action.equals(TIMER_BUTTON)){
                    Long end= intent.getLongExtra("end",0);
                    onControlButtonClick.onTimerSet(end);
                    Log.e("onReceive1", "onReceive1: "+ end);
                }
                else if (action.equals(SAVE_DURATION)){
                    onControlButtonClick.onSaveCurrentDuration();

                }
            }

        }  , new IntentFilter(MUSICCONTROLL));

    }





    public static void initBroadcast(Activity activity){

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getExtras().getString(ACTIONNAME);
                Intent intentforward = new Intent(MUSICCONTROLL);
                intentforward.putExtra(ACTIONNAME, action);
                assert getApplicationContext() != null;
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intentforward);
            }
        };
        activity.registerReceiver(broadcastReceiver, new IntentFilter(MUSICCONTROLL));


    }


}
