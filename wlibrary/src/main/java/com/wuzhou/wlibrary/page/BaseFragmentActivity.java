package com.wuzhou.wlibrary.page;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.wuzhou.wlibrary.R;

public class BaseFragmentActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_fragment);
    }
}
