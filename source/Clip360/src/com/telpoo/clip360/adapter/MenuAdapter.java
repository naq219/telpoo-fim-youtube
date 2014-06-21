package com.telpoo.clip360.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.telpoo.clip360.R;
import com.telpoo.clip360.object.CatOj;
import com.telpoo.frame.object.BaseObject;

public class MenuAdapter extends ArrayAdapter<BaseObject> {

	private Context context;
	private int mLayoutRes;
	private LayoutInflater mInflater;
	ArrayList<BaseObject> listIn;

	public MenuAdapter(Context context, int textViewResourceId, ArrayList<BaseObject> listIn) {
		super(context, textViewResourceId, listIn);

		this.context = context;
		this.mLayoutRes = textViewResourceId;
		this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.listIn = listIn;

	}

	static class ViewHolder {
		TextView tv_title;
		int position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(mLayoutRes, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.tv_title = (TextView) convertView.findViewById(R.id.name);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final BaseObject item = getItem(position);

		updateListView(viewHolder, item, position);

		return convertView;
	}

	public void SetItems(ArrayList<BaseObject> items) {
		clear();
		Adds(items);
	}

	private void Adds(ArrayList<BaseObject> items) {
		if (items != null) {
			for (BaseObject item : items) {
				add(item);
			}
		}
	}

	private void updateListView(ViewHolder view, BaseObject item, int position) {

		view.tv_title.setText(item.get(CatOj.CNAME));

	}

}
