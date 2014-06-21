package com.telpoo.clip360.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.telpoo.clip360.R;
import com.telpoo.clip360.db.Dbsupport;
import com.telpoo.clip360.object.VideoOj;
import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.model.BaseModel;
import com.telpoo.frame.model.BaseTask;
import com.telpoo.frame.model.ModelListener;
import com.telpoo.frame.object.BaseObject;

public class VideosAdapter extends ArrayAdapter<BaseObject> {

	private Context context;
	private int mLayoutRes;
	private LayoutInflater mInflater;
	ArrayList<BaseObject> listIn;
	private BaseModel model;
	private BaseTask task;

	public VideosAdapter(Context context, int textViewResourceId, ArrayList<BaseObject> listIn) {
		super(context, textViewResourceId, listIn);

		this.context = context;
		this.mLayoutRes = textViewResourceId;
		this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.listIn = listIn;

		

	}

	static class ViewHolder {
		int position;
		TextView vname;
		TextView vview;
		TextView vtime;
		ImageView vytid;
		boolean isFabvorite;
		ImageView favorite;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(mLayoutRes, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.vname = (TextView) convertView.findViewById(R.id.vname);
			viewHolder.vytid = (ImageView) convertView.findViewById(R.id.vytid);
			viewHolder.vview = (TextView) convertView.findViewById(R.id.vview);
			viewHolder.vtime = (TextView) convertView.findViewById(R.id.vtime);
			viewHolder.favorite = (ImageView) convertView.findViewById(R.id.fabvorite);
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
	
	public ArrayList<BaseObject> getAll(){
		return listIn;
	}

	public void Adds(ArrayList<BaseObject> items) {
		ArrayList<BaseObject> ojDb= Dbsupport.getFavorite();
		
		if(ojDb!=null&&items!=null)
		for (int i = 0; i < items.size(); i++) {
			BaseObject item= items.get(i);
			
			for (int j = 0; j < ojDb.size(); j++) {
				BaseObject oj= ojDb.get(j);
				if(item.get(VideoOj.VID).equalsIgnoreCase(oj.get(VideoOj.VID))){
					items.get(i).set(VideoOj.FAVORITE, true);
					break;
				}
			}
			
			
		}
		
		
			for (BaseObject item : items) {
				add(item);
			}
		
	}

	private void updateListView(final ViewHolder view, final BaseObject item, int position) {

		view.vname.setText(item.get(VideoOj.VNAME));
		view.vview.setText(item.get(VideoOj.VVIEW) + context.getString(R.string.luot_xem));
		String url = "http://i1.ytimg.com/vi/" + item.get(VideoOj.VYTID) + "/mqdefault.jpg";
		view.vtime.setText(item.get(VideoOj.VTIME));
		ImageLoader.getInstance().displayImage(url, view.vytid);

		if (item.getBool(VideoOj.FAVORITE))
			view.favorite.setImageResource(R.drawable.star_2);
		else
			view.favorite.setImageResource(R.drawable.star_1);
		 
		view.favorite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			
				
				if (Dbsupport.addFv(item)){
					Toast.makeText(context, "Đã thêm vào yêu thích!", 0).show();
					view.favorite.setImageResource(R.drawable.star_2);
				}
				else{
					Toast.makeText(context, "Đã bỏ yêu thích!", 0).show();
					view.favorite.setImageResource(R.drawable.star_1);
				}
				
				
				
			}
		});
		
	}

	public void setIdelegate(BaseModel model,BaseTask task){
		this.model=model;
		this.task=task;
		
	}

}
