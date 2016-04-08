package com.bashi_group_01.www.activity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.webkit.JsResult;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bashi_group_01.www.childactivity.EnmeryActivity;
import com.bashi_group_01.www.childactivity.FileMakeActivity;
import com.bashi_group_01.www.childactivity.MeetnotiyActivity;
import com.bashi_group_01.www.domain.MeettingDetail;
import com.bashi_group_01.www.sliding.lib.ClearEditText;
import com.bashi_group_01.www.util.CustomProgressDialog;
import com.bashi_group_01.www.util.PasueMeettingDetails;

/**
 * 会议通知详情类;
 * 
 * @author share
 * 
 */
public class MeetingNotieItemActivitty extends Activity {

	private TextView date, title, level, location, parter, issuesor,
			issuesApartment, issuesdate, beizhu;

	private ImageView titleback, homeback;
	private String mettingDetailjson;
	private List<MeettingDetail> list;
	StringBuffer sb;
	private CustomProgressDialog mDialog;

	String id;
	String username;
	private String agreeJsonString;

	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meeting_notie_item_activitty);
		initViews();
		initGetData();

		this.titleback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MeetingNotieItemActivitty.this,
						MeetnotiyActivity.class);
				startActivity(intent);
				MeetingNotieItemActivitty.this.finish();
			}
		});

		this.homeback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MeetingNotieItemActivitty.this,
						MainActivity.class);
				startActivity(intent);
			}
		});
	}

	protected void showSendMsgDialog() {
		// TODO Auto-generated method stub

		final ClearEditText editText;
		Button button;

		final AlertDialog builder;
		WindowManager manager = getWindowManager();
		Display display = manager.getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();

		builder = new AlertDialog.Builder(MeetingNotieItemActivitty.this)
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
				new AgreeMentAsyn().execute("", content);
				builder.cancel();
			}
		});
		builder.show();
		builder.getWindow().setLayout(width, height / 2 - 5);
	}

	private void initGetData() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		sp = getSharedPreferences("userInfo", MODE_PRIVATE);
		username = sp.getString("USER_NAME", "");
		new HttpMeettingDetails()
				.execute("http://116.236.170.106:8384/FunctionModule/RemoteData/InformationHandler.ashx?startIndex=0&pageSize=1000&type=1&id="
						+ id + "&userkey=" + username);
	}

	private void initViews() {
		// TODO Auto-generated method stub
		this.titleback = (ImageView) findViewById(R.id.titleback_meeting_items);
		this.homeback = (ImageView) findViewById(R.id.img_backtohome_meeting_items);
		this.date = (TextView) findViewById(R.id.meeting_items_date);
		this.title = (TextView) findViewById(R.id.meeting_items_title);
		this.level = (TextView) findViewById(R.id.meeting_items_level);
		this.location = (TextView) findViewById(R.id.meeting_items_location);
		this.parter = (TextView) findViewById(R.id.meeting_items_parter);
		this.issuesor = (TextView) findViewById(R.id.meeting_items_issusor);
		this.issuesApartment = (TextView) findViewById(R.id.meeting_items_issuesapratment);
		this.issuesdate = (TextView) findViewById(R.id.meeting_items_issuesdate);
		this.beizhu = (TextView) findViewById(R.id.meeting_items_beizhu);

	}

	class HttpMeettingDetails extends AsyncTask<String, Void, String> {

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
					mettingDetailjson = new String(bos.toByteArray(), "UTF-8");
					System.out.println(mettingDetailjson);
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
			return mettingDetailjson;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// System.out.println(result);
			list = PasueMeettingDetails.Pasue(result);
			// System.out.println(list.size());
			date.setText(list.get(0).getMeetingTime());
			title.setText(list.get(0).getTitle());
			level.setText(list.get(0).getMeetingLevel());
			location.setText(list.get(0).getMeetingPlace());
			issuesor.setText(list.get(0).getIssuer());
			issuesApartment.setText(list.get(0).getIssueDept());
			issuesdate.setText(list.get(0).getIssueDate());
			sb = new StringBuffer();
			for (int i = 0; i < list.get(0).getParticipants().size(); i++) {
				sb.append(list.get(0).getParticipants().get(i) + "  ");
			}
			parter.setText(sb);
		}
	}

	class AgreeMentAsyn extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mDialog = new CustomProgressDialog(MeetingNotieItemActivitty.this,
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
			try {
				JSONObject jsonObject = new JSONObject(result);
				String code = jsonObject.getString("resultCode");

				if ("1".equals(code) || "0".equals(code)) {
					mDialog.dismiss();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		new AgreeMentAsyn()
				.execute("http://116.236.170.106:8384/FunctionModule/RemoteData/LookInformation.ashx?userkey="
						+ username + "&id=" + id);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			Intent intent = new Intent(MeetingNotieItemActivitty.this,
					MeetnotiyActivity.class);
			startActivity(intent);
			MeetingNotieItemActivitty.this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
