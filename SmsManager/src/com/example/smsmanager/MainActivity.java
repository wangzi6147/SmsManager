package com.example.smsmanager;

import java.util.List;
import java.util.Vector;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.example.smsmanager.adapter.PagerAdapter;
import com.example.smsmanager.fragment.BackupFragment;
import com.example.smsmanager.fragment.ContactFragment;
import com.example.smsmanager.fragment.GarbageFragment;
import com.example.smsmanager.fragment.SensitiveWordFragment;
import com.example.smsmanager.fragment.SmsManagerFragment;

public class MainActivity extends FragmentActivity {
	private PagerAdapter mPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_main);
		this.initialisePaging();
	}

	/**
	 * Initialise the fragments to be paged
	 */
	private void initialisePaging()
	{

		List<Fragment> fragments = new Vector<Fragment>();
		fragments.add(Fragment.instantiate(this,
				ContactFragment.class.getName()));
		fragments.add(Fragment.instantiate(this,
				GarbageFragment.class.getName()));
		fragments.add(Fragment.instantiate(this,
				SmsManagerFragment.class.getName()));
		fragments.add(Fragment.instantiate(this,
				SensitiveWordFragment.class.getName()));
		fragments.add(Fragment.instantiate(this, 
				BackupFragment.class.getName()));
		this.mPagerAdapter = new PagerAdapter(
				super.getSupportFragmentManager(), fragments);
		
		//
		ViewPager pager = (ViewPager) super
				.findViewById(R.id.viewpager);
		pager.setAdapter(this.mPagerAdapter);
		
	}
}
