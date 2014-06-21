package com.telpoo.frame.ui;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;
import com.telpoo.frame.R;
import com.telpoo.frame.model.BaseModel;
import com.telpoo.frame.model.ModelListener;
import com.telpoo.frame.utils.Utils;
import com.telpoo.frame.utils.ViewUtils;
//import com.google.analytics.tracking.android.Fields;
//import com.google.analytics.tracking.android.GoogleAnalytics;
//import com.google.analytics.tracking.android.MapBuilder;
//import com.google.analytics.tracking.android.Tracker;

public class BaseFragment extends Fragment implements ModelListener {
	protected String TAG = getClass().getSimpleName();
	public ProgressDialog loadingProgress;
	public BaseFragmentActivity baseActivity;
	private Tracker mTracker;
	private String childName = TAG;

	public static final BaseFragment newInstance(Bundle bundle, BaseFragment fm) {

		fm.setArguments(bundle);
		return fm;
	}

	@Override
	public void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		baseActivity = (BaseFragmentActivity) getActivity();

	}

	public void setParent(BaseFragmentActivity baseActivity) {
		this.baseActivity = baseActivity;
	}

	public BaseFragmentActivity getParent() {
		return baseActivity;
	}

	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance(getActivity()).activityStart(getActivity());

	}

	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(getActivity()).activityStop(getActivity());
	}

	@Override
	public Context getContext() {
		return this.getActivity().getApplicationContext();

	}

	protected BaseModel getModel() {
		return baseActivity.model;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}

	public boolean onBackPressed() {
		return false;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	}

	public void showToast(String message) {
		if (message != null)
			Toast.makeText(getContext(), "" + message, Toast.LENGTH_SHORT).show();
	}

	public void showToastMessage(int resId) {
		Toast.makeText(getContext(), getString(resId), Toast.LENGTH_LONG).show();
	}

	public void showProgressDialog(Context context, String message) {
		closeProgressDialog();

		loadingProgress = new ProgressDialog(context);
		loadingProgress.setMessage(message);
		loadingProgress.setCanceledOnTouchOutside(false);
		loadingProgress.show();
	}

	public void showProgressDialog(Context context) {
		closeProgressDialog();

		loadingProgress = new ProgressDialog(context);
		loadingProgress.setMessage(getString(R.string.loading));
		loadingProgress.setCanceledOnTouchOutside(false);
		loadingProgress.show();
	}

	public void closeProgressDialog() {
		if (loadingProgress != null) {
			if (loadingProgress.isShowing())
				loadingProgress.dismiss();
			loadingProgress = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.telpoo.frame.model.ModelListener#onSuccess(int,
	 * java.util.ArrayList, java.lang.String)
	 */
	@Override
	public void onSuccess(int taskType, ArrayList<?> list, String msg) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.telpoo.frame.model.ModelListener#onFail(int, java.lang.String)
	 */
	@Override
	public void onFail(int taskType, String msg) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.telpoo.frame.model.ModelListener#onProgress(int, int)
	 */
	@Override
	public void onProgress(int taskType, int progress) {
		// TODO Auto-generated method stub

	}

	protected void pushFragment(int resource, Fragment fm) {

		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(resource, fm, "");
		ft.commit();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		enableHideKbOutSide();

		mTracker = GoogleAnalytics.getInstance(getParent()).getTracker("" + Utils.getStringSPR("trackingId", getParent()));
		trackFm();
		// mTracker=EasyTracker.getInstance(getActivity());

	}

	private void trackFm() {
		if (childName != null) {

			mTracker.set(Fields.SCREEN_NAME, getParent().getClass().getSimpleName() + "-" + childName);
			mTracker.send(MapBuilder.createAppView().build());
		}
	}

	protected void trackEvent(String ui_action,String button_press,String play_button) {
		mTracker.send(MapBuilder.createEvent(ui_action, button_press, play_button, null).build());
	}

	protected void trackScreen(String name) {
		childName = name;
	}

	protected void enableHideKbOutSide() {
		//View rootView = getView().findViewById(R.id.root_layout);
		// if(rootView!=null)
		// BHAds.countclick_hidekeyboard1(rootView, getActivity());
		// ViewUtils.HideKeyboarOutside(rootView, getActivity());
	}

}
