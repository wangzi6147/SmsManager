package com.example.smsmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.smsmanager.Dao.SmsDao;
import com.example.smsmanager.bean.SmsInfoBean;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class AllSmsActivity extends Activity{
	
	private List<SmsInfoBean> infos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_allsms);
		
		fillList();
	}
	private void fillList() {
        SmsDao sc = new SmsDao(this);  
        infos = sc.getAll();
		ListView view = (ListView) this.findViewById(
				R.id.smslist);
		ArrayList<HashMap<String, String>> list = readSMS();
		SimpleAdapter listsimpleAdapter = new SimpleAdapter(this,
				list, R.layout.listlayout, new String[] { "name", "text" },
				new int[] { R.id.listname, R.id.listtext });
		view.setAdapter(listsimpleAdapter);
		
		view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				arg0.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
					
					@Override
					public void onCreateContextMenu(ContextMenu arg0, View arg1,
							ContextMenuInfo arg2) {
						arg0.add(0, 0, 0, "删除");
						arg0.add(0, 1, 0, "备份");
						
					}
				});
				return false;
			}
		});
	}

	private ArrayList<HashMap<String, String>> readSMS() {
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		int infoslong = infos.size();
		for (int i = 0; i < infoslong; i++) {
			HashMap<String, String> m = new HashMap<String, String>();
			
			m.put("id", infos.get(i).get_id());
			m.put("name", infos.get(i).getPhoneNumber());
			m.put("text", infos.get(i).getSmsbody());
			list.add(m);
		}

		return list;
	}
	
	public boolean onContextItemSelected(MenuItem item){
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item 
                .getMenuInfo(); 
		int pos =  info.position;
		SmsDao sc = new SmsDao(this);
		ArrayList<HashMap<String, String>> list = readSMS();
		int smsid = Integer.valueOf(list.get(pos).get("id")).intValue();
		switch (item.getItemId()) {
		case 0:
			
			sc.delete(smsid);
			
			break;
		case 1:
			
			SmsInfoBean bean = sc.getBeanById(smsid);
			if(sc.addbackup(bean)){
				Toast.makeText(this.getApplicationContext(), "备份成功",
						Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(this.getApplicationContext(),"备份失败",
						Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
        

        fillList();
		return super.onContextItemSelected(item); 
	}
}
