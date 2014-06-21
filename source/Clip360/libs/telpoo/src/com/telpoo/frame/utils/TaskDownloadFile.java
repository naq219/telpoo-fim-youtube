package com.telpoo.frame.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import com.telpoo.frame.delegate.Idelegate;

@SuppressLint("NewApi")
public class TaskDownloadFile extends AsyncTask<String, String, String> {
	private static final String TAG = TaskDownloadFile.class.getSimpleName();
	ProgressDialog loadingProgress;
	String title;
	String pathStorage;
	Idelegate delegate;
	String url;
	Context ct;
	String message;
	boolean status=false;

	public TaskDownloadFile(Context ct, String title, String pathStorage, String url, Idelegate delegate) {
		this.ct = ct;
		this.title = title;
		this.pathStorage = pathStorage;
		File file=new File(pathStorage);
		file.getParentFile().mkdirs();
		this.delegate = delegate;
		this.url = url;
	}

	public void startDownload() {
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			ExecutorService modelExecutor = Executors.newFixedThreadPool(5);
			this.executeOnExecutor(modelExecutor, url);
		}
		else
		this.execute(url);
		

	}


	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		loadingProgress = new ProgressDialog(ct);
		loadingProgress.setMessage(title);
		loadingProgress.setIndeterminate(false);
		loadingProgress.setMax(100);
		loadingProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		loadingProgress.setCancelable(false);
		loadingProgress.show();
	}


	@Override
	protected String doInBackground(String... f_url) {
		int count;
		try {
			URL url = new URL(f_url[0]);
			URLConnection conection = url.openConnection();
			conection.connect();
			// getting file length
			int lenghtOfFile = conection.getContentLength();

			// input stream to read file - with 8k buffer
			InputStream input = new BufferedInputStream(url.openStream(), 8192);

			// Output stream to write file
			// OutputStream output = new
			// FileOutputStream("/sdcard/downloadedfile.jpg");
			OutputStream output = new FileOutputStream(pathStorage);
			byte data[] = new byte[1024];

			long total = 0;

			while ((count = input.read(data)) != -1) {
				total += count;
				// publishing the progress....
				// After this onProgressUpdate will be called
				publishProgress("" + (int) ((total * 100) / lenghtOfFile));

				// writing data to file
				output.write(data, 0, count);
			}

			// flushing output
			output.flush();

			// closing streams
			output.close();
			input.close();
			status=true;
			
		} catch (Exception e) {
			Mlog.E(TAG+" "+e);
			message=e.toString();
		}

		return null;
	}

	/**
	 * Updating progress bar
	 * */
	protected void onProgressUpdate(String... progress) {
		// setting progress percentage
		loadingProgress.setProgress(Integer.parseInt(progress[0]));
	}

	/**
	 * After completing background task Dismiss the progress dialog
	 * **/
	@Override
	protected void onPostExecute(String file_url) {
		loadingProgress.dismiss();
		delegate.callBack(status,1 );

	}

}