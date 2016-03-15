package com.example.smsmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class TagActivity extends Activity{
	private static final String FILENAME = "com.example.smsmanager";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag);
		ListView view = (ListView) this.findViewById(
				R.id.taglist);
		fillList(view);
		view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				TextView  text=(TextView)arg1.findViewById(R.id.listtext);
				String tag = text.getText().toString();
				Intent intent = new Intent(TagActivity.this,
						TagSmsActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("tag", tag);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
		view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				arg0.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
					
					@Override
					public void onCreateContextMenu(ContextMenu arg0, View arg1,
							ContextMenuInfo arg2) {
						arg0.add(0, 0, 0, "删除");
						
					}
				});
				return false;
			}
		});
	}
	public boolean onContextItemSelected(MenuItem item){
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item 
                .getMenuInfo(); 
		int pos =  info.position;
		Log.w("tag", "pos is"+pos);
		SharedPreferences SharedPreferences = getSharedPreferences(FILENAME,Activity.MODE_PRIVATE);
		Editor editor = SharedPreferences.edit();
		ArrayList<HashMap<String, String>> list = readtags();
		String deltag = list.get(pos).get("text");
		Log.w("deltag", deltag);
		editor.remove(deltag);
		editor.commit();
		ListView view = (ListView) this.findViewById(
				R.id.taglist);
		fillList(view);
		return super.onContextItemSelected(item); 
	}
	
	private void fillList(ListView view) {
		
		
		ArrayList<HashMap<String, String>> list = readtags();
		SimpleAdapter listsimpleAdapter = new SimpleAdapter(this,
				list, R.layout.listlayout, new String[] { "name", "text" },
				new int[] { R.id.listname, R.id.listtext });
		view.setAdapter(listsimpleAdapter);
	}

	private ArrayList<HashMap<String, String>> readtags() {
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		
		SharedPreferences SharedPreferences = getSharedPreferences(FILENAME,Activity.MODE_PRIVATE);
		Map<String, ?> allContent = SharedPreferences.getAll(); 
		int i = 1;
        //ע�����map�ķ���  
        for(Map.Entry<String, ?>  entry : allContent.entrySet()){  
        	HashMap<String, String> m = new HashMap<String, String>();
			m.put("name", "tag" + i);
			m.put("text", entry.getKey());
			list.add(m); 
			i++;
        }  
		
		return list;
	}
	
	
}
