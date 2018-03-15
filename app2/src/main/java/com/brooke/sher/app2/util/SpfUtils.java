package com.brooke.sher.app2.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.brooke.sher.app2.BaseAppContext;


public final class SpfUtils {

    private static String sSpfKey;

    private static synchronized void loadSpfKey() {
        if (sSpfKey == null) {
            try {
                ApplicationInfo appInfo = BaseAppContext.getInstance().getPackageManager()
                        .getApplicationInfo(BaseAppContext.getInstance().getPackageName(),
                                PackageManager.GET_META_DATA);
                sSpfKey = appInfo.metaData.getString("data_Name");
            } catch (PackageManager.NameNotFoundException e) {
            }
        }
    }

    public static void keepPreference(Context context, String key, String value) {
        // Context.MODE_PRIVATE代表在data/data下创建的首选项文件是私有的
        getSharedPreferences(context).edit().putString(key, value).commit();// 记得获取edit对象后一定要commit提交
    }

    /**
     * 当返回空字符串则不存在
     *
     * @param key
     * @return
     */
    public static String getPreference(Context context, String key) {
        // Context.MODE_PRIVATE代表在data/data下创建的首选项文件是私有的
        return getSharedPreferences(context).getString(key, "");
    }

    /**
     * @param key
     * @return
     */
    public static String getString(Context context, String key) {
        return getSharedPreferences(context).getString(key, "");
    }

    /**
     * @param key
     * @return
     */
    public static boolean saveString(Context context, String key, String value) {
        return getSharedPreferences(context).edit().putString(key, value).commit();
    }

    /**
     * @param key
     * @return
     */
    public static int getInt(Context context, String key, int defaultValue) {
        return getSharedPreferences(context).getInt(key, defaultValue);
    }

    public static int getInt(Context context, String key) {
        return getInt(context, key, -1);
    }

    /**
     * @param key
     * @return
     */
    public static boolean saveInt(Context context, String key, int value) {
        return getSharedPreferences(context).edit().putInt(key, value).commit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        loadSpfKey();
        return context.getSharedPreferences(sSpfKey, Context.MODE_PRIVATE);
    }
}
