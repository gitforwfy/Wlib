package com.wfy.test.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.wfy.test.R;
import com.wuzhou.wlibrary.page.TitleActivity;
import com.wuzhou.wlibrary.widget.WToast;

public class ZipActivity extends TitleActivity {
    FloatingActionButton fab_add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zip_activity);
        setTitle("下载解压示例");
        fab_add= (FloatingActionButton) findViewById(R.id.fab_add);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WToast.show(mActivity,"onclick");
            }
        });
    }
}
