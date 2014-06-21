package com.telpoo.clip360.home;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.telpoo.clip360.R;
import com.telpoo.clip360.actionbar.popup;
import com.telpoo.clip360.task.TaskNetwork;
import com.telpoo.clip360.task.TaskType;
import com.telpoo.clip360.utils.WhereCallback;
import com.telpoo.clip360.utils.myEdittext;
import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.model.BaseModel;
import com.telpoo.frame.model.ModelListener;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.KeyboardSupport;
import com.telpoo.frame.utils.ViewUtils;

@SuppressLint("ValidFragment")
public class TopView implements ModelListener {
	protected ImageButton left, right, search, clear;
	protected myEdittext ed;
	protected Idelegate idelegate;
	Context context;
	View v;
	int resource;
	public BaseModel model = null;
	TextView title;
	Animation slideOut;
	Animation slideOutTop;
	Animation slideIn;
	Animation slideInTop;
	String lastedKeySeach = "";
	ProgressBar progress_search;

	public TopView(Context context, Idelegate idelegate, int resource) {

		this.idelegate = idelegate;
		this.context = context;
		this.resource = resource;

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = inflater.inflate(resource, null);

		initView(v);

	}

	private void initView(View view) {
		progress_search = (ProgressBar) view.findViewById(R.id.progress_search);

		slideOut = AnimationUtils.loadAnimation(context, R.anim.slide_out);
		slideOut.setAnimationListener(setAnimationListener(0));
		slideOutTop = AnimationUtils.loadAnimation(context, R.anim.slide_out_top);
		slideOutTop.setAnimationListener(setAnimationListener(0));

		slideIn = AnimationUtils.loadAnimation(context, R.anim.slide_in);
		slideIn.setAnimationListener(setAnimationListener(1));

		slideInTop = AnimationUtils.loadAnimation(context, R.anim.slide_in_top);
		slideInTop.setAnimationListener(setAnimationListener(1));

		left = (ImageButton) view.findViewById(R.id.left);
		right = (ImageButton) view.findViewById(R.id.right);
		search = (ImageButton) view.findViewById(R.id.search);
		ed = (myEdittext) view.findViewById(R.id.ed);

		ed.setOnEditTextImeBackListener(new Idelegate() {

			@Override
			public void callBack(Object value, int where) {
				closeEd();

			}
		});

		ed.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					String textEd = "" + ed.getText().toString();
					if (ed.getVisibility() == View.VISIBLE) {
						if (textEd.length() == 0)
							closeEd();
						else
							doSearch();

					} else {
						showEd();

					}
					return true;
				}
				return false;
			}
		});

		clear = (ImageButton) view.findViewById(R.id.clear);
		title = (TextView) view.findViewById(R.id.title);
		title.setSelected(true);

		OnClickListener clickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.left:
					idelegate.callBack(0, WhereCallback.TOP_LEFT);
					if (resource == R.layout.top_detail) {
						if (ed.getVisibility() == View.VISIBLE)
							closeEd();
						else
							((Activity) context).finish();
					}
					break;
				case R.id.right:
					// idelegate.callBack(0, WhereCallback.TOP_RIGHT);
					context.startActivity(new Intent(context, popup.class));
					break;

				case R.id.search:
					String textEd = "" + ed.getText().toString();
					if (ed.getVisibility() == View.VISIBLE) {
						if (textEd.length() == 0)
							closeEd();
						else
							doSearch();

					} else {
						showEd();

					}
					break;

				case R.id.clear:
					ed.setText("");
					break;

				case R.id.title:
					idelegate.callBack(0, WhereCallback.TOP_LEFT);
					break;

				default:
					break;
				}
			}

		};

		left.setOnClickListener(clickListener);
		clear.setOnClickListener(clickListener);
		search.setOnClickListener(clickListener);
		right.setOnClickListener(clickListener);
		if (resource == R.layout.top_home) {
			title.setOnClickListener(clickListener);
		}

		int FILTERED_GREY = Color.argb(200, 42, 18, 191);
		ImageView[] imageViews = { left, right, search };
		ViewUtils.HighlightImageView(imageViews, FILTERED_GREY);

		if (model == null) {
			model = new BaseModel();
			model.setModelListener1(this);
		}

	}

	protected void doSearch() {
		progress_search.setVisibility(View.VISIBLE);
		ArrayList<String> dataSend = new ArrayList<String>();
		lastedKeySeach = ed.getText().toString();
		dataSend.add(lastedKeySeach);
		dataSend.add("1");
		TaskNetwork network = new TaskNetwork(model, TaskType.TASK_SEARCH, dataSend, context);
		model.exeTask(null, network);
		KeyboardSupport.hideKeyboard(context, ed);

	}

	protected void showEd() {
		clear.setVisibility(View.VISIBLE);
		ed.startAnimation(slideIn);
		title.startAnimation(slideInTop);

	}

	private void closeEd() {
		clear.setVisibility(View.GONE);
		ed.startAnimation(slideOut);
		title.startAnimation(slideOutTop);
		KeyboardSupport.hideKeyboard(context, ed);

	}

	public AnimationListener setAnimationListener(final int type) {
		AnimationListener listener = new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				if (type == 0) { // close ed
					title.setVisibility(View.VISIBLE);
				} else {
					ed.setVisibility(View.VISIBLE);

				}

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {

				if (type == 0) { // close ed
					ed.setVisibility(View.GONE);
				} else {

					title.setVisibility(View.GONE);
					ed.requestFocus();
					KeyboardSupport.showKeyboard(context, ed);

				}
			}
		};

		return listener;
	}

	public void setTitle(String text) {
		title.setText(text);
		title.setSelected(true);
	}

	public void setTitleHome(String text) {
		title.setText(text);
		title.setSelected(true);
		left.setVisibility(View.GONE);
	}

	public String getKeySearch() {
		return lastedKeySeach;
	}

	public View getView() {
		return v;
	}

	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onSuccess(int taskType, ArrayList<?> list, String msg) {

		switch (taskType) {
		case TaskType.TASK_SEARCH:
			progress_search.setVisibility(View.INVISIBLE);
			if (list == null || list.size() == 0)
				idelegate.callBack("Không tìm thấy video", WhereCallback.TOP_SEARCH_FAIL);
			else {
				ArrayList<BaseObject> ojres = (ArrayList<BaseObject>) list;
				idelegate.callBack(ojres, WhereCallback.TOP_SEARCH_SUCCESS);

			}
			break;

		default:
			break;
		}

	}

	@Override
	public void onFail(int taskType, String msg) {
		switch (taskType) {
		case TaskType.TASK_SEARCH:
			progress_search.setVisibility(View.INVISIBLE);
			Toast.makeText(context, "" + msg, 0).show();
			idelegate.callBack("" + msg, WhereCallback.TOP_SEARCH_FAIL);

			break;

		default:
			break;
		}

	}

	@Override
	public void onProgress(int taskType, int progress) {
		// TODO Auto-generated method stub

	}

}
