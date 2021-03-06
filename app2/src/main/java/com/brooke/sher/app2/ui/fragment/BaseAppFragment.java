package com.brooke.sher.app2.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.brooke.sher.app2.ToastHolder;


/**
 * Created by wangyang on 2017/9/14.
 */

public class BaseAppFragment extends Fragment {

    protected String TAG;
    protected Context mContext;
    protected String mPageName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        mContext =  getActivity().getApplicationContext();

        Bundle pars = super.getArguments();
        try {
            mPageName = pars.getString("mPageName");
        } catch (Exception e) {
        }
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
