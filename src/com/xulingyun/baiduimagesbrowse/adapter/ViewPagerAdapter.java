package com.xulingyun.baiduimagesbrowse.adapter;

import java.util.List;

import com.xulingyun.baiduimagesbrowse.R;
import com.xulingyun.baiduimagesbrowse.activity.LoginActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ViewPagerAdapter extends PagerAdapter {

	List<View> list;
	Context context;

	public ViewPagerAdapter(List<View> list, Context context) {
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(list.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(list.get(position));
		if(position==list.size()-1){
			TextView enter = (TextView) container.findViewById(R.id.gomain);
			enter.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					Intent intent = new Intent(context, LoginActivity.class);
					context.startActivity(intent);
					((Activity) context).overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
					((Activity) context).finish();
				}
			});
		}
		return list.get(position);
	}

}
