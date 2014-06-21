package com.telpoo.frame.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;

public class DeviceSupport {
	public static boolean isHaveSdCard() {
		return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}

	public static String getDeviceName() {
		String manufacturer = Build.MANUFACTURER;
		String model = Build.MODEL;
		if (model.startsWith(manufacturer)) {
			return capitalize(model);
		} else {
			return capitalize(manufacturer) + " " + model;
		}
	}

	private static String capitalize(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		char first = s.charAt(0);
		if (Character.isUpperCase(first)) {
			return s;
		} else {
			return Character.toUpperCase(first) + s.substring(1);
		}
	}

	@SuppressLint("NewApi")
	private Camera openFrontFacingCameraGingerbread() {
		int cameraCount = 0;
		Camera cam = null;
		Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
		cameraCount = Camera.getNumberOfCameras();
		for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
			Camera.getCameraInfo(camIdx, cameraInfo);
			if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
				try {
					cam = Camera.open(camIdx);
				} catch (RuntimeException e) {
					Mlog.E("Camera failed to open: " + e.getLocalizedMessage());
				}
			}
		}

		return cam;
	}

	public static String getCarrier(Context context) {
		TelephonyManager telephonyManager = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
		return telephonyManager.getNetworkOperatorName();

	}

	public static int getNetworkInfo(Context ct) {
		ConnectivityManager conMan = (ConnectivityManager) ct.getSystemService(Context.CONNECTIVITY_SERVICE);

		// mobile
		NetworkInfo cM = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo cW = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (cM != null) {

			State mobile = cM.getState();
			if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING)
				return ConnectivityManager.TYPE_MOBILE;
		}

		if (cW != null) {
			State wifi = cW.getState();
			if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING)
				return ConnectivityManager.TYPE_WIFI;
		}
		return -1;

	}
	
	public static String getMacAdd(Context ct){
		WifiManager manager = (WifiManager) ct.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = manager.getConnectionInfo();
		String address = info.getMacAddress();
		return address;
	}

}
