package com.bashi_group_01.www.activity;

import java.io.ByteArrayOutputStream;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bashi_group_01.www.childactivity.FileMakeActivity;
import com.bashi_group_01.www.domain.HeTongDetail;
import com.bashi_group_01.www.sliding.lib.ClearEditText;
import com.bashi_group_01.www.util.CustomProgressDialog;
import com.bashi_group_01.www.util.PasueHeTongDetail;

/**
 * 合同管理的详情类;
 * 
 * @author share
 * 
 */
public class EventHeTongDetailActivity extends Activity implements
		OnClickListener {

	private String url = "http://116.236.170.106:8384/FunctionModule/RemoteData/GetContractHandler.ashx?startIndex=0&pagesize=1000&querytime=0&userkey=";
	private String url_new;
	private ImageView imageView, homeback;
	private String hetongJson;
	private List<HeTongDetail> list;
	private TextView hetongcode, faceDanwei, hetongContent, hetongType,
			startdate, enddate, makeperson, money, payway, level;
	private Button mButtonAgree, mButtonDisAgree;
	private CustomProgressDialog mDialog;
	private String id;
	private String agreeJsonString;

	private LinearLayout ly_passflag;
	private String username;

	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_he_tong_detail);
		init();
		initEvents();
		Intent intent = getIntent();
		id = intent.getStringExtra("id");

		url_new = url + username + "&id=" + id;
		new HttpFaWenDetails().execute(url_new);
		System.out.println(url_new);

		mButtonAgree.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showSendMsgDialog("1");
			}
		});

		mButtonDisAgree.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showSendMsgDialog("0");
			}
		});
	}

	private void initEvents() {
		imageView.setOnClickListener(this);
		homeback.setOnClickListener(this);
	}

	private void init() {
		imageView = (ImageView) findViewById(R.id.titleback_hetong_items);
		homeback = (ImageView) findViewById(R.id.img_backtohome_hetong_items);
		this.hetongcode = (TextView) findViewById(R.id.hetong_items_hetongcode);
		this.faceDanwei = (TextView) findViewById(R.id.hetong_items_duifangdanwei);
		this.hetongContent = (TextView) findViewById(R.id.hetong_items_hetongcontent);
		this.hetongType = (TextView) findViewById(R.id.hetong_items_hetongtype);
		this.startdate = (TextView) findViewById(R.id.hetong_items_startdate);
		this.enddate = (TextView) findViewById(R.id.hetong_items_enddate);
		this.makeperson = (TextView) findViewById(R.id.hetong_items_makepeople);
		this.money = (TextView) findViewById(R.id.hetong_items_money);
		this.payway = (TextView) findViewById(R.id.hetong_items_sellsway);
		this.level = (TextView) findViewById(R.id.hetong_items_hetonglevel);
		mButtonAgree = (Button) findViewById(R.id.id_hetong_agree);
		mButtonDisAgree = (Button) findViewById(R.id.id_hetong_disagree);
		ly_passflag = (LinearLayout) findViewById(R.id.id_hetong_passflag);

		sp = getSharedPreferences("userInfo", MODE_PRIVATE);
		username = sp.getString("USER_NAME", "");
	}

	protected void showSendMsgDialog(final String AgreeString) {
		// TODO Auto-generated method stub

		final ClearEditText editText;
		Button button;

		final AlertDialog builder;
		WindowManager manager = getWindowManager();
		Display display = manager.getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();

		builder = new AlertDialog.Builder(EventHeTongDetailActivity.this)
				.create();
		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.dialog_meetting_agreement, null);
		builder.setView(view, 0, 0, 0, 0);
		editText = (ClearEditText) view.findViewById(R.id.id_dialog_meeting_ed);
		button = (Button) view.findViewById(R.id.id_dialog_meeting_bt);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String content = editText.getText().toString().trim();
				System.out.println("发送");
				new AgreeMentAsyn()
						.execute("http://116.236.170.106:8384/FunctionModule/RemoteData/ProcessContractHandler.ashx?userkey="
								+ Welcome.userName
								+ "&id="
								+ id
								+ "&result="
								+ AgreeString + "&opinion=" + content);
				builder.cancel();
			}
		});

		builder.show();
		builder.getWindow().setLayout(width, height / 2 - 5);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.titleback_hetong_items:
			Intent intent1 = new Intent(this, FileMakeActivity.class);
			startActivity(intent1);
			finish();
			break;
		case R.id.img_backtohome_hetong_items:
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			break;
		}
	}

	class HttpFaWenDetails extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			InputStream inputStream = null;
			ByteArrayOutputStream bos = null;
			try {
				URL url = new URL(params[0]);
				// System.out.println(url);

				HttpURLConnection httpURLConnection = (HttpURLConnection) url
						.openConnection();

				httpURLConnection.setConnectTimeout(5000);
				httpURLConnection.setRequestMethod("GET");

				if (httpURLConnection.getResponseCode() == 200) {

					inputStream = httpURLConnection.getInputStream();
					// System.out.println(inputStream);
					bos = new ByteArrayOutputStream();
					byte[] buffer = new byte[1024];

					int len = -1;
					while ((len = inputStream.read(buffer)) != -1) {
						bos.write(buffer, 0, len);
					}
					hetongJson = new String(bos.toByteArray(), "UTF-8");
					System.out.println(hetongJson);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (bos != null) {
						bos.close();
					}
					if (inputStream != null) {
						inputStream.close();
					}
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
			return hetongJson;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// System.out.println(result);
			list = PasueHeTongDetail.Pasue(result);
			hetongcode.setText(list.get(0).getContractId());
			faceDanwei.setText(list.get(0).getCutomer());
			hetongContent.setText(list.get(0).getAdContent());
			hetongType.setText(list.get(0).getContractType());
			startdate.setText(list.get(0).getStrartDate());
			enddate.setText(list.get(0).getEndDate());
			makeperson.setText(list.get(0).getSeller());
			money.setText(list.get(0).getContractMoney());
			payway.setText(list.get(0).getPayType());
			level.setText(list.get(0).getBigType());
			
			if(list.get(0).isPassflag()){
				ly_passflag.setVisibility(View.INVISIBLE);
			}
		}
	}

	class AgreeMentAsyn extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mDialog = new CustomProgressDialog(EventHeTongDetailActivity.this,
					null, R.anim.frame);
			mDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			InputStream inputStream = null;
			ByteArrayOutputStream bos = null;
			try {
				URL url = new URL(params[0]);
				System.out.println(url);
				HttpURLConnection httpURLConnection = (HttpURLConnection) url
						.openConnection();

				httpURLConnection.setConnectTimeout(5000);
				httpURLConnection.setRequestMethod("GET");

				if (httpURLConnection.getResponseCode() == 200) {

					inputStream = httpURLConnection.getInputStream();
					// System.out.println(inputStream);
					bos = new ByteArrayOutputStream();
					byte[] buffer = new byte[1024];
					int len = -1;

					while ((len = inputStream.read(buffer)) != -1) {
						bos.write(buffer, 0, len);
					}
					agreeJsonString = new String(bos.toByteArray(), "UTF-8");
					System.out.println(agreeJsonString);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				try {
					if (bos != null) {
						bos.close();
					}
					if (inputStream != null) {
						inputStream.close();
					}
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
			return agreeJsonString;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mDialog.cancel();
			try {
				JSONObject jsonObject = new JSONObject(result);
				String code = jsonObject.getString("resultCode");
				if ("1".equals(code)) {
					Toast.makeText(EventHeTongDetailActivity.this,
							jsonObject.getString("result"), Toast.LENGTH_LONG)
							.show();
					EventHeTongDetailActivity.this.finish();
				} else {
					Toast.makeText(EventHeTongDetailActivity.this,
							jsonObject.getString("result"), Toast.LENGTH_LONG)
							.show();
					EventHeTongDetailActivity.this.finish();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			Intent intent = new Intent(EventHeTongDetailActivity.this,
					FileMakeActivity.class);
			startActivity(intent);
			EventHeTongDetailActivity.this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
