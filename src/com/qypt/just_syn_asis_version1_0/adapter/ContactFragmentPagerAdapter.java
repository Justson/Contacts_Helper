package com.qypt.just_syn_asis_version1_0.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
/**
 * 
 * @author Administrator justson
 *
 */
public class ContactFragmentPagerAdapter extends FragmentPagerAdapter {

	List<Fragment>list=null;
	
	public ContactFragmentPagerAdapter(FragmentManager fm,List<Fragment> list) {
		super(fm);
		this.list=list;
	}

	@Override
	public Fragment getItem(int position) {
		return list.get(position);
	}

	@Override
	public int getCount() {
		return list.size();
	}

}
