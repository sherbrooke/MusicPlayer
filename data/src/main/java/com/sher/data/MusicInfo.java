package com.sher.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sher on 2017/9/10.
 */

public class MusicInfo implements Parcelable {
    private int id;
    private String name;
    private String album;
    private String pic;//专辑海报
    private String artist;
    private String url;
    private int  duration;
//    private  long size;
//    private String tag;
    private long alumbId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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

    public long getAlumbId() {
        return alumbId;
    }

    public void setAlumbId(long alumbId) {
        this.alumbId = alumbId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.album);
        dest.writeString(this.pic);
        dest.writeString(this.artist);
        dest.writeString(this.url);
        dest.writeInt(this.duration);
        dest.writeLong(this.alumbId);
    }

    public MusicInfo() {
    }

    protected MusicInfo(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.album = in.readString();
        this.pic = in.readString();
        this.artist = in.readString();
        this.url = in.readString();
        this.duration = in.readInt();
        this.alumbId = in.readLong();
    }

    public static final Parcelable.Creator<MusicInfo> CREATOR = new Parcelable.Creator<MusicInfo>() {
        @Override
        public MusicInfo createFromParcel(Parcel source) {
            return new MusicInfo(source);
        }

        @Override
        public MusicInfo[] newArray(int size) {
            return new MusicInfo[size];
        }
    };
}
