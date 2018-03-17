package com.sher.data.source;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sher.data.MusicInfo;

import java.util.List;

/**
 * Created by wangyang on 2017/9/14.
 */

public class MusicInfoRepository {

    @Nullable
    private static MusicInfoRepository INSTANCE = null;

    private MusicInfoSource mLocalMusicDataSource;

    private MusicInfoRepository(MusicInfoSource mLocalMusicDataSource){
        this.mLocalMusicDataSource = mLocalMusicDataSource;
    }

    public static MusicInfoRepository getInstance(@NonNull MusicInfoSource mLocalMusicDataSource
                                                ) {
        if (INSTANCE == null) {
            INSTANCE = new MusicInfoRepository( mLocalMusicDataSource);
        }
        return INSTANCE;
    }

    public List<MusicInfo> getMusic(Context context) {
        return mLocalMusicDataSource.getMusic(context);
    }

    public List<MusicInfo> searchList(String tag){
        return null;
    }

}
