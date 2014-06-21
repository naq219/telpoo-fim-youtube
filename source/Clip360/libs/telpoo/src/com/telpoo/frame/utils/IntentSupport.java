package com.telpoo.frame.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class IntentSupport {
	
	public static void openWeb(String url,Context ct){
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		ct.startActivity(browserIntent);
	}

}
