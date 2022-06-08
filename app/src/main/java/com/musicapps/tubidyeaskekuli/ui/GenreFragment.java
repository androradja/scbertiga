package com.musicapps.tubidyeaskekuli.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.musicapps.tubidyeaskekuli.Ads;
import com.musicapps.tubidyeaskekuli.GenreAdapter;
import com.musicapps.tubidyeaskekuli.Helper;
import com.musicapps.tubidyeaskekuli.R;
import com.musicapps.tubidyeaskekuli.SongModel;
import com.musicapps.tubidyeaskekuli.SonglistAdapter;
import com.musicapps.tubidyeaskekuli.Static;
import com.musicapps.tubidyeaskekuli.VolleyHelper;
import com.musicapps.tubidyeaskekuli.databinding.FragmentGenreBinding;
import com.musicapps.tubidyeaskekuli.databinding.FragmentHomeBinding;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GenreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GenreFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context context;
    private FragmentGenreBinding binding;
    SonglistAdapter songlistAdapter;
    private List<SongModel> listsonggenre= new ArrayList<>();
    KProgressHUD hud;
    public GenreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment GenreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GenreFragment newInstance() {

        return new GenreFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGenreBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hud= KProgressHUD.create(context, KProgressHUD.Style.SPIN_INDETERMINATE);

        setGenre();
        setData();
        Ads ads= new Ads(getActivity());
        ads.showBanner(binding.banner);

    }

    void setGenre(){
        String[] genre = getResources().getStringArray(R.array.genre);
        List<String> genrelist = Arrays.asList(genre);
        getDataGenre(genrelist.get(0));
        binding.rvgenre.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL,false));
        binding.rvgenre.setHasFixedSize(false);
        //set data and list adapter
        GenreAdapter songAdapterPopularGrid = new GenreAdapter(genrelist,context);
        songAdapterPopularGrid.setOnItemClickListener(new GenreAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
            getDataGenre(genrelist.get(position));
            }

        });

        binding.rvgenre.setAdapter(songAdapterPopularGrid);
    }


    void setData(){
        binding.rvsongs.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL,false));
        binding.rvsongs.setHasFixedSize(false);
        //set data and list adapter
        songlistAdapter = new SonglistAdapter(listsonggenre,getActivity(),1000);
        songlistAdapter.setOnItemClickListener(new SonglistAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Helper.Playmusic(getActivity(),position, listsonggenre);

            }

            @Override
            public void onMoreClick(SongModel songModel) {

            }

        });

        binding.rvsongs.setAdapter(songlistAdapter);


    }

    void getDataGenre(String genre){
        hud.show();
        VolleyHelper volleyHelper= new VolleyHelper();
        volleyHelper.getsong(context, Static.getGenreSong(genre));
        volleyHelper.setMySonglistener(new VolleyHelper.MySonglistener() {
            @Override
            public void onsuccess(List<SongModel> listsong) {
                listsonggenre.clear();
                listsonggenre.addAll(listsong);
                songlistAdapter.notifyDataSetChanged();
                hud.dismiss();
                Log.e("listsonggenre", "listsonggenre: "+listsonggenre.size() );

            }
        });
    }

}