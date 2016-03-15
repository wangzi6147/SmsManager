package com.example.smsmanager.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * The <code>PagerAdapter</code> serves the fragments when paging.
 * 
 * @author mwho
 */
public class PagerAdapter extends FragmentPagerAdapter
{

	private List<Fragment> fragments;

	@Override
	public CharSequence getPageTitle(int position)
	{
		switch (position)
		{
		case 0:
			return "ͨ通讯录";

		case 1:
			return "查询已拦截短信";

		case 2:
			return "短信分类管理";
		case 3:
			return "敏感词管理";
		case 4:
			return "短信备份";
		default:
			throw new RuntimeException("没有对应的标题");
		}
	}

	/**
	 * @param fm
	 * @param fragments
	 */
	public PagerAdapter(FragmentManager fm, List<Fragment> fragments)
	{
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int position)
	{
		return this.fragments.get(position);
	}

	@Override
	public int getCount()
	{
		return this.fragments.size();
	}
}