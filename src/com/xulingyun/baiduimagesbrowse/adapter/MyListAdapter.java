package com.xulingyun.baiduimagesbrowse.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.xulingyun.baiduimagesbrowse.R;
import com.xulingyun.baiduimagesbrowse.dao.ImageInfo;
import com.xulingyun.baiduimagesbrowse.util.BitmapCache;

public class MyListAdapter extends BaseAdapter {

	List<ImageInfo> imageInfos;
	Context context;
	LayoutInflater inflater;
	RequestQueue mQueue;
	ImageLoader imageLoader;
	
	public MyListAdapter(List<ImageInfo> imageInfos, Context context,RequestQueue mQueue) {
		this.imageInfos = imageInfos;
		this.context = context;
		this.mQueue = mQueue;
		inflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return imageInfos.size();
	}

	public Object getItem(int arg0) {
		return imageInfos.get(arg0);
	}

	public long getItemId(int arg0) {
		return arg0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder =  null;
		if(convertView==null){
			holder=new ViewHolder(); 
			convertView = inflater.inflate(R.layout.mylist, null);
			holder.bitmap = (NetworkImageView) convertView.findViewById(R.id.imageView1);
			holder.abs = (TextView) convertView.findViewById(R.id.textView1);
			holder.width_height = (TextView) convertView.findViewById(R.id.textView2);
			holder.bitmap.setTag(imageInfos.get(position).getThumbnail_url());
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		final String imgUrl = imageInfos.get(position).getThumbnail_url();
		if(imgUrl!=null&&!imgUrl.equals("")){
			imageLoader = new ImageLoader(mQueue, new BitmapCache());
			holder.bitmap.setDefaultImageResId(R.drawable.ic_launcher);
			holder.bitmap.setErrorImageResId(R.drawable.ic_launcher);
			holder.bitmap.setImageUrl(imgUrl, imageLoader);
		}
		holder.abs.setText(imageInfos.get(position).getAbs());
		holder.width_height.setText(imageInfos.get(position).getThumbnail_width()+" X "+imageInfos.get(position).getThumbnail_height());
		return convertView;
	}

	 public final class ViewHolder{
		TextView abs;
		TextView width_height;
		NetworkImageView bitmap;
	}

}
