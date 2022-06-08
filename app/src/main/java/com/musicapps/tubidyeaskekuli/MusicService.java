package com.musicapps.tubidyeaskekuli;



import static com.musicapps.tubidyeaskekuli.Static.ACTIONNAME;
import static com.musicapps.tubidyeaskekuli.Static.ACTION_PAUSE;
import static com.musicapps.tubidyeaskekuli.Static.ACTION_PREPARE;
import static com.musicapps.tubidyeaskekuli.Static.ACTION_RESUME;
import static com.musicapps.tubidyeaskekuli.Static.ACTION_SET_DURATION;
import static com.musicapps.tubidyeaskekuli.Static.ACTION_START;
import static com.musicapps.tubidyeaskekuli.Static.MUSICSTATE;
import static com.musicapps.tubidyeaskekuli.Static.NEXT_BUTTON;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.PresetReverb;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import com.musicapps.tubidyeaskekuli.notifservice.CreateNotification;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class MusicService extends Service {
    public static String lirikurl;

    public  static String PLAYERSTATUS="STOP",CURRENTTYPE="OFF";
    public static int totalduration,currentduraiton;
    public static SongModel currentSongModel;
    public static List<SongModel> currentlist =new ArrayList<>();
    public static boolean SHUFFLE=false;
    public static boolean REPEAT=false;
    public static int CURRENTPOS=0;
    String from;
    Realm realm;
    public  static int sessionId =0;
    public static Equalizer mEqualizer;
    public static BassBoost bassBoost;
    public static PresetReverb presetReverb;

    private final Handler mHandler = new Handler();

    //player
    private MediaPlayer mp = new MediaPlayer();


    @Override
    public void onCreate() {
        super.onCreate();

       BroadcastRes helper = new BroadcastRes();
        helper.ControlButton(getApplicationContext());
        helper.setOnControlButtonClick(new BroadcastRes.OnControlButtonClick() {
            @Override
            public void onPlay() {
                    if (mp.isPlaying()) {
                        CreateNotification.createNotification(getApplicationContext(), currentlist.get(CURRENTPOS),
                                R.drawable.ic_notif_play);
                        try {
                            mp.pause();
                        } catch (Exception e) {
                        }

                        PLAYERSTATUS = "PAUSED";
                        Intent intent1 = new Intent(MUSICSTATE);
                        intent1.putExtra(ACTIONNAME, ACTION_PAUSE);
                        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent1);

                    } else {
                        CreateNotification.createNotification(getApplicationContext(), currentlist.get(CURRENTPOS),
                                R.drawable.ic_notif_pause);
                        PLAYERSTATUS = "PLAYING";
                        Intent intent1 = new Intent(MUSICSTATE);
                        intent1.putExtra(ACTIONNAME, ACTION_RESUME);
                        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent1);
                        mHandler.post(mUpdateTimeTask);
                        mp.start();
                    }


            }

            @Override
            public void onPlayCurrentVid() {

            }

            @Override
            public void onPlayCurrentMusic() {
                playsong(CURRENTPOS);
            }

            @Override
            public void onNext() {
                if (CURRENTPOS==(currentlist.size()-1)){
                    playsong(0);
                }

                else {
                    playsong(CURRENTPOS+1);
                }

            }

            @Override
            public void onPrev() {
                if (CURRENTPOS==0){
                    playsong(currentlist.size()-1);
                }

                else {
                    playsong(CURRENTPOS-1);
                }

            }

            @Override
            public void onSeek(int seekto) {
                mp.pause();
                mp.seekTo(seekto);
                mp.start();
                PLAYERSTATUS = "PLAYING";
                mHandler.post(mUpdateTimeTask);
            }

            @Override
            public void onTimerSet(Long end) {
                    new CountDownTimer(end, 1000) {
                        public void onTick(long millisUntilFinished) {
                            Log.e("onTick", "onTick: "+millisUntilFinished );

                        }

                        public void onFinish() {
                            Log.e("onTick", "onend" );

                            mp.pause();
                            Intent intent1 = new Intent(MUSICSTATE);
                            intent1.putExtra(ACTIONNAME, ACTION_PAUSE);
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent1);
                        }
                    }.start();


            }

            @Override
            public void onStopAllMusic() {
               if (mp.isPlaying()){
                   mp.pause();
               }
            }

            @Override
            public void onStopServices() {
                NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService( Context.NOTIFICATION_SERVICE );
                notificationManager.cancel(1);
                stopSelf();

            }

            @Override
            public void onClosebutton() {
                NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService( Context.NOTIFICATION_SERVICE );
                notificationManager.cancel(1);
                stopSelf();


            }

            @Override
            public void onSaveCurrentDuration() {

            }
        });

    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initrealm();
        playsong(CURRENTPOS);
//        Log.e("onStartCommand", String.valueOf(currentSongModel.getSongurl()));
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void playsong(int pos){
        CURRENTPOS=pos;
        CreateNotification.createNotification(getApplicationContext(), currentlist.get(pos),
                R.drawable.ic_notif_pause);

            try {
                currentSongModel=currentlist.get(pos);
                Intent intent = new Intent(MUSICSTATE);
                intent.putExtra(ACTIONNAME, ACTION_PREPARE);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                mp.stop();
                mp.reset();
                mp.release();
                Uri myUri;
                if (currentSongModel.isdownload){
                    myUri   = Uri.parse(getFilesDir()+"/"+currentSongModel.getId()+".mp3");

                }
                else  myUri = Uri.parse("https://fando.id/soundcloud/get.php?id="+currentSongModel.getId());


                Log.e("myUri", String.valueOf(myUri));
                mp = new MediaPlayer();
                mp.setDataSource(this, myUri);
                mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        Log.e("onError", "onError: " );
                        return true;
                    }
                });
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp1) {
                        if (REPEAT){
                            playsong(CURRENTPOS);
                        }
                        else if (SHUFFLE){
                            int pos= (int) (Math.random() * (currentlist.size()));
                            playsong(pos);
                        }
                        else {
                            PlayerControl.MusicControl(getApplicationContext(),NEXT_BUTTON);
                        }
                    }
                });
                mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onPrepared(MediaPlayer mplayer) {
                        Log.e("onPrepared", mplayer.toString() );


                        if (mplayer.isPlaying()) {
                            mp.pause();
                        } else {
                            mp.start();
                            PLAYERSTATUS="PLAYING";
                            Intent intent = new Intent(MUSICSTATE);
                            intent.putExtra(ACTIONNAME, ACTION_START);
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                            mHandler.post(mUpdateTimeTask);

                        }
                    }
                });
                mp.prepareAsync();
            }
            catch (Exception e){
                Log.e("onError", e.toString() );
            }





    }

    private final Runnable mUpdateTimeTask = new Runnable() {
        public void run() {

            // Running this thread after 10 milliseconds
            if (MusicService.PLAYERSTATUS.equals("PLAYING")) {
                try {
                    MusicUtills helper= new MusicUtills();
                    totalduration=mp.getDuration();
                    currentduraiton=mp.getCurrentPosition();
                    Intent intent = new Intent(MUSICSTATE);
                    intent.putExtra(ACTIONNAME, ACTION_SET_DURATION);
                    intent.putExtra("currentduration", mp.getCurrentPosition());
                    intent.putExtra("progress", helper.getProgressSeekBar((long) mp.getCurrentPosition(),(long) mp.getDuration()));
                    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);


                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                mHandler.postDelayed(this, 10);
            }
        }
    };

    public void  initrealm(){
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().allowWritesOnUiThread(true).build();
        realm = Realm.getInstance(configuration);

    }






    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.reset();
        mp.release();
        mp=null;

    }
}
