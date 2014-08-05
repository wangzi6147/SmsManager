package com.example.smsmanager.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.smsmanager.R;
import com.example.smsmanager.Dao.InterceptDao;
import com.example.smsmanager.bean.InterceptInfoBean;

public class SensitiveWordFragment extends Fragment
{
	private List<InterceptInfoBean> infos;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_sensitive_word, null);
	}

	@Override
	public void onResume() {
		super.onResume();
		ListView view = (ListView) this.getActivity().findViewById(
				R.id.sensitivelist);
		if(null != view && null == view.getAdapter() && bDisplay)
		{
			fillList();
		}
	}
	
	private boolean bDisplay;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		EditText etSensitiveWord = (EditText) getActivity()
				.findViewById(R.id.etSensitive);
		Button btnSensitiveSave = (Button) getActivity().findViewById(
				R.id.btnSensitiveSave);
		Button btnSensitiveQuery = (Button) getActivity().findViewById(R.id.btnSensitiveQuery);
		btnSensitiveQuery.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				bDisplay = true;
				fillList();
				
			}
		});
		btnSensitiveSave.setOnClickListener(new SensitiveSaveBtnLsn(
				etSensitiveWord));
	}

	private static class SensitiveSaveBtnLsn implements OnClickListener
	{

		private EditText etSensitiveWord;
		private InterceptDao interceptDao;

		public SensitiveSaveBtnLsn(EditText etSensitiveWord)
		{
			this.etSensitiveWord = etSensitiveWord;
			interceptDao = new InterceptDao(
					etSensitiveWord.getContext());
		}

		@Override
		public void onClick(View v)
		{
			String strSensitiveWord = etSensitiveWord.getText()
					.toString().trim();
			if (isValidate(strSensitiveWord))
			{
				InterceptInfoBean bean = new InterceptInfoBean();
				bean.setName("");
				bean.setNumber("");
				bean.setSensitiveWord(strSensitiveWord);
				if(interceptDao.add(bean)){
				Toast.makeText(v.getContext(), "保存成功",
						Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(v.getContext(), "数据已存在",
							Toast.LENGTH_SHORT).show();
				}
			} else
				Toast.makeText(v.getContext(), "输入的敏感词不合法！",
						Toast.LENGTH_SHORT).show();
		}

		private boolean isValidate(String strSensitiveWord)
		{
			Log.w(getClass().getName(), "尚未完全完成");
			if ("".equals(strSensitiveWord))
				return false;
			return true;
		}
	}
	private void fillList() {
		
		ListView view = (ListView) this.getActivity().findViewById(
				R.id.sensitivelist);
		ArrayList<HashMap<String, String>> list = readsensitive();
		SimpleAdapter listsimpleAdapter = new SimpleAdapter(this.getActivity(),
				list, R.layout.listlayout, new String[] {  "text" },
				new int[] {  R.id.listtext });
		view.setAdapter(listsimpleAdapter);
		
		view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				arg0.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
					
					@Override
					public void onCreateContextMenu(ContextMenu arg0, View arg1,
							ContextMenuInfo arg2) {
						arg0.add(2, 0, 0, "删除");
						
					}
				});
				return false;
			}
		});
	}
	
	private ArrayList<HashMap<String, String>> readsensitive() {
		InterceptDao interceptDao = new InterceptDao(
				this.getActivity().getApplicationContext());
		infos = interceptDao.getAll();
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		int infoslong = infos.size();
		for (int i = 0; i < infoslong; i++) {
			Log.w(null, infos.get(i).getSensitiveWord());
			if( !infos.get(i).getSensitiveWord().equals("")){
			HashMap<String, String> m = new HashMap<String, String>();
			
			
			m.put("text", infos.get(i).getSensitiveWord());
			list.add(m);
			}
		}

		return list;
	}
	
	public boolean onContextItemSelected(MenuItem item){
		
		if(item.getGroupId()==2){
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item 
                .getMenuInfo(); 
		int pos =  info.position;
		
        ArrayList<HashMap<String, String>> list = readsensitive();
        String delsensitive = list.get(pos).get("text");
        InterceptInfoBean bean = new InterceptInfoBean();
        bean.setSensitiveWord(delsensitive);

        InterceptDao interceptDao = new InterceptDao(
				this.getActivity().getApplicationContext());
        interceptDao.delsensitive(bean);
		
        fillList();
		}
		return super.onContextItemSelected(item); 
	}
}
