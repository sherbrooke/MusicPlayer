package com.brooke.sher.loginregistertest.connect;


import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brooke.sher.loginregistertest.BaseActivity;
import com.brooke.sher.loginregistertest.BaseFragment;
import com.brooke.sher.loginregistertest.MusicService;
import com.brooke.sher.loginregistertest.R;
import com.brooke.sher.loginregistertest.connect.adapter.MusicAdapter;
import com.brooke.sher.loginregistertest.data.MusicInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Sher on 2017/8/20.
 */

public class ConnectFragment extends BaseFragment implements ConnectContact.View, CallBack {

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
        ((BaseActivity)getActivity()).setCallBack(this);
        rvList = view.findViewById(R.id.rv_list);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        musicInfoList =  presenter.getLocalMusic();


        return view;

    }

    @Override
    public void onComplete(MediaSessionCompat.Token token) {
        try {
            mMediaController = new MediaControllerCompat(mContext,token);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        adapter = new MusicAdapter(R.layout.item_music,musicInfoList);
        rvList.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<MusicInfo> infoList =  adapter.getData();
                ArrayList<MusicInfo> arrayStrings = new ArrayList<>();
                arrayStrings = (ArrayList<MusicInfo>) infoList;

                MusicInfo info = infoList.get(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("music",info);
                bundle.putParcelableArrayList("musicList",arrayStrings);
                bundle.putInt("position",position);
                mMediaController.getTransportControls().playFromMediaId(String.valueOf(info.getId()),bundle);
            }
        });
    }
}
