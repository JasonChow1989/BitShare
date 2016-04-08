package com.bashi_group_01.www.childactivity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.bashi_group_01.www.activity.MainActivity;
import com.bashi_group_01.www.activity.MeetingNotieItemActivitty;
import com.bashi_group_01.www.activity.R;
import com.bashi_group_01.www.activity.Welcome;
import com.bashi_group_01.www.adapter.MeetingAdapter;
import com.bashi_group_01.www.adapter.NewsAdapter;
import com.bashi_group_01.www.domain.Meetting;
import com.bashi_group_01.www.domain.News;
import com.bashi_group_01.www.util.PasueMeettings;
import com.bashi_group_01.www.util.PasueNews;

/**
 * 会议通知类;
 * 
 * @author share
 * 
 */
public class MeetnotiyActivity extends Activity implements OnClickListener {

	private String url = "http://116.236.170.106:8384/FunctionModule/RemoteData/InformationHandler.ashx?startIndex=0&pageSize=100&type=1&userkey=";
	private ImageView titleback;
	private ListView listView;
	private MeetingAdapter adapter;
	private List<Meetting> list;
	private String mettingjson;
	private SharedPreferences sp1;
	private String userName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meetnotiy);
		initViews();
		initEvents();
		System.out.println(url + userName);

		new HttpMeettings().execute(url + userName);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("id",
						((Meetting) adapter.getItem(position)).getTblrcdid());
				intent.putExtra("username", userName);

				intent.setClass(MeetnotiyActivity.this,
						MeetingNotieItemActivitty.class);
				startActivity(intent);
				overridePendingTransition(R.anim.others_getin,
						R.anim.others_getout);
			}
		});
	}

	private void initEvents() {
		this.titleback.setOnClickListener(this);
	}

	private void initViews() {
		// TODO Auto-generated method stub
		this.listView = (ListView) findViewById(R.id.listview_meeting);
		this.titleback = (ImageView) findViewById(R.id.titleback_meeting);
		sp1 = getSharedPreferences("userInfo", MODE_PRIVATE);
		userName = sp1.getString("USER_NAME", "");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		Intent intent = new Intent();
		switch (id) {
		case R.id.titleback_meeting:
			intent.setClass(MeetnotiyActivity.this, MainActivity.class);
			startActivity(intent);
			MeetnotiyActivity.this.finish();
			break;
		case R.id.img_backtohome_meeting:
			intent.setClass(MeetnotiyActivity.this, MainActivity.class);
			startActivity(intent);
			break;
		}
	}

	class HttpMeettings extends AsyncTask<String, Void, String> {

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

					mettingjson = new String(bos.toByteArray(), "UTF-8");
					System.out.println(mettingjson);

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
			return mettingjson;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// System.out.println(result);
			list = PasueMeettings.Pasue(result);
			// System.out.println(list.size());
			adapter = new MeetingAdapter(MeetnotiyActivity.this, list);
			listView.setAdapter(adapter);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			Intent intent = new Intent(MeetnotiyActivity.this,
					MainActivity.class);
			startActivity(intent);
			MeetnotiyActivity.this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
