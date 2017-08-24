package com.wuzhou.wlibrary.page;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuzhou.wlibrary.R;

/**
 * 常规title继承，特殊的继承base自己定义
 */
public class TitleActivity extends BaseActivity {
    protected Toolbar toolbar;
    protected FrameLayout t_content;
    protected ImageView imv_back;
    protected TextView tv_title;
    protected ImageView imv_right;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_title);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        t_content= (FrameLayout) findViewById(R.id.t_content);
        imv_back= (ImageView) findViewById(R.id.imv_back);
        tv_title= (TextView) findViewById(R.id.tv_title);
        imv_right= (ImageView) findViewById(R.id.imv_right);
        showBack(false);
        showTitleRight(false);
        imv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    protected void showBack(boolean show){
        if(imv_back!=null){
            if(show){
                imv_back.setVisibility(View.VISIBLE);
            }else{
                imv_back.setVisibility(View.GONE);
            }
        }
    }

    protected void showTitleRight(boolean show){
        if(imv_right!=null){
            if(show){
                imv_right.setVisibility(View.VISIBLE);
                imv_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onTitleRight();
                    }
                });
            }else{
                imv_right.setVisibility(View.GONE);
            }
        }
    }

    protected void onTitleRight(){

    }

    protected void setTitle(String name){
        if(tv_title!=null)tv_title.setText(name);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        t_content.removeAllViews();
        View.inflate(this,layoutResID,t_content);
        onContentChanged();
    }

}
