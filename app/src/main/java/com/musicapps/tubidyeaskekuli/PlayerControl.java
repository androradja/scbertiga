package com.musicapps.tubidyeaskekuli;

import static com.musicapps.tubidyeaskekuli.Static.ACTIONNAME;
import static com.musicapps.tubidyeaskekuli.Static.MUSICCONTROLL;


import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class PlayerControl {
    public static void MusicControl(Context context, String button){
        Intent intent = new Intent(MUSICCONTROLL);
        intent.putExtra(ACTIONNAME, button);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

}
