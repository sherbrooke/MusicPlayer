package com.brooke.sher.app2;

import android.app.Application;

import io.realm.Realm;

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
        BaseUncaughtCrashHandler.getInstance().initialize(this);
        Realm.init(this);
    }
    public static BaseAppContext getInstance() {
        return mApp;
    }
}
