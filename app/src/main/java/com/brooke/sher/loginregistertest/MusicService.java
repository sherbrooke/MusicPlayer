package com.brooke.sher.loginregistertest;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;

import com.brooke.sher.loginregistertest.data.MusicInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sher on 2017/9/10.
 */

public class MusicService extends Service {

    private MediaSessionCompat mediaSessionCompat;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private String currentMediaId;
    private int pos;
    private List<MusicInfo> infoList;
    PlaybackStateCompat.Builder stateBuilder = new PlaybackStateCompat.Builder();
    PlaybackStateCompat playbackStateCompat;
    private MediaSessionCompat.Callback callback = new MediaSessionCompat.Callback() {
        @Override
        public void onPlay() {
            super.onPlay();

        }

        @Override
        public void onPlayFromMediaId(String mediaId, Bundle extras) {
            super.onPlayFromMediaId(mediaId, extras);
            try {

                //通过mediaid获取到当前的列表以及当前的音乐,如果mediaid相同那么跳转到音乐详情界面(目前是暂停逻辑)，如果不同，播放不同的音乐
                extras.setClassLoader(getClass().getClassLoader());
                final MusicInfo info =  extras.getParcelable("music");
                pos = (int) extras.get("position");
                infoList = (ArrayList<MusicInfo>) extras.get("musicList");
                if (mediaPlayer.isPlaying()){
                    if (currentMediaId.equals(mediaId)){
                        mediaPlayer.pause();
                        updatePlaybackState(PlaybackStateCompat.STATE_PAUSED);

                    }else{
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(info.getUrl());
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        updatePlaybackState(PlaybackStateCompat.STATE_PLAYING);
                    }
                }else{
                    if (!TextUtils.isEmpty(currentMediaId) && currentMediaId.equals(mediaId) ){
                        mediaPlayer.start();
                    }else {
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(info.getUrl());
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    }
                    updatePlaybackState(PlaybackStateCompat.STATE_PLAYING);

                }
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {

                        //循环播放
                        if (infoList.size()<=pos+1){
                            pos = -1;
                        }
                        mediaPlayer.reset();
                        MusicInfo info = infoList.get(pos+1);
                        try {
                            mediaPlayer.setDataSource(info.getUrl());
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        updatePlaybackState(PlaybackStateCompat.STATE_PLAYING);
                        pos++;
                        currentMediaId = String.valueOf(info.getId());
                    }
                });
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

    private void updatePlaybackState(int statePaused) {
        playbackStateCompat = stateBuilder.setState(statePaused,0, 1.0f, SystemClock.elapsedRealtime()).build();
        mediaSessionCompat.setPlaybackState(playbackStateCompat);
    }
}
