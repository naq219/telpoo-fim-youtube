package com.telpoo.frame.model;

import java.util.ArrayList;

import android.content.Context;

public interface ModelListener {
	Context getContext();

	void onSuccess(int taskType, ArrayList<?> list, String msg);

	void onFail(int taskType, String msg);

	void onProgress(int taskType, int progress);
}
