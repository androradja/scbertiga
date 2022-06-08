package com.musicapps.tubidyeaskekuli.ui;

import static com.musicapps.tubidyeaskekuli.Static.listpopular;

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
import com.musicapps.tubidyeaskekuli.databinding.FragmentAllTrackBinding;
import com.musicapps.tubidyeaskekuli.databinding.FragmentHomeBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllTrackFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllTrackFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context context;
    private FragmentAllTrackBinding binding;

    public AllTrackFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment AllTrackFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllTrackFragment newInstance() {
        return new AllTrackFragment();
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
        binding = FragmentAllTrackBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getSongs();
        Ads ads= new Ads(getActivity());
        ads.showBanner(binding.banner);
    }
    void getSongs(){
        binding.rv.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL,false));
        binding.rv.setHasFixedSize(false);
        //set data and list adapter
        Songlist2Adapter songAdapterPopularGrid = new Songlist2Adapter(listpopular,getActivity(),1000);
        songAdapterPopularGrid.setOnItemClickListener(new Songlist2Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Helper.Playmusic(getActivity(),position, listpopular);

            }

            @Override
            public void onMoreClick(SongModel songModel) {

            }
        });

        binding.rv.setAdapter(songAdapterPopularGrid);
    }
}