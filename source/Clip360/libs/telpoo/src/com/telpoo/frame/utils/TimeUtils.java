package com.telpoo.frame.utils;

import java.util.Calendar;

public class TimeUtils {
	public static Long getTimeMillis() {
		Calendar cal = Calendar.getInstance();
		return cal.getTimeInMillis();
	}

	public static String cal2String(Calendar cal, String format) {
		String format1 = format.toLowerCase();

		String res = format1.replaceAll("dd", "" + cal.get(cal.DAY_OF_MONTH));
		res = res.replaceAll("mm", "" + (cal.get(cal.MONTH) + 1));
		if (format.contains("yyyy"))
			res = res.replaceAll("yyyy", "" + cal.get(cal.YEAR));

		if (format.contains("yy"))
			res = res.replaceAll("yy", "" + cal.get(cal.YEAR));
		return res;
	}

}
