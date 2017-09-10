package com.brooke.sher.loginregistertest.connect;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.brooke.sher.loginregistertest.data.MusicInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sher on 2017/9/10.
 */

public class ConnectPresenter implements ConnectContact.Presenter {

    private ConnectContact.View connectView;
    private MusicInfo musicInfo;
    private List<MusicInfo> musicInfoList;
    private Context context;

    public ConnectPresenter(ConnectContact.View connectView, Context context){
        this.connectView = connectView;
        this.context = context;
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public List<MusicInfo> getLocalMusic() {
        musicInfoList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query( MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,  new String[]{MediaStore.Audio.Media._ID,    //写入我想要获得的信息（列）
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.DATA}, null, null, null);
        cursor.moveToFirst();
        do{
            //歌曲ID：MediaStore.Audio.Media._ID
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
//歌曲的名称 ：MediaStore.Audio.Media.TITLE
            String tilte = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
//        歌曲的专辑名：MediaStore.Audio.Media.ALBUM
            String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
//歌曲的歌手名： MediaStore.Audio.Media.ARTIST
            String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
//歌曲文件的路径 ：MediaStore.Audio.Media.DATA
            String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
//歌曲的总播放时长 ：MediaStore.Audio.Media.DURATION
            int  duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
//歌曲文件的大小 ：MediaStore.Audio.Media.SIZE
            long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
            musicInfo= new MusicInfo();
            musicInfo.setId(id);
            musicInfo.setTilte(tilte);
            musicInfo.setAlbum(album);
            musicInfo.setArtist(artist);
            musicInfo.setUrl(url);
            musicInfo.setDuration(duration);
            musicInfo.setSize(size);

            musicInfoList.add(musicInfo);

        }while (cursor.moveToNext());

        cursor.close();
        return musicInfoList;
    }
}
