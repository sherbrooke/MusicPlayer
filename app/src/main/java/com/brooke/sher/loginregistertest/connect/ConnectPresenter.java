package com.brooke.sher.loginregistertest.connect;

import android.content.Context;

import com.brooke.sher.app2.net.HttpCallback;
import com.sher.data.MusicInfo;
import com.sher.data.source.MusicInfoRepository;
import com.sher.data.source.local.LocalMusicDataSource;
import com.sher.data.source.remote.RemoteMusicDataSource;

import java.util.List;

/**
 * Created by Sher on 2017/9/10.
 */

public class ConnectPresenter implements ConnectContact.Presenter {

    private ConnectContact.View connectView;
    private MusicInfo musicInfo;
    private List<MusicInfo> musicInfoList;
    private Context context;

    public ConnectPresenter( Context context){
        this.connectView = connectView;
        this.context = context;
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public List<MusicInfo> getLocalMusic() {
        return MusicInfoRepository.getInstance(LocalMusicDataSource.getINSTANCE(), RemoteMusicDataSource.getINSTANCE()).getMusic(context);
    }

    @Override
    public List<List<MusicInfo>> getRemoteMusic(HttpCallback callback) {
        return MusicInfoRepository.getInstance(LocalMusicDataSource.getINSTANCE(), RemoteMusicDataSource.getINSTANCE()).getNetMusic( callback);
    }
}
