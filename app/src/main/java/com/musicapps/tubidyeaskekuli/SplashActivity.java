package com.musicapps.tubidyeaskekuli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.musicapps.tubidyeaskekuli.databinding.ActivitySplashBinding;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding binding;
    int _launcherAnimationCounter = 0;
    Timer _launcherAnimationTimer;
    Context context;
    KProgressHUD hud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context=SplashActivity.this;
        hud= KProgressHUD.create(context, KProgressHUD.Style.SPIN_INDETERMINATE);
        hud.show();
        getTopchart();
        getTrending();

        _launcherAnimationTimer = new Timer();
        _launcherAnimationTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("listtopchart", String.valueOf(Static.listtrending.size()));
                        if (Static.listtrending.size()!=0 && Static.listpopular.size()!=0) {
                            hud.dismiss();
                            getStatusApp(getString(R.string.json));
                            _launcherAnimationTimer.cancel();
                        }
                        _launcherAnimationCounter = 0;
                    }
                });
            }
        }, 0, 1500);

    }

    void getTopchart(){
        VolleyHelper volleyHelper= new VolleyHelper();
        volleyHelper.getsong(context,Static.TOP);
        volleyHelper.setMySonglistener(new VolleyHelper.MySonglistener() {
            @Override
            public void onsuccess(List<SongModel> listsong) {
                Static.listpopular.clear();
                Static.listpopular.addAll(listsong);
            }
        });
    }
    void getTrending(){
        VolleyHelper volleyHelper= new VolleyHelper();
        volleyHelper.getsong(context,Static.TRENDING);
        volleyHelper.setMySonglistener(new VolleyHelper.MySonglistener() {
            @Override
            public void onsuccess(List<SongModel> listsong) {
                Static.listtrending.clear();
                Static.listtrending.addAll(listsong);
            }
        });
    }


    private void getStatusApp(String url){
        Log.e("url", url );
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                Ads.statusapp=response.getString("statusapp");
                Ads.appupdate=response.getString("appupdate");
                Ads.admobads=response.getString("admobads");
                Ads.applovingbanner=response.getString("mopubbaner");
                Ads.applovininter=response.getString("mopubinter");
                Ads.admobbanner=response.getString("admobbanner");
                Ads.admobinter=response.getString("admobinter");

                if (Ads.statusapp.equals("suspend")){
                    showDialog(Ads.appupdate);
                }
                else {
                    Ads ads = new Ads(SplashActivity.this);
                    ads.showinters();
                    ads.setadsListener(new Ads.adsListener() {
                        @Override
                        public void onAdsfinish() {
                            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });


                }
            } catch (JSONException e) {
                Log.e("errorparsing",e.getMessage());
            }
        }, error -> {
        });
        Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);
    }

    private void  showDialog(String appupdate){
        new SweetAlertDialog(SplashActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("App Was Discontinue")
                .setContentText("Please Install Our New Music App")
                .setConfirmText("Install")

                .setConfirmClickListener(sDialog -> {
                    sDialog
                            .setTitleText("Install From Playstore")
                            .setContentText("Please Wait, Open Playstore")
                            .setConfirmText("Go")


                            .changeAlertType(SweetAlertDialog.PROGRESS_TYPE);

                    final Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(
                                "https://play.google.com/store/apps/details?id="+appupdate));
                        intent.setPackage("com.android.vending");
                        startActivity(intent);
//                                Do something after 100ms
                    }, 3000);



                })
                .show();
    }


}