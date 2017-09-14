package com.brooke.sher.loginregistertest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.media.session.MediaControllerCompat;

import com.sher.android2.ui.fragment.BaseAppFragment;


/**
 * fragment基类，app内所有fragment都应继承自本类
 */
public abstract class BaseFragment extends BaseAppFragment implements ServiceConnection {

    private MediaControllerCompat mMediaController;
    private MusicService musicService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(mContext, MusicService.class);
        mContext.bindService(intent,this, Context.BIND_AUTO_CREATE);

    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        musicService = (MusicService) iBinder;
        try {
            mMediaController = new MediaControllerCompat(mContext,musicService.getToken());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }
}
