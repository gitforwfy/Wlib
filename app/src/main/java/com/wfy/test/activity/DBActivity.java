package com.wfy.test.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        page_index=1;
        String query=etv_query.getText().toString().trim();
        List<Worker> temp;
        if(TextUtils.equals(query,"")){
            temp= dao.queryAll(page_index,8);
        }else{
            temp= dao.queryAll(page_index,8,"jobNum",Long.parseLong(query));
        }
        list.addAll(temp);
        adapter.notifyDataSetChanged();
    }

    private void get(int page,String query){

    }

    List<Worker> list=new ArrayList<>();

    @Override
    public void onRefresh() {
        String query=etv_query.getText().toString().trim();
        List<Worker> temp;
        if(TextUtils.equals(query,"")){
            temp= dao.queryAll(1,8);
        }else{
            temp= dao.queryAll(1,8,"jobNum",Long.parseLong(query));
        }
        list.addAll(temp);
        adapter.notifyDataSetChanged();
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
