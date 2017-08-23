package com.wuzhou.wlibrary.page;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.wuzhou.xlibrary.R;

public class BaseActivity extends AppCompatActivity {
    protected String TAG=this.getClass().getSimpleName();
    protected Activity mActivity;
    protected String user_id;

    public FrameLayout f_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        mActivity=this;
        f_content= (FrameLayout) findViewById(R.id.f_content);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        f_content.removeAllViews();
        View.inflate(this,layoutResID,f_content);
        onContentChanged();
    }
}
