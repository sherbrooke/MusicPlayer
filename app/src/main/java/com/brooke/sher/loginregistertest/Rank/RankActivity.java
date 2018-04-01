package com.brooke.sher.loginregistertest.Rank;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.brooke.sher.app2.net.HttpCallback;
import com.brooke.sher.loginregistertest.BaseActivity;
import com.brooke.sher.loginregistertest.R;
import com.brooke.sher.loginregistertest.connect.CallBack;
import com.brooke.sher.loginregistertest.connect.ConnectPresenter;
import com.brooke.sher.loginregistertest.connect.adapter.MusicAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sher.data.MusicInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class RankActivity extends BaseActivity implements HttpCallback, CallBack {
    private RecyclerView rvList;
    private BaseQuickAdapter adapter;

    private List<MusicInfo> musicInfoList;
    private List<List<MusicInfo>> musicInfoLists;

    private ConnectPresenter presenter;

    private MediaControllerCompat mMediaController;

    private int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        Intent intent = getIntent();
        index = intent.getIntExtra("index",0);
        Log.i("ssss",index+"index");
        presenter = new ConnectPresenter(mContext);
        rvList = findViewById(R.id.rv_list);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        presenter.getRemoteMusic(this);
        setCallBack(this);
    }

    @Override
    public void onComplete(MediaSessionCompat.Token token) {
        try {
            mMediaController = new MediaControllerCompat(mContext,token);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onComplete(Response response) {
        musicInfoLists = (List<List<MusicInfo>>) (response.body());
        Log.i("ssss",index+"index1");
        musicInfoList  = musicInfoLists.get(index);
//                for (MusicInfo musicInfo : musicInfoList){
//                    Log.i("ssss",musicInfo.getName());
//                }
        adapter = new MusicAdapter(R.layout.item_music,musicInfoList);
        adapter.notifyDataSetChanged();
        rvList.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<MusicInfo> infoList =  adapter.getData();
                ArrayList<MusicInfo> arrayStrings = new ArrayList<>();
                arrayStrings = (ArrayList<MusicInfo>) infoList;
//                for (MusicInfo musicInfo : arrayStrings){
//                    Log.i("ssss",musicInfo.getName());
//                }
                MusicInfo info = infoList.get(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("music",info);
                bundle.putParcelableArrayList("musicList",arrayStrings);
                bundle.putInt("position",position);
                mMediaController.getTransportControls().playFromMediaId(String.valueOf(info.getId()),bundle);
            }
        });
    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
    }
}
