package com.brooke.sher.loginregistertest;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import com.brooke.sher.app2.ui.BaseAppActivity;
import com.brooke.sher.loginregistertest.connect.CallBack;
import com.brooke.sher.loginregistertest.data.event.TokenEvent;
import com.brooke.sher.loginregistertest.playbackcontroller.PlayBackControllerFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int hasWritePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int hasReadPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

            List<String> permissions = new ArrayList<String>();
            if (hasWritePermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            } else {
//              preferencesUtility.setString("storage", "true");
            }

            if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);

            } else {
//              preferencesUtility.setString("storage", "true");
            }

            if (!permissions.isEmpty()) {
//              requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_SOME_FEATURES_PERMISSIONS);

                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE },
                        0);
            }
        }

        Intent intent = new Intent(mContext, MusicService.class);
        startService(intent);
        bindService(intent,this, Context.BIND_AUTO_CREATE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 0: {
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {


                        System.out.println("Permissions --> " + "Permission Granted: " + permissions[i]);
                    } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        System.out.println("Permissions --> " + "Permission Denied: " + permissions[i]);
                    }
                }
            }
            break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }


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


        if (mMediaController!=null
//                && (long)(mMediaController.getPlaybackState().getPlaybackState()) == PlaybackStateCompat.STATE_PLAYING
                ){
            showPlaybackControls();
        }
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

//        ThreadLocal<MediaSessionCompat.Token> token = new ThreadLocal<>();
//        token.set(musicService.getToken());

        EventBus.getDefault().post(new TokenEvent(musicService.getToken()));
        if (callback!=null){
            callback.onComplete(musicService.getToken());
        }

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
                    .commitAllowingStateLoss();
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
        unbindService(this);
    }

}

