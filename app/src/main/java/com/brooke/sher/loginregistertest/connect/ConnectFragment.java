package com.brooke.sher.loginregistertest.connect;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brooke.sher.loginregistertest.MusicService;
import com.brooke.sher.loginregistertest.R;
import com.brooke.sher.loginregistertest.connect.adapter.MusicAdapter;
import com.brooke.sher.loginregistertest.data.MusicInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sher.android2.ui.fragment.BaseFragment;

import java.util.List;


/**
 * Created by Sher on 2017/8/20.
 */

public class ConnectFragment extends BaseFragment implements ConnectContact.View, ServiceConnection {

    private RecyclerView rvList;
    private BaseQuickAdapter adapter;

    private List<MusicInfo> musicInfoList;

    private ConnectPresenter presenter;

    private MediaControllerCompat mMediaController;
    private MusicService musicService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_connect,container,false);
        presenter = new ConnectPresenter(this,mContext);
        rvList = view.findViewById(R.id.rv_list);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        musicInfoList =  presenter.getLocalMusic();
        adapter = new MusicAdapter(R.layout.item_music,musicInfoList);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mMediaController.getPlaybackState().getState() == PlaybackStateCompat.STATE_PLAYING){
                    mMediaController.getTransportControls().pause();
                }else {
                    mMediaController.getTransportControls().play();
                }
            }
        });
        rvList.setAdapter(adapter);

        Intent intent = new Intent(mContext, MusicService.class);
        mContext.bindService(intent,this, Context.BIND_AUTO_CREATE);

        try {
            mMediaController = new MediaControllerCompat(mContext,musicService.getToken());
        } catch (RemoteException e) {
            e.printStackTrace();
        }


        return view;

    }


    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        musicService = (MusicService) iBinder;
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }
}
