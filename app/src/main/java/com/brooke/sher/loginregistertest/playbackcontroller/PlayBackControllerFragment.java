package com.brooke.sher.loginregistertest.playbackcontroller;

import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.brooke.sher.loginregistertest.BaseFragment;
import com.brooke.sher.loginregistertest.R;
import com.brooke.sher.loginregistertest.data.event.TokenEvent;
import com.bumptech.glide.Glide;
import com.sher.data.MusicInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Sher on 2017/9/16.
 */

public class PlayBackControllerFragment extends BaseFragment  {

    private MediaControllerCompat mMediaController;

    private ImageView ivThumb;
    private ImageView ivPauseOrplaying;
    private ImageView ivMenu;
    private TextView tvTitle;
    private TextView tvArtist;
    private ProgressBar pbProgress;

    private MusicInfo musicInfo;
    private PlaybackStateCompat playbackState;
    private long currentPosition;
    private MediaControllerCompat.Callback callback = new MediaControllerCompat.Callback() {
        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat state) {
            super.onPlaybackStateChanged(state);
            playbackState = state;
            //如果状态为播放中或者不是，修改图标
            if (mMediaController.getPlaybackState()!=null && mMediaController.getPlaybackState().getState() == PlaybackStateCompat.STATE_PLAYING){
                ivPauseOrplaying.setImageResource(R.drawable.a_8);
                currentPosition = playbackState.getPosition();
                if (playbackState.getState() == PlaybackStateCompat.STATE_PLAYING) {
                    // Calculate the elapsed time between the last position update and now and unless
                    // paused, we can assume (delta * speed) + current position is approximately the
                    // latest position. This ensure that we do not repeatedly call the getPlaybackState()
                    // on MediaControllerCompat.
                    long timeDelta = SystemClock.elapsedRealtime() -
                            playbackState.getLastPositionUpdateTime();
                    currentPosition += (int) timeDelta * playbackState.getPlaybackSpeed();
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pbProgress.setProgress((int) currentPosition);
                    }
                },1000);

            }else {
                ivPauseOrplaying.setImageResource(R.drawable.a_9);
            }

    }

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            super.onMetadataChanged(metadata);

//            getcurrentMusic获取当前音乐，显示在控制台上
//            musicInfo = MusicHelper.getCurrentMusic();
//            ivThumb.setImageBitmap();
//               .putString(MediaMetadataCompat.METADATA_KEY_ARTIST,info.getArtist())
//                    .putString(MediaMetadataCompat.METADATA_KEY_ALBUM,info.getAlbum())
//                    .putLong(MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER,info.getAlumbId())
//                    .putString(MediaMetadataCompat.METADATA_KEY_TITLE,info.getTilte())
//                    .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID,info.getId()+"")
//                    .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI,info.getUrl())
//                    .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, info.getDuration())
//                    .putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, info.getBitmap())
            String uri = metadata.getString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART);
            if ( uri  != null)
            Glide.with(PlayBackControllerFragment.this).load(uri).into(ivThumb);
            tvTitle.setText(metadata.getString(MediaMetadataCompat.METADATA_KEY_TITLE));
            tvArtist.setText(metadata.getString(MediaMetadataCompat.METADATA_KEY_ARTIST));
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_playback_controller,container,false);
        EventBus.getDefault().register(this);

        initView(view);
        return view;
    }

    private void initView(View view) {
        pbProgress = view.findViewById(R.id.pb_progress);
        pbProgress.setProgress(0);
        ivThumb = view.findViewById(R.id.iv_thumb);
        ivPauseOrplaying = view.findViewById(R.id.iv_play_or_pause);
        ivMenu = view.findViewById(R.id.iv_music_list);
        tvTitle = view.findViewById(R.id.tv_title);
        tvArtist = view.findViewById(R.id.tv_artist);
        ivPauseOrplaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMediaController.getPlaybackState()!=null && mMediaController.getPlaybackState().getState() == PlaybackStateCompat.STATE_PLAYING){
                    ivPauseOrplaying.setImageResource(R.drawable.a_8);
                    //开始播放
                    mMediaController.getTransportControls().pause();
                }else {
                    ivPauseOrplaying.setImageResource(R.drawable.a_9);
                    //停止播放
                    mMediaController.getTransportControls().play();
                }
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void onEventMainThread(TokenEvent event){
        try {
            mMediaController = new MediaControllerCompat(mContext,event.getToken());
            mMediaController.registerCallback(callback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }




}
