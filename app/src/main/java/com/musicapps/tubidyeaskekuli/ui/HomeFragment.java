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
import com.musicapps.tubidyeaskekuli.MainActivity;
import com.musicapps.tubidyeaskekuli.OnSingleClickListener;
import com.musicapps.tubidyeaskekuli.Route;
import com.musicapps.tubidyeaskekuli.SongModel;
import com.musicapps.tubidyeaskekuli.Songlist2Adapter;
import com.musicapps.tubidyeaskekuli.databinding.FragmentHomeBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentHomeBinding binding;
    Context context;
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
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
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setPopularSongs();
        setTrendingSongs();


        Ads ads= new Ads(getActivity());
        ads.showBanner(binding.banner);
        ads.showBanner(binding.banner2);


        binding.searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
//                    Route.gotofragment(getParentFragmentManager(),SearchFragment.newInstance());
                    binding.searchView.clearFocus();
                    ((MainActivity)getActivity()).gototabsearch();
                    view.clearFocus();
                }

            }
        });

    }

    void setPopularSongs(){
        binding.seealtopsong.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Route.gotofragment(getParentFragmentManager(),SonglistFragment.newInstance("Topsongs"));
            }
        });
        binding.rvtopsong.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL,false));
        binding.rvtopsong.setHasFixedSize(false);
        //set data and list adapter
        Songlist2Adapter songAdapterPopularGrid = new Songlist2Adapter(listpopular,getActivity(),4);
        songAdapterPopularGrid.setOnItemClickListener(new Songlist2Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Helper.Playmusic(getActivity(),position, listpopular);

            }

            @Override
            public void onMoreClick(SongModel songModel) {

            }
        });

        binding.rvtopsong.setAdapter(songAdapterPopularGrid);
    }

    void setTrendingSongs(){
        binding.seealtrending.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Route.gotofragment(getParentFragmentManager(),SonglistFragment.newInstance("Trending Song"));

            }
        });
        binding.rvtrending.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL,false));
        binding.rvtrending.setHasFixedSize(false);
        //set data and list adapter
        Songlist2Adapter songAdapterPopularGrid = new Songlist2Adapter(listtrending,getActivity(),4);
        songAdapterPopularGrid.setOnItemClickListener(new Songlist2Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Helper.Playmusic(getActivity(),position, listtrending);

            }

            @Override
            public void onMoreClick(SongModel songModel) {

            }
        });

        binding.rvtrending.setAdapter(songAdapterPopularGrid);
    }
}