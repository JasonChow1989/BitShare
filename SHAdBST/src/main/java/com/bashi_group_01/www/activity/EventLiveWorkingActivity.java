package com.bashi_group_01.www.activity;

import java.io.File;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Thumbnails;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.bashi_group_01.www.Recorder.AudioRecorderButton;
import com.bashi_group_01.www.Recorder.AudioRecorderButton.AudioFinishRecorderListener;
import com.bashi_group_01.www.Recorder.MediaManager;
import com.bashi_group_01.www.activity.EventGetAndDoActivity.AsyUpLoadingPic;
import com.bashi_group_01.www.activity.EventGetAndDoActivity.Recorder;
import com.bashi_group_01.www.sliding.lib.ClearEditText;
import com.bashi_group_01.www.util.CustomProgressDialog;
import com.bashi_group_01.www.util.UpLoadUtil;
import com.photo.choosephotos.adapter.AddImageGridAdapter;
import com.photo.choosephotos.controller.SelectPicPopupWindow;
import com.photo.choosephotos.photo.Item;
import com.photo.choosephotos.photo.PhotoAlbumActivity;
import com.photo.choosephotos.photoviewer.photoviewerinterface.ViewPagerActivity;
import com.photo.choosephotos.photoviewer.photoviewerinterface.ViewPagerDeleteActivity;
import com.photo.choosephotos.util.PictureManageUtil;
/**
 * 现场办公;
 * @author share
 *
 */
public class EventLiveWorkingActivity extends Activity {

	public static final int REQ_CODE_ALBUM = 1;
	public static final int REQ_CODE_CAMERA = 2;
	public static final int REQ_CODE_PIC = 3;
	private LocationClient mLocationClient;
	private ClearEditText textView, eventContent;
	private TextView tv_date;
	private ImageView titleback_eventstart, img_refrush_loc;
	SimpleDateFormat df;
	private static final int msgKey1 = 1;
	public static int img = 0;
	private ListView listView1;
	private Button bt_mEventSend;
	private ArrayAdapter<Recorder> mAdapter;
	private List<Recorder> mDat = new ArrayList<Recorder>();
	private AudioRecorderButton mAudioRecorderButton;
	private View mAnimView;
	String result;
	LocationClientOption option;
	CustomProgressDialog dialog;

	Dialog mdialog;
	Builder builder;
	int p;

	/*
	 * addPicImgs
	 */
	/* 鐢ㄦ潵鏍囪瘑璇锋眰鐓х浉鍔熻兘鐨刟ctivity */
	private final int CAMERA_WITH_DATA = 3023;
	/* 鐢ㄦ潵鏍囪瘑璇锋眰gallery鐨刟ctivity */
	private final int PHOTO_PICKED_WITH_DATA = 3021;
	// GridView棰勮鍒犻櫎椤甸潰杩囨潵
	private final int PIC_VIEW_CODE = 2016;
	/* 鎷嶇収鐨勭収鐗囧瓨鍌ㄤ綅缃� */
	private final File PHOTO_DIR = new File(
			Environment.getExternalStorageDirectory()
					+ "/Android/data/com.photo.choosephotos");
	private File mCurrentPhotoFile;
	private ArrayList<Bitmap> microBmList = new ArrayList<Bitmap>();
	private ArrayList<Item> photoList = new ArrayList<Item>();
	private AddImageGridAdapter imgAdapter;
	private Bitmap addNewPic;
	private GridView gridView;
	private SelectPicPopupWindow menuWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_live_working);
		initViews();
		bt_mEventSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mDat.size() != 0) {
					System.out.println(mDat.size());
					Builder builder = new Builder(
							EventLiveWorkingActivity.this);
					builder.setTitle("提示");
					builder.setMessage("确定要发送吗？");
					builder.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									new AsyUpLoadingPic()
											.execute("http://116.236.170.106:8384/FunctionModule/RemoteData/ProcessRequestHandler.ashx");
									dialog.dismiss();
								}
							});
					builder.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							});
					builder.show();
				} else {
					Builder builder = new Builder(
							EventLiveWorkingActivity.this);
					builder.setTitle("提示");
					builder.setMessage("语音不能为空");
					builder.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							});

					builder.show();
				}
			}
		});

		/**
		 * addpicImgs
		 */
		if (!(PHOTO_DIR.exists() && PHOTO_DIR.isDirectory())) {
			PHOTO_DIR.mkdirs();
		}
		gridView = (GridView) findViewById(R.id.allPic2);
		addNewPic = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.addimg);
		// addNewPic = PictureManageUtil.resizeBitmap(addNewPic, 180, 180);
		microBmList.add(addNewPic);
		imgAdapter = new AddImageGridAdapter(this, microBmList);
		gridView.setAdapter(imgAdapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				if (position == (photoList.size())) {
					menuWindow = new SelectPicPopupWindow(
							EventLiveWorkingActivity.this, itemsOnClick);
					menuWindow.showAtLocation(EventLiveWorkingActivity.this
							.findViewById(R.id.uploadPictureLayout2),
							Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 璁剧疆layout鍦≒opupWindow涓樉绀虹殑浣嶇疆
				} else {
					Intent intent = new Intent(EventLiveWorkingActivity.this,
							ViewPagerDeleteActivity.class);
					intent.putParcelableArrayListExtra("files", photoList);
					intent.putExtra(ViewPagerActivity.CURRENT_INDEX, position);
					startActivityForResult(intent, PIC_VIEW_CODE);
				}
			}
		});
	}

	private void initViews() {
		mLocationClient = ((LocationApplication) getApplication()).mLocationClient;
		this.textView = (ClearEditText) findViewById(R.id.tv_location2);
		this.eventContent = (ClearEditText) findViewById(R.id.event_content2);
		this.tv_date = (TextView) findViewById(R.id.now_date2);
		this.listView1 = (ListView) findViewById(R.id.recorder_listview2);
		this.titleback_eventstart = (ImageView) findViewById(R.id.titleback_eventstart2);
		bt_mEventSend = (Button) findViewById(R.id.bt_eventsend2);
		this.img_refrush_loc = (ImageView) findViewById(R.id.bt_loct_refrush1);
		mAudioRecorderButton = (AudioRecorderButton) findViewById(R.id.id_bt_recorderbutton2);
		mAudioRecorderButton
				.setAudioFinishRecorderListener(new AudioFinishRecorderListener() {

					@Override
					public void onFinish(float seconds, String filePath) {
						// TODO Auto-generated method stub
						Recorder recorder = new Recorder(seconds, filePath);
						mDat.add(recorder);
						mAdapter.notifyDataSetChanged();
						listView1.setSelection(mDat.size() - 1);
					}
				});

		this.mAdapter = new RecorderAdapter(EventLiveWorkingActivity.this, mDat);
		listView1.setAdapter(mAdapter);
		listView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (mAnimView != null) {
					mAnimView = null;
				}

				mAnimView = view.findViewById(R.id.id_recorder_anim);
				mAnimView.setBackgroundResource(R.drawable.anim_recorder_play);
				AnimationDrawable anim = (AnimationDrawable) mAnimView
						.getBackground();
				anim.start();

				MediaManager.playSound(mDat.get(position).filePath,
						new MediaPlayer.OnCompletionListener() {

							@Override
							public void onCompletion(MediaPlayer mp) {
								// TODO Auto-generated method stub
								mAnimView.setBackgroundResource(R.drawable.adj);
							}
						});
			}
		});
		
		
		/**
		 * 
		 * 语音的删除
		 * 
		 */
		listView1.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				p = position;

				new Builder(EventLiveWorkingActivity.this)
						.setMessage("确定要删除?")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										mAdapter.remove(mAdapter.getItem(p));
										mAdapter.notifyDataSetChanged();
										System.out.println(mDat.size());
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										dialog.dismiss();
									}
								}).show();

				return true;
			}
		});

		titleback_eventstart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EventLiveWorkingActivity.this.finish();
				img = 0;
			}
		});

		((LocationApplication) getApplication()).mLocationResult = textView;
		InitLocation();
		mLocationClient.start();
		new TimeThread().start();

		img_refrush_loc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Animation operatingAnim = AnimationUtils.loadAnimation(
						EventLiveWorkingActivity.this, R.anim.tip);
				LinearInterpolator lin = new LinearInterpolator();
				operatingAnim.setInterpolator(lin);
				if (operatingAnim != null) {
					img_refrush_loc.startAnimation(operatingAnim);
				}
				mLocationClient.start();
			}
		});

	}

	@Override
	protected void onPause() {
		super.onPause();
		MediaManager.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		MediaManager.resume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		MediaManager.release();
		img = 0;
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case msgKey1:
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd  HH:mm:ss");
				tv_date.setText(simpleDateFormat.format(new Date()));
				break;
			}
		}
	};

	private void InitLocation() {
		option = new LocationClientOption();
		option.setLocationMode(LocationMode.Battery_Saving);// 璁剧疆瀹氫綅妯″紡
		option.setCoorType("bd09ll");// 杩斿洖鐨勫畾浣嶇粨鏋滄槸鐧惧害缁忕含搴︼紝榛樿鍊糶cj02
		int span = 1000;
		option.setScanSpan(span);// 璁剧疆鍙戣捣瀹氫綅璇锋眰鐨勯棿闅旀椂闂翠负5000ms
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		mLocationClient.stop();
		super.onStop();
	}

	class TimeThread extends Thread {
		@Override
		public void run() {
			do {
				try {
					Thread.sleep(1000);
					Message msg = new Message();
					msg.what = msgKey1;
					mHandler.sendMessage(msg);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while (true);
		}
	}

	/*
	 * class Recorder { float time; String filePath;
	 * 
	 * public Recorder(float time, String filePath) { super(); this.time = time;
	 * this.filePath = filePath; }
	 * 
	 * public float getTime() { return time; }
	 * 
	 * public void setTime(float time) { this.time = time; }
	 * 
	 * public String getFilePath() { return filePath; }
	 * 
	 * public void setFilePath(String filePath) { this.filePath = filePath; }
	 * 
	 * }
	 */
	/*
	 * addPicImgs
	 */
	// 涓哄脊鍑虹獥鍙ｅ疄鐜扮洃鍚被
	private OnClickListener itemsOnClick = new OnClickListener() {
		public void onClick(View v) {
			menuWindow.dismiss();
			switch (v.getId()) {
			case R.id.btn_take_photo: {
				String status = Environment.getExternalStorageState();
				if (status.equals(Environment.MEDIA_MOUNTED)) {
					// 鍒ゆ柇鏄惁鏈塖D鍗�
					doTakePhoto();// 鐢ㄦ埛鐐瑰嚮浜嗕粠鐓х浉鏈鸿幏鍙�
				} else {
					Toast.makeText(EventLiveWorkingActivity.this, "",
							Toast.LENGTH_LONG).show();
				}
				break;
			}
			case R.id.btn_pick_photo: {
				// 鎵撳紑閫夋嫨鍥剧墖鐣岄潰
				doPickPhotoFromGallery();
				break;
			}
			default:
				break;
			}
		}
	};

	/**
	 * 鎷嶇収鑾峰彇鍥剧墖
	 * 
	 */
	protected void doTakePhoto() {
		try {
			mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());// 缁欐柊鐓х殑鐓х墖鏂囦欢鍛藉悕
			final Intent intent = getTakePickIntent(mCurrentPhotoFile);
			// Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent, CAMERA_WITH_DATA);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(this, "", Toast.LENGTH_LONG).show();
		}
	}

	public String getPhotoFileName() {
		UUID uuid = UUID.randomUUID();
		return uuid + ".jpg";
	}

	public static Intent getTakePickIntent(File f) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		return intent;
	}

	protected void doPickPhotoFromGallery() {
		try {
			final ProgressDialog dialog;
			dialog = new ProgressDialog(this);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // 璁剧疆涓哄渾褰�
			dialog.setMessage("正在加载图片...");
			dialog.setIndeterminate(false);//
			// dialog.setCancelable(true); //鎸夊洖閫�閿彇娑�
			dialog.show();
			Window window = dialog.getWindow();
			View view = window.getDecorView();
			// Tools.setViewFontSize(view,21);
			new Handler().postDelayed(new Runnable() {
				public void run() {
					dialog.dismiss();
				}

			}, 1000);
			// final Intent intent = new
			// Intent(MainActivity.this,GetAllImgFolderActivity.class);
			final Intent intent = new Intent(EventLiveWorkingActivity.this,
					PhotoAlbumActivity.class);
			startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(this, "", Toast.LENGTH_LONG).show();
		}
	}

	Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				imgAdapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case PHOTO_PICKED_WITH_DATA: {
			ArrayList<Item> tempFiles = new ArrayList<Item>();
			if (data == null)
				return;
			tempFiles = data.getParcelableArrayListExtra("fileNames");

			if (tempFiles == null) {
				return;
			}
			microBmList.remove(addNewPic);
			Bitmap bitmap;
			for (int i = 0; i < tempFiles.size(); i++) {
				bitmap = Thumbnails.getThumbnail(this
						.getContentResolver(), tempFiles.get(i).getPhotoID(),
						Thumbnails.MICRO_KIND, null);
				int rotate = PictureManageUtil
						.getCameraPhotoOrientation(tempFiles.get(i)
								.getPhotoPath());
				bitmap = PictureManageUtil.rotateBitmap(bitmap, rotate);
				if (img <= 3) {
					microBmList.add(bitmap);
					photoList.add(tempFiles.get(i));
				}
			}
			microBmList.add(addNewPic);
			imgAdapter.notifyDataSetChanged();
			break;
		}
		case CAMERA_WITH_DATA: {
			Long delayMillis = 0L;
			if (mCurrentPhotoFile == null) {
				delayMillis = 500L;
			}
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					microBmList.remove(addNewPic);
					Item item = new Item();
					item.setPhotoPath(mCurrentPhotoFile.getAbsolutePath());
					photoList.add(item);
					Bitmap bitmap = PictureManageUtil
							.getCompressBm(mCurrentPhotoFile.getAbsolutePath());
					int rotate = PictureManageUtil
							.getCameraPhotoOrientation(mCurrentPhotoFile
									.getAbsolutePath());
					bitmap = PictureManageUtil.rotateBitmap(bitmap, rotate);
					microBmList.add(bitmap);
					microBmList.add(addNewPic);
					Message msg = handler.obtainMessage(1);
					msg.sendToTarget();
				}
			}, delayMillis);
			break;
		}
		case PIC_VIEW_CODE: {
			ArrayList<Integer> deleteIndex = data
					.getIntegerArrayListExtra("deleteIndexs");
			if (deleteIndex.size() > 0) {
				for (int i = deleteIndex.size() - 1; i >= 0; i--) {
					microBmList.remove((int) deleteIndex.get(i));
					photoList.remove((int) deleteIndex.get(i));
				}
			}
			imgAdapter.notifyDataSetChanged();
			break;
		}
		}
	}

	class AsyUpLoadingPic extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {

			double latitude = LocationApplication.latitude;
			double longitude = LocationApplication.longitude;
			String lat = String.valueOf(latitude);
			String longi = String.valueOf(longitude);

			String charset = HTTP.UTF_8;
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			// builder.setCharset(Charset.forName(charset));

			builder.addTextBody("Type", "4",
					ContentType.create("text/plain", Charset.forName(charset)));
			builder.addTextBody("Sender", Welcome.userName,
					ContentType.create("text/plain", Charset.forName(charset)));
			builder.addTextBody("SendDate",
					tv_date.getText().toString().trim(),
					ContentType.create("text/plain", Charset.forName(charset)));
			builder.addTextBody("Desc", eventContent.getText().toString()
					.trim(),
					ContentType.create("text/plain", Charset.forName(charset)));
			builder.addTextBody("Location", textView.getText().toString()
					.trim(),
					ContentType.create("text/plain", Charset.forName(charset)));
			builder.addTextBody("Coordinate", lat + "," + longi,
					ContentType.create("text/plain", Charset.forName(charset)));

			for (int i = 0; i < 3; i++) {
				File file = new File(photoList.get(i).getPhotoPath());
				builder.addBinaryBody("images", file,
						ContentType.create("image"), i + ".jpg");
			}

			for (int i = 0; i < mDat.size(); i++) {
				System.out.println(mDat.size());
				File file1 = new File(mDat.get(i).getFilePath());
				builder.addBinaryBody("Audio", file1);
			}

			HttpEntity entity = builder.build();// 生成 HTTP POST 实体
			String rebackHtml = UpLoadUtil.uploadFile(params[0], entity);
			System.out.println(rebackHtml);

			return rebackHtml;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (!"文件上传失败".equals(result)) {
				dialog.dismiss();
				new Builder(EventLiveWorkingActivity.this)
						.setMessage("发送成功!")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										EventLiveWorkingActivity.this.finish();
										img = 0;
									}
								}).show();
			} else {
				new Builder(EventLiveWorkingActivity.this)
						.setMessage("你无此权限")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										EventLiveWorkingActivity.this.finish();
										img = 0;
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
			dialog = new CustomProgressDialog(EventLiveWorkingActivity.this,
					null, R.anim.frame);
			dialog.show();
		}
	}
}
