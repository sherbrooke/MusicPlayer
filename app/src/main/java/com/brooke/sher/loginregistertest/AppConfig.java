package com.brooke.sher.loginregistertest;

import android.content.Context;

import com.sher.android2.BaseAppConfig;


/**
 * Created by Sher on 2017/8/20.
 */

public class AppConfig extends BaseAppConfig {

    protected AppConfig(Context context) {
        super(context);
    }

    public static AppConfig getInstance(Context context){
        return new AppConfig(context);
    }
}
