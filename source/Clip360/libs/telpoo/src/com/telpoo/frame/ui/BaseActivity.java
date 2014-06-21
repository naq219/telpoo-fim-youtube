package com.telpoo.frame.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.telpoo.frame.R;
import com.telpoo.frame.model.BaseModel;
import com.telpoo.frame.model.ModelListener;

/**
 * @author NAQ219
 * 
 */
public class BaseActivity extends Activity implements ModelListener {

	public BaseModel model = null;
	protected ProgressDialog loadingProgress;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		// Display display = getWindowManager().getDefaultDisplay();
		// Point size = new Point();
		// display.getSize(size);
		// screenWidth = size.x;
		// screenHeight = size.y;
		if (model == null) {
			model = new BaseModel();
			model.setModelListener1(this);
		}

	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		model.setModelListener1(this);

	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	public Context getContext() {
		return this;
	}

	@Override
	public void onSuccess(int taskType, ArrayList<?> list, String msg) {

	}

	@Override
	public void onFail(int taskType, String msg) {

	}

	@Override
	public void onProgress(int taskType, int progress) {

	}

	protected void showToast(int msg) {
		Toast.makeText(getBaseContext(), getString(msg), Toast.LENGTH_LONG).show();
	}

	protected void showToast(String msg) {
		Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
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

}
