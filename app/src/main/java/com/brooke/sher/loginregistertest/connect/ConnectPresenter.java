package com.brooke.sher.loginregistertest.connect;

import android.content.Context;

import com.brooke.sher.loginregistertest.data.MusicInfo;
import com.brooke.sher.loginregistertest.data.source.MusicInfoRepository;
import com.brooke.sher.loginregistertest.data.source.local.LocalMusicDataSource;

import java.util.List;

/**
 * Created by Sher on 2017/9/10.
 */

public class ConnectPresenter implements ConnectContact.Presenter {

    private ConnectContact.View connectView;
    private MusicInfo musicInfo;
    private List<MusicInfo> musicInfoList;
    private Context context;

    public ConnectPresenter(ConnectContact.View connectView, Context context){
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
        return MusicInfoRepository.getInstance(LocalMusicDataSource.getINSTANCE()).getMusic(context);
    }
}
