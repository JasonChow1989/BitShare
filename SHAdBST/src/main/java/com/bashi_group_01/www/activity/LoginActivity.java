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
 * ��¼�ࣻ
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
	private int loginNum;// Ĭ�ϵ�һ�ε�½��
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

		// ����ϴ�ѡ�˼�ס���룬�ǽ����¼ҳ��Ҳ�Զ���ѡ��ס���룬�������û���������
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
					dialog.setTitle("��¼");
					dialog.setMessage("���ڵ�¼,���Ժ�....");
					dialog.show();
				}
			}
		});

	}

	private void initViews() {
		// ��ʼ���û��������롢��ס���롢�Զ���¼����¼��ť
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
				Toast.makeText(getApplicationContext(), "��¼�ɹ���",
						Toast.LENGTH_SHORT).show();
				Welcome.userName = userNameValue;
				// �����û���������
				editor.putString("USER_NAME", userNameValue);
				editor.putString("PASSWORD", passwordValue);
				editor.putInt("loginNum", loginNum);
				
				int ii = sp.getInt("loginNum", 0);
				System.out.println(ii);
				// �Ƿ��ס����
				if (remember.isChecked()) {
					editor.putBoolean("remember", true);
				} else {
					editor.putBoolean("remember", false);
				}

				editor.commit();

				// Toast.makeText(LoginActivity.this, "��¼�ɹ���",
				// Toast.LENGTH_SHORT).show();

				dialog.cancel();

				// ��ת
				Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				startActivity(intent);
				LoginActivity.this.finish();

			} else if (resultRetrun.getResultCode() == 0) {
				dialog.cancel();
				Toast.makeText(getApplicationContext(), "�û������������",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			dialog.cancel();
			Toast.makeText(getApplicationContext(), "�޷����ӷ�������",
					Toast.LENGTH_SHORT).show();
		}
	}

	// �ж��Ƿ�������
	private boolean NetWorkConnection() {
		boolean netw = false;
		ConnectivityManager con = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
		boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.isConnectedOrConnecting();
		boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.isConnectedOrConnecting();
		if (wifi | internet) {
			// ִ����ز���
			netw = true;

		} else {
			Toast.makeText(getApplicationContext(), "�ף���û���������磡",
					Toast.LENGTH_LONG).show();
		}
		return netw;
	}

	/**
	 * 
	 * md5����
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
				Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����",
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