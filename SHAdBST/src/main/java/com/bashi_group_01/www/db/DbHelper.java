package com.bashi_group_01.www.db;

import com.bashi_group_01.www.activity.Welcome;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * 保存本地的历史记录的数据库
 * @author share
 *
 */
public class DbHelper extends SQLiteOpenHelper {

	public final static String DB_NAME = "Feed_db.db";
	public final static int VERSION = 1;

	public DbHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = "create table if not exists "
				+ Welcome.userName
				+ " (_id integer primary key autoincrement,CapitalNumber varchar(20) not null,Description varchar(20) not null,NowTime varchar(20) not null,State varchar(20) not null);";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}
}
