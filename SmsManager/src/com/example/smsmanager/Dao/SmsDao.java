package com.example.smsmanager.Dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.smsmanager.AllFinalInfo;
import com.example.smsmanager.bean.SmsInfoBean;
import com.example.smsmanager.db.SmsDBHelper;

public class SmsDao {
	private Context context;
	private List<SmsInfoBean> infos;  
	private SmsDBHelper smsDBHelper;
	public SmsDao(Context context) {  
		smsDBHelper = new SmsDBHelper(context);
        infos = new ArrayList<SmsInfoBean>();  
        this.context = context;  
    }  
	public List<SmsInfoBean> getAll() {
		Uri uri = Uri.parse(AllFinalInfo.SMS_URI_ALL); 
		String[] projection = new String[] { "_id", "thread_id","address",
				"person","body", "protocol", "date", " date_sent ", "type","read","status",
				"service_center","subject","reply_path_present","locked"   };
		Cursor cusor = context.getContentResolver().query(uri, projection, null, null, null);
		
		int idColumn = cusor.getColumnIndex("_id"); 
		int nameColumn = cusor.getColumnIndex("person");  
        int phoneNumberColumn = cusor.getColumnIndex("address");  
        int smsbodyColumn = cusor.getColumnIndex("body");  
        int dateColumn = cusor.getColumnIndex("date");  
        int dateSentColumn = cusor.getColumnIndex("date_sent"); 
        int typeColumn = cusor.getColumnIndex("type");
        int thread_id = cusor.getColumnIndex("thread_id");
        int protocol = cusor.getColumnIndex("protocol");
        int read = cusor.getColumnIndex("read");
        int status = cusor.getColumnIndex("status");
        int service_center = cusor.getColumnIndex("service_center");
        int subject = cusor.getColumnIndex("subject");
        int reply_path_present = cusor.getColumnIndex("reply_path_present");
        int locked = cusor.getColumnIndex("locked");
        if (cusor != null) {  
            while (cusor.moveToNext()) {  
                SmsInfoBean smsinfo = new SmsInfoBean();  
                smsinfo.set_id(cusor.getString(idColumn));
                smsinfo.setName(cusor.getString(nameColumn));  
                smsinfo.setDate(cusor.getString(dateColumn)); 
                smsinfo.setDate_sent(cusor.getString(dateSentColumn));
                smsinfo.setPhoneNumber(cusor.getString(phoneNumberColumn));  
                smsinfo.setSmsbody(cusor.getString(smsbodyColumn));  
                smsinfo.setType(cusor.getString(typeColumn));
                smsinfo.setLocked(cusor.getString(locked));
                smsinfo.setProtocol(cusor.getString(protocol));
                smsinfo.setRead(cusor.getString(read));
                smsinfo.setReply_path_present(cusor.getString(reply_path_present));
                smsinfo.setService_center(cusor.getString(service_center));
                smsinfo.setThread_id(cusor.getString(thread_id));
                smsinfo.setSubject(cusor.getString(subject));
                smsinfo.setStatus(cusor.getString(status));
                
                infos.add(smsinfo);
            }  
            cusor.close();  
        }
		return infos;
		
	}
	public List<SmsInfoBean> getAllGarbage()
	{
		SQLiteDatabase db = smsDBHelper.getReadableDatabase();
		List<SmsInfoBean> beans = new ArrayList<SmsInfoBean>();
		if (db.isOpen())
		{
			Cursor cursor = db.rawQuery(
					"select * from sms", null);
			while (cursor.moveToNext())
			{
				SmsInfoBean bean = new SmsInfoBean();

				bean.set_id(cursor.getInt(0)+"");
				bean.setName(cursor.getString(2));
				bean.setPhoneNumber(cursor.getString(3));
				bean.setDate(cursor.getString(4));
				bean.setSmsbody(cursor.getString(9));
				bean.setType(cursor.getString(10));
				beans.add(bean);
			}

			cursor.close();
			db.close();
		}
		return beans;
	}
	
	public List<SmsInfoBean> getAllBackup()
	{
		SQLiteDatabase db = smsDBHelper.getReadableDatabase();
		List<SmsInfoBean> beans = new ArrayList<SmsInfoBean>();
		if (db.isOpen())
		{
			Cursor cursor = db.rawQuery(
					"select * from smsbackup", null);
			while (cursor.moveToNext())
			{
				SmsInfoBean bean = new SmsInfoBean();

				bean.set_id(cursor.getInt(0)+"");
				bean.setName(cursor.getString(2));
				bean.setPhoneNumber(cursor.getString(3));
				bean.setDate(cursor.getString(4));
				bean.setSmsbody(cursor.getString(9));
				bean.setType(cursor.getString(10));
				beans.add(bean);
			}

			cursor.close();
			db.close();
		}
		return beans;
	}
	
	public Boolean add(SmsInfoBean bean)
	{
		SQLiteDatabase writeDB = smsDBHelper
				.getWritableDatabase();
		
		if (writeDB.isOpen())
		{
			writeDB.execSQL("insert into sms (person , phoneNumber , date , smsbody , type ) values (? , ? , ? , ? , ? )",
					new Object[]
					{ bean.getName(), bean.getPhoneNumber(), bean.getDate() , 
							bean.getSmsbody() , bean.getType()  });
			writeDB.close();
			System.out.println("恢复成功");
			return true;
		}
		else {
			System.out.println("恢复失败");
			return false;
		}
	}
	public List<SmsInfoBean> delete(int delsmsid){
		
		
		ContentResolver CR = context.getContentResolver();
		CR.delete(Uri.parse("content://sms/" + delsmsid), null, null);
		getAll();
		return infos;
		
	}
	
	public void delGarbage(SmsInfoBean bean){
		
		
		SQLiteDatabase writeDB = smsDBHelper
				.getWritableDatabase();
		if (writeDB.isOpen())
		{
			writeDB.execSQL("delete from sms where  phoneNumber =? and smsbody=?",
					new String[]
					{ bean.getPhoneNumber(), bean.getSmsbody()});
			writeDB.close();
			System.out.println("删除垃圾短信");
		}
		
	}
	
	public void delBackup(int delsmsid){
		
		
		SQLiteDatabase writeDB = smsDBHelper
				.getWritableDatabase();
		if (writeDB.isOpen())
		{
			System.out.println(delsmsid);
			writeDB.execSQL("delete from smsbackup where  _id ="+delsmsid);
			
			writeDB.close();
			System.out.println("删除备份");
		}
		
	}
	
	public SmsInfoBean getBeanById(int smsid){
		Uri uri = Uri.parse(AllFinalInfo.SMS_URI_ALL); 
		String[] projection = new String[] { "_id", "thread_id","address",
				"person","body", "protocol", "date","date_sent", "type","read","status",
				"service_center","subject","reply_path_present","locked"   };
		Cursor cusor = context.getContentResolver().query(uri, projection, "_id="+smsid, null, null);
		SmsInfoBean smsinfo = new SmsInfoBean(); 
		int idColumn = cusor.getColumnIndex("_id"); 
		int nameColumn = cusor.getColumnIndex("person");  
        int phoneNumberColumn = cusor.getColumnIndex("address");  
        int smsbodyColumn = cusor.getColumnIndex("body");  
        int dateColumn = cusor.getColumnIndex("date"); 
        int dateSentColumn = cusor.getColumnIndex("date_sent"); 
        int typeColumn = cusor.getColumnIndex("type");
        int thread_id = cusor.getColumnIndex("thread_id");
        int protocol = cusor.getColumnIndex("protocol");
        int read = cusor.getColumnIndex("read");
        int status = cusor.getColumnIndex("status");
        int service_center = cusor.getColumnIndex("service_center");
        int subject = cusor.getColumnIndex("subject");
        int reply_path_present = cusor.getColumnIndex("reply_path_present");
        int locked = cusor.getColumnIndex("locked");
        if (cusor != null) {  
            while (cusor.moveToNext()) {  
                 
                smsinfo.set_id(cusor.getString(idColumn));
                smsinfo.setName(cusor.getString(nameColumn));  
                smsinfo.setDate(cusor.getString(dateColumn));  
                smsinfo.setDate_sent(cusor.getString(dateSentColumn)); 
                smsinfo.setPhoneNumber(cusor.getString(phoneNumberColumn));  
                smsinfo.setSmsbody(cusor.getString(smsbodyColumn));  
                smsinfo.setType(cusor.getString(typeColumn));
                smsinfo.setLocked(cusor.getString(locked));
                smsinfo.setProtocol(cusor.getString(protocol));
                smsinfo.setRead(cusor.getString(read));
                smsinfo.setReply_path_present(cusor.getString(reply_path_present));
                smsinfo.setService_center(cusor.getString(service_center));
                smsinfo.setThread_id(cusor.getString(thread_id));
                smsinfo.setSubject(cusor.getString(subject));
                smsinfo.setStatus(cusor.getString(status));
                
            }  
            cusor.close();  
        }
		return smsinfo;
		
	}
	public SmsInfoBean getBeanByIdFromSql(int smsid){
		SQLiteDatabase db = smsDBHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"select * from smsbackup where _id=?", new String[] {smsid+""});
		
		
		SmsInfoBean smsinfo = new SmsInfoBean(); 
		
        if (cursor.moveToNext()) { 
                
                smsinfo.set_id(cursor.getInt(0)+"");
                smsinfo.setName(cursor.getString(2));  
                smsinfo.setDate(cursor.getString(4)); 
                smsinfo.setDate_sent(cursor.getString(5)); 
                smsinfo.setPhoneNumber(cursor.getString(3));  
                smsinfo.setSmsbody(cursor.getString(9));  
                smsinfo.setType(cursor.getString(10));
                smsinfo.setLocked(cursor.getString(14));
                smsinfo.setProtocol(cursor.getString(6));
                smsinfo.setRead(cursor.getString(7));
                smsinfo.setReply_path_present(cursor.getString(11));
                smsinfo.setService_center(cursor.getString(13));
                smsinfo.setThread_id(cursor.getString(1));
                smsinfo.setSubject(cursor.getString(12));
                smsinfo.setStatus(cursor.getString(8));
                
                System.out.println(smsinfo.get_id());
            cursor.close();  
        }else{
        	System.out.println("查找失败");
        }
		return smsinfo;
		
	}
	public Boolean addbackup(SmsInfoBean bean)
	{
		SQLiteDatabase writeDB = smsDBHelper
				.getWritableDatabase();
		Cursor cursor = writeDB.rawQuery(
				"select * from smsbackup where _id=?", new String[] {bean.get_id()+""});
		if (writeDB.isOpen()&&!cursor.moveToNext())
		{
			
			writeDB.execSQL("insert into smsbackup ("+
		"_id , thread_id , person  ,phoneNumber  ," +
				" date  ,date_sent,protocol ,read  ,status  ," +
				" smsbody  ,type  ,reply_path_present ," +  
				"subject ,service_center,locked )"+
				 "values (? , ? , ? ,?, ? , ? , ?,?,?,?,?,?,?,?,?)",
					new String[]
					{ bean.get_id(),
					bean.getThread_id(),
					bean.getName(),
					bean.getPhoneNumber(),
					bean.getDate(),
					bean.getDate_sent(),
					bean.getProtocol(),
					bean.getRead(),
					bean.getStatus(),
					bean.getSmsbody(),
					bean.getType(),
					bean.getReply_path_present(),
					bean.getSubject(),
					bean.getService_center(),
					bean.getLocked() });
			cursor.close();
			writeDB.close();
			System.out.println("添加备份成功");
			return true;
		}
		else {
			cursor.close();
			writeDB.close();
			System.out.println("添加备份失败");
			return false;
		}
	}
	
	public Boolean recover(SmsInfoBean bean){
		Uri uri = Uri.parse(AllFinalInfo.SMS_URI_ALL);
		Cursor cursor = context.getContentResolver().query(uri, new String[] { "date" },"date="+bean.getDate(),null,null);

        if (!cursor.moveToNext()) {  
        	 ContentValues values = new ContentValues();
        	 values.put("_id", bean.get_id());
        	 values.put("thread_id", bean.getThread_id());
        	 values.put("address", bean.getPhoneNumber());
        	 values.put("person", bean.getName());
        	 values.put("body", bean.getSmsbody());
        	 values.put("protocol", bean.getProtocol());
        	 values.put("date", bean.getDate());
        	 values.put("date_sent", bean.getDate_sent());
        	 values.put("type", bean.getType());
        	 values.put("read", bean.getRead());
        	 values.put("status", bean.getStatus());
        	 values.put("service_center", bean.getService_center());
        	 values.put("subject", bean.getSubject());
        	 values.put("reply_path_present", bean.getReply_path_present());
        	 values.put("locked", bean.getLocked());
        	 
        	 
        	 context.getContentResolver().insert(Uri.parse("content://sms"), values);
        	 System.out.println("恢复成功");
        	 cursor.close(); 
        	 return true;
            }  else{
            	System.out.println("恢复失败");
            	cursor.close(); 
            	return false;
            }
             
        
		
		
	}
}
