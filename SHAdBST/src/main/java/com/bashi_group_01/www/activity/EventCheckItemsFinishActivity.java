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
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bashi_group_01.www.adapter.HistroyAdapter;
import com.bashi_group_01.www.domain.EventTaskInfo;
import com.bashi_group_01.www.util.PasueTaskInfo;
import com.example.asyncloadimage.AsynImageLoader;
/**
 * 事务查询中的事务完成类;
 * @author share
 *
 */
public class EventCheckItemsFinishActivity extends Activity implements
		OnClickListener {

	private LinearLayout tv_wancheng_event, ll_wancheng_event;
	private ImageView event_rightimg;
	private ImageView titleback, homeback, pic1, pic2, pic3;
	private TextView createUser, state, location, createTime, taskDesc;
	private List<EventTaskInfo> list;
	private ListView listView;
	private BaseAdapter adapter;
	
	private String taskInfoJson;

	private FrameLayout layout;
	private View mAnimView;
	String voiceUrl;
	String pic1Url;
	String pic2Url;
	String pic3Url;
	private TextView createPerson,resolverPerson,aduitPerson;

	String taskid;
	String type;
	String url_taskId = "http://116.236.170.106:8384/FunctionModule/RemoteData/TransHandler.ashx?userKey="+Welcome.userName;
	String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_check_items_finish);
		initViews();
		initEvent();
		initGetChuLiData();
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
				asynImageLoader1.showImageAsyn(pic1, pic1Url, R.drawable.addimg);
			}
			if (list.get(0).getPicture().size() > 1) {
				pic2Url = list.get(0).getPicture().get(1)
						.replace(".jpg", "3.jpg");
				asynImageLoader1.showImageAsyn(pic2, pic2Url, R.drawable.addimg);
			}
			if (list.get(0).getPicture().size() > 2) {
				pic3Url = list.get(0).getPicture().get(2)
						.replace(".jpg", "3.jpg");
				asynImageLoader1.showImageAsyn(pic3, pic3Url, R.drawable.addimg);
			}
			
			adapter = new HistroyAdapter(this, list.get(0).getResolveComment());
			listView.setAdapter(adapter);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initGetChuLiData() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		type = intent.getStringExtra("type");
		taskid = intent.getStringExtra("taskId");
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		this.titleback.setOnClickListener(this);
		this.homeback.setOnClickListener(this);
		this.pic1.setOnClickListener(this);
		this.pic2.setOnClickListener(this);
		this.pic3.setOnClickListener(this);

		this.tv_wancheng_event.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (ll_wancheng_event.getVisibility() == View.VISIBLE) {
					ll_wancheng_event.setVisibility(View.GONE);
					event_rightimg.setImageResource(R.drawable.droptop);
					return;
				}
				if (ll_wancheng_event.getVisibility() == View.GONE) {
					ll_wancheng_event.setVisibility(View.VISIBLE);
					event_rightimg.setImageResource(R.drawable.dropbottom);
					return;
				}
			}
		});
	}

	private void initViews() {
		// TODO Auto-generated method stub
		list = new ArrayList<EventTaskInfo>();
		this.tv_wancheng_event = (LinearLayout) findViewById(R.id.tv_eventfinish_event);
		this.ll_wancheng_event = (LinearLayout) findViewById(R.id.ll_eventfinish_event);
		this.createUser = (TextView) findViewById(R.id.eventcheckitems_wancheng_shiwu_createname);
		this.createTime = (TextView) findViewById(R.id.eventcheckitems_wancheng_shiwu_date);
		this.location = (TextView) findViewById(R.id.eventcheckitems_wancheng_shiwu_location);
		this.taskDesc = (TextView) findViewById(R.id.eventcheckitems_wancheng_shiwu_content);
		this.pic1 = (ImageView) findViewById(R.id.eventcheckitems_wancheng_shiwu_pic1);
		this.pic2 = (ImageView) findViewById(R.id.eventcheckitems_wancheng_shiwu_pic2);
		this.pic3 = (ImageView) findViewById(R.id.eventcheckitems_wancheng_shiwu_pic3);
		this.titleback = (ImageView) findViewById(R.id.titleback_eventchackitems_wancheng);
		this.homeback = (ImageView) findViewById(R.id.img_eventchackitems_backtohome_wancheng);
		this.event_rightimg = (ImageView) findViewById(R.id.img_eventwancheng_event_rightimg);
		this.layout = (FrameLayout) findViewById(R.id.id_recorder_length_wancheng);
		this.listView = (ListView) findViewById(R.id.listview_event_finish_histroy);
		this.createPerson = (TextView) findViewById(R.id.tv_eventfinish_history_createperson);
		this.resolverPerson = (TextView) findViewById(R.id.tv_eventfinish_history_reslperson);
		this.aduitPerson = (TextView) findViewById(R.id.tv_eventfinish_history_aduitperson);
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
		case R.id.titleback_eventchackitems_wancheng:
			EventCheckItemsFinishActivity.this.finish();
			break;

		case R.id.img_eventchackitems_backtohome_wancheng:
			Intent intent = new Intent(EventCheckItemsFinishActivity.this,
					MainActivity.class);
			startActivity(intent);
			break;
		case R.id.eventcheckitems_wancheng_shiwu_pic1:
			Intent intent3 = new Intent(EventCheckItemsFinishActivity.this,
					CheckPicBigActivity.class);
			intent3.putExtra("pic", pic1Url);
			startActivity(intent3);
			break;
		case R.id.eventcheckitems_wancheng_shiwu_pic2:
			Intent intent4 = new Intent(EventCheckItemsFinishActivity.this,
					CheckPicBigActivity.class);
			intent4.putExtra("pic", pic2Url);
			startActivity(intent4);
			break;
		case R.id.eventcheckitems_wancheng_shiwu_pic3:
			Intent intent5 = new Intent(EventCheckItemsFinishActivity.this,
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

			createUser.setText(result.get(0).getCreateUser());
			createTime.setText(result.get(0).getCreateTime().substring(5, 16));
			location.setText(result.get(0).getLocation());
			taskDesc.setText(result.get(0).getTaskDesc());
			
			createPerson.setText(result.get(0).getCreateUser());
			resolverPerson.setText(result.get(0).getResolveUser());
			aduitPerson.setText(result.get(0).getAuditUser());
			
			voiceUrl = result.get(0).getVoicePath().get(0);
			//System.out.println(result.get(0).getVoicePath().size());

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
								.findViewById(R.id.id_recorder_anim_wancheng);
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
