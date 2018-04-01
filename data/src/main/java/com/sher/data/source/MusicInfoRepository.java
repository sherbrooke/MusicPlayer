package com.sher.data.source;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.brooke.sher.app2.net.HttpCallback;
import com.sher.data.MusicInfo;
import com.sher.data.source.local.LocalMusicDataSource;
import com.sher.data.source.remote.RemoteMusicDataSource;

import java.util.List;

/**
 * Created by wangyang on 2017/9/14.
 */

public class MusicInfoRepository {

    @Nullable
    private static MusicInfoRepository INSTANCE = null;

    private LocalMusicDataSource mLocalMusicDataSource;
    private RemoteMusicDataSource mRemoteMusicDataSource;

    private MusicInfoRepository(LocalMusicDataSource mLocalMusicDataSource, RemoteMusicDataSource mRemoteMusicDataSource){
        this.mLocalMusicDataSource = mLocalMusicDataSource;
        this.mRemoteMusicDataSource = mRemoteMusicDataSource;
    }

    public static MusicInfoRepository getInstance(@NonNull LocalMusicDataSource mLocalMusicDataSource, @NonNull RemoteMusicDataSource mRemoteMusicDataSource
                                                ) {
        if (INSTANCE == null) {
            INSTANCE = new MusicInfoRepository( mLocalMusicDataSource,mRemoteMusicDataSource);
        }
        return INSTANCE;
    }

    public List<MusicInfo> getMusic(Context context) {
        return mLocalMusicDataSource.getMusic(context);
    }

    public List<List<MusicInfo>> getNetMusic(HttpCallback callback) {
        return mRemoteMusicDataSource.getRemoteMusic(callback);
    }

    public List<MusicInfo> searchList(String tag){
        return null;
    }

}
