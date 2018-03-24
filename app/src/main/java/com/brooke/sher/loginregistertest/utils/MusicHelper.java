package com.brooke.sher.loginregistertest.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;

import com.sher.data.MusicInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sher on 2017/9/10.
 */

public class MusicHelper {

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private String currentMediaId;
    private int pos;
    private List<MusicInfo> infoList;
    private MediaSessionCompat mediaSessionCompat;
    PlaybackStateCompat.Builder stateBuilder = new PlaybackStateCompat.Builder();
    PlaybackStateCompat playbackStateCompat;
    private PlaybackServiceCallback mServiceCallback;

    private static final int AUDIO_NO_FOCUS_NO_DUCK = 0;
    private int mCurrentAudioFocusState = AUDIO_NO_FOCUS_NO_DUCK;
    private static final int AUDIO_NO_FOCUS_CAN_DUCK = 1;
    // we have full audio focus
    private static final int AUDIO_FOCUSED = 2;

    private final AudioManager mAudioManager;

    public MusicHelper(Context context,MediaSessionCompat sessionCompat, PlaybackServiceCallback serviceCallback){
        this.mediaSessionCompat = sessionCompat;
        mediaSessionCompat.setCallback(callback);
        mServiceCallback = serviceCallback;
        mAudioManager = (AudioManager) context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
    }

    private MediaSessionCompat.Callback callback = new MediaSessionCompat.Callback() {
        @Override
        public void onPlay() {
            super.onPlay();
            tryToGetAudioFocus();
            mediaPlayer.start();
            updatePlaybackState(PlaybackStateCompat.STATE_PLAYING);
        }

        @Override
        public void onPlayFromMediaId(String mediaId, Bundle extras) {
            super.onPlayFromMediaId(mediaId, extras);
            tryToGetAudioFocus();
            try {
                //通过mediaid获取到当前的列表以及当前的音乐,如果mediaid相同那么跳转到音乐详情界面(目前是暂停逻辑)，如果不同，播放不同的音乐
                extras.setClassLoader(getClass().getClassLoader());
                final MusicInfo info = extras.getParcelable("music");
                pos = (int) extras.get("position");
                infoList = (ArrayList<MusicInfo>) extras.get("musicList");
                if (mediaPlayer.isPlaying()) {
                    if (currentMediaId.equals(mediaId)) {
                        mediaPlayer.pause();
                        updatePlaybackState(PlaybackStateCompat.STATE_PAUSED);
                    } else {
                        stopAndChangeMetaData(info.getUrl());
//                            mediaPlayer.stop();
//                            mediaPlayer.reset();
//                            mediaPlayer.setDataSource(info.getUrl());
//                            mediaPlayer.prepare();
//                            mediaPlayer.start();
                        updateMetaData(info);
                        updatePlaybackState(PlaybackStateCompat.STATE_PLAYING);
                    }
                } else {
                    if (!TextUtils.isEmpty(currentMediaId) && currentMediaId.equals(mediaId)) {
                        mediaPlayer.start();
                    } else {
                        changeMetaData(info.getUrl());
                        updateMetaData(info);
//                            mediaPlayer.reset();
//                            mediaPlayer.setDataSource(info.getUrl());
//                            mediaPlayer.prepare();
//                            mediaPlayer.start();
                    }
                    updatePlaybackState(PlaybackStateCompat.STATE_PLAYING);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {

                    //循环播放
                    if (infoList.size() <= pos + 1) {
                        pos = -1;
                    }
                    MusicInfo info = infoList.get(pos + 1);
                    try {
                        changeMetaData(info.getUrl());
                        updateMetaData(info);
//                                mediaPlayer.reset();
//                                mediaPlayer.setDataSource(info.getUrl());
//                                mediaPlayer.prepare();
//                                mediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    updatePlaybackState(PlaybackStateCompat.STATE_PLAYING);
                    pos++;
                    currentMediaId = String.valueOf(info.getId());
                }
            });
            currentMediaId = mediaId;


        }

        @Override
        public void onSkipToQueueItem(long id) {
            super.onSkipToQueueItem(id);
        }

        @Override
        public void onPause() {
            super.onPause();
            mediaPlayer.pause();
            updatePlaybackState(PlaybackStateCompat.STATE_PAUSED);
        }

        @Override
        public void onSkipToNext() {
            super.onSkipToNext();
        }

        @Override
        public void onSkipToPrevious() {
            super.onSkipToPrevious();
        }

        @Override
        public void onSeekTo(long pos) {
            super.onSeekTo(pos);
        }
    };
    private void updatePlaybackState(int statePaused) {
        playbackStateCompat = stateBuilder.setState(statePaused, 0, 1.0f, SystemClock.elapsedRealtime()).build();
        mediaSessionCompat.setPlaybackState(playbackStateCompat);
        if (statePaused == PlaybackStateCompat.STATE_PLAYING ||
                statePaused == PlaybackStateCompat.STATE_PAUSED) {
            mServiceCallback.onNotificationRequired();
        }

    }
    private void updateMetaData(MusicInfo info) {
        mediaSessionCompat.setMetadata(CreateMetaData(info));
    }

    private void changeMetaData(String url) throws IOException {
        mediaPlayer.reset();
        mediaPlayer.setDataSource(url);
        mediaPlayer.prepare();
        mediaPlayer.start();

    }

    private void stopAndChangeMetaData(String url) throws IOException {
        mediaPlayer.stop();
        changeMetaData(url);
    }

//    public static MusicInfo getCurrentMusic() {
//        //如果队列没有变，那么通过position来获得当前音乐
//        return new QueueManager(infoList,pos).getCurrentMusic();
//    }


    public static MediaMetadataCompat CreateMetaData(MusicInfo info){
        return new  MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST,info.getArtist())
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM,info.getAlbum())
                .putLong(MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER,info.getAlumbId())
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE,info.getTilte())
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID,info.getId()+"")
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI,info.getUrl())
                .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, info.getDuration())
                .putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, info.getBitmap())
                .build();
    }

    private void tryToGetAudioFocus() {
        int result =
                mAudioManager.requestAudioFocus(
                        mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mCurrentAudioFocusState = AUDIO_FOCUSED;
        } else {
            mCurrentAudioFocusState = AUDIO_NO_FOCUS_NO_DUCK;
        }
    }

    private void giveUpAudioFocus() {
        if (mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener)
                == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mCurrentAudioFocusState = AUDIO_NO_FOCUS_NO_DUCK;
        }
    }
    private final AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    switch (focusChange) {
                        case AudioManager.AUDIOFOCUS_GAIN:
                            mCurrentAudioFocusState = AUDIO_FOCUSED;
                            break;
                        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                            // Audio focus was lost, but it's possible to duck (i.e.: play quietly)
                            mCurrentAudioFocusState = AUDIO_NO_FOCUS_CAN_DUCK;
                            break;
                        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                            // Lost audio focus, but will gain it back (shortly), so note whether
                            // playback should resume
                            mCurrentAudioFocusState = AUDIO_NO_FOCUS_NO_DUCK;
                            break;
                        case AudioManager.AUDIOFOCUS_LOSS:
                            // Lost audio focus, probably "permanently"
                            mCurrentAudioFocusState = AUDIO_NO_FOCUS_NO_DUCK;
                            break;
                    }

                }
            };



    public interface PlaybackServiceCallback {
        void onPlaybackStart();

        void onNotificationRequired();

        void onPlaybackStop();

        void onPlaybackStateUpdated(PlaybackStateCompat newState);
    }
}
