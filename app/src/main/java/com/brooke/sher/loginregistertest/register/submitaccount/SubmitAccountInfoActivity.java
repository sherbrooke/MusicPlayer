package com.brooke.sher.loginregistertest.register.submitaccount;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.brooke.sher.loginregistertest.R;
import com.brooke.sher.loginregistertest.login.LoginActivity;

import android2.ui.BaseActivity;

public class SubmitAccountInfoActivity extends BaseActivity implements View.OnClickListener,SubmitAccountInfoContact.View {

    private AutoCompleteTextView actPasswd;
    private AutoCompleteTextView actPasswdAgain;
    private AutoCompleteTextView actUserName;
    private Button btnRegister;
    private TextView TvGoLogin;

    private String mPhone = "15737961902";
    private String mPasswd;
    private String mPasswdAgain;
    private String mUserName;

    private SubmitAccountInfoPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_account_info);
        initView();
        presenter = new SubmitAccountInfoPresenter(this);
    }

    private void initView() {
        actPasswd = (AutoCompleteTextView) findViewById(R.id.password);
        actPasswd.setText("123456");
        actPasswdAgain = (AutoCompleteTextView) findViewById(R.id.password_again);
        actPasswdAgain.setText("123456");
        actUserName = (AutoCompleteTextView) findViewById(R.id.username);
        actUserName.setText("123456");
        btnRegister = (Button) findViewById(R.id.register);
        TvGoLogin = (TextView) findViewById(R.id.tv_go_login);
        btnRegister.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register:
                mPasswd = actPasswd.getText().toString();
                mPasswdAgain = actPasswdAgain.getText().toString();
                mUserName = actUserName.getText().toString();
                presenter.register(mUserName,mPasswd,mPasswdAgain,mPhone);
                break;
            case R.id.tv_go_login:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }


    @Override
    public void showToast(String info) {
        androidToast(info);
    }
}
