package com.bashi_group_01.www.activity;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.bashi_group_01.www.domain.ResultRetrun;
import com.bashi_group_01.www.util.HttpUtil;
/**
 * 登录类；
 * @author share
 *
 */
public class LoginActivity extends Activity {
	private EditText et_username;
	private EditText et_userpassword;
	private CheckBox remember;
	private Button login;
	private SharedPreferences sp;
	private String userNameValue, passwordValue;
	private int loginNum;// 默认第一次登陆；
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initViews();
		sp = getSharedPreferences("userInfo", 0);
		String name = sp.getString("USER_NAME", "");
		String pass = sp.getString("PASSWORD", "");
		boolean choseRemember = sp.getBoolean("remember", false);
		// Toast.makeText(this, name, Toast.LENGTH_SHORT).show();

		// 如果上次选了记住密码，那进入登录页面也自动勾选记住密码，并填上用户名和密码
		if (choseRemember) {
			et_username.setText(name);
			et_userpassword.setText(pass);
			remember.setChecked(true);
		}

		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				boolean network = NetWorkConnection();

				if (network) {
					loginNum = 0;
					String path = "http://116.236.170.106:8384/FunctionModule/RemoteData/LoginCheckHandler.ashx";
					String name = et_username.getText().toString().trim();
					String pwd = et_userpassword.getText().toString().trim();
					System.out.println(name);
					System.out.println(pwd);

					String pwdmd5;
					try {
						pwdmd5 = md5Encode(name + pwd);
						new LoginAsy().execute(path, name, pwdmd5);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					dialog = new ProgressDialog(LoginActivity.this);
					dialog.setTitle("登录");
					dialog.setMessage("正在登录,请稍后....");
					dialog.show();
				}
			}
		});

	}

	private void initViews() {
		// 初始化用户名、密码、记住密码、自动登录、登录按钮
		et_username = (EditText) findViewById(R.id.et_login);
		et_userpassword = (EditText) findViewById(R.id.et_pwd);
		remember = (CheckBox) findViewById(R.id.remember);
		login = (Button) findViewById(R.id.bt_login);
	}

	class LoginAsy extends AsyncTask<String, Integer, ResultRetrun> {

		@Override
		protected ResultRetrun doInBackground(String... params) {
			// TODO Auto-generated method stub

			List<NameValuePair> list = new ArrayList<NameValuePair>();

			// System.out.println(params[1]);

			list.add(new BasicNameValuePair("LoginName", params[1]));
			list.add(new BasicNameValuePair("UserPwd", params[2]));
			String result = HttpUtil.doGet(params[0], list);
			// System.out.println(result);
			ResultRetrun rr = HttpUtil.ResultReturn(result);
			// System.out.println(rr.getResultCode());
			// System.out.println(rr);
			return rr;
		}

		@Override
		protected void onPostExecute(ResultRetrun result) {
			super.onPostExecute(result);
			LoginCheck(result);
		}
	}

	private void LoginCheck(ResultRetrun resultRetrun) {

		userNameValue = et_username.getText().toString();
		passwordValue = et_userpassword.getText().toString();
		SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();

		if (resultRetrun.getResultCode() != null) {
			if (resultRetrun.getResultCode() == 1) {
				loginNum = 1;
				System.out.println(">>>>>>>>>>>>>>");
				Toast.makeText(getApplicationContext(), "登录成功！",
						Toast.LENGTH_SHORT).show();
				Welcome.userName = userNameValue;
				// 保存用户名和密码
				editor.putString("USER_NAME", userNameValue);
				editor.putString("PASSWORD", passwordValue);
				editor.putInt("loginNum", loginNum);
				
				int ii = sp.getInt("loginNum", 0);
				System.out.println(ii);
				// 是否记住密码
				if (remember.isChecked()) {
					editor.putBoolean("remember", true);
				} else {
					editor.putBoolean("remember", false);
				}

				editor.commit();

				// Toast.makeText(LoginActivity.this, "登录成功！",
				// Toast.LENGTH_SHORT).show();

				dialog.cancel();

				// 跳转
				Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				startActivity(intent);
				LoginActivity.this.finish();

			} else if (resultRetrun.getResultCode() == 0) {
				dialog.cancel();
				Toast.makeText(getApplicationContext(), "用户名或密码错误！",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			dialog.cancel();
			Toast.makeText(getApplicationContext(), "无法连接服务器！",
					Toast.LENGTH_SHORT).show();
		}
	}

	// 判断是否联网；
	private boolean NetWorkConnection() {
		boolean netw = false;
		ConnectivityManager con = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
		boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.isConnectedOrConnecting();
		boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.isConnectedOrConnecting();
		if (wifi | internet) {
			// 执行相关操作
			netw = true;

		} else {
			Toast.makeText(getApplicationContext(), "亲，您没有连接网络！",
					Toast.LENGTH_LONG).show();
		}
		return netw;
	}

	/**
	 * 
	 * md5加密
	 * 
	 */
	public static String md5Encode(String inStr) throws Exception {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}

		byte[] byteArray = inStr.getBytes("UTF-8");
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	
	private long exitTime = 0;
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}