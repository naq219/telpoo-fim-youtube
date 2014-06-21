//package com.telpoo.frame.net;
//
//
//
//import com.google.analytics.tracking.android.GoogleAnalytics;
//
//import android.content.Context;
//
//public class GoogleAnalyticSupport {
//	private static final String TAG = "GoogleAnaTool";
//	private static GoogleAnalytics tracker = null;
//	public synchronized static void start(Context context) {
//		if (tracker != null)
//			stop(context);
//		String sAccount = "UA-32603851-1"; // create account in google
//											// Analytics.
//		tracker = GoogleAnalytics.getInstance(context);
//		tracker.startNewSession(sAccount, 0, context);
//	}
//	
//	public synchronized static void stop(Context context) { 
//        if (tracker != null) { 
//            GoogleAnalyticsTracker.getInstance().stopSession(); 
//            tracker = null; 
//        } 
//    } 
//	
//	private synchronized static void track(String page) { 
//        if (tracker == null) { 
//            Log.e(TAG, "instance is null"); 
//        } else { 
//            tracker.trackPageView(page); 
//            tracker.dispatch(); 
//            Log.v(TAG, page); 
//        } 
//    }  
//	
//	public synchronized static void trackEvent(String Catelog, String acttion, String label, int value) 
//    { 
//        tracker.trackEvent(Catelog, acttion, label, value); 
//    }
//}
