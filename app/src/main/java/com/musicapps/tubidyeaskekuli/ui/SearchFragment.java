package com.musicapps.tubidyeaskekuli.ui;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.musicapps.tubidyeaskekuli.Ads;
import com.musicapps.tubidyeaskekuli.Helper;
import com.musicapps.tubidyeaskekuli.SongModel;
import com.musicapps.tubidyeaskekuli.SonglistAdapter;
import com.musicapps.tubidyeaskekuli.Static;
import com.musicapps.tubidyeaskekuli.VolleyHelper;
import com.musicapps.tubidyeaskekuli.databinding.FragmentHomeBinding;
import com.musicapps.tubidyeaskekuli.databinding.FragmentSearchBinding;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentSearchBinding binding;
    Activity context;
    VolleyHelper volleyHelper = new VolleyHelper();
    SonglistAdapter adapter;
    private    List<SongModel> listsearch=new ArrayList<>();
    KProgressHUD hud;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hud= KProgressHUD.create(context, KProgressHUD.Style.SPIN_INDETERMINATE);

        binding.rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.rv.setHasFixedSize(true);
        //set data and list adapter
        adapter = new SonglistAdapter(listsearch, context,1000);
        adapter.setOnItemClickListener(new SonglistAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Helper.Playmusic(context, position, listsearch);
            }


            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onMoreClick(SongModel songModel) {

            }


        });
        binding.rv.setAdapter(adapter);


        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.equals("")) {
                    hud.show();
                    volleyHelper.getsong(context, Static.getSEARCH(query));
                    volleyHelper.setMySonglistener(new VolleyHelper.MySonglistener() {
                        @RequiresApi(api = Build.VERSION_CODES.R)
                        @Override
                        public void onsuccess(List<SongModel> listsong) {
                            listsearch.clear();
                            listsearch.addAll(listsong);
                            if (listsearch.size() < 1) {
                                Log.e("listsong", "onsuccess: " + query);
                                binding.nosong.setVisibility(View.VISIBLE);

                            } else {

                                binding.nosong.setVisibility(View.INVISIBLE);

                            }
                            hud.dismiss();


                            adapter.notifyDataSetChanged();
                            binding.searchView.clearFocus();
                        }
                    });
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });


        Ads ads = new Ads(context);
        ads.showBanner(binding.banner);
    }
}