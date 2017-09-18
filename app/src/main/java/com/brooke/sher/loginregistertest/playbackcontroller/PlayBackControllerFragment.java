package com.brooke.sher.loginregistertest.playbackcontroller;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brooke.sher.loginregistertest.BaseActivity;
import com.brooke.sher.loginregistertest.BaseFragment;
import com.brooke.sher.loginregistertest.R;
import com.brooke.sher.loginregistertest.connect.CallBack;

/**
 * Created by Sher on 2017/9/16.
 */

public class PlayBackControllerFragment extends BaseFragment implements CallBack {

    private MediaControllerCompat mMediaController;

    private ImageView ivThumb;
    private ImageView ivPauseOrplaying;
    private ImageView ivMenu;
    private TextView tvTitle;
    private TextView tvArtist;

    private MediaControllerCompat.Callback callback = new MediaControllerCompat.Callback() {
        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat state) {
            super.onPlaybackStateChanged(state);

            //如果状态为播放中或者不是，修改图标
            if (mMediaController.getPlaybackState()!=null && mMediaController.getPlaybackState().getState() == PlaybackStateCompat.STATE_PLAYING){
                ivPauseOrplaying.setImageResource(R.drawable.a_8);
            }else {
                ivPauseOrplaying.setImageResource(R.drawable.a_9);
            }

    }

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            super.onMetadataChanged(metadata);

//            getcurrentMusic获取当前音乐，显示在控制台上

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_playback_controller,container,false);

        ivThumb = view.findViewById(R.id.iv_thumb);
        ivPauseOrplaying = view.findViewById(R.id.iv_play_or_pause);
        ivMenu = view.findViewById(R.id.iv_music_list);
        tvTitle = view.findViewById(R.id.tv_title);
        tvArtist = view.findViewById(R.id.tv_artist);

        ((BaseActivity)getActivity()).setCallBack(this);
        return view;
    }

    @Override
    public void onComplete(MediaSessionCompat.Token token) {
        try {
            mMediaController = new MediaControllerCompat(mContext,token);
            mMediaController.registerCallback(callback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
