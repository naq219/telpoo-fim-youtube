package com.telpoo.frame.model;

import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

/**
 * 
 * @author NAQ219
 * 
 */
public class BaseTask extends AsyncTask<TaskParams, Void, Boolean> {

	protected static final Boolean TASK_FAILED = false;
	protected static final Boolean TASK_DONE = true;
	protected TaskListener taskListener;
	protected int taskType;
	protected int code = -1;
	protected String msg = null;
	protected Context context;
	protected ArrayList<?> dataFromModel = null;
	protected ArrayList<?> dataReturn = null;
	protected ArrayList<View> views;
	
	
	

	public BaseTask(TaskListener taskListener, int taskType) {
		this.taskListener = taskListener;
		this.taskType = taskType;
	}

	public BaseTask(TaskListener taskListener, int taskType, ArrayList<?> list, Context context) {
		this.taskListener = taskListener;
		this.taskType = taskType;
		this.dataFromModel = list;
		this.context = context;
	}
	
	

	@Override
	protected void onPreExecute() {
		switch (taskType) {
		default:
		}
	}

	@Override
	protected Boolean doInBackground(TaskParams... params) {
		if (taskListener == null)
			return TASK_FAILED;
		// DBSupport dbSupport = taskListener.getDBSupport();

		switch (taskType) {
		default:
			break;
		}

		if ((dataFromModel != null && dataFromModel.size() > 0)) {
			return TASK_DONE;
		}
		return TASK_FAILED;

	}


	@Override
	protected void onPostExecute(Boolean result) {

		if (!result) {
			if (taskListener != null)
				taskListener.onFail(taskType,msg);

		} else {
			if (taskListener != null) {
				taskListener.onSuccess(taskType, dataReturn,  msg);

			}
		}
	}

}
