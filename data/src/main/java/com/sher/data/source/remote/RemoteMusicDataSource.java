package com.sher.data.source.remote;

import com.brooke.sher.app2.net.HttpCallback;
import com.sher.data.MusicInfo;
import com.sher.data.net.HttpMethods;

import java.util.List;

/**
 * Created by Sher on 2018/3/30.
 */

public class RemoteMusicDataSource   {

    private static RemoteMusicDataSource INSTANCE;

    private RemoteMusicDataSource(){}

    public static RemoteMusicDataSource getINSTANCE(){
        if (INSTANCE == null) {
            INSTANCE = new RemoteMusicDataSource();
        }
        return INSTANCE;
    }

    public List<List<MusicInfo>> getRemoteMusic(HttpCallback callback){
        return HttpMethods.getInstance().getMusic(callback);
    }
}
