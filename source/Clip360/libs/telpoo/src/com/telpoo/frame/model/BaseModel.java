/**
 * 
 */
package com.telpoo.frame.model;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.os.AsyncTask;

import com.telpoo.frame.database.BaseDBSupport;
import com.telpoo.frame.utils.BUtils;
import com.telpoo.frame.utils.Mlog;

/**
 * @author NAQ219
 * 
 */
public class BaseModel implements TaskListener {
	String TAG=BaseModel.class.getSimpleName();
	protected static int MODEL_NTHREADS = 5;

	protected static ExecutorService modelExecutor = Executors.newFixedThreadPool(MODEL_NTHREADS);

	protected ModelListener modelListener = null;

	/**
	 * @param modelListener
	 *            the modelListener to set
	 */
	public void setModelListener1(ModelListener modelListener) {
		this.setModelListener(modelListener);
	}

	@Override
	public Context getContext() {
		if (modelListener != null)
			return modelListener.getContext();
		return null;
	}

	@Override
	public BaseDBSupport getDBSupport() {
		if (modelListener != null)
			return BaseDBSupport.getInstance(modelListener.getContext());
		return null;
	}

	public void exeTask(TaskParams params,AsyncTask<TaskParams, Void, Boolean> task) {
		
//		AsyncTask<TaskParams, Void, Boolean> task=null;
//		task=new BaseTask(this, taskType, list, getContext());
		if (task != null) {
			BUtils.executeAsyncTask(modelExecutor, task, new TaskParams[]{params});
		}
		else{
			Mlog.E(TAG+"-exeTask-task is null");
		}
		

	}

	@Override
	public void onSuccess(int taskType, ArrayList<?> list, String msg) {
		if (modelListener != null)
			modelListener.onSuccess(taskType, list, msg);
	}

	@Override
	public void onFail(int taskType, String msg) {
		if (modelListener != null)
			modelListener.onFail(taskType, msg);
	}

	@Override
	public void onProgress(int taskType, int progress) {
		if (modelListener != null)
			modelListener.onProgress(taskType, progress);
	}

	/**
	 * @return the modelListener
	 */
	public ModelListener getModelListener() {
		return modelListener;
	}

	/**
	 * @param modelListener
	 *            the modelListener to set
	 */
	public void setModelListener(ModelListener modelListener) {
		this.modelListener = modelListener;
	}

	/*
	 * 
	 */

}
