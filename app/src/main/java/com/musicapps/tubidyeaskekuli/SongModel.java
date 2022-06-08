package com.musicapps.tubidyeaskekuli;



import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

public class SongModel extends RealmObject implements Parcelable {

    String title,image,likes_count,duration,genre,artist,waveform_url;
    int id;
    int index;
    boolean isdownload;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(String likes_count) {
        this.likes_count = likes_count;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getWaveform_url() {
        return waveform_url;
    }

    public void setWaveform_url(String waveform_url) {
        this.waveform_url = waveform_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isIsdownload() {
        return isdownload;
    }

    public void setIsdownload(boolean isdownload) {
        this.isdownload = isdownload;
    }

    public SongModel() {
    }


    protected SongModel(Parcel in) {
        title = in.readString();
        image = in.readString();
        likes_count = in.readString();
        duration = in.readString();
        genre = in.readString();
        artist = in.readString();
        waveform_url = in.readString();
        id = in.readInt();
        index = in.readInt();
        isdownload = in.readByte() != 0;
    }

    public static final Creator<SongModel> CREATOR = new Creator<SongModel>() {
        @Override
        public SongModel createFromParcel(Parcel in) {
            return new SongModel(in);
        }

        @Override
        public SongModel[] newArray(int size) {
            return new SongModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(image);
        parcel.writeString(likes_count);
        parcel.writeString(duration);
        parcel.writeString(genre);
        parcel.writeString(artist);
        parcel.writeString(waveform_url);
        parcel.writeInt(id);
        parcel.writeInt(index);
        parcel.writeByte((byte) (isdownload ? 1 : 0));
    }
}
