package com.example.smsmanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class InterceptDBHelper extends SQLiteOpenHelper
{

	public InterceptDBHelper(Context context)
	{
		super(context, "intercept.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL("CREATE TABLE blacknumber (_id integer primary key autoincrement, name varchar(10) , number varchar(20) , sensitive_word varchar(100))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// TODO Auto-generated method stub

	}

}
