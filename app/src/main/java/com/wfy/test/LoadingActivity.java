package com.wfy.test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.wuzhou.wlibrary.page.BaseActivity;

public class LoadingActivity extends BaseActivity {

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    TextView tv_version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        tv_version= (TextView) findViewById(R.id.tv_version);
        tv_version.setText(Version());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(mActivity,MainActivity.class));
                finish();
            }
        },1500);

    }
}
