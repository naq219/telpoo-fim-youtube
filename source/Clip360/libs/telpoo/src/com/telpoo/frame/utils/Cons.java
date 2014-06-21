package com.telpoo.frame.utils;


public class Cons {

	public class Defi {
		public static final String SPR_GET_FALL = "get reference fall";
		public static final String SPR_NAME = "TAG_FM_TEST";

	}

	public interface NetConfig1 {

		public static final int FILE_BUFFER_SIZE = 8192;
		public static final int PAGE_SIZE = 5;
		public final static int NUMBER_OF_RETRY = 3;
		public static final int CONNECTION_TIMEOUT = 15000;
		public static final int SO_TIMEOUT = 15000;
		public static final String CONTENT_TYPE = "UTF-8";
		public final String AUTHORIZATION_TYPE = null;

		public static final int CODE_SUCCESS = 0;
		public static final int CODE_FAIL = -1;

		public static final String CODE = "ok";
		public static final String MESSAGE = "message";

	}

	public class Spr {
		public static final String KEY_PASS_SAVE = "KEY_PASS_SAVE";
		public static final String KEY_USER_SAVE = "KEY_USER_SAVE";
		public static final String SAVE_LOGIN = "save_login";
		public static final String KEY_TICK_MENU = "KEY_TICK_MENU";
		public static final String KEY_PHONE = "KEY_PHONE";
	}

}
