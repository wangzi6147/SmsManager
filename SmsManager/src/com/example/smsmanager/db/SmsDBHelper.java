package com.example.smsmanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SmsDBHelper extends SQLiteOpenHelper {

	public SmsDBHelper(Context context) {
		super(context, "sms.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE sms (" +
				"_id integer primary key autoincrement," +
				" thread_id varchar(10)," +
				" person varchar(5) ," +
				" phoneNumber varchar(20) ," +
				" date varchar(20) ," +
				" date_sent text ,"+
				"protocol varchar(5)," +  
				 "read varchar(5) ," + 
				 "status varchar(5) ," +
				" smsbody varchar(200) ," +
				" type varchar(5)," +
				"reply_path_present varchar(5)," +  
				"subject varchar(20)," + 
				 "service_center varchar(20)," +  
				 "locked varchar(5)" +  
				" )");
		
		db.execSQL("CREATE TABLE smsbackup (" +
				"_id integer primary key autoincrement," +
				" thread_id text," +
				" person text ," +
				" phoneNumber text ," +
				" date text ," +
				" date_sent text ,"+
				"protocol text," +  
				 "read text ," + 
				 "status text ," +
				" smsbody text ," +
				" type text," +
				"reply_path_present text," +  
				"subject text," + 
				 "service_center text," +  
				 "locked text" +  
				" )");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
