package com.photo.album.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.photo.album.R;
import com.photo.album.bean.HandlerModel;
import com.photo.album.bean.Worker;
import com.photo.album.db.HandlerModelDAO;
import com.photo.album.db.WorkDao;
import com.wuzhou.wlibrary.page.CommonAdapter;
import com.wuzhou.wlibrary.page.TitleActivity;
import com.wuzhou.wlibrary.utils.WLog;

import java.util.ArrayList;
import java.util.List;

public class DBActivity extends TitleActivity implements SwipeRefreshLayout.OnRefreshListener {
    WorkDao dao;
    HandlerModelDAO handlerModelDAO;
    EditText etv_query;
    EditText etv_jobNum;
    EditText etv_name;
    SwipeRefreshLayout swp_worker;
    ListView lv_worker;
//    MyAdapter adapter;
    WorkAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
        etv_query = (EditText) findViewById(R.id.etv_query);
        etv_jobNum = (EditText) findViewById(R.id.etv_jobNum);
        etv_name= (EditText) findViewById(R.id.etv_name);
        swp_worker= (SwipeRefreshLayout) findViewById(R.id.swp_worker);
        swp_worker.setOnRefreshListener(this);
        lv_worker= (ListView) findViewById(R.id.lv_worker);
        lv_worker.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    // 判断是否滚动到底部
                    if (absListView.getLastVisiblePosition() == absListView.getCount() - 1) {
                        //加载更多功能的代码
                        WLog.print("加载更多功能的代码");
                        page_index++;
                        String q_case=etv_query.getText().toString().trim();
                        LoadTask loadTask=new LoadTask();
                        loadTask.execute(new String[]{q_case,page_index+""});
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
        adapter=new WorkAdapter(this,list,R.layout.item_worker);
        lv_worker.setAdapter(adapter);
        dao=new WorkDao(this);
        handlerModelDAO=new HandlerModelDAO(this, HandlerModel.class);

        List<HandlerModel> list=handlerModelDAO.queryAll();
        Log.e(TAG,"size = "+list.size());
        for(HandlerModel handlerModel:list){
            Log.e(TAG,handlerModel.toString());
        }
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("数据库示例");
        setTopRightButton("", R.mipmap.ic_launcher, new OnTopClickListener() {
            @Override
            public void onClick() {
                Toast.makeText(mActivity,"我的",Toast.LENGTH_SHORT).show();
            }
        });
        setTopLeftButton(R.mipmap.ic_launcher, new OnTopClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }

    public void add(View view) {
        String jnum= etv_jobNum.getText().toString().trim();
        String name=etv_name.getText().toString().trim();
        if(TextUtils.equals(name,"")||TextUtils.equals(jnum,"")){
            Toast.makeText(this,"请完善员工信息",Toast.LENGTH_SHORT).show();
        }else{
            Worker worker=new Worker(name,Long.parseLong(jnum));
            if(dao.query("jobNum",worker.jobNum)==null){
                dao.insert(worker);
                list.add(worker);
                adapter.notifyDataSetChanged();
            }else{
                Toast.makeText(this,"该编号已存在",Toast.LENGTH_SHORT).show();
            }
        }
    }

    int page_index=0;
    public void query(View view) {
        page_index=0;
        list.clear();
        String q_case=etv_query.getText().toString().trim();
        LoadTask loadTask=new LoadTask();
        loadTask.execute(new String[]{q_case,1+""});
    }

    List<Worker> list=new ArrayList<>();

    @Override
    public void onRefresh() {
        String q_case=etv_query.getText().toString().trim();
        LoadTask loadTask=new LoadTask();
        loadTask.execute(new String[]{q_case,1+""});
    }


    class LoadTask extends AsyncTask<String,Integer,String>{
        long page_size=8;
        @Override
        protected String doInBackground(String... integers) {
            String q_case=integers[0];
            int page_index=Integer.parseInt(integers[1]);
            List<Worker> temp;
            if(TextUtils.equals(q_case,"")){
                temp= dao.queryAll((page_index-1)*page_size,page_size);
            }else{
                temp= dao.queryAll((page_index-1)*page_size,page_size,"jobNum",Long.parseLong(q_case));
            }

            for(Worker worker:temp){
                if(!list.contains(worker)){
                    list.add(worker);
                }
            }
            return "ok";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            adapter.notifyDataSetChanged();
            swp_worker.setRefreshing(false);
        }
    }

    class WorkAdapter extends CommonAdapter<Worker>{
        public WorkAdapter(Context context, List<Worker> datas, int layoutId) {
            super(context, datas, layoutId);
        }
        @Override
        public void convert(com.wuzhou.wlibrary.page.ViewHolder viewHolder, Worker worker, int position) {
            TextView tv_jnum = viewHolder.getView(R.id.tv_jnum);
            TextView tv_name = viewHolder.getView(R.id.tv_name);
            tv_jnum.setText(worker.jobNum+"");
            tv_name.setText(worker.name);
        }
    }
}
