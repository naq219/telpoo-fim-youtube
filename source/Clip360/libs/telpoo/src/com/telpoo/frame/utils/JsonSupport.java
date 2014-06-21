package com.telpoo.frame.utils;

import org.json.JSONException;
import org.json.JSONObject;

import com.telpoo.frame.object.BaseObject;

public class JsonSupport {
	private static String TAG=JsonSupport.class.getSimpleName();
	
	protected static String BaseObject2Json(BaseObject baseObject, String[] keys) {
		JSONObject jSend = new JSONObject();
		try {
			for (String value : keys) {
				if (baseObject.get(value) != null)
					jSend.put(value, baseObject.get(value));

			}

		} catch (Exception e) {
			Mlog.E(TAG+"-putObjectToJson =6523f3= " + e);
			return "";
		}
		return jSend.toString();
	}
	
	public static BaseObject Json2BaseObject(String value, String[] keys){
		JSONObject jsonObject;
		BaseObject baseObject=new BaseObject();
		try {
			jsonObject=new JSONObject(value);
			for (String key : keys) {
				if(jsonObject.has(key))
				{
					
					String valuekey=jsonObject.getString(key);
					baseObject.set(key, valuekey);
				}
				
			}
		} catch (JSONException e) {
			Mlog.E("=672345=parseJsonToBaseObject="+e);
			e.printStackTrace();
		}
		return baseObject;
	}
	

}
