package com.telpoo.clip360;
//package com.telpoo.clip360.home;
//
//import java.util.ArrayList;
//
//import com.telpoo.clip360.R;
//import com.telpoo.clip360.task.TaskType;
//import com.telpoo.frame.object.BaseObject;
//
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//
//@SuppressLint("ValidFragment")
//public class DetailFm extends DetailFmLayout {
//	ArrayList<BaseObject> details;
//	int position;
//	String cID;

//	String page;
//	ListVideoFm listVideoFm;
////	public DetailFm() {
////
////	}
//
//	public DetailFm(ArrayList<BaseObject> details, int position, String cID, String page) {
//		this.details = details;
//		this.position = position;
//		this.cID = cID;
//		this.page = page;
//	}
//
//	@Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//
//		
//		for (int i = 0; i < position; i++) {
//			details.remove(i);
//		}
//		
//		listVideoFm=new ListVideoFm(details, page, cID);
//		
//		pushFragment(R.id.fl_bottom, listVideoFm);
//
//	}
//	
//	@Override
//	public void onSuccess(int taskType, ArrayList<?> list, String msg) {
//		super.onSuccess(taskType, list, msg);
//		switch (taskType) {
//		case TaskType.TASK_GET_LIST_VIDEO:
//			listVideoFm.onSuccess(taskType, list, msg);
//			break;
//
//		default:
//			break;
//		}
//	}
//
//}
