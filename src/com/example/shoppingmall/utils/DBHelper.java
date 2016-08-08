package com.example.shoppingmall.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static  int DB_VERSION = 4;
	private static String DB_PATH = "/data/data/com.sun.shoppingmall/databases/" ;
	private static  String DB_NAME = "taojinxi.db";
	private SQLiteDatabase myDataBase = null;
	private final Context myContext;
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		this.myContext = context ;
	}
	
	public DBHelper(Context context) {
		this(context, DB_NAME,null ,DB_VERSION);
	}


	public void createDataBase() throws IOException {
		boolean dbExist = checkDataBase();
		if (dbExist) {
			// 数据库已存在，do nothing.
		} else {
			// 创建数据库
			try {
				File dir = new File(DB_PATH);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				File dbf = new File(DB_PATH + DB_NAME);
				if (dbf.exists()) {
					dbf.delete();
				}
				SQLiteDatabase.openOrCreateDatabase(dbf, null);
				// 复制asseets中的db文件到DB_PATH下
				copyDataBase();
			} catch (IOException e) {
				throw new Error("数据库创建失败");
			}
		}
	}
	
	// 检查数据库是否有效
		private boolean checkDataBase() {
			SQLiteDatabase checkDB = null;
			String myPath = DB_PATH + DB_NAME;
			try {
				checkDB = SQLiteDatabase.openDatabase(myPath, null,
						SQLiteDatabase.OPEN_READONLY);
			} catch (SQLiteException e) {
				// database does't exist yet.
			}
			if (checkDB != null) {
				checkDB.close();
			}
			return checkDB != null ? true : false;
		}
	
		private void copyDataBase() throws IOException {
			// Open your local db as the input stream
			InputStream myInput = myContext.getAssets().open("taojinxi.db");
			// Path to the just created empty db
			String outFileName = DB_PATH + DB_NAME;
			// Open the empty db as the output stream
			OutputStream myOutput = new FileOutputStream(outFileName);
			// transfer bytes from the inputfile to the outputfile
			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}
			// Close the streams
			myOutput.flush();
			myOutput.close();
			myInput.close();
		}
		

		@Override
		public synchronized void close() {
			if (myDataBase != null) {
				myDataBase.close();
			}
			super.close();
		}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
