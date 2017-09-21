package com.wuzhou.wlibrary.page;

import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wuzhou.wlibrary.R;

/**
 * 常规title继承，特殊的继承base自己定义
 */
public abstract class TitleActivity extends BaseActivity {
    protected Toolbar toolbar;
    TextView tv_title;
    protected FrameLayout t_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_title);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        tv_title= (TextView) findViewById(R.id.tv_title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Drawable navigationIcon = toolbar.getNavigationIcon();
        TypedArray ta = getTheme().obtainStyledAttributes(new int[]{R.attr.album_element_color});
        int color = ta.getColor(0, 0);
        ta.recycle();
        navigationIcon.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        t_content= (FrameLayout) findViewById(R.id.t_content);
        init(savedInstanceState);
    }

    protected abstract void init(Bundle savedInstanceState);

    protected void setTitle(String name){
        if(tv_title!=null)tv_title.setText(name);
    }

    protected interface OnTopClickListener{
        void onClick();
    }
    OnTopClickListener onClickListenerTopRight;
    OnTopClickListener onClickListenerTopLeft;
    protected void setTopRightButton(String s,int id,OnTopClickListener onClickListener){
        this.menuStr=s;
        this.menuResId=id;
        this.onClickListenerTopRight=onClickListener;
    }
    protected void setTopRightButton(int id,OnTopClickListener onClickListener){
        this.menuResId=id;
        this.onClickListenerTopRight=onClickListener;
    }
    protected void setTopLeftButton(int id,OnTopClickListener onClickListener){
        if(id==0){
            this.toolbar.setNavigationIcon(null);
        }else{
            this.toolbar.setNavigationIcon(id);
            this.onClickListenerTopLeft=onClickListener;
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        t_content.removeAllViews();
        View.inflate(this,layoutResID,t_content);
        onContentChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(menuResId!=0||TextUtils.isEmpty(menuStr)){
            getMenuInflater().inflate(R.menu.tool_bar,menu);
        }
        return true;
    }

    /**
     * 1.setSupportActionBar(toolbar);
     * toolbar.setNavigationOnClickListener(this);
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if(onClickListenerTopLeft==null){
                onBackPressed();
            }else{
                onClickListenerTopRight.onClick();
            }
        }

        if(item.getItemId()== R.id.menu_right){
            if(onClickListenerTopRight!=null)onClickListenerTopRight.onClick();
        }

        return true;
    }

    int menuResId;
    String menuStr;
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if(menuResId!=0){
            menu.findItem(R.id.menu_right).setIcon(menuResId);
        }

        if(!TextUtils.isEmpty(menuStr)){
            menu.findItem(R.id.menu_right).setTitle(menuResId);
        }

        return super.onPrepareOptionsMenu(menu);
    }
}
