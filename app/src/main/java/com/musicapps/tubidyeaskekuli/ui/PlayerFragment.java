package com.musicapps.tubidyeaskekuli.ui;

import static com.musicapps.tubidyeaskekuli.MusicService.currentSongModel;
import static com.musicapps.tubidyeaskekuli.Static.ACTIONNAME;
import static com.musicapps.tubidyeaskekuli.Static.MUSICCONTROLL;
import static com.musicapps.tubidyeaskekuli.Static.NEXT_BUTTON;
import static com.musicapps.tubidyeaskekuli.Static.PLAY_BUTTON;
import static com.musicapps.tubidyeaskekuli.Static.PREV_BUTTON;
import static com.musicapps.tubidyeaskekuli.Static.SEEK_BUTTON;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.musicapps.tubidyeaskekuli.Ads;
import com.musicapps.tubidyeaskekuli.BroadcastRes;
import com.musicapps.tubidyeaskekuli.Dialog;
import com.musicapps.tubidyeaskekuli.Helper;
import com.musicapps.tubidyeaskekuli.MusicService;
import com.musicapps.tubidyeaskekuli.MusicUtills;
import com.musicapps.tubidyeaskekuli.OnSingleClickListener;
import com.musicapps.tubidyeaskekuli.R;
import com.musicapps.tubidyeaskekuli.RealmHelper;
import com.musicapps.tubidyeaskekuli.Route;
import com.musicapps.tubidyeaskekuli.SongModel;
import com.musicapps.tubidyeaskekuli.databinding.FragmentPlayerBinding;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SongModel songModel;
    Context context;
    private FragmentPlayerBinding binding;
    public PlayerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment PlayerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayerFragment newInstance() {

        return new PlayerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            songModel = getArguments().getParcelable(ARG_PARAM1);
        }
        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPlayerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (MusicService.PLAYERSTATUS.equals("PLAYING")){
            binding.play.setVisibility(View.VISIBLE);
            binding.play.setImageResource(R.drawable.ic_pause);
            binding.progressBar.setVisibility(View.GONE);

            updateinfo(currentSongModel);

        }
        else if (MusicService.PLAYERSTATUS.equals("PAUSED")){
            binding.play.setVisibility(View.VISIBLE);
            binding.play.setImageResource(R.drawable.ic_play);
            binding.progressBar.setVisibility(View.GONE);

            updateinfo(currentSongModel);

        }

        binding.waveformSeekBar.setMaxProgress(MusicUtills.MAX_PROGRESS);
        binding.waveformSeekBar.setProgress(0);
        binding.seekbar.setProgress(0);
        binding.seekbar.setMax(MusicUtills.MAX_PROGRESS);
        binding.seekbar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                if(seekParams.fromUser){
                    binding.seekbar.setProgress(seekParams.progress);
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
                binding.progressBar.setVisibility(View.INVISIBLE);
                binding.play.setVisibility(View.VISIBLE);
                binding.play.setImageResource(R.drawable.ic_pause);

            }

            @Override
            public void onNext() {

            }

            @Override
            public void onPrev() {

            }

            @Override
            public void onPrepare() {
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.play.setVisibility(View.INVISIBLE);
                updateinfo(currentSongModel);
            }

            @Override
            public void onPause() {
                binding.play.setImageResource(R.drawable.ic_play);

            }

            @Override
            public void onStop() {

            }

            @Override
            public void onResume() {
                binding.play.setImageResource(R.drawable.ic_pause);

            }

            @Override
            public void onGetduration(String currentduration, int progress) {
                binding.play.setImageResource(R.drawable.ic_pause);
                binding.currentduration.setText(currentduration);
                binding.seekbar.setProgress(progress);
                binding.waveformSeekBar.setProgress(progress);

            }
        });




        binding.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.musicControl(context,PLAY_BUTTON);
            }
        });

        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.musicControl(context,NEXT_BUTTON);

            }
        });

        binding.prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.musicControl(context,PREV_BUTTON);

            }
        });
        binding.shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MusicService.SHUFFLE){
                    MusicService.SHUFFLE=false;
                    binding.shuffle.setImageResource(R.drawable.shuffle);

                }
                else {
                    MusicService.SHUFFLE=true;
                    binding.shuffle.setImageResource(R.drawable.shuffle1);


                }
            }
        });

        binding.repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MusicService.REPEAT){
                    MusicService.REPEAT=false;
                    binding.repeat.setImageResource(R.drawable.repeat);

                }
                else {
                    MusicService.REPEAT=true;
                    binding.repeat.setImageResource(R.drawable.repeat1);


                }
            }
        });

        binding.share.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Dialog.sharedialog(context);
            }
        });
        binding.back.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Route.backPress(getActivity());
            }
        });
        binding.download.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                RealmHelper realmHelper= new RealmHelper(getActivity());
                if (realmHelper.checkisDownloaded(songModel.getId())){
                    Toast.makeText(context,songModel.getTitle()+"Has downloaded",Toast.LENGTH_LONG).show();

                }
                else Dialog.DownloadSongs(songModel,getActivity());


            }
        });

        Ads ads= new Ads(getActivity());
        ads.showBanner(binding.banner);

    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    void updateinfo(SongModel songModel) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);



        Helper.displayImage(context, binding.cover, songModel.getImage(), R.drawable.defaultimage);
        binding.title.setText(songModel.getTitle());
//        binding.toptitle.setText(songModel.getTitle());
//        binding.toptitle.setSelected(true);
        binding.title.setSelected(true);
        binding.artist.setText(songModel.getArtist());
        binding.totalduration.setText(MusicUtills.milliSecondsToTimerstatic(Long.parseLong(songModel.getDuration())));

    }





}