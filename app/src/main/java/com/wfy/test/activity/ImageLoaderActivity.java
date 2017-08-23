package com.wfy.test.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.wfy.test.R;
import com.wuzhou.wlibrary.imageloader.Image;
import com.wuzhou.wlibrary.page.TitleActivity;

import java.util.LinkedList;

public class ImageLoaderActivity extends TitleActivity implements SwipeRefreshLayout.OnRefreshListener {
    private int page_size=8;
    private SwipeRefreshLayout swp;
    private ListView lv;
    private LinkedList<String> list=new LinkedList<>();
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader);
        setTitle("图片缓存示例");
        lv= (ListView) findViewById(R.id.lv);
        swp= (SwipeRefreshLayout) findViewById(R.id.swp);
        swp.setOnRefreshListener(this);
        adapter=new MyAdapter(this);
        lv.setAdapter(adapter);
        handler.post(new Runnable() {
            @Override
            public void run() {
                swp.setRefreshing(true);
            }
        });
        onRefresh();
    }

    private int index=0;
    @Override
    public void onRefresh() {
        for(int i=0;i<page_size;i++){
            index++;
            String url="https://unsplash.it/400/400/?random="+index;
            list.addFirst(url);
        }
        adapter.notifyDataSetChanged();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swp.setRefreshing(false);
            }
        },2000);
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    class MyAdapter extends BaseAdapter{
        private Context context;

        public MyAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if(view==null){
                view= LayoutInflater.from(context).inflate(R.layout.item_imv,null);
                holder=new ViewHolder();
                holder.imv=(ImageView) view.findViewById(R.id.imv);
                view.setTag(holder);
            }else{
                holder= (ViewHolder) view.getTag();
            }
            Image.displayImage(context,holder.imv,list.get(i));
            return view;
        }

        class ViewHolder{
            ImageView imv;
        }
    }
}
