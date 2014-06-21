package com.telpoo.clip360.actionbar;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.WebView;
import android.widget.TextView;

import com.telpoo.clip360.R;
import com.telpoo.frame.ui.BaseFragmentActivity;
import com.telpoo.frame.utils.IntentSupport;

public class popup extends BaseFragmentActivity {
	TextView danhgia, lienhe, ungdungkhac;
	WebView wv1, wv2;
	View temp1;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.popup);
		
		
		
		temp1=findViewById(R.id.temp1);
		ungdungkhac = (TextView) findViewById(R.id.ungdungkhac);
		lienhe = (TextView) findViewById(R.id.lienhe);
		danhgia = (TextView) findViewById(R.id.danhgia);
		
		findViewById(R.id.rootviewaction).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
		View temp=findViewById(R.id.temp);
		
		temp.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				finish();
				return true;
			}
		});
		
		wv1 = (WebView) findViewById(R.id.wv1);
		wv2 = (WebView) findViewById(R.id.wv2);

		wv1.loadUrl("http://clip360.net/mobile/json/apps.php");
		wv2.loadUrl("http://clip360.net/mobile/json/contact.php");
		danhgia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				temp1.setVisibility(View.GONE);
				IntentSupport.openWeb(getString(R.string.url_app_google), popup.this);
				finish();

			}
		});

		lienhe.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				wv2.setVisibility(View.VISIBLE);
				wv1.setVisibility(View.GONE);
				temp1.setVisibility(View.GONE);
				((TextView)findViewById(R.id.title)).setText("Liên hệ");
				findViewById(R.id.temp).setVisibility(View.VISIBLE);

			}
		});

		ungdungkhac.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				wv2.setVisibility(View.GONE);
				wv1.setVisibility(View.VISIBLE);
				temp1.setVisibility(View.GONE);
				((TextView)findViewById(R.id.title)).setText("Ứng dụng khác");
				findViewById(R.id.temp).setVisibility(View.VISIBLE);
			}
		});

	}

}
