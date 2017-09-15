package com.brooke.sher.loginregistertest.data;

import java.io.Serializable;

/**
 * Created by Sher on 2017/9/10.
 */

public class MusicInfo implements Serializable {
    private int id;
    private String tilte;
    private String album;
    private String artist;
    private String url;
    private int  duration;
    private  long size;
    private String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTilte() {
        return tilte;
    }

    public void setTilte(String tilte) {
        this.tilte = tilte;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(this.id);
//        dest.writeString(this.tilte);
//        dest.writeString(this.album);
//        dest.writeString(this.artist);
//        dest.writeString(this.url);
//        dest.writeInt(this.duration);
//        dest.writeLong(this.size);
//        dest.writeString(this.tag);
//    }
//
//    public MusicInfo() {
//    }
//
//    protected MusicInfo(Parcel in) {
//        this.id = in.readInt();
//        this.tilte = in.readString();
//        this.album = in.readString();
//        this.artist = in.readString();
//        this.url = in.readString();
//        this.duration = in.readInt();
//        this.size = in.readLong();
//        this.tag = in.readString();
//    }
//
//    public static final Parcelable.Creator<MusicInfo> CREATOR = new Parcelable.Creator<MusicInfo>() {
//        @Override
//        public MusicInfo createFromParcel(Parcel source) {
//            return new MusicInfo(source);
//        }
//
//        @Override
//        public MusicInfo[] newArray(int size) {
//            return new MusicInfo[size];
//        }
//    };
}
