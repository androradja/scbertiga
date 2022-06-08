package com.musicapps.tubidyeaskekuli;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VolleyHelper {


    public interface MySonglistener {
        void onsuccess(List<SongModel> listsong);
    }


    private MySonglistener songlistener;
    private MySonglistener topchartlistener;


    public MySonglistener getTopchartlistener() {
        return topchartlistener;
    }

    public void setTopchartlistener(MySonglistener topchartlistener) {
        this.topchartlistener = topchartlistener;
    }

    public void setMySonglistener(MySonglistener listener) {
        this.songlistener = listener;
    }


    public  void getSearch(Context context, String url){

        List<SongModel> list = new ArrayList<>();
        list.clear();

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("songs");
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject jsonObject= jsonArray.getJSONObject(i);
                        SongModel modelSong = new SongModel();
                        modelSong.setId(jsonObject.getInt("id"));
                        modelSong.setImage(jsonObject.getString("image"));
                        modelSong.setLikes_count(jsonObject.getString("likes_count"));
                        modelSong.setDuration(jsonObject.getString("duration"));
                        modelSong.setGenre(jsonObject.getString("genre"));
                        modelSong.setArtist(jsonObject.getString("artist"));
                        modelSong.setWaveform_url(jsonObject.getString("waveform_url"));
                        list.add(modelSong);



                    }

                    songlistener.onsuccess(list);

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, error -> Log.e("err","test"));

        Volley.newRequestQueue(context).add(jsonObjectRequest);


    }

    public  void getsong(Context context, String url){
        Log.e("getsong", url );
        List<SongModel> listsong= new ArrayList<>();
        listsong.clear();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("songs");
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject jsonObject= jsonArray.getJSONObject(i);
                        SongModel modelSong = new SongModel();
                        modelSong.setId(jsonObject.getInt("id"));
                        modelSong.setTitle(jsonObject.getString("title"));
                        modelSong.setImage(jsonObject.getString("image"));
                        modelSong.setLikes_count(jsonObject.getString("likes_count"));
                        modelSong.setDuration(jsonObject.getString("duration"));
                        modelSong.setGenre(jsonObject.getString("genre"));
                        modelSong.setArtist(jsonObject.getString("artist"));
                        modelSong.setWaveform_url(jsonObject.getString("waveform_url"));
                        listsong.add(modelSong);
                    }
                    songlistener.onsuccess(listsong);

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, error -> Log.e("err","err"+error.getMessage()));

        Volley.newRequestQueue(context).add(jsonObjectRequest);


    }




}
