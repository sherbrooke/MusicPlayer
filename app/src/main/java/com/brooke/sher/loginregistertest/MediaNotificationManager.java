package com.brooke.sher.loginregistertest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import com.brooke.sher.loginregistertest.mainui.MainActivity;
import com.brooke.sher.loginregistertest.utils.AlbumArtCache;
import com.brooke.sher.loginregistertest.utils.ResourceHelper;

/**
 * Created by wangyang on 2017/9/21.
 */

public class MediaNotificationManager extends BroadcastReceiver{

    private static final String ACTION_PLAY = "com.sher.music.play";
    private static final String ACTION_PAUSE = "com.sher.music.pause";
    private static final String ACTION_PREV = "com.sher.music.prev";
    private static final String ACTION_NEXT = "com.sher.music.next";
    private static final String ACTION_STOP = "com.sher.music.stop";
    private static final String ACTION_STOP_CASTING = "com.sher.music.stop_cast";

    private final PendingIntent mPlayIntent;
    private final PendingIntent mPauseIntent;
    private final PendingIntent mPreviousIntent;
    private final PendingIntent mNextIntent;
    private final PendingIntent mStopIntent;
    private final PendingIntent mStopCastIntent;
    
    private MusicService service;
    private MediaControllerCompat controllerCompat;
    private MediaSessionCompat.Token mSessionToken;
    private MediaControllerCompat mController;
    private MediaControllerCompat.TransportControls mTransportControls;

    private final NotificationManager mNotificationManager;
    private final int mNotificationColor;

    private static final int NOTIFICATION_ID = 412;
    private static final int REQUEST_CODE = 100;

    private String channelID = "1";

    private String channelName = "channel_name";

    private MediaMetadataCompat mMetadata;
    private PlaybackStateCompat mPlaybackState;
    
    private boolean mStarted = false;
    
    public  MediaNotificationManager(MusicService service) throws RemoteException {
        this.service = service;
        updateSessionToken();

        mNotificationColor = ResourceHelper.getThemeColor(service, R.attr.colorPrimary,
                Color.DKGRAY);

        mNotificationManager = (NotificationManager) service.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
            channel.setSound(null,null);
            mNotificationManager.createNotificationChannel(channel);
        }


        String pkg = service.getPackageName();
        mPauseIntent = PendingIntent.getBroadcast(service, REQUEST_CODE,
                new Intent(ACTION_PAUSE).setPackage(pkg), PendingIntent.FLAG_CANCEL_CURRENT);
        mPlayIntent = PendingIntent.getBroadcast(service, REQUEST_CODE,
                new Intent(ACTION_PLAY).setPackage(pkg), PendingIntent.FLAG_CANCEL_CURRENT);
        mPreviousIntent = PendingIntent.getBroadcast(service, REQUEST_CODE,
                new Intent(ACTION_PREV).setPackage(pkg), PendingIntent.FLAG_CANCEL_CURRENT);
        mNextIntent = PendingIntent.getBroadcast(service, REQUEST_CODE,
                new Intent(ACTION_NEXT).setPackage(pkg), PendingIntent.FLAG_CANCEL_CURRENT);
        mStopIntent = PendingIntent.getBroadcast(service, REQUEST_CODE,
                new Intent(ACTION_STOP).setPackage(pkg), PendingIntent.FLAG_CANCEL_CURRENT);
        mStopCastIntent = PendingIntent.getBroadcast(service, REQUEST_CODE,
                new Intent(ACTION_STOP_CASTING).setPackage(pkg),
                PendingIntent.FLAG_CANCEL_CURRENT);
    }

    private void updateSessionToken() throws RemoteException {
        MediaSessionCompat.Token refreshToken = service.getToken();
        if (mSessionToken == null && refreshToken != null ||
                mSessionToken != null && !mSessionToken.equals(refreshToken)) {
            if (mController != null) {
                mController.unregisterCallback(mCb);
            }
            mSessionToken = refreshToken;
            if (mSessionToken != null) {
                mController = new MediaControllerCompat(service,mSessionToken);
                mTransportControls = mController.getTransportControls();
                if (mStarted){
                    mController.registerCallback(mCb);
                }
            }
        }
    }

    public void startNotification() {
        if (!mStarted){
            mMetadata = mController.getMetadata();
            mPlaybackState = mController.getPlaybackState();
            Notification notification = createNotification();
            if (notification!=null){
                mController.registerCallback(mCb);
                IntentFilter filter = new IntentFilter();
                filter.addAction(ACTION_NEXT);
                filter.addAction(ACTION_PAUSE);
                filter.addAction(ACTION_PLAY);
                filter.addAction(ACTION_PREV);
                filter.addAction(ACTION_STOP_CASTING);
                service.registerReceiver(this, filter);

                service.startForeground(NOTIFICATION_ID, notification);
                mStarted = true;
            }
        }
        
    }
    public void stopNotification() {
        if (mStarted) {
            mStarted = false;
            mController.unregisterCallback(mCb);
            try {
                mNotificationManager.cancel(NOTIFICATION_ID);
                service.unregisterReceiver(this);
            } catch (IllegalArgumentException ex) {
                // ignore if the receiver is not registered.
            }
            service.stopForeground(true);
        }
    }


    private final MediaControllerCompat.Callback mCb = new MediaControllerCompat.Callback() {
        @Override
        public void onPlaybackStateChanged(@NonNull PlaybackStateCompat state) {
            mPlaybackState = state;
            if (state.getState() == PlaybackStateCompat.STATE_STOPPED ||
                    state.getState() == PlaybackStateCompat.STATE_NONE) {
                stopNotification();
            } else {
                Notification notification = createNotification();
                if (notification != null) {
                    mNotificationManager.notify(NOTIFICATION_ID, notification);
                }
            }
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            mMetadata = metadata;
            Notification notification = createNotification();
            if (notification != null) {
                mNotificationManager.notify(NOTIFICATION_ID, notification);
            }
        }

        @Override
        public void onSessionDestroyed() {
            super.onSessionDestroyed();
            Log.d("ssss", "Session was destroyed, resetting to the new session token");
            try {
                updateSessionToken();
            } catch (RemoteException e) {
                Log.e("ssss",  "could not connect media controller");
            }
        }
    };

    private Notification createNotification() {
        if (mMetadata == null || mPlaybackState == null) {
            return null;
        }

        android.support.v4.app.NotificationCompat.Builder notificationBuilder = new android.support.v4.app.NotificationCompat.Builder(service,channelID);
        int playPauseButtonPosition = 0;

        // If skip to previous action is enabled
        if ((mPlaybackState.getActions() & PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS) != 0) {
            notificationBuilder.addAction(R.drawable.ic_skip_previous_white_24dp,
                    service.getString(R.string.label_previous), mPreviousIntent);

            // If there is a "skip to previous" button, the play/pause button will
            // be the second one. We need to keep track of it, because the MediaStyle notification
            // requires to specify the index of the buttons (actions) that should be visible
            // when in compact view.
            playPauseButtonPosition = 1;
        }

        addPlayPauseAction(notificationBuilder);

        // If skip to next action is enabled
        if ((mPlaybackState.getActions() & PlaybackStateCompat.ACTION_SKIP_TO_NEXT) != 0) {
            notificationBuilder.addAction(R.drawable.ic_skip_next_white_24dp,
                    service.getString(R.string.label_next), mNextIntent);
        }

        MediaDescriptionCompat description = mMetadata.getDescription();

        String fetchArtUrl = null;
        Bitmap art = null;
        if (description.getIconUri() != null) {
            // This sample assumes the iconUri will be a valid URL formatted String, but
            // it can actually be any valid Android Uri formatted String.
            // async fetch the album art icon
            String artUrl = description.getIconUri().toString();
            art = AlbumArtCache.getInstance().getBigImage(artUrl);
            if (art == null) {
                fetchArtUrl = artUrl;
                // use a placeholder art while the remote art is being downloaded
                art = BitmapFactory.decodeResource(service.getResources(),
                        R.drawable.ic_default_art);
            }
        }

        notificationBuilder
                .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(
                                new int[]{playPauseButtonPosition})  // show only play/pause in compact view
                        .setMediaSession(mSessionToken))
                .setColor(mNotificationColor)
                .setSmallIcon(R.drawable.ic_notification)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setUsesChronometer(true)
                .setContentIntent(createContentIntent(description))
                .setContentTitle(description.getTitle())
                .setContentText(description.getSubtitle())
                .setLargeIcon(art);
//        notificationBuilder.setChannelId(channelID);
        if (mController != null && mController.getExtras() != null) {
            String castName = mController.getExtras().getString(MusicService.EXTRA_CONNECTED_CAST);
            if (castName != null) {
                String castInfo = service.getResources()
                        .getString(R.string.casting_to_device, castName);
                notificationBuilder.setSubText(castInfo);
                notificationBuilder.addAction(R.drawable.ic_close_black_24dp,
                        service.getString(R.string.stop_casting), mStopCastIntent);
            }
        }

        setNotificationPlaybackState(notificationBuilder);
        if (fetchArtUrl != null) {
            fetchBitmapFromURLAsync(fetchArtUrl, notificationBuilder);
        }

        return notificationBuilder.build();
    }

    private void fetchBitmapFromURLAsync(String bitmapUrl, final NotificationCompat.Builder builder) {
        AlbumArtCache.getInstance().fetch(bitmapUrl, new AlbumArtCache.FetchListener() {
            @Override
            public void onFetched(String artUrl, Bitmap bitmap, Bitmap icon) {
                if (mMetadata != null && mMetadata.getDescription().getIconUri() != null &&
                        mMetadata.getDescription().getIconUri().toString().equals(artUrl)) {
                    // If the media is still the same, update the notification:
                    builder.setLargeIcon(bitmap);
                    Log.i("ssss","onNotificationRequired");
                    mNotificationManager.notify(NOTIFICATION_ID, builder.build());
                }else{
                    Log.i("ssss","onNotificationRequired1");
                    mNotificationManager.notify(NOTIFICATION_ID, builder.build());
                }
            }
        });
    }

    private void setNotificationPlaybackState(NotificationCompat.Builder builder) {
        if (mPlaybackState == null || !mStarted) {
            service.stopForeground(true);
            return;
        }
        if (mPlaybackState.getState() == PlaybackStateCompat.STATE_PLAYING
                && mPlaybackState.getPosition() >= 0) {
            builder
                    .setWhen(System.currentTimeMillis() - mPlaybackState.getPosition())
                    .setShowWhen(true)
                    .setUsesChronometer(true);
        } else {
            builder
                    .setWhen(0)
                    .setShowWhen(false)
                    .setUsesChronometer(false);
        }

        // Make sure that the notification can be dismissed by the user when we are not playing:
        builder.setOngoing(mPlaybackState.getState() == PlaybackStateCompat.STATE_PLAYING);
    }


    private void addPlayPauseAction(NotificationCompat.Builder builder) {
        String label;
        int icon;
        PendingIntent intent;
        if (mPlaybackState.getState() == PlaybackStateCompat.STATE_PLAYING) {
            label = service.getString(R.string.label_pause);
            icon = R.drawable.ic_pause_white_24dp;
            intent = mPauseIntent;
        } else {
            label = service.getString(R.string.label_play);
            icon = R.drawable.ic_play_arrow_white_24dp;
            intent = mPlayIntent;
        }
        builder.addAction(new NotificationCompat.Action(icon, label, intent));
    }


    private PendingIntent createContentIntent(MediaDescriptionCompat description) {
        Intent openUI = new Intent(service, MainActivity.class);
        openUI.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        openUI.putExtra(MainActivity.EXTRA_START_FULLSCREEN, true);
        if (description != null) {
//            openUI.putExtra(MainActivity.EXTRA_CURRENT_MEDIA_DESCRIPTION, description);
        }
        return PendingIntent.getActivity(service, REQUEST_CODE, openUI,
                PendingIntent.FLAG_CANCEL_CURRENT);
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        Log.d("ssss", "Received intent with action ");
        switch (action) {
            case ACTION_PAUSE:
                mTransportControls.pause();
                break;
            case ACTION_PLAY:
                mTransportControls.play();
                break;
            case ACTION_NEXT:
                mTransportControls.skipToNext();
                break;
            case ACTION_PREV:
                mTransportControls.skipToPrevious();
                break;
            case ACTION_STOP_CASTING:
                //停止
                
                Intent i = new Intent(context, MusicService.class);
                i.setAction(MusicService.ACTION_CMD);
                i.putExtra(MusicService.CMD_NAME, MusicService.CMD_STOP_CASTING);
                service.startService(i);
                break;
            default:
                Log.w("ssss", "Unknown intent ignored");
        }
    }
}
