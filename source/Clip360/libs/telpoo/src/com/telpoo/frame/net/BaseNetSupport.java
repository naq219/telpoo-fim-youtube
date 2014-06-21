/**
 */
package com.telpoo.frame.net;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
//import org.apache.http.entity.mime.HttpMultipartMode;
//import org.apache.http.entity.mime.MultipartEntity;
//import org.apache.http.entity.mime.content.FileBody;
//import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.telpoo.frame.utils.Cons.NetConfig1;
import com.telpoo.frame.utils.FileSupport;
import com.telpoo.frame.utils.Mlog;

/**
 * 
 * @author NAQ
 * 
 */
public class BaseNetSupport implements NetConfig1 {
	protected static String TAG = BaseNetSupport.class.getSimpleName();

	public class NetData {
		public NetData() {

		}

		public boolean code;
		public String msg;
		public int pages;

		public ArrayList<?> data;
	}

	private static HttpClient myHttpClient() {
		HttpParams params = new BasicHttpParams();
		params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, SO_TIMEOUT);
		HttpClient client = new DefaultHttpClient(params);
		return client;
	}


	
	public static String method_GET(String url, List<String> value) {
		
		url=	url.replace(" ", "%20");
		
		int retryCount = 0;
		do {
			try {
				URL myUrl;
				if (value != null) {

					String query = "";
					for (String mstring : value) {
						query += mstring + "/";
					}
					myUrl = new URL(url + query);
				} else {
					myUrl = new URL(url);
				}
				Mlog.D(TAG + "- method_GET -URl:" + myUrl);

				HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();
				conn.setConnectTimeout(CONNECTION_TIMEOUT);
				conn.setReadTimeout(SO_TIMEOUT);
				if (AUTHORIZATION_TYPE != null)
					conn.setRequestProperty("Authorization", AUTHORIZATION_TYPE);

				conn.setRequestProperty("Content-Type", CONTENT_TYPE);
				//conn.setRequestProperty("User-Agent","Apple-iPhone/");

				String jsonContent = FileSupport.readFromInputStream(conn.getInputStream());
				Mlog.D(TAG + "- method_GET - json result=" + jsonContent);

				conn.disconnect();
				return jsonContent;
			} catch (FileNotFoundException ex) {
				Mlog.E(TAG + "658345234 NetworkSupport - getNetworkData - FileNotFoundException = " + ex.getMessage());

			} catch (Exception ex) {
				Mlog.E(TAG + "789345564 NetworkSupport - getNetworkData - Exception = " + ex.getMessage());
			}
		} while (++retryCount < NUMBER_OF_RETRY);

		return null;
	}
	
	

	

	/*
	 * protected static NetData parseResponseToNetData(String response, String[]
	 * keys) { NetData netData = new NetData(); if (response == null ||
	 * response.length() == 0) { netData.code = false; netData.msg =
	 * HomeActivity.str_connect_error;
	 * 
	 * return netData; } return BaseNetSupport.ParseJson(response, keys);
	 * 
	 * }
	 */

	public static boolean isNetworkAvailable(Context context) {

		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}

		}
		return false;
	}

//	public static String uploadImgur(String path, String clientId) {
//		try {
//			File file = new File(path);
//			final String upload_to = "https://api.imgur.com/3/upload.json";
//			String API_key = "27905d84c9ec40a";
//			if (clientId != null)
//				API_key = clientId;
//			HttpClient httpClient = new DefaultHttpClient();
//			HttpContext localContext = new BasicHttpContext();
//			HttpPost httpPost = new HttpPost(upload_to);
//			httpPost.setHeader("Authorization", "Client-ID " + API_key);
//
//			final MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
//			entity.addPart("image", new FileBody(file));
//			entity.addPart("key", new StringBody(API_key));
//			entity.addPart("description", new StringBody("test upimgae"));
//			httpPost.setEntity(entity);
//			final HttpResponse response = httpClient.execute(httpPost, localContext);
//			final String response_string = EntityUtils.toString(response.getEntity());
//			// return response_string;
//			final JSONObject json = new JSONObject(response_string);
//			String link = json.getJSONObject("data").getString("link");
//			// link = "http://i.imgur.com/"+link+"l.png";
//			return link;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//
//	}
	
	
	
//	final DefaultHttpClient client = new DefaultHttpClient();
//	HttpHost proxy = new HttpHost("127.0.0.1", 8888, "http");
//	HttpHost target = new HttpHost("www.google.com", 443, "https");
//	client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
//	HttpProtocolParams
//	        .setUserAgent(client.getParams(),
//	                "Mozilla/5.0 (Windows NT 5.1; rv:19.0) Gecko/20100101 Firefox/19.0");
//	final HttpGet get = new HttpGet("/");
//
//	HttpResponse response = client.execute(target, get);

}
