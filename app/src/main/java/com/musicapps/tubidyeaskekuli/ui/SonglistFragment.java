package com.musicapps.tubidyeaskekuli.ui;

import static com.musicapps.tubidyeaskekuli.Static.listpopular;
import static com.musicapps.tubidyeaskekuli.Static.listtrending;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.musicapps.tubidyeaskekuli.Ads;
import com.musicapps.tubidyeaskekuli.Helper;
import com.musicapps.tubidyeaskekuli.SongModel;
import com.musicapps.tubidyeaskekuli.Songlist2Adapter;
import com.musicapps.tubidyeaskekuli.databinding.FragmentHomeBinding;
import com.musicapps.tubidyeaskekuli.databinding.FragmentSonglistBinding;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SonglistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SonglistFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String from;
    private String mParam2;
    private Context context;
    private FragmentSonglistBinding binding;
    public SonglistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment SonglistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SonglistFragment newInstance(String from) {
        SonglistFragment fragment = new SonglistFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, from);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            from = getArguments().getString(ARG_PARAM1);
        }
        context=getContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSonglistBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (from.equals("Trending")){
            setTrendingSongs(listtrending);
        }
        else setTrendingSongs(listpopular);

        binding.title.setText(from);
        Ads ads = new Ads(getActivity());
        ads.showBanner(binding.banner);

    }
    void setTrendingSongs(List<SongModel> listsongsss){
        binding.rv.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL,false));
        binding.rv.setHasFixedSize(false);
        //set data and list adapter
        Songlist2Adapter songAdapterPopularGrid = new Songlist2Adapter(listsongsss,getActivity(),1000);
        songAdapterPopularGrid.setOnItemClickListener(new Songlist2Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Helper.Playmusic(getActivity(),position, listsongsss);

            }

            @Override
            public void onMoreClick(SongModel songModel) {

            }
        });

        binding.rv.setAdapter(songAdapterPopularGrid);
    }
}