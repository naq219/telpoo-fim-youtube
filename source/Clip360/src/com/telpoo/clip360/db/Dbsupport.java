package com.telpoo.clip360.db;

import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.telpoo.clip360.object.CatOj;
import com.telpoo.clip360.object.VideoOj;
import com.telpoo.frame.database.BaseDBSupport2;
import com.telpoo.frame.object.BaseObject;

public class Dbsupport extends BaseDBSupport2 {

	protected Dbsupport(Context context) {
		super(context);
		
		
	}
	

	
	
	
	/*
	 * thêm 1 đối tượng (là 1 dòng ) vào table favorite
	 * sử dụng hàm Dbsupport.addToTable
	 */
	public static boolean addFv(BaseObject oj) {
		oj.set(VideoOj.FAVORITE, true); // bỏ qua
		ArrayList<BaseObject> ojs = new ArrayList<BaseObject>(); 
		ojs.add(oj);// đưa và arraylist
		boolean isNoRow = Dbsupport.addToTable(ojs, TableDb.TB_FAVORITE); // hàm này trả về true nếu add thành công
		
		if (!isNoRow) { // nếu false nghĩa là có rồi. thực hiện xóa
			//hàm này để xóa trong bảng
			Dbsupport.deleteRowInTable(TableDb.TB_FAVORITE, VideoOj.VID, oj.get(VideoOj.VID));
			//xóa 1số dòng tỏng bảngt bảng favorite mà có điều kiện: trường vid="gì đó"
			
			//delete from favorite where vid='12'
			
		}
		return isNoRow;

	}

	public static ArrayList<BaseObject> getFavorite() {
		
		//hàm này hơi thừa, thậm chí anh chả cần VideoOj.keys;
		return Dbsupport.getAllOfTable(TableDb.TB_FAVORITE, VideoOj.keys);
		// hàm này trả về hết dữ liệu 1 bảng nên nếu bảng nhiều dòng thì ko ổn
	}

	public static ArrayList<BaseObject> getMenu() {
		return Dbsupport.getAllOfTable(TableDb.TB_CATEGORY, CatOj.keys);

	}

	public static void addMenu(ArrayList<BaseObject> ojs) {
		Dbsupport.removeAllInTable(TableDb.TB_CATEGORY); // hàm remove chỉ cần tên bảng là được
		Dbsupport.addToTable(ojs, TableDb.TB_CATEGORY);

	}
	
	public static ArrayList<BaseObject> getHome() {
		return Dbsupport.getAllOfTable(TableDb.TB_HOME, VideoOj.keys);

	}

	public static void addHome(ArrayList<BaseObject> ojs) {
		Dbsupport.removeAllInTable(TableDb.TB_HOME);
		Dbsupport.addToTable(ojs, TableDb.TB_HOME);

	}
	
//	public void hammoi(){
//		SQLiteDatabase sql=getSQLiteDatabase();
//		
//		sql.execSQL(sql)
//	}


}
