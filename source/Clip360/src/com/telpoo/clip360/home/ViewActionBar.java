package com.telpoo.clip360.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.telpoo.clip360.R;
import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.model.BaseModel;
import com.telpoo.frame.utils.IntentSupport;

@SuppressLint("ValidFragment")
public class ViewActionBar {
	protected TextView lienhe, danhgia, ungdungkhac;
	protected Idelegate idelegate;
	Context context;
	RelativeLayout rootViewAction;

	View v;
	public BaseModel model = null;

	public ViewActionBar(Context context, Idelegate idelegate) {

		this.idelegate = idelegate;
		this.context = context;

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = inflater.inflate(R.layout.view_action_bar, null);

		initView(v);

	}

	private void initView(View view) {
		rootViewAction = (RelativeLayout) view.findViewById(R.id.rootviewaction);
		lienhe = (TextView) view.findViewById(R.id.lienhe);
		danhgia = (TextView) view.findViewById(R.id.danhgia);
		ungdungkhac = (TextView) view.findViewById(R.id.ungdungkhac);

		danhgia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				IntentSupport.openWeb("https://play.google.com/store/apps/details?id="+context.getApplicationContext().getPackageName(), context);

			}
		});
		
//		final View vr= ((Activity)context).findViewById(R.id.root_bar);
//		vr.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				
//				vr.setVisibility(View.GONE);
//				
//				// rootViewAction.setVisibility(View.GONE);
//				//((Activity) context).findViewById(R.id.rootviewaction).setVisibility(View.GONE);
//
//			}
//		});

		// lienhe.setOnClickListener(clickListener);
		// ungdungkhac.setOnClickListener(clickListener);

		// int FILTERED_GREY = Color.argb(200, 42, 18, 191);
		// ImageView[] imageViews = { lienhe, danhgia, ungdungkhac };
		// ViewUtils.HighlightImageView(imageViews, FILTERED_GREY);

	}

	public View getView() {
		return v;
	}

}
