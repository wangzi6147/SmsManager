package com.example.smsmanager.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.smsmanager.AllSmsActivity;
import com.example.smsmanager.R;
import com.example.smsmanager.TagActivity;

public class SmsManagerFragment extends Fragment {
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_sms_manager, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		EditText tagnameET = (EditText) getActivity().findViewById(R.id.inputtag);
		Button smsbutton = (Button) getActivity().findViewById(R.id.allsmsbutton);
		smsbutton.setOnClickListener(new smsbtnlsn());
		Button addtagbtn = (Button) getActivity().findViewById(R.id.addtagbtn);
		addtagbtn.setOnClickListener(new addtagbtnlsn(tagnameET,getActivity()));
		Button viewtagbtn = (Button) getActivity().findViewById(R.id.viewtagbtn);
		viewtagbtn.setOnClickListener(new viewtagbtnlsn());
		
	}

	private static class smsbtnlsn implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(v.getContext(),
					AllSmsActivity.class);
			v.getContext().startActivity(intent);

		}
	} 
	
	private class addtagbtnlsn implements OnClickListener
	{
		private static final String FILENAME = "com.example.smsmanager";
		private EditText tagnameET;
		private Activity activity;

		public addtagbtnlsn(EditText tagnameET, Activity activity)
		{
			this.tagnameET = tagnameET;
			this.activity = activity;
		}
		@Override
		public void onClick(View v)
		{
			SharedPreferences SharedPreferences = activity.getSharedPreferences(FILENAME , Activity.MODE_PRIVATE);
			Editor editor = SharedPreferences.edit();
			
			String tagname = tagnameET.getText().toString();
			editor.putString(tagname, tagname);  
            editor.commit();  
		}
	} 
	private static class viewtagbtnlsn implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(v.getContext(),
					TagActivity.class);
			v.getContext().startActivity(intent);

		}
	} 
}
