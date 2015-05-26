package com.xulingyun.baiduimagesbrowse.activity;

import java.util.Calendar;

import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.xulingyun.baiduimagesbrowse.R;

public class ChangeActivity extends Activity {

	RadioButton male, female;
	EditText name, birthday, hobby;
	RadioGroup sex;
	DatePickerDialog dataBirthday;
	Button submit;
	String name_c, birthday_c, hobby_c;
	int sex_c = 1;

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				Toast.makeText(ChangeActivity.this, "登录成功！", Toast.LENGTH_LONG)
						.show();
			} else {
				Toast.makeText(ChangeActivity.this, "登录失败！", Toast.LENGTH_LONG)
						.show();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info);
		initView();
		initData();
	}

	class MyDateSetListener implements OnDateSetListener {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			birthday.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
		}
	}

	private void initData() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int monthOfYear = calendar.get(Calendar.MONTH);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		dataBirthday = new DatePickerDialog(this, new MyDateSetListener(),
				year, monthOfYear, dayOfMonth);
		dataBirthday.setCancelable(true);
		dataBirthday.setCanceledOnTouchOutside(true);
		dataBirthday.setButton(DialogInterface.BUTTON_POSITIVE, "设置",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dataBirthday.dismiss();
					}
				});
		dataBirthday.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dataBirthday.dismiss();
					}
				});
	}

	private void initView() {
		name = (EditText) findViewById(R.id.name_e);
		birthday = (EditText) findViewById(R.id.birthday_e);
		hobby = (EditText) findViewById(R.id.hobby_e);
		sex = (RadioGroup) findViewById(R.id.sex_radio);
		male = (RadioButton) findViewById(R.id.male);
		female = (RadioButton) findViewById(R.id.female);
		submit = (Button) findViewById(R.id.submit);
		birthday.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 弹出日期控件
				dataBirthday.show();
			}
		});
		sex.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO
				if (checkedId == male.getId()) {
					sex_c = 1;
				} else {
					sex_c = 0;
				}
			}
		});

		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 提交数据
				name_c = name.getText().toString();
				birthday_c = birthday.getText().toString();
				hobby_c = hobby.getText().toString();
				new Thread(new Runnable() {
					public void run() {
						RequestQueue mQueue = Volley
								.newRequestQueue(ChangeActivity.this);
						JsonObjectRequest request = new JsonObjectRequest(
								Method.GET,
								"http://10.100.110.62:8080/test/UserServlet?method=update&name="
										+ name_c + "&userId=" + 123456
										+ "&birthday=" + birthday_c + "&hobby="
										+ birthday_c + "&sex=" + sex_c
										+ "&headicon=12333", null,
								new Listener<JSONObject>() {
									public void onResponse(JSONObject object) {
										try {
											String status = (String) object
													.getString("status");
											String errorInfo = (String) object
													.getString("errorInfo");
											System.out.println("status:"
													+ status + ",errorInfo:"
													+ errorInfo);
											if (Integer.parseInt(status) == 1) {
												handler.sendEmptyMessage(1);
											} else {
												handler.sendEmptyMessage(1);
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

}
