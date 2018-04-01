package com.brooke.sher.loginregistertest.connect;

import com.brooke.sher.app2.net.HttpCallback;
import com.brooke.sher.loginregistertest.BasePresenter;
import com.brooke.sher.loginregistertest.BaseView;
import com.sher.data.MusicInfo;

import java.util.List;

/**
 * Created by Sher on 2017/9/10.
 */

public class ConnectContact {

    interface View extends BaseView<Presenter>{
    }

    interface Presenter extends BasePresenter{
        List<MusicInfo> getLocalMusic();

        List<List<MusicInfo>> getRemoteMusic(HttpCallback callback);
    }
}
