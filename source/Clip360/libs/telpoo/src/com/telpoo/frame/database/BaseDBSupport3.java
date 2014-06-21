package com.telpoo.frame.database;

import java.util.ArrayList;
import java.util.concurrent.SynchronousQueue;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.Mlog;
import com.telpoo.frame.utils.TimeUtils;

public class BaseDBSupport3 extends SQLiteOpenHelper {
	private static String TAG = "BaseDBSupport";

	private static String dbName = "BaseDBSupport.db";
	private static int dbVersion = 1;
	private static boolean tableCreated = false;
	private static BaseDBSupport3 instance = null;
	private static Context _context = null;

	protected static SQLiteDatabase mSqliteDatabase = null;

	public static String[] tables = null;
	private static String[][] keys = null;

	protected BaseDBSupport3(Context context) {
		super(context, dbName, null, dbVersion);
		mSqliteDatabase = this.getWritableDatabase();

	}

	public SQLiteDatabase getSQLiteDatabase() {
		return mSqliteDatabase;
	}

	public static boolean init(String[] tables1, String[][] keys1, Context context, String db_Name, Integer db_Version) {
		tables = tables1;
		keys = keys1;
		dbName = db_Name;
		dbVersion = db_Version;
		_context = context;

		if (context == null)
			return false;
		if (instance == null) {
			instance = new BaseDBSupport3(context.getApplicationContext());
		}

		if (!isTableExist())
			return createTable();

		return true;

	}

	public static BaseDBSupport3 getInstance(Context context) {
		if (context == null)
			return null;
		if (instance == null) {
			instance = new BaseDBSupport3(context.getApplicationContext());
		}
		return instance;
	}

	public static void openDB() {
		mSqliteDatabase = instance.getWritableDatabase();
	}

	public static void openDBRead() {
		mSqliteDatabase = instance.getReadableDatabase();
	}

	public static void closeDB() {
		mSqliteDatabase.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	private static Boolean isTableExist() {
		return tableCreated;
	}

	private static boolean createTable() {
		try {

			openDB();
			mSqliteDatabase.execSQL("PRAGMA encoding = 'UTF-8'");
			String query = "";
			for (int i = 0; i < tables.length; i++) {
				
				query = "CREATE TABLE IF NOT EXISTS " + tables[i] + "(";
				
				//ArrayList<String> listPrimaryKey=new ArrayList<String>();
				
				String prikey="";
				for (String key : keys[i]) {
					if(key.indexOf("primarykey_")!=-1){
						key=key.substring(11);
					
						prikey=prikey+" "+key+",";
					}
					query += key + " TEXT" + ",";

				}
				
				if(prikey.length()>0){
					 prikey="PRIMARY KEY ("+ prikey.substring(0, prikey.length() - 1)+")";
					 query = query+prikey + ")";
				}
				query = query.substring(0, query.length() - 1) + ")";
				mSqliteDatabase.execSQL(query);
			}

			Mlog.D("createTable - query = " + query);
			closeDB();
			tableCreated = true;
		} catch (Exception ex) {
			Mlog.E("-createTable=542734=" + ex);
			return false;
		}
		return true;
	}

	public void upgradeDB() {

		try {
			SQLiteDatabase db = this.getWritableDatabase();
			for (String tableName : tables) {
				db.execSQL("DROP TABLE IF EXISTS " + tableName);
			}
			db.close();
			createTable();
		} catch (Exception ex) {
			Mlog.E("-upgradeDB=57452=" + ex);
		}
	}

	// success
	protected synchronized static Cursor getAll(String tableName, String[] keys, String keyid, String id) {
		try {
			openDB();
			String query = "SELECT rowid,* ";
			/*
			 * for (String key : keys) { query += "," + key; }
			 */
			query += " FROM " + tableName;
			if (keyid != null) {
				query += " WHERE " + keyid + "='" + id + "'";
			}
			Mlog.D("getAll - query=" + query);
			Cursor cur = mSqliteDatabase.rawQuery(query, new String[] {});
			Mlog.E("getAll - cur.getCount()=" + cur.getCount());
			if (cur.getCount() == 0) {
				cur.close();
				mSqliteDatabase.close();
				return null;
			}
			closeDB();
			return cur;
		} catch (Exception ex) {
			// ex.printStackTrace();
			Mlog.E("getAll =9769785=" + ex.toString());
		}
		return null;
	}

	// success
	/*
	 * @
	 */
//	public synchronized static boolean addList(ArrayList<BaseObject> pl, String tableName, String idKey) {
//		if (pl == null)
//			return false;
//		try {
//
//			openDB();
//			BaseObject v1 = null;
//
//			String[] columns = { idKey };
//			String selection = idKey + " = ?";
//
//			for (int i = 0; i < pl.size(); i++) {
//				v1 = (BaseObject) pl.get(i);
//				int cou = 0;
//				Cursor cur = null;
//				if (idKey != null) {
//
//					String[] selectionArgs = { v1.get(idKey) };
//					cur = query(tableName, selection, selectionArgs, columns, null);
//					cou = cur.getCount();
//				}
//				if (cou == 0) {
//					long resultInsertDb = mSqliteDatabase.insert(tableName, null, v1.getParams());
//					Mlog.D("addList - resultInsertDb:" + resultInsertDb);
//					if (resultInsertDb == -1) {
//						return false;
//					}
//
//				} else {
//
//					long resultupdateDb = mSqliteDatabase.update(tableName, v1.getParams(), idKey + "=?", new String[] { String.valueOf(v1.get(idKey)) });
//					Mlog.D("addList - resultupdateDb:" + resultupdateDb);
//				}
//				cur.close();
//			}
//			mSqliteDatabase.close();
//		} catch (Exception ex) {
//			Mlog.E(TAG + "-addList- =87908989=" + ex);
//			return false;
//		}
//		return true;
//	}

	// success
	public synchronized static boolean addToTable(ArrayList<BaseObject> pl, String tableName) {
		if (pl == null)
			return false;
		try {

			openDB();
			BaseObject v1 = null;

			for (int i = 0; i < pl.size(); i++) {
				v1 = (BaseObject) pl.get(i);

				long resultInsertDb = mSqliteDatabase.insert(tableName, null, v1.getParams());
				Mlog.D("DBSuppost - resultInsertDb:" + resultInsertDb);
				if (resultInsertDb == -1) {
					return false;
				}

			}
			closeDB();
		} catch (Exception ex) {
			Mlog.E(TAG + "-addListNoCheck- 44676634==" + ex);
			return false;
		}
		return true;
	}

	protected static Cursor query(String tableName, String selection, String[] selectionArgs, String[] columns, String sortOrder) {
		/*
		 * The SQLiteBuilder provides a map for all possible columns requested
		 * to actual columns in the database, creating a simple column alias
		 * mechanism by which the ContentProvider does not need to know the real
		 * column names
		 */

		try {
			openDB();
			SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
			builder.setTables(tableName);
			Cursor cursor = builder.query(mSqliteDatabase, columns, selection, selectionArgs, null, null, sortOrder);
			if (cursor == null) {
				return null;
			}
			closeDB();
			return cursor;
		} catch (SQLiteException ex) {
			Mlog.E(" query - query - SQLiteException = " + ex.getMessage());
		}

		return null;
	}

	protected static int delete(String tableName, String selection, String[] selectionArgs) {
		String log="delete db: tableName="+tableName+"selection=";
		if(selection!=null)
		log=log + selection ;
		
		if(selection!=null)
			log=log + selection ;
		try {
			openDB();
			int deletedRows;
			if(selection!=null)
			 deletedRows = mSqliteDatabase.delete(tableName, selection + " IN (?)", selectionArgs);
			else deletedRows = mSqliteDatabase.delete(tableName,null, null);
			closeDB();
			return deletedRows;

		} catch (SQLiteException ex) {
			Mlog.E("delete" + " - query - SQLiteException = " + ex.getMessage());
		}
		return 0;
	}

	public static synchronized int count(String tableName, String selection, String[] selectionArgs) {
		try {
			openDB();
			String sql = "SELECT count(*) FROM " + tableName;
			Cursor mcursor = mSqliteDatabase.rawQuery(sql, selectionArgs);
			mcursor.moveToFirst();
			int icount = mcursor.getInt(0);
			mcursor.close();
			closeDB();
			return icount;
		} catch (SQLiteException ex) {
			Mlog.E(TAG + " - count - SQLiteException =9080238234= " + ex.getMessage());
		}
		return 0;
	}

	public static synchronized Cursor get(String query) {
		Mlog.D(TAG+" -get:query="+query);
		
		long timeS = TimeUtils.getTimeMillis();
		openDB();
		long timeS1 = TimeUtils.getTimeMillis();
		Mlog.T("sp- step 1:" + (timeS1 - timeS));
		Cursor cur = mSqliteDatabase.rawQuery(query, new String[] {});
		long timeS2 = TimeUtils.getTimeMillis();
		Mlog.T("sp- step 2:" + (timeS2 - timeS1));
		// if (cur.getCount() == 0) {
		//
		// cur.close();
		// closeDB();
		// return null;
		// }
		// closeDB();
		long timeS3 = TimeUtils.getTimeMillis();
		Mlog.T("sp- step 3:" + (timeS3 - timeS2));
		return cur;

	}

	public static synchronized ArrayList<BaseObject> rawQuery(String query) {
		return cursorToOj(get(query));

	}

	protected synchronized static ArrayList<BaseObject> cursorToOj(Cursor cur) {

		ArrayList<BaseObject> params = new ArrayList<BaseObject>();
		try {

			if (cur != null) {


				Long firstT = TimeUtils.getTimeMillis();
				//boolean ifMove = cur.moveToFirst();
				
			//	if (true) {
				
					while (cur.moveToNext()) {
						
						ContentValues vl = new ContentValues();
						DatabaseUtils.cursorRowToContentValues(cur, vl);
						
						BaseObject oj = new BaseObject();
						oj.setParams(vl);
						params.add(oj);
						
						
					} ;
					
					Mlog.T(params.size()+"speed cursorToOj:" + (TimeUtils.getTimeMillis() - firstT));
					
					
				//}
				cur.close();
				
				return params;
			}
		} catch (Exception ex) {
			Mlog.E(TAG + " cursorToOj:" + ex);
		}
		return params;
	}

	public static synchronized Boolean update(BaseObject v1, String tableName, String idKey) {
		int count = 0;
		openDB();
		count = mSqliteDatabase.update(tableName, v1.getParams(), idKey + "=?", new String[] { String.valueOf(v1.get(idKey)) });
		closeDB();

		if (count == 0)
			return false;
		return true;

	}

	// success
	protected synchronized static ArrayList<ContentValues> getAllInTableToCV(String TableName, String[] keys, String keyid, String id) {
		ArrayList<ContentValues> params = new ArrayList<ContentValues>();
		try {

			Cursor cur = getAll(TableName, keys, keyid, id);
			if (cur != null) {
				if (cur.moveToFirst()) {
					do {
						ContentValues param = new ContentValues();
						for (String key : keys) {
							param.put(key, cur.getString(cur.getColumnIndex(key)));
						}
						params.add(param);

					} while (cur.moveToNext());
				}
				cur.close();

				return params;
			}
		} catch (Exception ex) {

		}
		return params;
	}

	// success
	public synchronized static ArrayList<BaseObject> getAllOfTable(String tableName, String[] keys) {

		ArrayList<ContentValues> params = getAllInTableToCV(tableName, keys, null, null);
		ArrayList<BaseObject> baseOjs = new ArrayList<BaseObject>();
		for (int i = 0; i < params.size(); i++) {
			BaseObject baseOj = new BaseObject();
			baseOj.setParams(params.get(i));
			baseOjs.add(baseOj);
		}
		return baseOjs;
	}

	/*
	 * 
	 */
//	public synchronized static boolean addToTable(ArrayList<BaseObject> pl, String tableName, boolean isCheck, String ID) {
//		if (isCheck)
//			return addList(pl, tableName, ID);
//		return addListNoCheck(pl, tableName);
//
//	}

	public synchronized static int removeAllInTable(String tableName) {
		return delete(tableName, null, null);
	}

	public synchronized static boolean deleteRowInTable(String tableName, String keyID, String valueID) {
		return delete(tableName, keyID, new String[] { valueID }) > 0;
	}
	
	public synchronized static ArrayList<BaseObject> selectRow(String table,String key,String value){
		String query="select * from "+table+" where "+key+"='"+value+"'";
		return cursorToOj(get(query));
	}

}

// ContentValues param = new ContentValues();
// for (String key : keys) {
//
// int columm = cur.getColumnIndex(key);
// if (columm != -1)
// param.put(key, cur.getString(columm));
// }