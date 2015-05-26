package com.xulingyun.baiduimagesbrowse.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.xulingyun.baiduimagesbrowse.R;
import com.xulingyun.baiduimagesbrowse.adapter.MyListAdapter;
import com.xulingyun.baiduimagesbrowse.dao.ImageInfo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

@SuppressLint("HandlerLeak")
public class ShowActivity extends Activity {

	ListView listView;
	ProgressBar mProgressBar ;
	List<ImageInfo> imageInfos = new ArrayList<ImageInfo>();
	RequestQueue mQueue;
	Handler handler = new Handler(){
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			mProgressBar.setVisibility(View.GONE);
			imageInfos = (List<ImageInfo>) msg.obj;
			listView.setAdapter(new MyListAdapter(imageInfos, ShowActivity.this,mQueue));
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show);
		listView = (ListView) findViewById(R.id.lv);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
		mQueue = Volley.newRequestQueue(this);
		JsonObjectRequest request = new JsonObjectRequest("http://image.baidu.com/channel/listjson?pn=0&rn=200&tag1=%E7%BE%8E%E5%A5%B3&tag2=%E5%85%A8%E9%83%A8&ie=utf8", null, 
				new Response.Listener<JSONObject>() {
					public void onResponse(JSONObject jsonObject) {
						try {
							JSONArray array = jsonObject.getJSONArray("data");
							int len = array.length();
							for(int i=0;i<len-2;i++){
								JSONObject object = array.getJSONObject(i);
								String abs = null ;
								try {
									abs = new String(object.getString("abs").getBytes("ISO-8859-1"),"UTF-8");
								} catch (UnsupportedEncodingException e) {
									e.printStackTrace();
								}
								ImageInfo imageInfo = new ImageInfo(abs,object.getString("image_url"),object.getString("thumbnail_width"),object.getString("thumbnail_height"));
								imageInfos.add(imageInfo);
								System.out.println(imageInfo.toString());
							}
							Message message = Message.obtain();
							message.obj = imageInfos;
							handler.sendMessage(message);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {

					public void onErrorResponse(VolleyError volleyError) {
						
					}
				});
		mQueue.add(request);
		mProgressBar.setVisibility(View.VISIBLE);
	}
	
}
