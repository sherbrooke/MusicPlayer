package com.brooke.sher.loginregistertest.connect.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.brooke.sher.loginregistertest.R;
import com.sher.data.MusicInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Sher on 2017/9/10.
 */

public class MusicAdapter extends BaseQuickAdapter<MusicInfo,BaseViewHolder> {


    public MusicAdapter(@LayoutRes int layoutResId, @Nullable List<MusicInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MusicInfo item) {
        helper.setText(R.id.tv_title,item.getName());

    }


}

