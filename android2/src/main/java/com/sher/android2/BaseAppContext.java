package com.sher.android2;

import android.app.Application;

/**
 * 全局应用程序类：用于数据初始化
 */
public abstract class BaseAppContext extends Application {

    protected static BaseAppContext mApp;
    @Override
    public void onCreate() {
        super.onCreate();
        BaseAppConfig.getInstance(this);
        mApp = this;
        com.sher.android2.BaseUncaughtCrashHandler.getInstance().initialize(this);

    }
    public static BaseAppContext getInstance() {
        return mApp;
    }
}
