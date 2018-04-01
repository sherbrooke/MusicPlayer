package com.brooke.sher.loginregistertest.mainui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brooke.sher.loginregistertest.BaseFragment;
import com.brooke.sher.loginregistertest.R;
import com.brooke.sher.loginregistertest.Rank.RankActivity;


/**
 * Created by Sher on 2017/8/20.
 */

public class MainFragment extends BaseFragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_main,container,false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.tv_new).setOnClickListener(this);
        view.findViewById(R.id.tv_hot).setOnClickListener(this);
        view.findViewById(R.id.tv_billboard).setOnClickListener(this);
        view.findViewById(R.id.tv_pop).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this.getActivity(), RankActivity.class);
        switch (v.getId()){
            case R.id.tv_new:
                intent.putExtra("index",0);
                startActivity(intent);
                break;
            case R.id.tv_hot:
                intent.putExtra("index",1);
                startActivity(intent);
                break;
            case R.id.tv_billboard:
                intent.putExtra("index",2);
                startActivity(intent);
                break;
            case R.id.tv_pop:
                intent.putExtra("index",3);
                startActivity(intent);
                break;
        }
    }
}
