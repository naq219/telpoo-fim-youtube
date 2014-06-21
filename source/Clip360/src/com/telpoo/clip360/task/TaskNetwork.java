package com.telpoo.clip360.task;

import java.util.ArrayList;

import org.json.JSONException;

import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.telpoo.clip360.R;
import com.telpoo.clip360.db.Dbsupport;
import com.telpoo.clip360.net.NetSupport;
import com.telpoo.clip360.utils.Defi;
import com.telpoo.frame.model.BaseTask;
import com.telpoo.frame.model.TaskListener;
import com.telpoo.frame.model.TaskParams;
import com.telpoo.frame.net.BaseNetSupport;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.Mlog;

public class TaskNetwork extends BaseTask implements TaskType {
	private static String TAG = TaskNetwork.class.getSimpleName();

	public TaskNetwork(TaskListener taskListener, int taskType, ArrayList<?> list, Context context) {
		super(taskListener, taskType, list, context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Boolean doInBackground(TaskParams... params) {

		switch (taskType) {

		case TASK_GET_FAVORITE:

			dataReturn = Dbsupport.getFavorite();
			return TASK_DONE;

		case TASK_GET_CATEGORY:

			if (!BaseNetSupport.isNetworkAvailable(context)) {
				msg = context.getString(R.string.no_network_try_again);
				return TASK_FAILED;
			}

			return getCategory();

		case TASK_GET_LIST_VIDEO:

			if (!BaseNetSupport.isNetworkAvailable(context)) {
				msg = context.getString(R.string.no_network_try_again);
				return TASK_FAILED;
			}
			String cID = (String) dataFromModel.get(0);
			String page = (String) dataFromModel.get(1);
			return getListVideo("null".equalsIgnoreCase(cID) ? null : cID, "null".equalsIgnoreCase(page) ? null : page);

		case TASK_SEARCH:

			
			if (!BaseNetSupport.isNetworkAvailable(context)) {
				msg = context.getString(R.string.no_network_try_again);
				return TASK_FAILED;
			}
			String key = (String) dataFromModel.get(0);
			String page_search = (String) dataFromModel.get(1);

			return search(key, page_search);

		case TASK_DOWNIMAGE:

			String url = (String) dataFromModel.get(0);
			ImageView iv = (ImageView) dataFromModel.get(1);
			if (url.indexOf("ytimg") != -1) {
				int end = url.lastIndexOf("/");

				String n1 = url.substring(0, end != -1 ? end : url.length() - 1);
				int startq = n1.lastIndexOf("/") + 1;
				if (end != -1 && startq != -1) {

					String cut = url.substring(startq, end);
					// Log.i("telpoo", "11111"+imageUri.substring(startq, end));
					String resYu = BaseNetSupport.method_GET("http://www.youtube.com/oembed?url=http://www.youtube.com/watch?v=" + cut, null);
					if (resYu == null || resYu.length() < 15) {
						url = "http://naq.name.vn/file/youtube-notfound.png";

					}
				}

				ArrayList<Object> ress = new ArrayList<Object>();
				ress.add(url);
				ress.add(iv);
				dataReturn = ress;
				return TASK_DONE;

				// ImageLoader.getInstance().displayImage(url, iv);
				// return true;

			}

		default:
			msg = "fail(1098)";
			return TASK_FAILED;
		}
	}

	private Boolean search(String key, String page_search) {
		ArrayList<BaseObject> Ojres = new ArrayList<BaseObject>();
		try {
			Ojres = NetSupport.search(key, page_search);
			if (Ojres == null) {
				msg = "Lỗi kết nối";
				return TASK_FAILED;
			}
			dataReturn = Ojres;
			return TASK_DONE;
		} catch (JSONException e) {
			msg = Defi.error_connect;
			return TASK_FAILED;

		}

	}

	private Boolean getListVideo(String cID, String page) {
		ArrayList<BaseObject> Ojres = new ArrayList<BaseObject>();
		try {
			Ojres = NetSupport.getVideoCategory(cID, page);

			dataReturn = Ojres;
			return TASK_DONE;
		} catch (JSONException e) {
			msg = Defi.error_connect;
			return TASK_FAILED;

		}

		
	}

	private Boolean getCategory() {
		try {
			ArrayList<BaseObject> ojRes = NetSupport.getCategory();

			if (ojRes == null || ojRes.size() == 0)
				return TASK_FAILED;
			Dbsupport.addMenu(ojRes);
			dataReturn = ojRes;
			return TASK_DONE;
		} catch (JSONException e) {
			Mlog.E(TAG + e);
			msg = com.telpoo.clip360.utils.Defi.error_connect;
			return TASK_FAILED;
		}
	}

}
