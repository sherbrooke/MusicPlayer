package com.brooke.sher.app2;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.Context;

import com.brooke.sher.app2.ui.BaseAppActivity;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 */
public class AppManager {

    private static Stack<BaseAppActivity> sActivityStack;
    private static AppManager sInstance;

    private AppManager() {
        sActivityStack = new Stack<BaseAppActivity>();
    }

    /**
     * 单一实例
     */
    public synchronized static AppManager getInstance() {
        if (sInstance == null) {
            sInstance = new AppManager();
        }
        return sInstance;
    }

    /**
     * 添加Activity到堆栈
     */
    public synchronized void addActivity(BaseAppActivity activity) {
        if (sActivityStack == null) {
            sActivityStack = new Stack<BaseAppActivity>();
        }
        sActivityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public BaseAppActivity currentActivity() {
        try {
            return sActivityStack.peek();
        } catch (EmptyStackException e) {
            return null;
        }
    }

    public BaseAppActivity getActivityBackCurrent() {
        try {
            if (sActivityStack.size() > 1) {
                return sActivityStack.get(sActivityStack.size() - 2);
            }
            return currentActivity();
        } catch (EmptyStackException e) {
            return null;
        }
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        try {
            finishActivity(sActivityStack.peek());
        } catch (EmptyStackException e) {
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(BaseAppActivity activity) {
        if (null != activity) {
            sActivityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<? extends BaseAppActivity> cls) {
        int stackSize = sActivityStack.size();
        for (int i = stackSize - 1; i >= 0; i--) {
            BaseAppActivity activity = sActivityStack.get(i);
            if (null == activity) {
                sActivityStack.remove(i);
            } else if (activity.getClass().equals(cls)) {
                sActivityStack.remove(i);
                activity.finish();
                activity = null;
            }
        }
    }

    public void finishActivityExcept(Class<? extends BaseAppActivity> cls) {
        // TODO Auto-generated method stub
        int stackSize = sActivityStack.size();
        for (int i = stackSize - 1; i >= 0; i--) {
            BaseAppActivity activity = sActivityStack.get(i);
            if (null == activity) {
                sActivityStack.remove(i);
            } else if (!activity.getClass().equals(cls)) {
                sActivityStack.remove(i);
                activity.finish();
                activity = null;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        while (true) {
            try {
                BaseAppActivity activity = sActivityStack.pop();
                if (null != activity)
                    activity.finish();
            } catch (EmptyStackException e) {
                break;
            }
        }
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());

            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}