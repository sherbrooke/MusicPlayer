package com.brooke.sher.loginregistertest.mine;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brooke.sher.loginregistertest.R;
import com.brooke.sher.loginregistertest.login.LoginActivity;

import android2.ui.fragment.BaseFragment;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Sher on 2017/8/20.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {

    private TextView tvLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_mine,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tvLogin = view.findViewById(R.id.login);
        tvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login:
                Intent intent = new Intent(this.getActivity().getApplicationContext(), LoginActivity.class);
//                Intent intent = new Intent(this.getActivity().getApplicationContext(), SubmitAccountInfoActivity.class);
                startActivityForResult(intent,4);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == -100)
            tvLogin.setText("注销");
        }
    }
}
