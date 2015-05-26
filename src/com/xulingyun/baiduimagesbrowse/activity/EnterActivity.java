package com.xulingyun.baiduimagesbrowse.activity;

import java.util.ArrayList;
import java.util.List;

import com.xulingyun.baiduimagesbrowse.R;
import com.xulingyun.baiduimagesbrowse.adapter.ViewPagerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class EnterActivity extends Activity {
	
	ImageView[] dots;
	int pageIndex;
	ViewPager mViewPager;
	LayoutInflater inflater;
	List<View> list;
	ViewPagerAdapter adapter;
	LinearLayout dotsLayout ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pageIndex = 0;
		setContentView(R.layout.activity_enter);
		dotsLayout = (LinearLayout) findViewById(R.id.dots);
		initViews();
		dots = new ImageView[list.size()];
		initDots(pageIndex);
	}

	private void initDots(int index) {
		for(int i=0;i<list.size();i++){
			dots[i] = (ImageView) dotsLayout.getChildAt(i);
			if(i==index)
				dots[i].setBackgroundResource(R.drawable.dot2);
			else
				dots[i].setBackgroundResource(R.drawable.dot1);
		}
	}

	private void initViews() {
		mViewPager = (ViewPager) findViewById(R.id.viewpage);
		inflater = LayoutInflater.from(this);
		list = new ArrayList<View>();
		list.add(inflater.inflate(R.layout.first_enter, null));
		list.add(inflater.inflate(R.layout.second_enter, null));
		list.add(inflater.inflate(R.layout.thrid_enter, null));
		adapter = new ViewPagerAdapter(list, this);
		mViewPager.setAdapter(adapter);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			public void onPageSelected(int arg0) {
				initDots(arg0);
			}
			
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}
}
