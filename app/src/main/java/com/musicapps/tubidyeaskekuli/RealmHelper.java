package com.musicapps.tubidyeaskekuli;

import android.content.Context;
import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class RealmHelper {

    Realm realm;
    Context contex;
    boolean isexist =false;
    String name;
    public RealmHelper(Context contex) {
        Realm.init(contex);
        RealmConfiguration configuration = new RealmConfiguration.Builder().allowWritesOnUiThread(true).build();
        realm = Realm.getInstance(configuration);
        this.contex = contex;
    }


    public interface MyRealmListener {
        void onsuccess();
        void onFailed();
    }

    public interface MyChangelistener {
        void ondatachange(Realm realm);
    }

    private MyChangelistener myChangelistener;

    public void setMyChangelistener(MyChangelistener myChangelistener) {
        this.myChangelistener = myChangelistener;
    }

    private  MyRealmListener listener;

    public void setMyRealmListener(MyRealmListener listener) {
        this.listener = listener;
    }

    public void check(){
        realm.addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm realm) {
                myChangelistener.ondatachange(realm);

            }
        });
    }


    int checkindexSongs(){
        Number currentIdNum = realm.where(SongModel.class).max("id");
        int nextId;
        if(currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        return  nextId;
    }
    public List<SongModel> getRecent() {
        RealmResults<SongModel> results = realm.where(SongModel.class).findAll();
        return results;
    }


    public boolean checkisDownloaded(int id) {
        RealmResults<SongModel> results = realm.where(SongModel.class).equalTo("id",id).findAll();

        if (results.size()==0){
            return false;
        }
        else return true;
    }






    public void addDownloads(SongModel songModel){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm != null) {
                    try {
                        songModel.setIsdownload(true);
                        realm.copyToRealm(songModel);//
                        Log.e("added", songModel.getTitle());
                    } catch (Exception e) {
                        Log.e("onError", e.getMessage());

                    }
                }
            }
        });
    }

    public void deleteDownloads(int id){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm != null) {
                    try {
                        SongModel model = realm.where(SongModel.class)
                                .equalTo("id", id)
                                .findFirst();
                        assert model != null;
                        model.deleteFromRealm();

                        Log.e("delete", String.valueOf(id));
                    } catch (Exception e) {
                        Log.e("onError", e.getMessage());

                    }
                }
            }
        });
    }




}
