package com.telpoo.frame.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.util.Log;

import com.telpoo.frame.utils.ExecShell.SHELL_CMD;

/** @author Kevin Kowalewski */
public class RootSupport {

	private static String LOG_TAG = RootSupport.class.getName();

	public static boolean isDeviceRooted() {
		boolean res= checkRootMethod1() || checkRootMethod2() || checkRootMethod3();
		Mlog.D(LOG_TAG+" -is rooted="+res);
		return res;
	}

	public static boolean checkRootMethod1() {
		String buildTags = android.os.Build.TAGS;
		return buildTags != null && buildTags.contains("test-keys");
	}

	public static boolean checkRootMethod2() {
        try {
            File file = new File("/system/app/Superuser.apk");
            return file.exists();
        } catch (Exception e){
        	return false;
        }
    }

	public static boolean checkRootMethod3() {
		return new ExecShell().executeCommand(SHELL_CMD.check_su_binary) != null;
	}
}

/** @author Kevin Kowalewski */
 class ExecShell {

	private static String LOG_TAG = ExecShell.class.getName();

	public static enum SHELL_CMD {
		check_su_binary(new String[] { "/system/xbin/which", "su" });

		String[] command;

		SHELL_CMD(String[] command) {
			this.command = command;
		}
	}

	public ArrayList<String> executeCommand(SHELL_CMD shellCmd) {
		String line = null;
		ArrayList<String> fullResponse = new ArrayList<String>();
		Process localProcess = null;
		try {
			localProcess = Runtime.getRuntime().exec(shellCmd.command);
		} catch (Exception e) {
			return null;
		}
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(localProcess.getOutputStream()));
		BufferedReader in = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
		try {
			while ((line = in.readLine()) != null) {
				Log.d(LOG_TAG, "--> Line received: " + line);
				fullResponse.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d(LOG_TAG, "--> Full response was: " + fullResponse);
		return fullResponse;
	}
}