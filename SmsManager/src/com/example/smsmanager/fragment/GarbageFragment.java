package com.example.smsmanager.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.smsmanager.R;
import com.example.smsmanager.Dao.SmsDao;
import com.example.smsmanager.bean.SmsInfoBean;

public class GarbageFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_garbage, null);
	}
	
	public void onResume() {
		super.onResume();
		ListView view = (ListView) this.getActivity().findViewById(
				R.id.GarbageList);
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
		
		Button garbageListBtn = (Button) getActivity().findViewById(R.id.GarbageListButton);
		garbageListBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				bDisplay = true;
				fillList();
			}
		});
	}

	private void fillList() {
		ListView view = (ListView) getActivity().findViewById(
				R.id.GarbageList);
		ArrayList<HashMap<String, String>> list = readGarbageSMS();
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
						arg0.add(0, 0, 0, "É¾³ý");
						
					}
				});
				return false;
			}
		});
	}

	private ArrayList<HashMap<String, String>> readGarbageSMS() {
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		SmsDao smsDao = new SmsDao(getActivity().getApplicationContext());
		List<SmsInfoBean> infos = smsDao.getAllGarbage();
		for (int i = 0; i < infos.size(); i++) {
			HashMap<String, String> m = new HashMap<String, String>();
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
		
        ArrayList<HashMap<String, String>> list = readGarbageSMS();
        String delnember = list.get(pos).get("name");
        String deltext = list.get(pos).get("text");
        SmsInfoBean bean = new SmsInfoBean();
        bean.setPhoneNumber(delnember);
        bean.setSmsbody(deltext);

        SmsDao  smsDao = new SmsDao(
				getActivity().getApplicationContext());
        smsDao.delGarbage(bean);
		
        fillList();
		return super.onContextItemSelected(item); 
	}
}
