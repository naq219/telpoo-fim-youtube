package com.telpoo.clip360;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.telpoo.clip360.R;
import com.telpoo.clip360.home.MyBaseFm;
import com.telpoo.clip360.utils.Utils1;

public class DetailFmLayout extends MyBaseFm {
	ImageView favorite, comment, share, goto_web;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.detail, container, false);
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		favorite = (ImageView) view.findViewById(R.id.favorite);
		comment = (ImageView) view.findViewById(R.id.comment);
		share = (ImageView) view.findViewById(R.id.share);
		goto_web = (ImageView) view.findViewById(R.id.goto_web);

		Utils1.hilightButton(new ImageView[] { goto_web, share, favorite, comment });
	}

}
