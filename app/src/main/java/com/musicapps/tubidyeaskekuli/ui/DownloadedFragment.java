package com.musicapps.tubidyeaskekuli.ui;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.musicapps.tubidyeaskekuli.Helper;
import com.musicapps.tubidyeaskekuli.RealmHelper;
import com.musicapps.tubidyeaskekuli.SongModel;
import com.musicapps.tubidyeaskekuli.Songlist2Adapter;
import com.musicapps.tubidyeaskekuli.databinding.FragmentDownloadedBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DownloadedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DownloadedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Activity activity;
    private FragmentDownloadedBinding binding;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<SongModel> listsong= new ArrayList<>();

    public DownloadedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment DownloadedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DownloadedFragment newInstance() {
        return new DownloadedFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         activity=getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDownloadedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setData();
    }
    void setData(){
        RealmHelper realmHelper= new RealmHelper(activity);
        listsong=realmHelper.getRecent();
        binding.rv.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL,false));
        binding.rv.setHasFixedSize(false);
        //set data and list adapter
        Songlist2Adapter songlistAdapter = new Songlist2Adapter(listsong,getActivity(),1000);
        songlistAdapter.setOnItemClickListener(new Songlist2Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Helper.Playmusic(getActivity(),position, listsong);

            }

            @Override
            public void onMoreClick(SongModel songModel) {

            }

        });

        binding.rv.setAdapter(songlistAdapter);


    }
}