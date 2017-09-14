package com.brooke.sher.loginregistertest.connect;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brooke.sher.loginregistertest.BaseFragment;
import com.brooke.sher.loginregistertest.MusicService;
import com.brooke.sher.loginregistertest.R;
import com.brooke.sher.loginregistertest.connect.adapter.MusicAdapter;
import com.brooke.sher.loginregistertest.data.MusicInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sher.android2.ui.BaseAppActivity;

import java.util.List;


/**
 * Created by Sher on 2017/8/20.
 */

public class ConnectFragment extends BaseFragment implements ConnectContact.View {

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

        mMediaController = MediaControllerCompat.getMediaController(((BaseAppActivity)this.getActivity()).baseAppActivity);

        rvList = view.findViewById(R.id.rv_list);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        musicInfoList =  presenter.getLocalMusic();
        adapter = new MusicAdapter(R.layout.item_music,musicInfoList);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MusicInfo info = (MusicInfo) adapter.getData().get(position);
                Log.i("ssss",info.getTilte());
                if (mMediaController.getPlaybackState().getState() == PlaybackStateCompat.STATE_PLAYING){
                    mMediaController.getTransportControls().pause();
                }else {
                    Bundle bundle = new Bundle();
//                    bundle.putInt("");
//                    mMediaController.getTransportControls().playFromMediaId();
                }
            }
        });
        rvList.setAdapter(adapter);

        return view;

    }
}
