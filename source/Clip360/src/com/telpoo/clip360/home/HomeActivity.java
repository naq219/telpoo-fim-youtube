package com.telpoo.clip360.home;

import java.util.ArrayList;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenedListener;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.startapp.android.publish.SDKAdPreferences;
import com.startapp.android.publish.StartAppSDK;
import com.telpoo.clip360.R;
import com.telpoo.clip360.db.Dbsupport;
import com.telpoo.clip360.db.TableDb;
import com.telpoo.clip360.object.CatOj;
import com.telpoo.clip360.task.TaskNetwork;
import com.telpoo.clip360.task.TaskType;
import com.telpoo.clip360.utils.TabId;
import com.telpoo.clip360.utils.Utils1;
import com.telpoo.clip360.utils.WhereCallback;
import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.ui.BaseFragmentActivity;
import com.telpoo.frame.utils.FileSupport;

public class HomeActivity extends SlidingFragmentActivity implements Idelegate, TaskType {

	int resource_home;
	String toastAskExit;
	static String[] tabids = { TabId.home };
	ViewMenu viewMenu;
	TopView topView;
	int currTaskType = TASK_GET_LIST_VIDEO;
	String lastedKeySearch = "";
	ArrayList<BaseObject> ojFavorite;
	boolean dataHomeFromDb = true;
	ViewActionBar viewBar;
	RelativeLayout rootBar;
	AdView adView;
	
	public HomeActivity() {
		super(tabids, R.id.realTabContent, "Bấm thêm lần nữa để thoát");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		SDKAdPreferences ad1= new SDKAdPreferences();
		StartAppSDK.init(this, "105338017", "205466137", ad1); 
		setupTracking();
		
		Dbsupport.init(TableDb.tables, TableDb.keys, HomeActivity.this, TableDb.pathDbName, 2);
		super.onCreate(savedInstanceState);
		initView();
		initData();
		
	}

	private void setupTracking() {
		setTrackingId("UA-48151387-2"); //UA-22328459-27
		
	}

	private void initData() {
		ImageLoader.getInstance().init(Utils1.imageLoaderCf(0, getBaseContext()));
		downloadCategory();
		
		JSONArray array=new JSONArray();
		String[] arr={"USjPI3pLXlQ","TNKSOp6diDo","FNyK3VMk3Lc","L783xTI01wQ","bxuTfQscq_8","7SSsj0IORRQ","EkHmo3v_7h8","qSxVf6Se7ps","cW8IryXdzGk","5QYV2_vW-vc","aBpw8gJBD9M","rkqNtuCKocE","vE2rmsGKibo","oUZ2dK93FuA","90yjf9vs1mU","grPkS2xrGIk","6wJfV1dP7gw","jw-IaB_Iw6I","5u3C9sCpYZs","wmh5ibibuDA","XezJUToW1D4","n24tkL3ZCY4","-QegXJ02Lk0","dYM6kWoNE3o","3KzJf0ddioI","Uv9hrBe3T2c","bJlNLBpvfmA","T3JrsISW1_A","leXJ9yAwilU","dJoLLaxPIdU","U9oQjZ3l2Xg","eJMLXgyUjZE","Dgo9syH9uHY","x3Tw6a3939s","krEhDskx0i8","QiQRaA57ZVE","QiQRaA57ZVE","YWhb0T9KfZQ","gxWWGjf41Eg","tEo9bAHzHzk","AK6em3z7B8g"};
		
		try {
			for (int i = 0; i < 40; i++) {
				JSONObject oj=new JSONObject();
				oj.put("vid", i);
				oj.put("vname", "Tập "+(i+1)+" - Ỷ Thiên Đồ Long Ký 2009");
				oj.put("vytid", arr[i]);
				oj.put("vtime", ":");
				oj.put("vview", new Random().nextInt(1000000));
				
				array.put(oj);
			}
			
			
			
		} catch (Exception e) {
		}
		String a="";
		String b=a;
		
		
	}

	private void downloadCategory() {
		TaskNetwork taskGetCategory = new TaskNetwork(model, TASK_GET_CATEGORY, null, getBaseContext());
		model.exeTask(null, taskGetCategory);
	}

	
	private void initView() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		
		//Utils1.loadAd(HomeActivity.this);
		ArrayList<BaseObject> dataMenu = new ArrayList<BaseObject>();
		dataMenu = Dbsupport.getMenu();

		getSlidingMenu().setMode(SlidingMenu.LEFT);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		viewMenu = new ViewMenu(getBaseContext(), dataMenu, this);
		setBehindContentView(viewMenu.getView());

		// getSlidingMenu().setSecondaryMenu(R.layout.layout_menu);

		getSlidingMenu().setShadowWidthRes(R.dimen.shadow_width);
		getSlidingMenu().setShadowDrawable(R.drawable.shadow);
		getSlidingMenu().setBehindOffsetRes(R.dimen.slidingmenu_offset);
		getSlidingMenu().setFadeDegree(0.35f);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		getSlidingMenu().setOnOpenedListener(new OnOpenedListener() {

			@Override
			public void onOpened() {

				TaskNetwork network = new TaskNetwork(model, TASK_GET_FAVORITE, null, getBaseContext());
				model.exeTask(null, network);

			}
		});

		pushFragments(TabId.home, new ListVideoFm("null"), true, null);

		RelativeLayout fm_top = (RelativeLayout) findViewById(R.id.fm_top);
		topView = new TopView(HomeActivity.this, this, R.layout.top_home);
		fm_top.addView(topView.getView());
		topView.setTitle("");
		rootBar = (RelativeLayout) findViewById(R.id.root_bar);
		viewBar = new ViewActionBar(HomeActivity.this, this);

		rootBar.addView(viewBar.getView());
		rootBar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				rootBar.setVisibility(View.GONE);

			}
		});
		
	}

	

	private void pushListVideo(String cID) {
		pushFragments(TabId.home, new ListVideoFm(cID), false, null);

	}

	@Override
	public void callBack(Object value, int where) {
		switch (where) {
		case WhereCallback.MENU_FAVORITE:
			pushFragments(TabId.home, new ListVideoFm(ojFavorite), false, null);
			getSlidingMenu().toggle(true);
			topView.setTitleHome(getString(R.string.video_yeu_thich));
			break;

		case WhereCallback.MENU_ITEM:
			BaseObject catOj = (BaseObject) value;
			pushListVideo("" + catOj.get(CatOj.CID));
			getSlidingMenu().toggle(true);
			//topView.setTitle(catOj.get(CatOj.CNAME));
			topView.setTitleHome(catOj.get(CatOj.CNAME));
			break;

		case WhereCallback.TOP_LEFT:

			getSlidingMenu().showMenu(true);
			break;

		case WhereCallback.TOP_SEARCH_SUCCESS:

			ArrayList<BaseObject> ojres = (ArrayList<BaseObject>) value;
			pushFragments(TabId.home, new ListVideoFm(ojres, topView.getKeySearch()), false, "");
			break;

		case WhereCallback.TOP_SEARCH_FAIL:
			Toast.makeText(getBaseContext(), "" + value, Toast.LENGTH_SHORT).show();
			break;

		case WhereCallback.TOP_RIGHT:
			if (rootBar.getVisibility() == View.VISIBLE)
				rootBar.setVisibility(View.GONE);
			else
				rootBar.setVisibility(View.VISIBLE);
			break;

		case WhereCallback.MENU_RELOAD:
			downloadCategory();
			break;
		default:
			break;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(int taskType, ArrayList<?> list, String msg) {
		super.onSuccess(taskType, list, msg);
		if (list == null || list.size() == 0)
			return;
		switch (taskType) {

		case TASK_GET_CATEGORY:

			ArrayList<BaseObject> ojres = (ArrayList<BaseObject>) list;

			viewMenu.setMenu(ojres);
			break;

		case TASK_GET_FAVORITE:
			ojFavorite = (ArrayList<BaseObject>) list;
			viewMenu.setSizeFavorite(ojFavorite.size());
			break;

		default:
			break;
		}
	}

	@Override
	public void onFail(int taskType, String msg) {
		super.onFail(taskType, msg);
		switch (taskType) {
		case TASK_GET_CATEGORY:
			viewMenu.setWhenLoadFail();
			break;

		default:
			break;
		}
	}

	@Override
	public BaseFragmentActivity getRootFA() {
		return super.getRootFA();
	}

	public void getFavorite() {
		TaskNetwork taskGetFavorite = new TaskNetwork(model, TASK_GET_FAVORITE, null, getBaseContext());
		model.exeTask(null, taskGetFavorite);
	}

	@Override
	protected void onResume() {
		super.onResume();
		getFavorite();

	}

}
