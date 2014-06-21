package com.telpoo.clip360.net;

import java.util.ArrayList;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.telpoo.clip360.db.Dbsupport;
import com.telpoo.clip360.object.CatOj;
import com.telpoo.clip360.object.VideoOj;
import com.telpoo.clip360.utils.Defi;
import com.telpoo.frame.net.BaseNetSupport;
import com.telpoo.frame.net.BaseNetSupportBeta;
import com.telpoo.frame.object.BaseObject;

public class NetSupport {
	public static ArrayList<BaseObject> getCategory() throws JSONException {
		
		String res= BaseNetSupportBeta.getInstance().method_GET(Defi.URL_CATEGORY);
		
		
		//String res = BaseNetSupport.method_GET(Defi.URL_CATEGORY, null);
		if (res == null)
			return null;
		ArrayList<BaseObject> ojRes = new ArrayList<BaseObject>();

		JSONArray root = new JSONArray(res);

		for (int i = 0; i < root.length(); i++) {
			JSONObject joj = root.getJSONObject(i);
			BaseObject oj = new BaseObject();

			oj.set(CatOj.CID, joj.getString(CatOj.CID));
			oj.set(CatOj.CNAME, joj.getString(CatOj.CNAME));
			ojRes.add(oj);

		}
		return ojRes;
	}

	public static ArrayList<BaseObject> getVideoCategory(String CID, String page) throws JSONException {

		String url = Defi.URL_VIDEOS + "?" + (CID != null ? "c=" + CID : "") + (page != null ? "&p=" + page : "");
		//String res = BaseNetSupport.method_GET(url, null);
		String res=BaseNetSupportBeta.getInstance().method_GET(url);
		ArrayList<BaseObject> Ojres = json2VideoOj(res);
		if (CID == null && "1".equalsIgnoreCase(page)&&Ojres!=null&& Ojres.size()>0) {
			Dbsupport.addHome(Ojres);
		}
		return Ojres;

	}
	

	public static ArrayList<BaseObject> search(String key, String page) throws JSONException {

		String url = Defi.URL_SEARCH + "?" + (key != null ? "q=" + key : "") + (page != null ? "&p=" + page : "");
		String res = BaseNetSupport.method_GET(url, null);

		return json2VideoOj(res);

	}

	private static ArrayList<BaseObject> json2VideoOj(String res) throws JSONException {
		ArrayList<BaseObject> ojres = new ArrayList<BaseObject>();
		if (res == null)
			return null;
		JSONArray root = new JSONArray(res);

		for (int i = 0; i < root.length(); i++) {
			JSONObject joj = root.getJSONObject(i);
			BaseObject oj = new BaseObject();
			String name = joj.getString(VideoOj.VNAME);
			// String s1 =name;
			// byte[] bytes;
			// String s2=name;
			// try {
			// bytes = s1.getBytes("UTF-8");
			// s2 = new String(bytes, "UTF-8");
			// } catch (UnsupportedEncodingException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

			name = StringEscapeUtils.unescapeHtml4(name);

			oj.set(VideoOj.VNAME, name);
			oj.set(VideoOj.VID, joj.getString(VideoOj.VID));
			oj.set(VideoOj.VTIME, joj.getString(VideoOj.VTIME));
			oj.set(VideoOj.VVIEW, joj.getString(VideoOj.VVIEW));
			oj.set(VideoOj.VYTID, joj.getString(VideoOj.VYTID));

			ojres.add(oj);
		}

		return ojres;

	}
}
