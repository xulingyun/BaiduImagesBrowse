package com.xulingyun.baiduimagesbrowse.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.xulingyun.baiduimagesbrowse.dao.Photo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class MyListActivity extends Activity {

	List<Photo> list = new ArrayList<Photo>();
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==1){
				Toast.makeText(MyListActivity.this, "获取数据成功！", Toast.LENGTH_LONG).show();
//				Intent intent = new Intent(MyListActivity.this, ChangeActivity.class);
//				startActivity(intent);
			}else if(msg.what==0){
				Toast.makeText(MyListActivity.this, "获取数据失败！", Toast.LENGTH_LONG).show();
//				Intent intent = new Intent(MyListActivity.this, MyListActivity.class);
//				startActivity(intent);
			}
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getData();
	}

	private void getData() {
		// TODO 获取照片相关的内容
		new Thread(new Runnable() {
			public void run() {
				RequestQueue mQueue = Volley.newRequestQueue(MyListActivity.this);
				JsonObjectRequest request = new JsonObjectRequest(
						Method.GET,
						"http://10.100.110.62:8080/test/PhotoServlet?method=query&page="+0+"&pageNum="+4,
						null, new Listener<JSONObject>() {
							public void onResponse(JSONObject object) {
								try {
									String status = object.getString("status");
									String content = object.getString("content");
									list = JSONArray.parseArray(content, Photo.class);
									for(int i=0;i<list.size();i++){
										System.out.println("----"+list.get(i).toString());
									}
									if(Integer.parseInt(status)==1){
										handler.sendEmptyMessage(1);
									}else if(Integer.parseInt(status)==0){
										handler.sendEmptyMessage(0);
									}
								} catch (Exception e) {
									Toast.makeText(MyListActivity.this, "解析出错了！", Toast.LENGTH_LONG).show();
								}
							}
						}, new ErrorListener() {
							public void onErrorResponse(VolleyError arg0) {
							}
						});
				mQueue.add(request);
			}
		}).start();
	}
}
