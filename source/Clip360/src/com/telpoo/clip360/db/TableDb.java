package com.telpoo.clip360.db;

import java.io.File;

import com.telpoo.clip360.object.CatOj;
import com.telpoo.clip360.object.VideoOj;


/**
 * @author naq
 *
 */
public class TableDb {
	public static String pathDbName= "db.sqlite";
	
	public static String[] tables = { "favorite","category","homevideo"};
	public static String[][] keys = {VideoOj.keys_db,CatOj.keys,VideoOj.keys_db};
	//public static String[][] keys = {{"vid","vname","rate"},{"id","class"}};
	public static String TB_FAVORITE=tables[0];
	public static String TB_CATEGORY=tables[1];
	public static String TB_HOME=tables[2];

}
