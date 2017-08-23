package com.wfy.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wfy.test.activity.DBActivity;
import com.wfy.test.activity.EventActivity;
import com.wfy.test.activity.HttpActivity;
import com.wfy.test.activity.ImageLoaderActivity;
import com.wuzhou.wlibrary.page.BaseActivity;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lv= (ListView) findViewById(R.id.lv);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent();
        switch (i){
            case 0:
                intent.setClass(this, ImageLoaderActivity.class);
                startActivity(intent);
                break;
            case 1:
                intent.setClass(this, EventActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent.setClass(this, DBActivity.class);
                startActivity(intent);
                break;
            case 3:
                intent.setClass(this, HttpActivity.class);
                startActivity(intent);
                break;
        }
    }
}
