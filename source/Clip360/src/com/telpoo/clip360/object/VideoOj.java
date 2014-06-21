package com.telpoo.clip360.object;

import com.telpoo.frame.object.BaseObject;

public class VideoOj extends BaseObject {

	public static final String keys[] = { "vid", "vname", "vytid", "vtime", "vview","favorite" };
	public static final String keys_db[] = { "primarykey_vid", "vname", "vytid", "vtime", "vview","favorite"  };

	public static final String VID = keys[0];
	public static final String VNAME = keys[1];
	public static final String VYTID = keys[2];
	public static final String VTIME = keys[3];
	public static final String VVIEW = keys[4];
	public static final String FAVORITE = keys[5];

}
