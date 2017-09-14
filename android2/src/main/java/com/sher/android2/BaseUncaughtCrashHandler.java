package com.sher.android2;

import android.content.Context;
import android.content.pm.PackageInfo;

import com.sher.android2.util.LogUtils;
import com.sher.android2.util.SdkUtils;
import com.sher.android2.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;



/** 未捕获crash处理类 */
public class BaseUncaughtCrashHandler implements UncaughtExceptionHandler {
    private static BaseUncaughtCrashHandler instance;
    private Context                         context;
    private UncaughtExceptionHandler        defaultExceptionHandler;
    private String                          TAG = BaseUncaughtCrashHandler.class.getSimpleName();

    private BaseUncaughtCrashHandler() {
    }

    /**
     * 单例方法
     * @return
     */
    public static BaseUncaughtCrashHandler getInstance() {
        if (null == instance)
            instance = new BaseUncaughtCrashHandler();
        return instance;
    }

    /**
     * 初始化
     * @param context
     */
    public void initialize(Context context) {
        this.context = context;
        defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        BaseAppConfig.getInstance(context);
        if (LogUtils.isDebug || (!handleException(ex) && null != defaultExceptionHandler)) {
            defaultExceptionHandler.uncaughtException(thread, ex);
        }
    }

    /** 异常处理 */
    private boolean handleException(Throwable ex) {
        if (null == ex) {
            return false;
        }
        FileOutputStream fos = null;
        try {
            String cachePath = context.getCacheDir().getAbsolutePath();
            LogUtils.v(TAG, "CachePath : " + cachePath);
            fos = new FileOutputStream(new File(BaseAppConfig.PATH_CACHE_DEFAULT + "/crash_"
                    + StringUtils.getDate("yyyyMMdd_HH_mm_ss") + ".log"));

            String errorMsg = getCrashReport(context, ex);
            if (LogUtils.isDebug)
                LogUtils.e(TAG, errorMsg);
            fos.write(errorMsg.getBytes());
            fos.close();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                fos.close();
                fos = null;
            } catch (IOException e) {
            }
        }
        reStarApp();
        return true;
    }

    /**
     * 获取APP崩溃异常报告
     * 
     * @param ex
     * @return
     */
    private String getCrashReport(Context context, Throwable ex) {
        PackageInfo pinfo = SdkUtils.getPackageInfo(context);
        StringBuffer exceptionStr = new StringBuffer();
        exceptionStr.append("Version: " + pinfo.versionName + "(" + pinfo.versionCode + ")\n");
        exceptionStr.append("Android: " + android.os.Build.VERSION.RELEASE + "(" + android.os.Build.MODEL + ")\n");
        exceptionStr.append("Exception: " + ex.getMessage() + "\n");
        StackTraceElement[] elements = ex.getStackTrace();
        for (int i = 0; i < elements.length; i++) {
            exceptionStr.append(elements[i].toString() + "\n");
        }
        return exceptionStr.toString();
    }

    /** 重启应用 */
    private void reStarApp() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

}
