package com.brooke.sher.loginregistertest;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaSessionCompat;

import com.brooke.sher.loginregistertest.data.MusicInfo;

import java.io.IOException;

/**
 * Created by Sher on 2017/9/10.
 */

public class MusicService extends Service {

    private MediaSessionCompat mediaSessionCompat;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private String currentMediaId;

    private MediaSessionCompat.Callback callback = new MediaSessionCompat.Callback() {
        @Override
        public void onPlay() {
            super.onPlay();

        }

        @Override
        public void onPlayFromMediaId(String mediaId, Bundle extras) {
            super.onPlayFromMediaId(mediaId, extras);
            try {

                //通过mediaid获取到当前的列表以及当前的音乐,如果mediaid相同那么跳转到音乐详情界面，如果不同，播放不同的音乐
                MusicInfo info = (MusicInfo) extras.getSerializable("music");
//           MusicInfo info =  extras.getParcelable("music");
                if (mediaPlayer.isPlaying()){
                    if (currentMediaId.equals(mediaId)){
                        mediaPlayer.pause();
                    }else{
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(info.getUrl());
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    }
                }else{
                    mediaPlayer.setDataSource(info.getUrl());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                }
                currentMediaId = mediaId;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onSkipToQueueItem(long id) {
            super.onSkipToQueueItem(id);
        }

        @Override
        public void onPause() {
            super.onPause();
        }

        @Override
        public void onSkipToNext() {
            super.onSkipToNext();
        }

        @Override
        public void onSkipToPrevious() {
            super.onSkipToPrevious();
        }

        @Override
        public void onSeekTo(long pos) {
            super.onSeekTo(pos);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MusicBinder();
    }

    public class MusicBinder extends Binder{
        public MusicService getService(){
            return MusicService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaSessionCompat = new MediaSessionCompat(this,"MusicService");
        mediaSessionCompat.setCallback(callback);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    public MediaSessionCompat.Token getToken(){
        return mediaSessionCompat.getSessionToken();
    }
}
