package com.wuzhou.wlibrary.page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 *
 * @param <T>
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

	private Context context;
	protected List<T> datas;
	private LayoutInflater mInflater;
	private int layoutId;


	public CommonAdapter(Context context,List<T> datas,int layoutId){
		this.context = context;
		this.datas = datas;
		this.layoutId = layoutId;
		this.mInflater = (LayoutInflater) LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public T getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = ViewHolder.get(context, convertView, parent, layoutId, position);
		convert(viewHolder,getItem(position),position);
		return viewHolder.getConvertView();
	}

	public abstract void convert(ViewHolder viewHolder,T t,int position);
}
