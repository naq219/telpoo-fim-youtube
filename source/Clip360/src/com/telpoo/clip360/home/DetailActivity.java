package com.telpoo.clip360.home;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.startapp.android.publish.Ad;
import com.startapp.android.publish.AdDisplayListener;
import com.startapp.android.publish.AdEventListener;
import com.startapp.android.publish.SDKAdPreferences;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;
import com.startapp.android.publish.banner.Banner;
import com.startapp.android.publish.nativead.NativeAdDetails;
import com.startapp.android.publish.nativead.NativeAdPreferences;
import com.startapp.android.publish.nativead.NativeAdPreferences.NativeAdBitmapSize;
import com.startapp.android.publish.nativead.StartAppNativeAd;
import com.startapp.android.publish.splash.SplashConfig;
import com.startapp.android.publish.splash.SplashConfig.Theme;
import com.telpoo.clip360.R;
import com.telpoo.clip360.adapter.VideosAdapter;
import com.telpoo.clip360.db.Dbsupport;
import com.telpoo.clip360.object.VideoOj;
import com.telpoo.clip360.task.TaskType;
import com.telpoo.clip360.utils.Defi;
import com.telpoo.clip360.utils.EndlessScrollListener;
import com.telpoo.clip360.utils.WhereCallback;
import com.telpoo.clip360.youtube.YouTubeFailureRecoveryActivity;
import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.model.BaseModel;
import com.telpoo.frame.model.ModelListener;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.IntentSupport;
import com.telpoo.frame.utils.Mlog;
import com.telpoo.frame.utils.ViewUtils;

public class DetailActivity extends YouTubeFailureRecoveryActivity implements View.OnClickListener,
		CompoundButton.OnCheckedChangeListener, YouTubePlayer.OnFullscreenListener, Idelegate, ModelListener, TaskType {
	private static final int PORTRAIT_ORIENTATION = Build.VERSION.SDK_INT < 9 ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
			: ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;
	private LinearLayout baseLayout;
	private YouTubePlayerView playerView;
	private YouTubePlayer player;
	private View bottomLayout;
	private boolean fullscreen;
	private String cueVideo;
	private RelativeLayout rootTop;
	private ArrayList<BaseObject> datas;
	private ListView lv;
	private VideosAdapter adapter;
	int countTryWv = 0;
	private String idVideo;
	private String cID;
	private TopView topView;
	private BaseObject oj;
	private int tasktype;
	public BaseModel model = null;
	private String currentPage = "0";
	private View loadingView;
	WebView wv;
	Idelegate onLoginFbsuccess;
	int numcallbackFblg = 0;
	private ImageView favorite, comment, share, goto_web;
	String urlFb = "";
	RelativeLayout root_bar;
	ImageView close;
	public static int countForAds = 0;
	Banner adView;

	/** StartAppAd object declaration */
	private StartAppAd startAppAd = new StartAppAd(this);

	/** StartApp Native Ad declaration */
	private StartAppNativeAd startAppNativeAd = new StartAppNativeAd(this);
	private NativeAdDetails nativeAd = null;

	private AdEventListener nativeAdListener = new AdEventListener() {

		@Override
		public void onReceiveAd(Ad ad) {

			// Get the native ad
			ArrayList<NativeAdDetails> nativeAdsList = startAppNativeAd.getNativeAds();
			if (nativeAdsList.size() > 0) {
				nativeAd = nativeAdsList.get(0);
			}

			// Verify that an ad was retrieved
			if (nativeAd != null) {

				// When ad is received and displayed - we MUST send impression
				nativeAd.sendImpression(DetailActivity.this);

			}
		}

		@Override
		public void onFailedToReceiveAd(Ad ad) {

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		StartAppSDK.init(this, "105338017", "205466137", new SDKAdPreferences());
		//StartAppAd.showSplash(this, savedInstanceState, new SplashConfig().setTheme(Theme.SKY).setAppName("StartApp Example"));
		
		
		
		setContentView(R.layout.detail);

		//StartAppAd.showSlider(this);

		startAppNativeAd.loadAd(
				new NativeAdPreferences().setAdsNumber(1).setAutoBitmapDownload(true)
						.setImageSize(NativeAdBitmapSize.SIZE150X150), nativeAdListener);

		if (model == null) {
			model = new BaseModel();
			model.setModelListener1(this);
		}
		int position = getIntent().getIntExtra("position", 0);
		cID = getIntent().getStringExtra("cID");
		tasktype = getIntent().getIntExtra("tasktype", 9);
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			datas = bundle.getParcelableArrayList("data");
			if (datas.size() - position < 1)
				finish();
			cueVideo = datas.get(position).get(VideoOj.VYTID);
			oj = datas.get(position);
			idVideo = datas.get(position).get(VideoOj.VID);

		}

		rootTop = (RelativeLayout) findViewById(R.id.rootTop);

		topView = new TopView(DetailActivity.this, this, R.layout.top_detail);

		rootTop.addView(topView.getView());
		baseLayout = (LinearLayout) findViewById(R.id.layout);
		playerView = (YouTubePlayerView) findViewById(R.id.player);
		adapter = new VideosAdapter(getBaseContext(), R.layout.item_videos, new ArrayList<BaseObject>());
		lv = (ListView) findViewById(R.id.lv);
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		loadingView = inflater.inflate(R.layout.loading, null);
		if (tasktype != TASK_GET_FAVORITE)
			lv.addFooterView(loadingView);
		lv.setAdapter(adapter);
		adapter.SetItems(datas);

		// temp
		currentPage = "" + ((int) datas.size() / 23);
		lv.setOnScrollListener(new EndlessScrollListener(0, model, tasktype, cID, getBaseContext(), currentPage));
		bottomLayout = findViewById(R.id.root_bottom);

		playerView.initialize(Defi.DEVELOPER_KEY_YOUTUBE, this);

		initView();
		initData();
		doLayout();

	}

/*	private void setupAds1() {

		countForAds++;

		// if (countForAds >= 2) {
		countForAds = 0;
		// Show an Ad
		startAppAd.showAd(new AdDisplayListener() {

			*//**
			 * Callback when Ad has been hidden
			 * 
			 * @param ad
			 *//*
			@Override
			public void adHidden(Ad ad) {

			}

			*//**
			 * Callback when ad has been displayed
			 * 
			 * @param ad
			 *//*
			@Override
			public void adDisplayed(Ad ad) {

			}
		});

		// }

	}*/

	private void initView() {
		//
		adView = (Banner) findViewById(R.id.adView);
		close = (ImageView) findViewById(R.id.close);
		favorite = (ImageView) findViewById(R.id.favorite);
		share = (ImageView) findViewById(R.id.share);
		comment = (ImageView) findViewById(R.id.comment);
		goto_web = (ImageView) findViewById(R.id.goto_web);
		wv = (WebView) findViewById(R.id.wv);
		int FILTERED_GREY = Color.argb(200, 42, 18, 191);
		ImageView[] imageViews = { favorite, share, comment, goto_web };
		ViewUtils.HighlightImageView(imageViews, FILTERED_GREY);
		wv.getSettings().setJavaScriptEnabled(true);
		ViewUtils.loadDataWv(wv, "Đang tải...");
		OnClickListener onclick4 = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.comment:
					if (lv.getVisibility() == View.VISIBLE) {
						// show commentp
						showComment();
					} else {
						hideComment();
					}
					break;

				case R.id.favorite:
					

					if (Dbsupport.addFv(oj)) {
						Toast.makeText(getBaseContext(), "Đã thêm vào yêu thích!", 0).show();
						favorite.setImageResource(R.drawable.star_2);
					}

					else {
						Toast.makeText(getBaseContext(), "Đã bỏ yêu thích!", 0).show();
						favorite.setImageResource(R.drawable.star_1);
					}

					break;

				case R.id.goto_web:
					IntentSupport.openWeb("http://clip360.net/?p=" + oj.get(VideoOj.VID), DetailActivity.this);

					break;

				case R.id.share:
					Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
					shareIntent.setType("text/plain");
					shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Xem video " + oj.get(VideoOj.VNAME)
							+ " với ứng dụng Clip Việt, tải tại: play.google.com/store/apps/details?id=" + Defi.PACKPAGE);
					startActivity(shareIntent);
					break;

				case R.id.close:
					hideComment();
					break;
				default:
					break;
				}

			}

			private void hideComment() {
				findViewById(R.id.other_views).setVisibility(View.VISIBLE);
				findViewById(R.id.other2).setVisibility(View.GONE);
				close.setVisibility(View.GONE);
				playerView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				player.play();
				LinearLayout.LayoutParams playerParams = (LinearLayout.LayoutParams) playerView.getLayoutParams();
				playerParams.height = LayoutParams.WRAP_CONTENT;
				wv.setVisibility(View.GONE);
				lv.setVisibility(View.VISIBLE);
				// findViewById(R.id.temp2).setVisibility(View.VISIBLE);

			}

			private void showComment() {
				findViewById(R.id.other_views).setVisibility(View.GONE);
				findViewById(R.id.other2).setVisibility(View.VISIBLE);
				close.setVisibility(View.VISIBLE);
				player.pause();
				LinearLayout.LayoutParams playerParams = (LinearLayout.LayoutParams) playerView.getLayoutParams();
				playerParams.height = 0;
				lv.setVisibility(View.GONE);
				// findViewById(R.id.temp2).setVisibility(View.GONE);
				wv.setVisibility(View.VISIBLE);

			}
		};
		close.setOnClickListener(onclick4);
		favorite.setOnClickListener(onclick4);
		share.setOnClickListener(onclick4);
		comment.setOnClickListener(onclick4);
		goto_web.setOnClickListener(onclick4);
		// root_bar.setOnClickListener(onclick4);
		if (oj.getBool(VideoOj.FAVORITE))
			favorite.setImageResource(R.drawable.star_2);

	}

	private void initData() {
		urlFb = "http://clip360.net/mobile/json/comment.php?v=" + oj.get(VideoOj.VID);
		topView.setTitle(oj.get(VideoOj.VNAME));
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				BaseObject oj1 = (BaseObject) arg0.getAdapter().getItem(position);

				updateUi(oj1);

				//setupAds1();

			}
		});

		wv.setWebViewClient(new WebViewClient() {
			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
				countTryWv = countTryWv + 1;
				if (countTryWv < 5)
					view.loadUrl(urlFb);
				wv.setVisibility(View.GONE);
				lv.setVisibility(View.VISIBLE);
				playerView.getLayoutParams().height = LayoutParams.WRAP_CONTENT;
				Toast.makeText(getBaseContext(), R.string.co_loi_xay_ra, Toast.LENGTH_LONG).show();
				ViewUtils.loadDataWv(wv, getString(R.string.no_network));

			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				Mlog.T("view.getUrl()=" + view.getUrl());
				String curUrl = "" + view.getUrl();
				if (curUrl.indexOf("plugins/login_success.php") != -1) {
					numcallbackFblg++;
					if (numcallbackFblg == 1)
						onLoginFbsuccess.callBack(0, 0);
				}
			}

		});
		ViewUtils.loadDataWv(wv, "Đang tải...");
		wv.loadUrl("http://clip360.net/mobile/json/comment.php?v=" + oj.get(VideoOj.VID));

		onLoginFbsuccess = new Idelegate() {

			@Override
			public void callBack(Object value, int where) {

				wv.loadUrl("http://clip360.net/mobile/json/comment.php?v=" + oj.get(VideoOj.VID));

			}
		};

	}

	@Override
	public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
		this.player = player;

		player.play();

		player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
		player.setOnFullscreenListener(this);
		if (!wasRestored) {
			player.loadVideo(cueVideo);

		}

		int controlFlags = player.getFullscreenControlFlags();

		setRequestedOrientation(PORTRAIT_ORIENTATION);
		controlFlags |= YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE;

		player.setFullscreenControlFlags(controlFlags);
	}

	@Override
	protected YouTubePlayer.Provider getYouTubePlayerProvider() {
		return playerView;
	}

	@Override
	public void onClick(View v) {
		player.setFullscreen(!fullscreen);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		int controlFlags = player.getFullscreenControlFlags();
		if (isChecked) {

			setRequestedOrientation(PORTRAIT_ORIENTATION);
			controlFlags |= YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE;
		} else {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
			controlFlags &= ~YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE;
		}
		player.setFullscreenControlFlags(controlFlags);
	}

	private void doLayout() {

		LinearLayout.LayoutParams playerParams = (LinearLayout.LayoutParams) playerView.getLayoutParams();
		if (fullscreen) {
			playerParams.height = LayoutParams.MATCH_PARENT;

			hideViewForFullScreen();
		} else {

			if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
				hideViewForFullScreen();
			} else {
				showView();
				playerParams.height = WRAP_CONTENT;
			}

		}
	}

	@Override
	public void onFullscreen(boolean isFullscreen) {
		fullscreen = isFullscreen;
		doLayout();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		doLayout();
	}

	@Override
	public void callBack(Object value, int where) {
		if (where == WhereCallback.TOP_SEARCH_SUCCESS) {

			ArrayList<BaseObject> ojres = (ArrayList<BaseObject>) value;
			adapter.SetItems(ojres);
			adapter.notifyDataSetChanged();
		}

		if (where == WhereCallback.TOP_SEARCH_FAIL) {
			Toast.makeText(getBaseContext(), "" + value, Toast.LENGTH_SHORT).show();
		}

	}

	void hideViewForFullScreen() {
		rootTop.setVisibility(View.GONE);
	}

	void showView() {
		rootTop.setVisibility(View.VISIBLE);
	}

	@Override
	public Context getContext() {
		return null;
	}

	@Override
	public void onSuccess(int taskType, ArrayList<?> list, String msg) {
		switch (taskType) {
		case TASK_GET_LIST_VIDEO:
		case TASK_SEARCH:
			if (list == null || list.size() == 0) {
				lv.removeFooterView(loadingView);
				return;
			}
			ArrayList<BaseObject> ojRes = (ArrayList<BaseObject>) list;
			adapter.Adds(ojRes);
			adapter.notifyDataSetChanged();
			// neu tra ve it du lieu thi xoa loadmore
			break;

		default:
			break;
		}

	}

	@Override
	public void onFail(int taskType, String msg) {
		switch (taskType) {
		case TASK_GET_LIST_VIDEO:
		case TASK_SEARCH:
			Toast.makeText(getBaseContext(), "" + msg, 0).show();
			break;

		default:
			break;
		}

	}

	@Override
	public void onProgress(int taskType, int progress) {

	}

	void updateUi(BaseObject oj) {

		if (oj.getBool(VideoOj.FAVORITE))
			favorite.setImageResource(R.drawable.star_2);

		urlFb = "http://clip360.net/mobile/json/comment.php?v=" + oj.get(VideoOj.VID);
		topView.setTitle(oj.get(VideoOj.VNAME));
		wv.loadUrl("http://clip360.net/mobile/json/comment.php?v=" + oj.get(VideoOj.VID));

		player.loadVideo(oj.get(VideoOj.VYTID));
	}

}
