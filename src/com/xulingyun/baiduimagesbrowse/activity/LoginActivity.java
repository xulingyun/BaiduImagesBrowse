package com.xulingyun.baiduimagesbrowse.activity;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.xulingyun.baiduimagesbrowse.R;
import com.xulingyun.baiduimagesbrowse.util.SharedPreferencesUtil;

public class LoginActivity extends Activity {

	AutoCompleteTextView user_name_v;
	EditText password_v;
	CheckBox rememberMe_v;
	Button login_v;
	int userName;
	String password;
	boolean isRememberMe;
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==1){
				Toast.makeText(LoginActivity.this, "用户创建成功！", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(LoginActivity.this, ChangeActivity.class);
				startActivity(intent);
			}else if(msg.what==0){
				Toast.makeText(LoginActivity.this, "用户登录成功！", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(LoginActivity.this, MyListActivity.class);
				startActivity(intent);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
		initData();
		login_v.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				final String nameName = user_name_v.getText().toString();
				final String password = password_v.getText().toString();
				if (nameName.equals("")) {
					Toast.makeText(LoginActivity.this, "帐号不能为空！", Toast.LENGTH_LONG).show();
					return;
				}
				if (password.equals("")) {
					Toast.makeText(LoginActivity.this, "密码不能为空！", Toast.LENGTH_LONG).show();
					return;
				}
				new Thread(new Runnable() {
					public void run() {
						// TODO 访问网路去验证帐号密码
						RequestQueue mQueue = Volley
								.newRequestQueue(LoginActivity.this);
						JsonObjectRequest request = new JsonObjectRequest(
								Method.GET,
								"http://10.100.110.62:8080/test/UserServlet?method=add&userId="+nameName+"&userPassword="+password,
								null, new Listener<JSONObject>() {
									public void onResponse(JSONObject object) {
										try {
											String status = (String) object.getString("status");
											String errorInfo = (String) object.getString("errorInfo");
											System.out.println("status:"+ status + ",errorInfo:"+ errorInfo);
											if(Integer.parseInt(status)==1){
												handler.sendEmptyMessage(1);
											}else if(Integer.parseInt(status)==0){
												handler.sendEmptyMessage(0);
											}
										} catch (Exception e) {
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
		});
	}

	private void initData() {
		isRememberMe = SharedPreferencesUtil.getSharedPreferences(this)
				.getBoolean("isRememberMe", false);
		if (isRememberMe) {
			userName = SharedPreferencesUtil.getSharedPreferences(this).getInt(
					"userName", -1);
			password = SharedPreferencesUtil.getSharedPreferences(this)
					.getString("password", "");
			rememberMe_v.setSelected(true);
			user_name_v.setText(userName);
			password_v.setText(password);
		} else {
			rememberMe_v.setSelected(false);
			user_name_v.setText("");
			password_v.setText("");
		}
	}

	private void initView() {
		user_name_v = (AutoCompleteTextView) findViewById(R.id.login_account);
		password_v = (EditText) findViewById(R.id.login_password);
		rememberMe_v = (CheckBox) findViewById(R.id.login_rememberMe);
		login_v = (Button) findViewById(R.id.login_btn);
	}

}
