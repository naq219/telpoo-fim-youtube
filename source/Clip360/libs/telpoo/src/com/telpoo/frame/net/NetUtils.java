package com.telpoo.frame.net;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import com.telpoo.frame.utils.Cons.NetConfig1;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


public class NetUtils implements NetConfig1 {
	
	public static boolean download(URL url, OutputStream outputStream) {

		HttpGet httpRequest = null;
		try {
			httpRequest = new HttpGet(url.toURI());
		} catch (URISyntaxException e) {
			Log.i("NAQ", "Downloader - SERVICE_JOB_COMMAND_DOWNLOAD_FILE - URISyntaxException e = " + e);
			return false;
		}
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, SO_TIMEOUT);
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpClient httpclient = new DefaultHttpClient(params);
		int numberOfRetry = NUMBER_OF_RETRY;
		while(numberOfRetry-->=0) {
			try {
				HttpResponse response = (HttpResponse) httpclient
						.execute(httpRequest);

				HttpEntity entity = response.getEntity();

				BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);

				InputStream instream = bufHttpEntity.getContent();
				byte[] data = new byte[FILE_BUFFER_SIZE];
				int bytesRead = 0;
				while ((bytesRead = instream.read(data)) > 0) {
					outputStream.write(data, 0, bytesRead);
				}
				return true;
			} catch (Exception e) {				
				Log.i("nth", "Downloader - SERVICE_JOB_COMMAND_DOWNLOAD_FILE - Exception e = " + e);
				return false;
			}
		}
		return false;
	}
	
	public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
	}
	
}