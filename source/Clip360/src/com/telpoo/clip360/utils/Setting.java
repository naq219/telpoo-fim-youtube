package com.telpoo.clip360.utils;

import android.content.Context;

import com.telpoo.frame.utils.Utils;

public class Setting {
	public class IdTruyen{
		public static final int YTDLK=0;
		
	}
	
	public static void sprIdTruyen(int id, Context context){
		Utils.saveStringSPR("save id truyen", ""+id, context);
	}
	
	public static int getIdTruyen(Context context){
		return Utils.getIntSPR("save id truyen", context);
	}
	
	
	
}
