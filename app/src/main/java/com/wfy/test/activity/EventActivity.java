package com.wfy.test.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wfy.test.R;
import com.wfy.test.event.Cat;
import com.wuzhou.wlibrary.event.EventManager;
import com.wuzhou.wlibrary.event.MessageEvent;
import com.wuzhou.wlibrary.page.TitleActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventActivity extends TitleActivity {
    private TextView tv_num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        setTitle("event示例");
        tv_num= (TextView) findViewById(R.id.tv_num);
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventManager.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventManager.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 2)
    public void onMessageEventPost(MessageEvent event) {
        tv_num.setText(event.message);
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 2)
    public void onMessageEventPost(Cat event) {
        tv_num.setText(event.name);
    }

    public void send_default(View view) {
        EventManager.postSticky(new MessageEvent("发布MessageEvent消息了"));
    }

    public void send_cat(View view) {
        EventManager.postSticky(new Cat("hello kitty"));
    }
}
