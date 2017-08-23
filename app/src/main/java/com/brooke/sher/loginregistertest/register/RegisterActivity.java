package com.brooke.sher.loginregistertest.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.brooke.sher.loginregistertest.R;
import com.brooke.sher.loginregistertest.register.submitcode.SubmitCodeActivity;

import android2.ui.BaseActivity;

public class RegisterActivity extends BaseActivity implements View.OnClickListener,RegisterContact.View {

    private AutoCompleteTextView actPhone;
    private TextView tvGetCode;
    private RegisterPresenter registerPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        actPhone = (AutoCompleteTextView) findViewById(R.id.phone);
        tvGetCode = (TextView) findViewById(R.id.bt_get_code);
        tvGetCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_get_code:
                registerPresenter.getCode();
                break;
        }
    }

    @Override
    public void setPresenter(RegisterContact.Presenter persenter) {
    }

    @Override
    public void goNextPage() {
        Intent intent = new Intent(this,SubmitCodeActivity.class);
        startActivity(intent);
    }
}
