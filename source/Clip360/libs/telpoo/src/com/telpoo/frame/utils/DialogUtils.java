package com.telpoo.frame.utils;


import android.app.Activity;

import java.util.Calendar;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.support.v4.app.DialogFragment;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.widget.Button;
import android.widget.DatePicker;

import com.telpoo.frame.R;
import com.telpoo.frame.delegate.Idelegate;

public class DialogUtils {

	public static void confirm(final Context context, int resource, final Idelegate dldelegate, final int where) {

		final AlertDialog.Builder builder = new AlertDialog.Builder(context);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layoutView = inflater.inflate(resource, null);
		builder.setView(layoutView);
		final Idelegate delegate = dldelegate;
		final Dialog dialog = builder.create();
		dialog.setCanceledOnTouchOutside(false);
		Button cancel = (Button) layoutView.findViewById(R.id.dialogSupport_cancel);
		if (cancel != null)
			cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					if (dialog != null && dialog.isShowing())
						dialog.dismiss();

					delegate.callBack(-1, where);
				}
			});

		Button ok = (Button) layoutView.findViewById(R.id.dialogSupport_ok);
		if (ok != null)
			ok.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					delegate.callBack(0, where);
					dialog.dismiss();

				}
			});

		dialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface arg0) {
				delegate.callBack(1, where);
				dialog.dismiss();

			}
		});
		dialog.show();

	}

	public static OnClickListener dismisDialog(final Dialog dialog) {

		OnClickListener clickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (dialog != null && dialog.isShowing())
					dialog.dismiss();

			}
		};
		return clickListener;
	}
	
	public static void supportDialog(Activity activity)
    {
             Dialog dialog = new Dialog(activity.getApplicationContext());
            
            dialog.show();
    }

	public static Dialog datePicker(Context context, final Idelegate idelegate) {

		OnDateSetListener callBack = new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker arg0, int year, int monofyear, int dayofmonth) {
				Calendar c = Calendar.getInstance();

				c.set(year, monofyear, dayofmonth);
				idelegate.callBack(c, 0);

			}
		};

		DatePickerDialog dialog = new DatePickerDialog(context, callBack, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar
				.getInstance().get(Calendar.DAY_OF_MONTH));

		dialog.show();
		return dialog;
	}
}
