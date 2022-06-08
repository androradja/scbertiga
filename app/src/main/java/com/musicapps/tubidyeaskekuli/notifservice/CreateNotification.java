package com.musicapps.tubidyeaskekuli.notifservice;



import static com.musicapps.tubidyeaskekuli.Static.CLOSE_BUTTON;
import static com.musicapps.tubidyeaskekuli.Static.NEXT_BUTTON;
import static com.musicapps.tubidyeaskekuli.Static.PLAY_BUTTON;
import static com.musicapps.tubidyeaskekuli.Static.PREV_BUTTON;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadata;
import android.media.session.MediaSession;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.musicapps.tubidyeaskekuli.R;
import com.musicapps.tubidyeaskekuli.SongModel;


public class CreateNotification {
 public  static    Bitmap icon = null;

    public static final String CHANNEL_ID = "1010010010010010001100";






    public static Notification notification;

    public static void createNotification(Context context, SongModel songModel, int playbutton){
        Intent intentClose = new Intent(context, NotificationActionService.class)
                .setAction(CLOSE_BUTTON);
        PendingIntent pendingIntentClose = PendingIntent.getBroadcast(context, 0,
                intentClose, PendingIntent.FLAG_IMMUTABLE);


        Intent intentPlay = new Intent(context, NotificationActionService.class)
                .setAction(PLAY_BUTTON);
        PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(context, 0,
                intentPlay, PendingIntent.FLAG_IMMUTABLE);

        PendingIntent pendingIntentNext;
        Intent intentNext = new Intent(context, NotificationActionService.class)
                .setAction(NEXT_BUTTON);
        pendingIntentNext = PendingIntent.getBroadcast(context, 0,
                intentNext, PendingIntent.FLAG_IMMUTABLE);


        PendingIntent pendingIntentPrev;
        Intent intentPrev = new Intent(context, NotificationActionService.class)
                .setAction(PREV_BUTTON);
        pendingIntentPrev = PendingIntent.getBroadcast(context, 0,
                intentPrev, PendingIntent.FLAG_IMMUTABLE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            MediaSession mediaSession = new MediaSession( context, "tag");


                MediaMetadata mediaMetadata = new MediaMetadata.Builder()
                        .putString(MediaMetadata.METADATA_KEY_TITLE, songModel.getTitle())


                        // Artist.
                        // Could also be the channel name or TV series.
                        .putString(MediaMetadata.METADATA_KEY_ARTIST, songModel.getArtist())

                        // Album art.
                        // Could also be a screenshot or hero image for video content
                        // The URI scheme needs to be "content", "file", or "android.resource".


                        .putLong(MediaMetadata.METADATA_KEY_DURATION, 100000) // 4

                    .build();
                mediaSession.setMetadata(mediaMetadata);
                mediaSession.setActive(true);

            //create notification

            Glide.with(context)
                    .asBitmap()
                    .load(songModel.getImage())
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            icon=resource;
                            notification = new Notification.Builder(context, CHANNEL_ID)
                                    .setSmallIcon(R.drawable.defaultimage)
                                    .setContentTitle(songModel.getTitle())
                                    .setContentText(songModel.getArtist())
                                    .setLargeIcon(icon)
                                    .setOnlyAlertOnce(false)//show notification for only first time
                                    .setShowWhen(false)
                                    .setDeleteIntent(pendingIntentClose)
                                    .addAction(new Notification.Action(R.drawable.ic_notif_prev,"Prev",pendingIntentPrev))
                                    .addAction(new Notification.Action(playbutton,"Pause",pendingIntentPlay))
                                    .addAction(new Notification.Action(R.drawable.ic_notif_next,"Next",pendingIntentNext))
                                    .addAction(new Notification.Action(R.drawable.icon_close,"Close",pendingIntentClose))
                                    .setStyle(new Notification.MediaStyle()
                                            .setShowActionsInCompactView(0,1,2,3,4)
                                            .setMediaSession(mediaSession.getSessionToken()))


                                    .build();



                            NotificationManager notificationManager = (NotificationManager) context.getSystemService( Context.NOTIFICATION_SERVICE );
                            notificationManager.notify(1,notification);





                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });




        }
    }
}