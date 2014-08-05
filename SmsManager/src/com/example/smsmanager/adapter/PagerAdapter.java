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
			return "ͨѶ¼";

		case 1:
			return "��ѯ�����ض���";

		case 2:
			return "���ŷ������";
		case 3:
			return "���дʹ���";
		case 4:
			return "���ű���";
		default:
			throw new RuntimeException("û�ж�Ӧ�ı���");
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