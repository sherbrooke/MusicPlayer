package com.brooke.sher.loginregistertest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import com.brooke.sher.loginregistertest.utils.MusicHelper;

/**
 * Created by Sher on 2017/9/10.
 */

public class MusicService extends Service implements MusicHelper.PlaybackServiceCallback{

    private MediaSessionCompat mediaSessionCompat;
    private MediaNotificationManager mNotificationManager;
    public static final String EXTRA_CONNECTED_CAST = "com.sher.CAST_NAME";
    public static final String ACTION_CMD = "com.sher.ACTION_CMD";
    // The key in the extras of the incoming Intent indicating the command that
    // should be executed (see {@link #onStartCommand})
    public static final String CMD_NAME = "CMD_NAME";
    // A value of a CMD_NAME key in the extras of the incoming Intent that
    // indicates that the music playback should be paused (see {@link #onStartCommand})
    public static final String CMD_PAUSE = "CMD_PAUSE";
    // A value of a CMD_NAME key that indicates that the music playback should switch
    // to local playback from cast playback.
    public static final String CMD_STOP_CASTING = "CMD_STOP_CASTING";

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
        MusicHelper musicHelper = new MusicHelper(this.getApplicationContext(),mediaSessionCompat,this);
        try {
            mNotificationManager = new MediaNotificationManager(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
        Log.i("ssss","onNotificationRequired");
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
