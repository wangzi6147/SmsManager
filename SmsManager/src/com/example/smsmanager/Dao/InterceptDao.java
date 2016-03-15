package com.example.smsmanager.Dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.smsmanager.bean.InterceptInfoBean;
import com.example.smsmanager.db.InterceptDBHelper;

public class InterceptDao
{
	private InterceptDBHelper interceptDBHelper;

	public InterceptDao(Context context)
	{
		interceptDBHelper = new InterceptDBHelper(context);
	}

	public List<InterceptInfoBean> getAll()
	{
		SQLiteDatabase db = interceptDBHelper.getReadableDatabase();
		List<InterceptInfoBean> beans = new ArrayList<InterceptInfoBean>();
		if (db.isOpen())
		{
			Cursor cursor = db.rawQuery(
					"select * from blacknumber", null);
			while (cursor.moveToNext())
			{
				InterceptInfoBean bean = new InterceptInfoBean();
				bean.setName(cursor.getString(1));
				bean.setNumber(cursor.getString(2));
				bean.setSensitiveWord(cursor.getString(3));
				beans.add(bean);
			}

			cursor.close();
			db.close();
		}
		return beans;
	}

	public Boolean add(InterceptInfoBean bean)
	{
		SQLiteDatabase writeDB = interceptDBHelper
				.getWritableDatabase();
		Cursor cursor = writeDB.rawQuery(
				"select * from blacknumber where name=? and number =? and sensitive_word=?", new String[]
						{  bean.getName() , bean.getNumber() , bean.getSensitiveWord() });
		if (writeDB.isOpen() && !cursor.moveToNext())
		{
			writeDB.execSQL("insert into blacknumber (number , name , sensitive_word) values (? , ? , ?)",
					new String[]
					{ bean.getNumber(), bean.getName(),
							bean.getSensitiveWord() });
			writeDB.close();
			System.out.println("添加成功");
			return true;
		}
		else {
			System.out.println("添加失败");
			return false;
		}
	}

	public boolean isContainSensitiveWord(String strSmsContent)
	{

		List<InterceptInfoBean> beans = getAll();
		for (InterceptInfoBean bean : beans)
		{
			if (!bean.getSensitiveWord().equals("")
					&& strSmsContent.contains(bean
							.getSensitiveWord()))
			{
				System.out.println("包含敏感词"
						+ bean.getSensitiveWord());
				return true;
			}
		}

		System.out.println("不包含敏感词");
		return false;

	}

	public boolean isContainNumber(String strNumber)
	{
		boolean bContain = false;
		SQLiteDatabase database = interceptDBHelper
				.getReadableDatabase();
		if (database.isOpen())
		{
			if(strNumber.contains("+86")){

				strNumber = strNumber.replaceAll("\\+86", "");

				System.out.println(strNumber);
			}
			Cursor cursor = database
					.rawQuery("select number from blacknumber where number=?",
							new String[]
							{ strNumber });
			if (cursor.moveToNext())
				bContain = true;

			cursor.close();
			database.close();
		}
		System.out.println("检测号码：" + strNumber + " 是否匹配："
				+ bContain);
		return bContain;
	}
	
	public void del(InterceptInfoBean bean)
	{
		SQLiteDatabase writeDB = interceptDBHelper
				.getWritableDatabase();
		if (writeDB.isOpen())
		{
			writeDB.execSQL("delete from blacknumber where name=? and number =?",
					new String[]
					{ bean.getName(), bean.getNumber()});
			writeDB.close();
			Log.w(null, bean.getName());
			Log.w(null, bean.getNumber());
			System.out.println("删除黑名单号码");
		}
	}
	
	public void delsensitive(InterceptInfoBean bean)
	{
		SQLiteDatabase writeDB = interceptDBHelper
				.getWritableDatabase();
		if (writeDB.isOpen())
		{	

			Log.w(null, bean.getSensitiveWord());
			writeDB.execSQL("delete from blacknumber where sensitive_word=?",
					new String[]
					{  bean.getSensitiveWord()});
			writeDB.close();
			System.out.println("删除敏感词");
		}
	}
}
