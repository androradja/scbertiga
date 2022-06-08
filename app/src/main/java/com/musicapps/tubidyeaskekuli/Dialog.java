package com.musicapps.tubidyeaskekuli;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.tonyodev.fetch2.Download;
import com.tonyodev.fetch2.Error;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchConfiguration;
import com.tonyodev.fetch2.FetchListener;
import com.tonyodev.fetch2.NetworkType;
import com.tonyodev.fetch2.Priority;
import com.tonyodev.fetch2.Request;
import com.tonyodev.fetch2core.DownloadBlock;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Dialog {

    public  static void sharedialog(Context context){
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String shareMessage= "\nLet me recommend you this application\n\n";
            //  shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            context.startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }
    public static  void showExitDialog(Context context, Activity activity){
        final android.app.Dialog dialog = new android.app.Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.exit_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.findViewById(R.id.submit).setOnClickListener(v -> {

            activity.finish();


        });
        dialog.findViewById(R.id.cancel_action).setOnClickListener(v -> {
            dialog.dismiss();
            Toast.makeText(context, "Dismiss", Toast.LENGTH_SHORT).show();
        });

        dialog.show();


        dialog.getWindow().setAttributes(lp);

    }




   public static void  DownloadSongs(SongModel songModel,Activity activity){

       Fetch fetch;
       FetchConfiguration fetchConfiguration = new FetchConfiguration.Builder(activity)
               .setDownloadConcurrentLimit(3)
               .build();
        fetch = Fetch.Impl.getInstance(fetchConfiguration);
        String myUri = "https://fando.id/soundcloud/get.php?id="+songModel.getId();
        String file = activity.getFilesDir()+"/"+songModel.getId()+".mp3";
        final Request request = new Request(myUri, file);
        request.setPriority(Priority.HIGH);
        request.setNetworkType(NetworkType.ALL);
        FetchListener fetchListener = new FetchListener() {
            @Override
            public void onError(@NonNull Download download, @NonNull Error error, @Nullable Throwable throwable) {
                Log.e("error dowload", error.toString() );
            }

            @Override
            public void onWaitingNetwork(@NonNull Download download) {

            }

            @Override
            public void onStarted(@NonNull Download download, @NonNull List<? extends DownloadBlock> list, int i) {
                Toast.makeText(activity,"Download start for" +songModel.getTitle(),Toast.LENGTH_LONG).show();
                Log.e("error dowload", "started");

            }



            @Override
            public void onDownloadBlockUpdated(@NonNull Download download, @NonNull DownloadBlock downloadBlock, int i) {

            }

            @Override
            public void onAdded(@NonNull Download download) {


            }

            @Override
            public void onQueued(@NotNull Download download, boolean waitingOnNetwork) {
                if (request.getId() == download.getId()) {

                }
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCompleted(@NotNull Download download) {
                RealmHelper realmHelper = new RealmHelper(activity);
                realmHelper.addDownloads(songModel);
                Toast.makeText(activity,"Download Complete for" +songModel.getTitle(),Toast.LENGTH_LONG).show();


            }



            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onProgress(@NotNull Download download, long etaInMilliSeconds, long downloadedBytesPerSecond) {


            }

            @Override
            public void onPaused(@NotNull Download download) {

            }

            @Override
            public void onResumed(@NotNull Download download) {

            }

            @Override
            public void onCancelled(@NotNull Download download) {

            }

            @Override
            public void onRemoved(@NotNull Download download) {

            }

            @Override
            public void onDeleted(@NotNull Download download) {

            }
        };

        fetch.addListener(fetchListener);

        fetch.enqueue(request, updatedRequest -> {
            //Request was successfully enqueued for download.
        }, error -> {
            //An error occurred enqueuing the request.
        });

    }


}
