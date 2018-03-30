package com.sher.data.source.remote;

import android.content.Context;

import com.sher.data.MusicInfo;
import com.sher.data.source.MusicInfoSource;

import java.util.List;

/**
 * Created by Sher on 2018/3/30.
 */

public class RemoteMusicDataSource implements MusicInfoSource {

    private static RemoteMusicDataSource INSTANCE;

    @Override
    public List<MusicInfo> getMusic(Context context) {
        return null;
    }

    private RemoteMusicDataSource(){}

    public static RemoteMusicDataSource getINSTANCE(){
        if (INSTANCE == null) {
            INSTANCE = new RemoteMusicDataSource();
        }
        return INSTANCE;
    }

    public List<List<MusicInfo>> getMusic(){

        return null;
    }
}
