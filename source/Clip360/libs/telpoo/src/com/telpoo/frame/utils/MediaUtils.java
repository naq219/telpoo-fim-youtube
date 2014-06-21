package com.telpoo.frame.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

public class MediaUtils {
	static String TAG = MediaUtils.class.getSimpleName();

	public static boolean cropImage(Uri picUri, int requsetCode, Activity activity) {
		try {

			Intent cropIntent = new Intent("com.android.camera.action.CROP");
			cropIntent.setDataAndType(picUri, "image/*");
			cropIntent.putExtra("crop", "true");
//			cropIntent.putExtra("aspectX", 1);
//			cropIntent.putExtra("aspectY", 1);
			cropIntent.putExtra("outputX", 200);
			cropIntent.putExtra("outputY", 200);
			cropIntent.putExtra("scale", true);
			cropIntent.putExtra("return-data", true);
			cropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
			
			
			activity.startActivityForResult(cropIntent, requsetCode);
			return true;
		}
		catch (ActivityNotFoundException anfe) {
			Mlog.E(TAG + " - " + anfe);
			return false;
		}
	}

	public static Bitmap getIntentCropImage(Intent data) {

		if (data != null) {
			Bundle extras = data.getExtras();
			Bitmap selectedBitmap = extras.getParcelable("data");

			return selectedBitmap;
		}
		return null;

	}

}
