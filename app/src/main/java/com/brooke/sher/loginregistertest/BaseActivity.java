package com.brooke.sher.loginregistertest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.brooke.sher.app2.ui.BaseAppActivity;
import com.brooke.sher.loginregistertest.connect.CallBack;
import com.brooke.sher.loginregistertest.data.event.TokenEvent;
import com.brooke.sher.loginregistertest.playbackcontroller.PlayBackControllerFragment;

import org.greenrobot.eventbus.EventBus;


/**
 * activity的基类，应用内所有页面都应继承自该类
 */
public abstract class BaseActivity extends BaseAppActivity implements ServiceConnection {

    private MediaControllerCompat mMediaController;
    private MusicService musicService;
    private CallBack callback;
    private PlayBackControllerFragment mControlsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(mContext, MusicService.class);
        bindService(intent,this, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mControlsFragment = (PlayBackControllerFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_playback_controls);
        if (mControlsFragment == null) {
            throw new IllegalStateException("Mising fragment with id 'controls'. Cannot continue.");
        }

        hidePlaybackControls();
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        musicService = ((MusicService.MusicBinder) iBinder).getService();
        try {
            mMediaController = new MediaControllerCompat(this,musicService.getToken());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        mMediaController.registerCallback(mMediaControllerCallback);

        EventBus.getDefault().post(new TokenEvent(musicService.getToken()));
        if (callback!=null)
            callback.onComplete(musicService.getToken());

        boolean shou = shouldShowControls();
        if (shou) {
            showPlaybackControls();
        } else {
            hidePlaybackControls();
        }


    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }

    public void setCallBack(CallBack callBack){
        this.callback = callBack;
    }

    protected boolean shouldShowControls() {
        if (mMediaController == null ||
//                mMediaController.getMetadata() == null ||
                mMediaController.getPlaybackState() == null) {
            return false;
        }
        switch (mMediaController.getPlaybackState().getState()) {
            case PlaybackStateCompat.STATE_ERROR:
            case PlaybackStateCompat.STATE_NONE:
            case PlaybackStateCompat.STATE_STOPPED:
                return false;
            default:
                return true;
        }
    }

    protected void showPlaybackControls() {
        getSupportFragmentManager().beginTransaction()
                    .show(mControlsFragment)
                    .commit();
    }

    protected void hidePlaybackControls() {
        getSupportFragmentManager().beginTransaction()
                .hide(mControlsFragment)
                .commit();
    }

    // Callback that ensures that we are showing the controls
    private final MediaControllerCompat.Callback mMediaControllerCallback =
            new MediaControllerCompat.Callback() {
                @Override
                public void onPlaybackStateChanged(@NonNull PlaybackStateCompat state) {
                    if (shouldShowControls()) {
                        showPlaybackControls();
                    } else {
                        hidePlaybackControls();
                    }
                }

                @Override
                public void onMetadataChanged(MediaMetadataCompat metadata) {
                    if (shouldShowControls()) {
                        showPlaybackControls();
                    } else {
                        hidePlaybackControls();
                    }
                }
            };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

