package com.brooke.sher.loginregistertest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.brooke.sher.loginregistertest.utils.MusicHelper;

/**
 * Created by Sher on 2017/9/10.
 */

public class MusicService extends Service implements MusicHelper.PlaybackServiceCallback{

    private MediaSessionCompat mediaSessionCompat;
    private MediaNotificationManager mNotificationManager;

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
        MusicHelper musicHelper = new MusicHelper(mediaSessionCompat,this);
        mNotificationManager = new MediaNotificationManager(this);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"MusicService");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    public MediaSessionCompat.Token getToken(){
        return mediaSessionCompat.getSessionToken();
    }

    @Override
    public void onPlaybackStart() {

    }

    @Override
    public void onNotificationRequired() {
        mNotificationManager.startNotification();
    }

    @Override
    public void onPlaybackStop() {

    }

    @Override
    public void onPlaybackStateUpdated(PlaybackStateCompat newState) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNotificationManager.stopNotification();
    }
}
