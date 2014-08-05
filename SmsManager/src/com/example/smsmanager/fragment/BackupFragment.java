package com.example.smsmanager.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.smsmanager.R;
import com.example.smsmanager.Dao.SmsDao;
import com.example.smsmanager.bean.SmsInfoBean;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class BackupFragment extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_backup, null);
	}
	
	public void onResume() {
		super.onResume();
		ListView view = (ListView) this.getActivity().findViewById(
				R.id.backuplist);
		if(null != view && null == view.getAdapter() && bDisplay)
		{
			fillList();
		}
	}
	private boolean bDisplay;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Button backupListBtn = (Button) getActivity().findViewById(R.id.BackupListBtn);
		backupListBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				bDisplay = true;
				fillList();
			}
		});
	}
	
	private void fillList() {
		ListView view = (ListView) getActivity().findViewById(
				R.id.backuplist);
		ArrayList<HashMap<String, String>> list = readbackupSMS();
		SimpleAdapter listsimpleAdapter = new SimpleAdapter(getActivity(),
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
						arg0.add(1, 0, 0, "É¾³ý");
						arg0.add(1, 1, 0, "»Ö¸´");
						
					}
				});
				return false;
			}
		});
	}

	private ArrayList<HashMap<String, String>> readbackupSMS() {
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		SmsDao smsDao = new SmsDao(getActivity().getApplicationContext());
		List<SmsInfoBean> infos = smsDao.getAllBackup();
		for (int i = 0; i < infos.size(); i++) {
			HashMap<String, String> m = new HashMap<String, String>();
			m.put("_id", infos.get(i).get_id());
			m.put("name", infos.get(i).getPhoneNumber());
			m.put("text", infos.get(i).getSmsbody());
			list.add(m);
		}

		return list;
	}
	
	public boolean onContextItemSelected(MenuItem item){
		
		if (item.getGroupId() == 1){
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item 
                .getMenuInfo(); 
		int pos =  info.position;
		
        ArrayList<HashMap<String, String>> list = readbackupSMS();
        
        int smsid = Integer.valueOf(list.get(pos).get("_id")).intValue();
        
        SmsDao  smsDao = new SmsDao(
				getActivity().getApplicationContext());
        switch (item.getItemId()) {
		case 0:
			

	        smsDao.delBackup(smsid);
			
			break;
		case 1:
			
			SmsInfoBean bean = smsDao.getBeanByIdFromSql(smsid);
			if(smsDao.recover(bean)){
				Toast.makeText(this.getActivity().getApplicationContext(), "¶ÌÐÅÒÑ»Ö¸´",
						Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(this.getActivity().getApplicationContext(), "¶ÌÐÅÒÑ´æÔÚ£¬ÎÞÐë»Ö¸´",
						Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
		
        fillList();
		}
		return super.onContextItemSelected(item); 
	}
}
