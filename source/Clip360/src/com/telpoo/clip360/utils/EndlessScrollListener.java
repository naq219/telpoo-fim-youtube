package com.telpoo.clip360.utils;

import java.util.ArrayList;

import com.telpoo.clip360.task.TaskNetwork;
import com.telpoo.clip360.task.TaskType;
import com.telpoo.frame.model.BaseModel;
import com.telpoo.frame.model.TaskParams;
import com.telpoo.frame.utils.Mlog;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;


	
	public class EndlessScrollListener implements OnScrollListener {

    private int visibleThreshold = 5;
    private int currentPage = 0;
    private int previousTotal = 0;
    private boolean loading = true;
    private BaseModel model;
	private String cID;
	
	private Context ct;
	int tasktype;
    public EndlessScrollListener() {
    }
    public EndlessScrollListener(int visibleThreshold, BaseModel model,int tasktype, String cID,Context ct,String currentPage) {
        this.visibleThreshold = visibleThreshold;
        this.model=model;
        this.cID=cID;
        this.ct=ct;
        this.tasktype=tasktype;
        boolean isFirst=true;
        try {
        	this.currentPage=Integer.parseInt(currentPage);
		} catch (Exception e) {
			
		}
        
    }
    
    
	public void setLoading(){
    	
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
            int visibleItemCount, int totalItemCount) {
    	if(tasktype==TaskType.TASK_GET_FAVORITE)return;
    	
    	//Mlog.T("firstVisibleItem="+firstVisibleItem+"=visibleItemCount"+visibleItemCount+"=totalItemCount"+totalItemCount);
    	
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
                currentPage++;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            // I load the next page of gigs using a background task,
            // but you can call any function here.
        	
        	ArrayList<String> vlSend = new ArrayList<String>();
        	vlSend.add(cID);
    		vlSend.add(""+(currentPage));
    		
    		

    		TaskNetwork taskGetVideos = new TaskNetwork(model, tasktype, vlSend, ct);
    		Mlog.T("load more"+(currentPage));
    		
            model.exeTask(null, taskGetVideos);
            loading = true;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }


}
