/**
 * copyright WISeKey SA
 * author: Phathv 
 * date: 09/04/2012
 * GPL
 */

package com.telpoo.frame.utils;

import android.content.Context;
import android.util.Log;

import com.telpoo.frame.object.BaseObject;

public class Mlog {
	protected static Mlog instance;
	private static String TAG = "telpoo";

	public static Mlog getInstance() {
		if (instance == null)
			instance = new Mlog();
		return instance;
	}

	public static void D(String info) {

		Log.d(TAG, info + "");

	}
	public static void D(int info) {

		Log.d(TAG, info + "");

	}
	public static void D(boolean info) {

		Log.d(TAG, info + "");

	}

	public static void D(Context context, String info) {
		if (info != null) {
			Log.d(TAG, context.getClass().getSimpleName() + " - " + info);
		}
	}

	public static void E(String info) {
		if (info != null) {
			Log.e("NAQ", info);
		}
	}

	public static void I(String info) {
		if (info != null) {
			Log.i(TAG, info);
		}
	}
	
	public static void T(String info) {
		if (info != null) {
			Log.i(TAG, info);
		}
	}
	
	public static void w(String info) {
		if (info != null) {
			Log.w(TAG, info);
		}
	}
	
	public static void showLogObject(BaseObject oj, String[] keys){
		for (String string : keys) {
			
			Mlog.T("123123="+string+"="+oj.get(string));
		}
		
	}


}
