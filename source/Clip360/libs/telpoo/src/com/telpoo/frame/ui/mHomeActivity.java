package com.telpoo.frame.ui;
//package com.telpoo.frame.ui;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Stack;
//import java.util.Vector;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.util.DisplayMetrics;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.FrameLayout;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
//import com.telpoo.frame.R;
//import com.telpoo.frame.utils.Mlog;
//
//public class HomeActivity extends BaseActivity {
//
//	/* A HashMap of stacks, where we use tab identifier as keys.. */
//	public static HashMap<String, Stack<Fragment>> mStacks;
//	public String mCurrentTab;// = Const.MenuID.OTHER;
//
//	BaseFragment showingFragment = null;
//	protected int realtabcontent=1;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		//setContentView(R.layout.home_layout);
//
//		 mStacks = new HashMap<String, Stack<Fragment>>();
//
//
//		//init();
//
//	}
//
//	private void init() {
//
//		// mStacks = new HashMap<String, Stack<Fragment>>();
//		// // mStacks.put(Const.MenuID.HOW_TO, new Stack<Fragment>());
//		// mStacks.put(Const.MenuID.MY_PAGE, new Stack<Fragment>());
//		// mStacks.put(Const.MenuID.NEWFEED, new Stack<Fragment>());
//		// mStacks.put(Const.MenuID.OTHER, new Stack<Fragment>());
//		// // mStacks.put(Const.MenuID.SEARCH_TRAIL, new Stack<Fragment>());
//		// // mStacks.put(Const.MenuID.SETTING, new Stack<Fragment>());
//		// mStacks.put(Const.MenuID.START_NEW_TRAIL, new Stack<Fragment>());
//		//
//		// mStacks.put(Const.MenuID.LOGIN, new Stack<Fragment>());
//		// mStacks.put(Const.MenuID.CHAT, new Stack<Fragment>());
//		// mStacks.put(Const.MenuID.CHECKPOINT, new Stack<Fragment>());
//		// mStacks.put(Const.MenuID.SHOP, new Stack<Fragment>());
//		//
//		// homeContext = getBaseContext();
//
//	}
//
//	@Override
//	public void onSuccess(int taskType, ArrayList<?> list, String msg) {
//
//		showingFragment.onSuccess(taskType, list, msg);
//
//		super.onSuccess(taskType, list, msg);
//	}
//
//	@Override
//	public void onFail(int taskType, String msg) {
//
//		showingFragment.onFail(taskType, msg);
//
//		super.onFail(taskType, msg);
//	}
//
////	public void pushFragments(final String tag, final Fragment fragment, final boolean shouldAdd) {
////
////		if (shouldAdd) {
////
////			mStacks.get(tag).push(fragment);
////			mCurrentTab = tag;
////		}
////
////		FragmentManager manager = getSupportFragmentManager();
////		FragmentTransaction ft = manager.beginTransaction();
////		ft.replace(realtabcontent, fragment);
////		// ft.add(fragment, tag);
////		ft.commit();
////		showingFragment = (BaseFragment) fragment;
////
////		Mlog.T("pushFragments==tag=" + tag);
////
////	}
//
////	public void popFragments() {
////		Fragment fragment = mStacks.get(mCurrentTab).elementAt(mStacks.get(mCurrentTab).size() - 2);
////
////		mStacks.get(mCurrentTab).pop();
////
////		FragmentManager manager = getSupportFragmentManager();
////		FragmentTransaction ft = manager.beginTransaction();
////		ft.replace(realtabcontent, fragment);
////		ft.commit();
////
////		showingFragment = (BaseFragment) fragment;
////
////	}
////
////	@Override
////	public void onBackPressed() {
////
////		backFragment(mCurrentTab);
////
////	}
////
////	public void backFragment(String tabID) {
////
////		if (((BaseFragment) mStacks.get(tabID).lastElement()).onBackPressed() == false) {
////			if (mStacks.get(tabID).size() <= 1) {
////				// exit
////
////			} else {
////				popFragments();
////			}
////		} else {
////		}
////
////	}
////
////	@Override
////	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////
////		super.onActivityResult(requestCode, resultCode, data);
////
////		Mlog.D("onActivityResult");
////
////	}
////
////	public void addFragment(Fragment fragment) {
////
////		FragmentTransaction fr = getSupportFragmentManager().beginTransaction();
////		fr.replace(realtabcontent, fragment);
////		fr.addToBackStack(null);
////		fr.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
////		fr.commit();
////
////	}
////
//}
