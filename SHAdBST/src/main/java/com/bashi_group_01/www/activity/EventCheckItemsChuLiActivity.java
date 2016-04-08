package com.bashi_group_01.www.activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bashi_group_01.www.domain.EventTaskInfo;
import com.bashi_group_01.www.util.PasueTaskInfo;
import com.example.asyncloadimage.AsynImageLoader;
/**
 * 事务查询中的事务处理类;
 * @author share
 *
 */
public class EventCheckItemsChuLiActivity extends Activity implements
		OnClickListener {

	private ImageView titleback, homeback, pic1, pic2, pic3;
	private String taskInfoJson;
	private TextView createName, date, location, desc;
	private Button button;
	private List<EventTaskInfo> list;
	String taskid, createname, relname, aditname;
	String type;
	String url_taskId = "http://116.236.170.106:8384/FunctionModule/RemoteData/TransHandler.ashx?userKey="
			+ Welcome.userName;
	String url;
	private FrameLayout layout;

	String voiceUrl;
	View mAnimView;

	String pic1Url;
	String pic2Url;
	String pic3Url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_chack_items);
		initviews();
		initEvents();
		initGetData();
		url = url_taskId + "&type=" + type + "&taskid=" + taskid;
		System.out.println(url);
		TaskIdNeed idNeed = new TaskIdNeed();
		idNeed.execute(url);

		AsynImageLoader asynImageLoader1 = new AsynImageLoader();
		try {
			list = idNeed.get();

			if (list.get(0).getPicture().get(0) != null) {
				pic1Url = list.get(0).getPicture().get(0)
						.replace(".jpg", "3.jpg");
				asynImageLoader1
						.showImageAsyn(pic1, pic1Url, R.drawable.addimg);
			}
			if (list.get(0).getPicture().size() > 1) {
				pic2Url = list.get(0).getPicture().get(1)
						.replace(".jpg", "3.jpg");
				asynImageLoader1
						.showImageAsyn(pic2, pic2Url, R.drawable.addimg);
			}
			if (list.get(0).getPicture().size() > 2) {
				pic3Url = list.get(0).getPicture().get(2)
						.replace(".jpg", "3.jpg");
				asynImageLoader1
						.showImageAsyn(pic3, pic3Url, R.drawable.addimg);
			}

			createname = list.get(0).getCreateUser();
			relname = list.get(0).getResolveUser();
			aditname = list.get(0).getAuditUser();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initviews() {
		// TODO Auto-generated method stub
		list = new ArrayList<EventTaskInfo>();
		this.desc = (TextView) findViewById(R.id.evnetcheckitems_chuli_taskdesc);
		this.titleback = (ImageView) findViewById(R.id.img_titleback_eventchackitems_chuli);
		this.homeback = (ImageView) findViewById(R.id.img_backtohome_eventchackitems_chuli);
		this.createName = (TextView) findViewById(R.id.evnetcheckitems_chuli_createname);
		this.date = (TextView) findViewById(R.id.evnetcheckitems_chuli_date);
		this.location = (TextView) findViewById(R.id.evnetcheckitems_chuli_location);
		this.button = (Button) findViewById(R.id.evnetcheckitems_chuli_button);
		this.pic1 = (ImageView) findViewById(R.id.evnetcheckitems_chuli_pic1);
		this.pic2 = (ImageView) findViewById(R.id.evnetcheckitems_chuli_pic2);
		this.pic3 = (ImageView) findViewById(R.id.evnetcheckitems_chuli_pic3);
		this.layout = (FrameLayout) findViewById(R.id.id_recorder_length_chuli);
	}

	private void initEvents() {
		this.titleback.setOnClickListener(this);
		this.homeback.setOnClickListener(this);
		this.button.setOnClickListener(this);
		this.pic1.setOnClickListener(this);
		this.pic2.setOnClickListener(this);
		this.pic3.setOnClickListener(this);
	}

	private void initGetData() {
		Intent intent = getIntent();
		type = intent.getStringExtra("type");
		taskid = intent.getStringExtra("taskId");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.img_titleback_eventchackitems_chuli:
			finish();
			break;
		case R.id.img_backtohome_eventchackitems_chuli:
			Intent intent = new Intent();
			intent.setClass(EventCheckItemsChuLiActivity.this,
					MainActivity.class);
			startActivity(intent);
			break;
		case R.id.evnetcheckitems_chuli_button:
			Intent intent2 = new Intent();
			intent2.putExtra("createUser", createname);
			intent2.putExtra("ResolveUser", relname);
			intent2.putExtra("AuditUser", aditname);
			intent2.putExtra("type", type);
			intent2.putExtra("taskId", taskid);
			intent2.setClass(EventCheckItemsChuLiActivity.this,
					EventCheckItemsUpdateSendActivity.class);
			startActivity(intent2);
			break;
		case R.id.evnetcheckitems_chuli_pic1:
			Intent intent3 = new Intent(EventCheckItemsChuLiActivity.this,
					CheckPicBigActivity.class);
			intent3.putExtra("pic", pic1Url);
			startActivity(intent3);
			break;
		case R.id.evnetcheckitems_chuli_pic2:
			Intent intent4 = new Intent(EventCheckItemsChuLiActivity.this,
					CheckPicBigActivity.class);
			intent4.putExtra("pic", pic2Url);
			startActivity(intent4);
			break;
		case R.id.evnetcheckitems_chuli_pic3:
			Intent intent5 = new Intent(EventCheckItemsChuLiActivity.this,
					CheckPicBigActivity.class);
			intent5.putExtra("pic", pic3Url);
			startActivity(intent5);
			break;
		}
	}

	class TaskIdNeed extends AsyncTask<String, Void, List<EventTaskInfo>> {

		@Override
		protected List<EventTaskInfo> doInBackground(String... params) {
			// TODO Auto-generated method stub

			InputStream inputStream = null;
			ByteArrayOutputStream bos = null;
			try {
				HttpClient client = new DefaultHttpClient();

				HttpGet httpGet = new HttpGet(params[0]);

				HttpResponse httpResponse = client.execute(httpGet);

				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					// System.out.println("<<<<<<<<<<<<<<<");
					inputStream = httpResponse.getEntity().getContent();
					// System.out.println(inputStream);
					bos = new ByteArrayOutputStream();

					byte[] buffer = new byte[1024];

					int len = -1;
					while ((len = inputStream.read(buffer)) != -1) {
						bos.write(buffer, 0, len);
					}
					taskInfoJson = new String(bos.toByteArray(), "UTF-8");
					// System.out.println(taskInfoJson);
					list = PasueTaskInfo.PsTaskInfo(taskInfoJson);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				if (bos != null) {
					try {
						bos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
			return list;
		}

		@Override
		protected void onPostExecute(List<EventTaskInfo> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			createName.setText(result.get(0).getCreateUser());
			date.setText(result.get(0).getCreateTime().substring(5, 16));
			location.setText(result.get(0).getLocation());
			desc.setText(result.get(0).getTaskDesc());

			voiceUrl = result.get(0).getVoicePath().get(0);
			// System.out.println(voiceUrl);

			if (voiceUrl != "") {
				layout.setVisibility(View.VISIBLE);

				layout.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (mAnimView != null) {
							mAnimView = null;
						}

						mAnimView = v
								.findViewById(R.id.id_recorder_anim_shenhe);
						mAnimView
								.setBackgroundResource(R.drawable.anim_recorder_play);
						AnimationDrawable anim = (AnimationDrawable) mAnimView
								.getBackground();
						anim.start();

						try {
							MediaPlayer mediaPlayer = new MediaPlayer();
							mediaPlayer.setDataSource(voiceUrl);
							mediaPlayer.prepare();
							mediaPlayer.start();

							mediaPlayer
									.setOnCompletionListener(new OnCompletionListener() {

										@Override
										public void onCompletion(MediaPlayer mp) {
											// TODO Auto-generated method stub
											mAnimView
													.setBackgroundResource(R.drawable.adj);
										}
									});

						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SecurityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalStateException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}

		}
	}
}