package com.photo.album.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.photo.album.R;
import com.photo.album.activity.model.LoginPresenter;
import com.photo.album.activity.view.ILoginView;
import com.wuzhou.wlibrary.page.TitleActivity;

public class LoginActivity extends TitleActivity implements ILoginView{
    LoginPresenter loginPresenter;
    EditText etv_user;
    EditText etv_pwd;
    Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        etv_user= (EditText) findViewById(R.id.etv_user);
        etv_pwd= (EditText) findViewById(R.id.etv_pwd);
        btn_login= (Button) findViewById(R.id.btn_login);
        loginPresenter = new LoginPresenter(this);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=etv_user.getText().toString();
                String password=etv_pwd.getText().toString();
                loginPresenter.login(username,password);
            }
        });
    }


    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hiddenLoading() {

    }

    @Override
    public void jumpActivity() {

    }

    @Override
    public boolean back() {
        return false;
    }
}
