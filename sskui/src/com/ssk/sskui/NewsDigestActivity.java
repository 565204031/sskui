package com.ssk.sskui;

import java.util.ArrayList;

import com.ssk.sskui.fragment.NewsDigestFragment;
import com.ssk.sskui.utils.ViewUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class NewsDigestActivity extends FragmentActivity implements OnPageChangeListener {
	
	private ArrayList<NewsDigestFragment> data;
	private ViewPager vp;
	private MyAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newsdigest_activity);
		ViewUtils.initViews(this);
		data=new ArrayList<NewsDigestFragment>();
		for (int i=0; i<8; i++)
		{
			NewsDigestFragment fragment=new NewsDigestFragment();
			Bundle bundle=new Bundle();
			bundle.putInt("index", i);
			fragment.setArguments(bundle);
			data.add(fragment);
		}
		adapter=new MyAdapter(getSupportFragmentManager());
		vp.setAdapter(adapter);
		vp.setPageMargin(10);
		vp.setCurrentItem(0);
		vp.setOnPageChangeListener(this);
	}
	 private class MyAdapter extends FragmentPagerAdapter{

			public MyAdapter(FragmentManager fm) {
				super(fm);
			}
			@Override
			public Fragment getItem(int position) {
				return data.get(position);
			}
			@Override
			public int getCount() {
				return data.size();
			}
		}
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		//当前page效果
		data.get(position).Scroll(position,positionOffsetPixels);
		if(data.size()-1!=position)
		{
			//下个page效果
			data.get(position+1).ScrollNext(position,positionOffsetPixels);
		}
		
	}
	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		
	}
}
