package com.example.smsmanager.fragment;

import com.example.smsmanager.BlackActivity;
import com.example.smsmanager.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class ContactFragment extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_contact, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		setupView();
	}

	private void setupView()
	{
		Button btnWhite = (Button) getActivity().findViewById(
				R.id.btnWhite);
		Button btnBlack = (Button) getActivity().findViewById(
				R.id.btnBlack);
		btnWhite.setOnClickListener(new WhiteBtnLsn());
		btnBlack.setOnClickListener(new BlackBtnLsn());
	}

	private static class WhiteBtnLsn implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			Toast.makeText(v.getContext(), "该功能未开放",
					Toast.LENGTH_SHORT).show();
		}
	}

	private static class BlackBtnLsn implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(v.getContext(),
					BlackActivity.class);
			v.getContext().startActivity(intent);
		}
	}

}
