package com.telpoo.frame.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.telpoo.frame.model.TaskParams;

public class BUtils {

	private static boolean isHB = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;

	@SuppressLint("NewApi")
	public static void executeAsyncTask(Executor executor, AsyncTask<TaskParams, Void, Boolean> asyncTask, TaskParams[] params) {
		if (isHB) {
			asyncTask.executeOnExecutor(executor, params);

		} else {
			asyncTask.execute(params);
		}
	}

	public static void saveStringSPR(String key, String value, Context context) {
		if (key.equalsIgnoreCase(Cons.Spr.KEY_TICK_MENU))
			Mlog.T("KEY_TICK_MENU=" + value);
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = preferences.edit();

		editor.putString(key, value);

		editor.commit();

	}

	public static int getIntSPR(String key, Context context) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		return preferences.getInt(key, 0);
	}

	public static String getStringSPR(String key, Context context) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		return preferences.getString(key, Cons.Defi.SPR_GET_FALL);
	}

	public static void hideKeyboard(Context context, EditText editText) { //
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}

	/*
	 * public static boolean isNotEmptyEdittext(ViewGroup group){
	 * 
	 * for (int i = 0, count = group.getChildCount(); i < count; ++i) { View
	 * view = group.getChildAt(i); if (view instanceof EditText) { String
	 * text=((EditText)view).getText()+""; Log.i("CMC", "Sign up : " + text);
	 * if(TextUtils.isEmpty(text)) return false; }
	 * 
	 * } return true; }
	 */

	public static Date convertStringToDate(String value, String format) {
		Mlog.T("convertStringToDate=value:" + value);
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date convertedDate = new Date();
		try {
			convertedDate = dateFormat.parse(value);
			Mlog.T("234234=" + convertedDate.getMinutes());
		} catch (ParseException e) {
			Mlog.E("convertStringToDateTime=5408532=" + e);
		}
		return convertedDate;

	}

	public static String convertToDate(String dateString) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yy");
		Date convertedDate = new Date();
		try {
			convertedDate = dateFormat.parse(dateString);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertedDate.toLocaleString();
	}

}
