package com.wfy.test.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wfy.test.R;
import com.wfy.test.bean.Worker;
import com.wfy.test.db.WorkDao;
import com.wuzhou.wlibrary.page.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class DBActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    WorkDao dao;
    EditText etv_query;
    EditText etv_jobNum;
    EditText etv_name;
    SwipeRefreshLayout swp_worker;
    ListView lv_worker;
    MyAdapter adapter;
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
        adapter=new MyAdapter(this);
        lv_worker.setAdapter(adapter);

        dao=new WorkDao(this);
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

    int page_index=1;
    public void query(View view) {
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
                temp= dao.queryAll(page_index,page_size);
            }else{
                temp= dao.queryAll(page_index,page_size,"jobNum",Long.parseLong(q_case));
            }

            for(Worker worker:temp){
                if(list.contains(temp)){
                    list.remove(temp);
                }
                list.addAll(temp);
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


    class MyAdapter extends BaseAdapter{
        Context context;

        public MyAdapter(Context context) {
            this.context=context;
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
            ViewHolder holder=null;
            if(view==null){
                view= LayoutInflater.from(context).inflate(R.layout.item_worker,null);
                holder=new ViewHolder();
                holder.tv_jnum= (TextView) view.findViewById(R.id.tv_jnum);
                holder.tv_name= (TextView) view.findViewById(R.id.tv_name);
                holder.tv_profession= (TextView) view.findViewById(R.id.tv_profession);
                holder.btn_op= (Button) view.findViewById(R.id.btn_op);
                view.setTag(holder);
            }else{
                holder= (ViewHolder) view.getTag();
            }

            Worker worker=list.get(i);
            holder.tv_jnum.setText(worker.jobNum+"");
            holder.tv_name.setText(worker.name);
//            holder.tv_profession.setText(worker.profession.name);
            return view;
        }
    }

    class ViewHolder{
        TextView tv_jnum;
        TextView tv_name;
        TextView tv_profession;
        Button btn_op;
    }
}
