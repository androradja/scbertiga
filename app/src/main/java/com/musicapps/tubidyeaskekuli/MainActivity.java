package com.musicapps.tubidyeaskekuli;

import static com.musicapps.tubidyeaskekuli.Static.PLAY_BUTTON;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.musicapps.tubidyeaskekuli.databinding.ActivityMainBinding;
import com.musicapps.tubidyeaskekuli.ui.AllTrackFragment;
import com.musicapps.tubidyeaskekuli.ui.DownloadedFragment;
import com.musicapps.tubidyeaskekuli.ui.GenreFragment;
import com.musicapps.tubidyeaskekuli.ui.HomeFragment;
import com.musicapps.tubidyeaskekuli.ui.PlayerFragment;
import com.musicapps.tubidyeaskekuli.ui.SearchFragment;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RequestConfiguration configuration = new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("your device id should go here")).build();
        MobileAds.setRequestConfiguration(configuration);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        MainFramework.setBottomnav(binding.nav, getSupportFragmentManager(), HomeFragment.newInstance(), AllTrackFragment.newInstance(), GenreFragment.newInstance(), SearchFragment.newInstance(), DownloadedFragment.newInstance());
        MainFramework.setMiniPlayer(binding.miniplayer.miniplayer, binding.miniplayer.seekbar, MainActivity.this, binding.miniplayer.play, binding.miniplayer.next, binding.miniplayer.prev, binding.miniplayer.title,binding.miniplayer.artist, binding.miniplayer.cover, binding.miniplayer.progressBar);
        Route.gotofragment(getSupportFragmentManager(), HomeFragment.newInstance());

        binding.miniplayer.miniplayer.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Route.gotofragmentPlayer(getSupportFragmentManager(), PlayerFragment.newInstance());

            }

        });
        binding.miniplayer.title.setSelected(true);
        Config.createChannel(MainActivity.this);
        Helper.initBroadcast(MainActivity.this);
        ToolsPermision.Build(MainActivity.this);

//        RealmHelper realmHelper= new RealmHelper(MainActivity.this);
//        realmHelper.getRecent().forEach(new Consumer<SongModel>() {
//            @Override
//            public void accept(SongModel songModel) {
//                Log.e("songModel", String.valueOf(songModel.getId()));
//            }
//        });


    }

    public void gototabsearch(){
        binding.nav.setSelectedItemId(R.id.search_menu);
    }

    @Override
    public void onBackPressed() {
        MainFramework.setOnbackpress(getSupportFragmentManager(), MainActivity.this, MainActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Helper.musicControl(MainActivity.this,PLAY_BUTTON);

    }
}