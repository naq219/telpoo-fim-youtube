/**
 */
package com.telpoo.frame.net;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.telpoo.frame.utils.FileSupport;
import com.telpoo.frame.utils.Mlog;

/**
 * 
 * @author NAQ
 * 
 */
public class BaseNetSupportBeta {
	protected static String TAG = BaseNetSupportBeta.class.getSimpleName();
	private String contentType;
	private String userAgent;
	private int connectTimeout;
	private int soTimeout;
	private String authorization;
	private int numberRetry = 3;
	private boolean isInited = false;

	//private volatile static BaseNetSupportBeta instance;
	private  static BaseNetSupportBeta instance;
	/** Returns singleton class instance */
//	public static BaseNetSupportBeta getInstance() {
//		if (instance == null) {
//			synchronized (ImageLoader.class) {
//				if (instance == null) {
//					instance = new BaseNetSupportBeta();
//				}
//			}
//		}
//		return instance;
//	}
	
	
	public static BaseNetSupportBeta getInstance() {
	if (instance == null) {
		
			if (instance == null) {
				instance = new BaseNetSupportBeta();
			}
		
	}
	return instance;
}
	

	private BaseNetSupportBeta() {

	}

	public void init(NetConfig netConfig) {
		connectTimeout = netConfig.getConnectTimeout();
		soTimeout = netConfig.getSoTimeout();
		authorization = netConfig.getAuthorization();
		contentType = netConfig.getContentType();
		userAgent = netConfig.getUserAgent();
	}

	private HttpClient myHttpClient() {
		HttpParams params = new BasicHttpParams();
		params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
				HttpVersion.HTTP_1_1);
		HttpConnectionParams.setConnectionTimeout(params, connectTimeout);
		HttpConnectionParams.setSoTimeout(params, soTimeout);
		HttpClient client = new DefaultHttpClient(params);
		return client;
	}

	public String method_GET(String url) {
		int retryCount = 0;
		do {
			try {
				URL myUrl = new URL(url);

				Mlog.D(TAG + "- method_GET -URl:" + myUrl);

				HttpURLConnection conn = (HttpURLConnection) myUrl
						.openConnection();
				conn.setConnectTimeout(connectTimeout);
				conn.setReadTimeout(soTimeout);
				if (authorization != null)
					conn.setRequestProperty("Authorization", authorization);

//				conn.setRequestProperty("Content-Type", contentType);
//				conn.setRequestProperty("User-Agent", userAgent);

				String jsonContent = FileSupport.readFromInputStream(conn
						.getInputStream());
				Mlog.D(TAG + "- method_GET - json result=" + jsonContent);

				conn.disconnect();
				return jsonContent;
			} catch (FileNotFoundException ex) {
				Mlog.E(TAG
						+ "658345234 NetworkSupport - getNetworkData - FileNotFoundException = "
						+ ex.getMessage());

			} catch (Exception ex) {
				Mlog.E(TAG
						+ "789345564 NetworkSupport - getNetworkData - Exception = "
						+ ex.getMessage());
			}
		} while (++retryCount < numberRetry);

		return null;
	}


	public String method_POST(String url, String bodySend) {

		int retryCount = 0;

		do {
			try {

				URL myUrl = new URL(url);
				HttpClient client = myHttpClient();

				HttpConnectionParams.setConnectionTimeout(client.getParams(),
						connectTimeout);
				HttpResponse response;

				InputStream in = null;
				try {
					HttpPost post = new HttpPost(myUrl.toURI());

					StringEntity se = new StringEntity(bodySend, "UTF8");
					Mlog.D(TAG + "-method_POST - url=" + myUrl);
					Mlog.D(TAG + "-method_POST - json sent=" + bodySend);

					se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
							contentType));

					se.setContentEncoding(HTTP.UTF_8);

					post.setEntity(se);
					response = client.execute(post);

					/* Checking response */
					if (response != null) {
						in = response.getEntity().getContent(); // Get the data
					}

				} catch (Exception e) {
					Mlog.E(TAG + "576237 method_POST " + e.toString());
					return null;
				}
				String jsonContent = FileSupport.readFromInputStream(in);
				Mlog.D("method_POST - response: " + jsonContent);
				return jsonContent;
			} catch (FileNotFoundException ex) {
				Mlog.E("method_POST - getNetworkData - FileNotFoundException = "
						+ ex.getMessage());
				return null;

			} catch (Exception ex) {
				Mlog.E("method_POST - getNetworkData - Exception = "
						+ ex.getMessage());
				return null;
			}
		} while (++retryCount < numberRetry);

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

		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
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

	// public static String uploadImgur(String path, String clientId) {
	// try {
	// File file = new File(path);
	// final String upload_to = "https://api.imgur.com/3/upload.json";
	// String API_key = "27905d84c9ec40a";
	// if (clientId != null)
	// API_key = clientId;
	// HttpClient httpClient = new DefaultHttpClient();
	// HttpContext localContext = new BasicHttpContext();
	// HttpPost httpPost = new HttpPost(upload_to);
	// httpPost.setHeader("Authorization", "Client-ID " + API_key);
	//
	// final MultipartEntity entity = new
	// MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
	// entity.addPart("image", new FileBody(file));
	// entity.addPart("key", new StringBody(API_key));
	// entity.addPart("description", new StringBody("test upimgae"));
	// httpPost.setEntity(entity);
	// final HttpResponse response = httpClient.execute(httpPost, localContext);
	// final String response_string =
	// EntityUtils.toString(response.getEntity());
	// // return response_string;
	// final JSONObject json = new JSONObject(response_string);
	// String link = json.getJSONObject("data").getString("link");
	// // link = "http://i.imgur.com/"+link+"l.png";
	// return link;
	// } catch (Exception e) {
	// e.printStackTrace();
	// return null;
	// }
	//
	// }

	// final DefaultHttpClient client = new DefaultHttpClient();
	// HttpHost proxy = new HttpHost("127.0.0.1", 8888, "http");
	// HttpHost target = new HttpHost("www.google.com", 443, "https");
	// client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
	// HttpProtocolParams
	// .setUserAgent(client.getParams(),
	// "Mozilla/5.0 (Windows NT 5.1; rv:19.0) Gecko/20100101 Firefox/19.0");
	// final HttpGet get = new HttpGet("/");
	//
	// HttpResponse response = client.execute(target, get);

}
