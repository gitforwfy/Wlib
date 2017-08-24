package com.wfy.test.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wfy.test.R;

public class ZipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zip_activity);
        setTitle("下载解压示例");
    }
}
