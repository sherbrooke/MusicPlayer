package com.brooke.sher.loginregistertest.data.event;

import android.support.v4.media.session.MediaSessionCompat;

/**
 * Created by wangyang on 2017/9/21.
 */

public class TokenEvent {
    private MediaSessionCompat.Token token;

    public TokenEvent(MediaSessionCompat.Token token) {
        this.token = token;
    }

    public MediaSessionCompat.Token getToken() {
        return token;
    }

    public void setToken(MediaSessionCompat.Token token) {
        this.token = token;
    }
}
