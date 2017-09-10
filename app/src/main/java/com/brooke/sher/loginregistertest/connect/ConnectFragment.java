package com.brooke.sher.loginregistertest.connect;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brooke.sher.loginregistertest.R;
import com.brooke.sher.loginregistertest.connect.adapter.MusicAdapter;
import com.brooke.sher.loginregistertest.data.MusicInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import android2.ui.fragment.BaseFragment;

/**
 * Created by Sher on 2017/8/20.
 */

public class ConnectFragment extends BaseFragment implements ConnectContact.View{

    private RecyclerView rvList;
    private BaseQuickAdapter adapter;

    private List<MusicInfo> musicInfoList;

    private ConnectPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_connect,container,false);
        presenter = new ConnectPresenter(this,mContext);
        rvList = view.findViewById(R.id.rv_list);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        musicInfoList =  presenter.getLocalMusic();
        adapter = new MusicAdapter(R.layout.item_music,musicInfoList);
        rvList.setAdapter(adapter);
        return view;

    }



}
