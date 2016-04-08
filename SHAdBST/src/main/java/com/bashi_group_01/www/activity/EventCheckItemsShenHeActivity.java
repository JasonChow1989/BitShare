package com.bashi_group_01.www.activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bashi_group_01.www.adapter.HistroyAdapter;
import com.bashi_group_01.www.domain.EventTaskInfo;
import com.bashi_group_01.www.util.CustomProgressDialog;
import com.bashi_group_01.www.util.PasueTaskInfo;
import com.bashi_group_01.www.util.UpLoadUtil;
import com.example.asyncloadimage.AsynImageLoader;
/**
 * �����ѯ�е��������;
 * @author share
 *
 */
public class EventCheckItemsShenHeActivity extends Activity implements
		OnClickListener {

	private LinearLayout tv_shenhe_event, tv_histroy_event;
	private LinearLayout ll_shenhe_event, ll_histroy_event;
	private ImageView event_rightimg,event_rightimg2;
	private ImageView titleback, homeback, pic1, pic2, pic3;
	private TextView createUser, state, location, createTime, taskDesc;
	private List<EventTaskInfo> list;
	private String taskInfoJson;

	private FrameLayout layout, layout2;
	private View mAnimView, mAnimView2;

	String pic1Url;
	String pic2Url;
	String pic3Url;

	String voiceUrl, voiceUrl1;
	String taskid;
	String type;
	String url_taskId = "http://116.236.170.106:8384/FunctionModule/RemoteData/TransHandler.ashx?userKey="
			+ Welcome.userName;
	String url;
	private CustomProgressDialog dialog;
	private Button pass, nopass;

	Drawable drawable1;
	// rescomment;
	private ListView listView;
	private BaseAdapter adapter;
	private TextView createperson, resvorperson, aduitperson, restaskdesc;
	private ImageView rescomment_pic1, rescomment_pic2, rescomment_pic3;
	private String respic1Url, respic2Url, respic3Url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_check_items_shen_he);
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
			//System.out.println("aaaaaa>>>>>>"+list.size());
			if (list.get(0).getResolveComment().get(0).getPictures()
					.get(0) != null) {
				respic1Url = list.get(0).getResolveComment().get(0)
						.getPictures().get(0).replace(".jpg", "3.jpg");
				asynImageLoader1.showImageAsyn(rescomment_pic1, respic1Url,
						R.drawable.addimg);
			}
			if (list.get(0).getResolveComment().get(0).getPictures()
					.size() > 1) {
				respic2Url = list.get(0).getResolveComment().get(0)
						.getPictures().get(1).replace(".jpg", "3.jpg");
				asynImageLoader1.showImageAsyn(rescomment_pic2, respic2Url,
						R.drawable.addimg);
			}
			if (list.get(0).getResolveComment().get(0).getPictures()
					.size() > 2) {
				respic3Url = list.get(0).getResolveComment().get(0)
						.getPictures().get(2).replace(".jpg", "3.jpg");
				asynImageLoader1.showImageAsyn(rescomment_pic3, respic3Url,
						R.drawable.addimg);
			}
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

			if (list.get(0).getResolveComment().size() > 1) {
				tv_histroy_event.setVisibility(View.VISIBLE);
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
		this.pass.setOnClickListener(this);
		this.nopass.setOnClickListener(this);
		this.rescomment_pic1.setOnClickListener(this);
		this.rescomment_pic2.setOnClickListener(this);
		this.rescomment_pic3.setOnClickListener(this);

		this.tv_shenhe_event.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (ll_shenhe_event.getVisibility() == View.VISIBLE) {
					ll_shenhe_event.setVisibility(View.GONE);
					event_rightimg.setImageResource(R.drawable.droptop);
					return;
				}
				if (ll_shenhe_event.getVisibility() == View.GONE) {
					ll_shenhe_event.setVisibility(View.VISIBLE);
					event_rightimg.setImageResource(R.drawable.dropbottom);
					return;
				}
			}
		});

		this.tv_histroy_event.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (ll_histroy_event.getVisibility() == View.VISIBLE) {
					ll_histroy_event.setVisibility(View.GONE);
					event_rightimg2.setImageResource(R.drawable.droptop);
					return;
				}
				if (ll_histroy_event.getVisibility() == View.GONE) {
					ll_histroy_event.setVisibility(View.VISIBLE);
					event_rightimg2.setImageResource(R.drawable.dropbottom);
					return;
				}
			}
		});

	}

	private void initViews() {
		// TODO Auto-generated method stub
		list = new ArrayList<EventTaskInfo>();
		this.tv_shenhe_event = (LinearLayout) findViewById(R.id.tv_eventshenhe_event);
		this.tv_histroy_event = (LinearLayout) findViewById(R.id.tv_eventshenhe_hostroyaaa);
		this.ll_histroy_event = (LinearLayout) findViewById(R.id.ll_eventshenhe_hostroyaaa);
		this.ll_shenhe_event = (LinearLayout) findViewById(R.id.ll_eventshenhe_event);
		this.state = (TextView) findViewById(R.id.eventcheckitems_shenhe_shiwu_state);
		this.createUser = (TextView) findViewById(R.id.eventcheckitems_shenhe_shiwu_createname);
		this.createTime = (TextView) findViewById(R.id.eventcheckitems_shenhe_shiwu_date);
		this.location = (TextView) findViewById(R.id.eventcheckitems_shenhe_shiwu_location);
		this.taskDesc = (TextView) findViewById(R.id.eventcheckitems_shenhe_shiwu_content);
		this.pic1 = (ImageView) findViewById(R.id.eventcheckitems_shenhe_shiwu_pic1);
		this.pic2 = (ImageView) findViewById(R.id.eventcheckitems_shenhe_shiwu_pic2);
		this.pic3 = (ImageView) findViewById(R.id.eventcheckitems_shenhe_shiwu_pic3);
		this.titleback = (ImageView) findViewById(R.id.titleback_eventchackitems_shenhe);
		this.homeback = (ImageView) findViewById(R.id.img_eventchackitems_backtohome_shenhe);
		this.event_rightimg = (ImageView) findViewById(R.id.img_eventshenhe_event_rightimg);
		this.event_rightimg2 = (ImageView) findViewById(R.id.img_eventshenhe_hostroyaaa_rightimg);
		this.pass = (Button) findViewById(R.id.bt_eventshenhe_pass);
		this.nopass = (Button) findViewById(R.id.bt_eventshenhe_nopass);
		this.layout = (FrameLayout) findViewById(R.id.id_recorder_length_shenhe);
		this.layout2 = (FrameLayout) findViewById(R.id.id_recorder_length_shenhe1);
		this.createperson = (TextView) findViewById(R.id.tv_eventshenhe_rescomment_createperson);
		this.resvorperson = (TextView) findViewById(R.id.tv_eventshenhe_rescomment_resolveser);
		this.aduitperson = (TextView) findViewById(R.id.tv_eventshenhe_rescomment_audituser);
		this.rescomment_pic1 = (ImageView) findViewById(R.id.img_eventshenhe_rescomment_pic1);
		this.rescomment_pic2 = (ImageView) findViewById(R.id.img_eventshenhe_rescomment_pic2);
		this.rescomment_pic3 = (ImageView) findViewById(R.id.img_eventshenhe_rescomment_pic3);
		this.restaskdesc = (TextView) findViewById(R.id.tv_eventshenhe_rescomment_taskdesc);
		this.listView = (ListView) findViewById(R.id.listview_eventshenhe_histroy);
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
		case R.id.titleback_eventchackitems_shenhe:
			EventCheckItemsShenHeActivity.this.finish();
			break;

		case R.id.img_eventchackitems_backtohome_shenhe:
			Intent intent = new Intent(EventCheckItemsShenHeActivity.this,
					MainActivity.class);
			startActivity(intent);
			break;
		case R.id.eventcheckitems_shenhe_shiwu_pic1:
			Intent intent3 = new Intent(EventCheckItemsShenHeActivity.this,
					CheckPicBigActivity.class);
			intent3.putExtra("pic", pic1Url);
			startActivity(intent3);
			break;
		case R.id.eventcheckitems_shenhe_shiwu_pic2:
			Intent intent4 = new Intent(EventCheckItemsShenHeActivity.this,
					CheckPicBigActivity.class);
			intent4.putExtra("pic", pic2Url);
			startActivity(intent4);
			break;
		case R.id.eventcheckitems_shenhe_shiwu_pic3:
			Intent intent5 = new Intent(EventCheckItemsShenHeActivity.this,
					CheckPicBigActivity.class);
			intent5.putExtra("pic", pic3Url);
			startActivity(intent5);
			break;
		case R.id.img_eventshenhe_rescomment_pic1:
			Intent intent6 = new Intent(EventCheckItemsShenHeActivity.this,
					CheckPicBigActivity.class);
			intent6.putExtra("pic", respic1Url);
			startActivity(intent6);
			break;
		case R.id.img_eventshenhe_rescomment_pic2:
			Intent intent7 = new Intent(EventCheckItemsShenHeActivity.this,
					CheckPicBigActivity.class);
			intent7.putExtra("pic", respic2Url);
			startActivity(intent7);
			break;
		case R.id.img_eventshenhe_rescomment_pic3:
			Intent intent8 = new Intent(EventCheckItemsShenHeActivity.this,
					CheckPicBigActivity.class);
			intent8.putExtra("pic", respic3Url);
			startActivity(intent8);
			break;
		case R.id.bt_eventshenhe_pass:
			Builder builder = new Builder(
					EventCheckItemsShenHeActivity.this);
			builder.setTitle("��ʾ");
			builder.setMessage("ȷ��ͨ��?");
			builder.setPositiveButton("ȷ��",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							new AsyUpLoadingPic()
									.execute(
											"http://116.236.170.106:8384/FunctionModule/RemoteData/UpdateTransHandler.ashx",
											"2");
							dialog.dismiss();
						}
					});
			builder.setNegativeButton("ȡ��",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
			builder.show();

			break;
		case R.id.bt_eventshenhe_nopass:

			Builder builder1 = new Builder(
					EventCheckItemsShenHeActivity.this);
			builder1.setTitle("��ʾ");
			builder1.setMessage("ȷ����ͨ��?");
			builder1.setPositiveButton("ȷ��",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							new AsyUpLoadingPic()
									.execute(
											"http://116.236.170.106:8384/FunctionModule/RemoteData/UpdateTransHandler.ashx",
											"0");
							dialog.dismiss();
						}
					});
			builder1.setNegativeButton("ȡ��",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
			builder1.show();
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

			voiceUrl = result.get(0).getVoicePath().get(0);

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

			createperson.setText(result.get(0).getCreateUser());
			resvorperson.setText(result.get(0).getResolveUser());
			aduitperson.setText(result.get(0).getAuditUser());

			restaskdesc.setText(result.get(0).getResolveComment().get(0)
					.getComment());
			voiceUrl1 = result.get(0).getResolveComment().get(0)
					.getVoicepath().get(0);
			
			if (voiceUrl1 != "") {
				layout2.setVisibility(View.VISIBLE);

				layout2.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (mAnimView2 != null) {
							mAnimView2 = null;
						}

						mAnimView2 = v
								.findViewById(R.id.id_recorder_anim_shenhe1);
						mAnimView2
								.setBackgroundResource(R.drawable.anim_recorder_play);
						AnimationDrawable anim = (AnimationDrawable) mAnimView2
								.getBackground();
						anim.start();

						try {
							MediaPlayer mediaPlayer = new MediaPlayer();
							mediaPlayer.setDataSource(voiceUrl1);
							mediaPlayer.prepare();
							mediaPlayer.start();

							mediaPlayer
									.setOnCompletionListener(new OnCompletionListener() {

										@Override
										public void onCompletion(MediaPlayer mp) {
											// TODO Auto-generated method stub
											mAnimView2
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

	class AsyUpLoadingPic extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {

			String charset = HTTP.UTF_8;
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			// builder.setCharset(Charset.forName(charset));

			builder.addTextBody("Type", type,
					ContentType.create("text/plain", Charset.forName(charset)));
			builder.addTextBody("UserKey", Welcome.userName,
					ContentType.create("text/plain", Charset.forName(charset)));
			builder.addTextBody("taskId", list.get(0).getTaskId(),
					ContentType.create("text/plain", Charset.forName(charset)));
			builder.addTextBody("nextstate", params[1],
					ContentType.create("text/plain", Charset.forName(charset)));

			HttpEntity entity = builder.build();// ���� HTTP POST ʵ��
			String rebackHtml = UpLoadUtil.uploadFile(params[0], entity);
			// System.out.println(rebackHtml);

			return rebackHtml;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			System.out.println(result);
			if (!"�ļ��ϴ�ʧ��".equals(result)) {
				dialog.dismiss();
				new Builder(EventCheckItemsShenHeActivity.this)
						.setMessage("���ͳɹ�!")
						.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										EventCheckItemsShenHeActivity.this
												.finish();
									}
								}).show();
			} else {
				new Builder(EventCheckItemsShenHeActivity.this)
						.setMessage("����ʧ��!")
						.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										EventCheckItemsShenHeActivity.this
												.finish();
									}
								}).show();
			}
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new CustomProgressDialog(
					EventCheckItemsShenHeActivity.this, null, R.anim.frame);
			dialog.show();
		}
	}
}
