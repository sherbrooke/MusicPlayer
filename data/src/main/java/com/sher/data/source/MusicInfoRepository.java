package com.sher.data.source;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sher.data.MusicInfo;

import java.util.List;

/**
 * Created by wangyang on 2017/9/14.
 */

public class MusicInfoRepository implements MusicInfoSource {

    @Nullable
    private static MusicInfoRepository INSTANCE = null;

    private MusicInfoSource mLocalMusicDataSource;
    private MusicInfoSource mRemoteMusicDataSource;

    private MusicInfoRepository(MusicInfoSource mLocalMusicDataSource, MusicInfoSource mRemoteMusicDataSource){
        this.mLocalMusicDataSource = mLocalMusicDataSource;
        this.mRemoteMusicDataSource = mRemoteMusicDataSource;
    }

    public static MusicInfoRepository getInstance(@NonNull MusicInfoSource mLocalMusicDataSource, @NonNull MusicInfoSource mRemoteMusicDataSource
                                                ) {
        if (INSTANCE == null) {
            INSTANCE = new MusicInfoRepository( mLocalMusicDataSource,mRemoteMusicDataSource);
        }
        return INSTANCE;
    }

    public List<MusicInfo> getMusic(Context context) {
        return mLocalMusicDataSource.getMusic(context);
    }

    public List<MusicInfo> getNetMusic(Context context) {
        return mRemoteMusicDataSource.getMusic(context);
    }

    public List<MusicInfo> searchList(String tag){
        return null;
    }

}
