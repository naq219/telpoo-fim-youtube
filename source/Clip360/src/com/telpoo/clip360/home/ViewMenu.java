package com.telpoo.clip360.home;
/**
 * @author naq
 * day la class cua phan menu
 */
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.telpoo.clip360.R;
import com.telpoo.clip360.adapter.MenuAdapter;
import com.telpoo.clip360.utils.WhereCallback;
import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.object.BaseObject;

public class ViewMenu {
	Context context;
	ListView lv;
	MenuAdapter adapter;
	ArrayList<BaseObject> ojs;
	View mn_favorite;
	View v;
	Idelegate idelegate;
	private TextView tv_favorite;
	Button reload;

	public ViewMenu(Context context1, ArrayList<BaseObject> ojs1, Idelegate idelegate1) {
		this.context = context1;
		this.ojs = ojs1;
		this.idelegate = idelegate1;

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = inflater.inflate(R.layout.layout_menu, null);
		
		reload=(Button) v.findViewById(R.id.reload);
		
		lv = (ListView) v.findViewById(R.id.lvMenu);
		tv_favorite= (TextView) v.findViewById(R.id.favorite);
		mn_favorite = v.findViewById(R.id.mn_favorite);
		
		mn_favorite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				idelegate.callBack(0, WhereCallback.MENU_FAVORITE);
				//Toast.makeText(context, "fv", 1).show();

			}
		});

		adapter = new MenuAdapter(context, R.layout.item_menu, new ArrayList<BaseObject>());
		lv.setAdapter(adapter);
		adapter.SetItems(ojs);
		adapter.notifyDataSetChanged();
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				
				
				idelegate.callBack(ojs.get(position), WhereCallback.MENU_ITEM);
				
			}
		});
		
		reload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				v.findViewById(R.id.progress_menu).setVisibility(View.VISIBLE);
				reload.setVisibility(View.GONE);
				idelegate.callBack(0, WhereCallback.MENU_RELOAD);
				
			}
		});
		

	}

	public View getView() {
	
		return v;
	}

	public void setMenu(ArrayList<BaseObject> ojs) {
		v.findViewById(R.id.progress_menu).setVisibility(View.GONE);
		v.findViewById(R.id.reload).setVisibility(View.GONE);
		this.ojs.clear();
		this.ojs.addAll(ojs);
		adapter.SetItems(ojs);
		adapter.notifyDataSetChanged();
	}

	public ListView getMenu() {
		return lv;
	}
	
	public void setSizeFavorite(int size){
		tv_favorite.setText(context.getString(R.string.video_yeu_thich)+" ("+size+")");
	}
	
	public void setWhenLoadFail(){
		
		v.findViewById(R.id.progress_menu).setVisibility(View.GONE);
		reload.setVisibility(View.VISIBLE);
		
	}

}
