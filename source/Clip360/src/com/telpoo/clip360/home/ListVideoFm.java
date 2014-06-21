package com.telpoo.clip360.home;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.startapp.android.publish.Ad;
import com.startapp.android.publish.AdDisplayListener;
import com.startapp.android.publish.StartAppAd;
import com.telpoo.clip360.R;
import com.telpoo.clip360.adapter.VideosAdapter;
import com.telpoo.clip360.db.Dbsupport;
import com.telpoo.clip360.task.TaskNetwork;
import com.telpoo.clip360.task.TaskType;
import com.telpoo.clip360.utils.EndlessScrollListener;
import com.telpoo.frame.object.BaseObject;

@SuppressLint("ValidFragment")
public class ListVideoFm extends MyBaseFm implements TaskType {
	ListView lv;
	String cID_keysearch;
	VideosAdapter videosAdapter;
	ArrayList<BaseObject> details = null;
	// String page1 = "0";
	View loadingView;
	int currtasktype = TASK_GET_LIST_VIDEO;
	LinearLayout temp;
	boolean isDataHomeFromDb = false;
	TextView tv;
	Button btnReload;
	public ListVideoFm() {
		// TODO Auto-generated constructor stub
	}

	public ListVideoFm(String cID) {
		if ("null".equalsIgnoreCase(cID)) {
			isDataHomeFromDb = true;
			details = Dbsupport.getHome();
		}
		this.cID_keysearch = cID;
		currtasktype = TASK_GET_LIST_VIDEO;
	}

	public ListVideoFm(ArrayList<BaseObject> details, String keysearch) { // for
																			// search
		this.details = details;
		this.cID_keysearch = keysearch;
		currtasktype = TASK_SEARCH;
	}

	public ListVideoFm(ArrayList<BaseObject> details) { // for favorite
		this.details = details;
		currtasktype = TASK_GET_FAVORITE;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = null;
		v = inflater.inflate(R.layout.layout_listmenu, container, false);
		return v;

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		temp = (LinearLayout) view.findViewById(R.id.temp);
		tv = (TextView) view.findViewById(R.id.tv);
		btnReload = (Button) view.findViewById(R.id.btnReload);
		lv = (ListView) view.findViewById(R.id.lv);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initData();

	}

	private void initView() {
		LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		loadingView = inflater.inflate(R.layout.loading, null);
		if(currtasktype!=TASK_GET_FAVORITE)
		lv.addFooterView(loadingView);

	}

	private void initData() {

		videosAdapter = new VideosAdapter(getActivity(), R.layout.item_videos, new ArrayList<BaseObject>());
		lv.setAdapter(videosAdapter);

		if (details != null) {
			videosAdapter.Adds(details);
			videosAdapter.notifyDataSetChanged();
		} else
			details = new ArrayList<BaseObject>();

		if (currtasktype != TASK_GET_FAVORITE && !isDataHomeFromDb)
			lv.setOnScrollListener(new EndlessScrollListener(0, getModel(), currtasktype, cID_keysearch, getActivity(), "0"));

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
				// getParent().pushFragments(TabId.home, new DetailFm(details,
				// arg2, cID, page), true, null);
				
				
				Intent it = new Intent(getActivity(), DetailActivity.class);
				it.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				it.putExtra("position", position);
				it.putExtra("tasktype", currtasktype);
				it.putExtra("cID", cID_keysearch);
				
				it.putParcelableArrayListExtra("data", videosAdapter.getAll());
				//it.putParcelableArrayListExtra("data", details);
				startActivity(it);

			}
		});

		if (isDataHomeFromDb) {
			ArrayList<String> vlSend = new ArrayList<String>();
			vlSend.add("null");
			vlSend.add("1");

			TaskNetwork taskGetVideos = new TaskNetwork(getModel(), TASK_GET_LIST_VIDEO, vlSend, getActivity());
			getModel().exeTask(null, taskGetVideos);
		}

		btnReload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ArrayList<String> vlSend = new ArrayList<String>();
				vlSend.add("null");
				vlSend.add("1");

				TaskNetwork taskGetVideos = new TaskNetwork(getModel(), TASK_GET_LIST_VIDEO, vlSend, getActivity());
				getModel().exeTask(null, taskGetVideos);
				lv.setVisibility(View.VISIBLE);

			}
		});

	}

	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(int taskType, ArrayList<?> list, String msg) {
		super.onSuccess(taskType, list, msg);
		btnReload.setVisibility(View.GONE);
		tv.setVisibility(View.GONE);
		switch (taskType) {

		case TASK_GET_LIST_VIDEO:

			if (isDataHomeFromDb) {
				lv.setOnScrollListener(new EndlessScrollListener(0, getModel(), currtasktype, cID_keysearch, getActivity(), "1"));
				//isDataHomeFromDb = false;
				//details.clear();
				videosAdapter.SetItems(new ArrayList<BaseObject>());
			}
			
			if (list == null || list.size() == 0) {
				lv.removeFooterView(loadingView);
				return;
			}
			ArrayList<BaseObject> ojRes1 = (ArrayList<BaseObject>) list;
			details.addAll(ojRes1);
			videosAdapter.Adds(ojRes1);
			videosAdapter.notifyDataSetChanged();

			if (ojRes1.size() < 24)
				lv.removeFooterView(loadingView);
			
			if(isDataHomeFromDb){
				videosAdapter.SetItems(ojRes1);
				videosAdapter.notifyDataSetChanged();
				details= new ArrayList<BaseObject>();
				details.addAll(ojRes1);
				isDataHomeFromDb = false;
			}
			
			break;
			
			

		case TASK_SEARCH:
			if (list == null || list.size() == 0) {
				lv.removeFooterView(loadingView);
				return;
			}
			ArrayList<BaseObject> ojRes = (ArrayList<BaseObject>) list;
			details.addAll(ojRes);
			videosAdapter.Adds(ojRes);

			videosAdapter.notifyDataSetChanged();

			if (ojRes.size() < 24)
				lv.removeFooterView(loadingView);

			// neu tra ve it du lieu thi xoa loadmore
			break;

		default:
			break;
		}
	}

	@Override
	public void onFail(int taskType, String msg) {
		super.onFail(taskType, msg);

		switch (taskType) {
		case TASK_GET_LIST_VIDEO:
		case TASK_SEARCH:

			// lv.setVisibility(View.GONE);
			tv.setText("" + msg);
			if (getString(R.string.no_network_try_again).equalsIgnoreCase(msg)) {
				btnReload.setVisibility(View.VISIBLE);
			}

			lv.removeFooterView(loadingView);
			break;

		default:
			break;
		}
	}

}
