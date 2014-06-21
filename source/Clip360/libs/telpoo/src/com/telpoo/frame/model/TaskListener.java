package com.telpoo.frame.model;

import java.util.ArrayList;

import android.content.Context;

import com.telpoo.frame.database.BaseDBSupport;

public interface TaskListener {
	Context getContext();

	BaseDBSupport getDBSupport();

	void onSuccess(int taskType, ArrayList<?> list, String result);

	void onFail(int taskType, String result);

	void onProgress(int taskType, int progress);
}
