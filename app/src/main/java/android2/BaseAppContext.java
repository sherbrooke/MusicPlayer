package android2;

import android.app.Application;

import com.brooke.sher.loginregistertest.AppConfig;

/**
 * 全局应用程序类：用于数据初始化
 */
public abstract class BaseAppContext extends Application {

    protected static BaseAppContext mApp;
    @Override
    public void onCreate() {
        super.onCreate();
        AppConfig.getInstance(this);
        mApp = this;
        BaseUncaughtCrashHandler.getInstance().initialize(this);

    }
    public static BaseAppContext getInstance() {
        return mApp;
    }
}
