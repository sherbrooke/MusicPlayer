package com.brooke.sher.loginregistertest.connect;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brooke.sher.loginregistertest.R;

import android2.ui.fragment.BaseFragment;

/**
 * Created by Sher on 2017/8/20.
 */

public class ConnectFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_connect,container,false);



        return view;

    }
}
