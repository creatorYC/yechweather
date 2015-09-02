package com.yc.yechweather.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * viewPager ��������
 * ��״̬�� ��ֻ����ǰ3�������������٣�  ǰ1���� �м䣬 ��1�� 
 */
public class MyFragmentAdapter extends FragmentStatePagerAdapter {

	private List<Fragment> fragments; 
	public MyFragmentAdapter(FragmentManager fm,List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

}
