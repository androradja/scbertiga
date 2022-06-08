package com.musicapps.tubidyeaskekuli.notifservice;



import static com.musicapps.tubidyeaskekuli.Static.ACTIONNAME;
import static com.musicapps.tubidyeaskekuli.Static.MUSICCONTROLL;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class NotificationActionService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.sendBroadcast(new Intent(MUSICCONTROLL)
        .putExtra(ACTIONNAME, intent.getAction()));
        Log.e("NOTIFFILTER", intent.getAction() );

    }


}
