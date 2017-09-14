package com.brooke.sher.loginregistertest.data.source;

import android.content.Context;

import com.brooke.sher.loginregistertest.data.MusicInfo;

import java.util.List;

/**
 * Created by wangyang on 2017/9/14.
 */

public interface MusicInfoSource {

    List<MusicInfo> getMusic(Context context);
}
