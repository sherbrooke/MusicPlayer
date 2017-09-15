package com.brooke.sher.loginregistertest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.session.MediaControllerCompat;

import com.brooke.sher.loginregistertest.connect.CallBack;
import com.sher.android2.ui.BaseAppActivity;


/**
 * activity的基类，应用内所有页面都应继承自该类
 */
public abstract class BaseActivity extends BaseAppActivity implements ServiceConnection {

    private MediaControllerCompat mMediaController;
    private MusicService musicService;
    private CallBack callback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(mContext, MusicService.class);
        bindService(intent,this, Context.BIND_AUTO_CREATE);

    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        musicService = ((MusicService.MusicBinder) iBinder).getService();
            callback.onComplete(musicService.getToken());
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }

    public void setCallBack(CallBack callBack){
        this.callback = callBack;
    }

}

