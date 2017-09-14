package com.sher.android2.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import com.sher.android2.AppManager;
import com.sher.android2.ToastHolder;


/**
 * activity的基类，应用内所有页面都应继承自该类
 */
public abstract class BaseActivity extends AppCompatActivity {
    // 日志打印tag
    protected String TAG;
    protected Context mContext;
    public static int width = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getApplicationContext();

        TAG = this.getClass().getSimpleName();
        // 添加Activity到堆栈
        AppManager.getInstance().addActivity(this);

        WindowManager wm = getWindowManager();
        Display d = wm.getDefaultDisplay();
        width = d.getWidth()/6;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 结束Activity&从堆栈中移除
        AppManager.getInstance().finishActivity(this);
    }

    /**
     * @see BaseActivity#startActivity(Class, Bundle)
     */
    public void startActivity(Class<? extends BaseActivity> cls) {
        startActivity(cls, null);
    }

    /**
     * 页面跳转
     *
     * @param cls  目标页面
     * @param data 传递的数据
     */
    public void startActivity(Class<? extends BaseActivity> cls, Bundle data) {
        Intent intent = new Intent(this, cls);
        if (null != data)
            intent.putExtras(data);
        startActivity(intent);
    }

    protected void androidToast(String toastMsg) {
        androidToast(toastMsg, Toast.LENGTH_SHORT);
    }

    protected void androidToast(int res) {
        androidToast(res, Toast.LENGTH_SHORT);
    }

    protected void androidToast(int res, int duration) {
        ToastHolder.showToast(mContext, res, duration);
    }

    protected void androidToast(String toastMsg, int duration) {
        ToastHolder.showToast(mContext, toastMsg, duration);
    }

}
