package com.example.smsmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.smsmanager.Dao.InterceptDao;
import com.example.smsmanager.bean.InterceptInfoBean;

public class BlackActivity extends Activity
{
	private List<InterceptInfoBean> infos;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_black);

		setupView();
	}

	private void setupView()
	{
		Button btnSave = (Button) findViewById(R.id.btnSaveToDB);
		Button btnQueryAll = (Button) findViewById(R.id.btnQueryFromDB);
		EditText etName = (EditText) findViewById(R.id.etName);
		EditText etNumber = (EditText) findViewById(R.id.etNumber);

		InterceptDao interceptDao = new InterceptDao(
				getApplicationContext());
		btnSave.setOnClickListener(new SaveBtnLsn(etName, etNumber,
				interceptDao));
		btnQueryAll.setOnClickListener(new QueryAllBtnLsn(interceptDao));
	}

	private static class SaveBtnLsn implements OnClickListener
	{

		private EditText etName;
		private EditText etNumber;
		private InterceptDao interceptDao;

		public SaveBtnLsn(EditText etName, EditText etNumber,
				InterceptDao interceptDao)
		{
			this.etName = etName;
			this.etNumber = etNumber;
			this.interceptDao = interceptDao;
		}

		@Override
		public void onClick(View v)
		{
			String strName = etName.getText().toString().trim();
			String strNumber = etNumber.getText().toString().trim();

			if (isValidate(strName, strNumber))
			{
				InterceptInfoBean bean = new InterceptInfoBean();
				bean.setName(strName);
				bean.setNumber(strNumber);
				bean.setSensitiveWord("");
				if(interceptDao.add(bean)){
				InputMethodManager imm = (InputMethodManager) v
						.getContext()
						.getSystemService(
								Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(
						etName.getWindowToken(), 0);
				Toast.makeText(v.getContext(), "保存成功",
						Toast.LENGTH_SHORT).show();
				}
				else{
					InputMethodManager imm = (InputMethodManager) v
							.getContext()
							.getSystemService(
									Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(
							etName.getWindowToken(), 0);
					Toast.makeText(v.getContext(), "数据已存在",
							Toast.LENGTH_SHORT).show();
				}
			} else
				Toast.makeText(v.getContext(), "输入的姓名、号码不合法！",
						Toast.LENGTH_SHORT).show();
		}

		private boolean isValidate(String strName, String strNumber)
		{
			// TODO
			Log.w(getClass().getName(), "尚未完全完成");

			if ("".equals(strName) || "".equals(strNumber))
				return false;
			return true;
		}
	}

	private class QueryAllBtnLsn implements OnClickListener
	{

		public QueryAllBtnLsn(InterceptDao interceptDao)
		{
		}

		@Override
		public void onClick(View v)
		{
			// TODO
			
			
			fillList();
		}
	}
	
	private void fillList() {
		
		ListView view = (ListView) this.findViewById(
				R.id.blacklist);
		ArrayList<HashMap<String, String>> list = readblack();
		SimpleAdapter listsimpleAdapter = new SimpleAdapter(this,
				list, R.layout.listlayout, new String[] { "name", "text" },
				new int[] { R.id.listname , R.id.listtext });
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
						
					}
				});
				return false;
			}
		});
	}
	
	private ArrayList<HashMap<String, String>> readblack() {
		InterceptDao interceptDao = new InterceptDao(
				getApplicationContext());
		infos = interceptDao.getAll();
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		int infoslong = infos.size();
		for (int i = 0; i < infoslong; i++) {
			if( !infos.get(i).getName().equals("") && !infos.get(i).getNumber().equals("")){
			HashMap<String, String> m = new HashMap<String, String>();
			
			m.put("name", infos.get(i).getName());
			m.put("text", infos.get(i).getNumber());
			list.add(m);
			}
		}

		return list;
	}
	
	public boolean onContextItemSelected(MenuItem item){
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item 
                .getMenuInfo(); 
		int pos =  info.position;
		
        ArrayList<HashMap<String, String>> list = readblack();
        String delname = list.get(pos).get("name");
        String delnumber = list.get(pos).get("text");
        InterceptInfoBean bean = new InterceptInfoBean();
        bean.setName(delname);
        bean.setNumber(delnumber);

        InterceptDao interceptDao = new InterceptDao(
				getApplicationContext());
        interceptDao.del(bean);
		
        fillList();
		return super.onContextItemSelected(item); 
	}
}
