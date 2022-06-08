package com.musicapps.tubidyeaskekuli;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdFormat;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.applovin.sdk.AppLovinSdkUtils;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.kaopiz.kprogresshud.KProgressHUD;


import org.jetbrains.annotations.NotNull;

import java.util.Arrays;


public class Ads {


    public static  String statusapp="";
    public static String appupdate = "";
    public static String applovingbanner="";
    public static String applovininter="";
    public static String admobbanner="";
    public static String admobinter="";
    public static String admobads="";

    private InterstitialAd admobInterstitialAd;
    private AdView admobview;

    public  static   String appid="";
    Activity activity;


    KProgressHUD hud;

    public interface adsListener {
         void onAdsfinish();
    }

    private adsListener listener;

    public void setadsListener(adsListener listener) {
        this.listener = listener;
    }

    public Ads(Activity activity) {
        this.activity = activity;

        AppLovinSdk.getInstance( activity ).setMediationProvider( "max" );
        AppLovinSdk.getInstance(activity).getSettings().setTestDeviceAdvertisingIds(Arrays.asList("80784a92-8b3a-47c6-8de5-e290aa8db75c","5bc74b6a-89f9-48b5-ab81-3c5a1eeab1f1"));

        AppLovinSdk.initializeSdk( activity, new AppLovinSdk.SdkInitializationListener() {
            @Override
            public void onSdkInitialized(final AppLovinSdkConfiguration configuration)
            {
                // AppLovin SDK is initialized, start loading ads

            }
        } );


        MobileAds.initialize(activity, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

    }


    public  void  showinters(){
        hud = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Loading")
                .setCancellable(true)
                .setDetailsLabel("Please Wait")
                .setMaxProgress(100)
                .show();
        if (admobads.equals("0")){
            showinterMopub();
        }
        else {
        showinterAdmob();
        }
    }

    public void showBanner(LinearLayout mAdViewLayout){
        if (admobads.equals("0")){
            showBannerMopub(mAdViewLayout);
        }
        else {
            showBannerAdmob(mAdViewLayout);
        }

    }


    public void showinterAdmob(){
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(activity,admobinter, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        hud.dismiss();
                        admobInterstitialAd=interstitialAd;
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.

                        admobInterstitialAd.show(activity);
                        admobInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull @NotNull AdError adError) {
                                super.onAdFailedToShowFullScreenContent(adError);
                                listener.onAdsfinish();
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                super.onAdShowedFullScreenContent();

                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();
                                listener.onAdsfinish();
                            }
                        });

                        Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        listener.onAdsfinish();
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                    }
                });



    }



    public void showBannerAdmob(LinearLayout mAdViewLayout){
        mAdViewLayout.removeAllViews();
        admobview = new AdView(activity);
        admobview.setAdSize(AdSize.FULL_BANNER);
        admobview.setAdUnitId(admobbanner);
        AdRequest adRequest = new AdRequest.Builder().build();
        admobview.loadAd(adRequest);
        mAdViewLayout.addView(admobview);




    }

    public void showinterMopub(){  MaxInterstitialAd interstitialAd;
        hud = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Loading")
                .setCancellable(true)
                .setDetailsLabel("Please Wait")
                .setMaxProgress(100)
                .show();
        interstitialAd = new MaxInterstitialAd( applovininter, activity );
        interstitialAd.setListener(new MaxAdListener() {
            @Override
            public void onAdLoaded(MaxAd ad) {
                interstitialAd.showAd();

            }

            @Override
            public void onAdDisplayed(MaxAd ad) {
                hud.dismiss();
//               listener.onAdsfinish();


            }

            @Override
            public void onAdHidden(MaxAd ad) {
                hud.dismiss();
                listener.onAdsfinish();

            }

            @Override
            public void onAdClicked(MaxAd ad) {
                hud.dismiss();
                listener.onAdsfinish();


            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                hud.dismiss();
                listener.onAdsfinish();

            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                hud.dismiss();
                listener.onAdsfinish();


            }
        });

        // Load the first ad
        interstitialAd.loadAd();
    }





    public  void showBannerMopub(LinearLayout mAdViewLayout) {
        MaxAdView adView;
        adView = new MaxAdView( applovingbanner, activity );

        // Stretch to the width of the screen for banners to be fully functional
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        // Get the adaptive banner height.
        int heightDp = MaxAdFormat.BANNER.getAdaptiveSize( activity ).getHeight();
        int heightPx = AppLovinSdkUtils.dpToPx( activity, heightDp );

        adView.setLayoutParams( new FrameLayout.LayoutParams( width, heightPx ) );
        adView.setExtraParameter( "adaptive_banner", "true" );
        mAdViewLayout.addView(adView);
        // Load the ad
        adView.loadAd();




    }


}