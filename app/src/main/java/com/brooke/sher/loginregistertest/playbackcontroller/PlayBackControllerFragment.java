package com.brooke.sher.loginregistertest.playbackcontroller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brooke.sher.loginregistertest.BaseFragment;
import com.brooke.sher.loginregistertest.R;

/**
 * Created by Sher on 2017/9/16.
 */

public class PlayBackControllerFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_playback_controller,container,false);

        return view;
    }
}
