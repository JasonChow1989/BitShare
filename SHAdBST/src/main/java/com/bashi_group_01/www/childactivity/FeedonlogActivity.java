package com.bashi_group_01.www.childactivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Thumbnails;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.bashi_group_01.www.activity.EventGetAndDoActivity;
import com.bashi_group_01.www.activity.LocationApplication;
import com.bashi_group_01.www.activity.MainActivity;
import com.bashi_group_01.www.activity.R;
import com.bashi_group_01.www.activity.Welcome;
import com.bashi_group_01.www.db.DbHelper;
import com.bashi_group_01.www.domain.MiNum;
import com.bashi_group_01.www.sliding.lib.ClearEditText;
import com.bashi_group_01.www.util.CustomProgressDialog;
import com.bashi_group_01.www.util.PasueMinNumber;
import com.bashi_group_01.www.util.UpLoadUtil;
import com.photo.choosephotos.adapter.AddImageGridAdapter;
import com.photo.choosephotos.controller.SelectPicPopupWindow;
import com.photo.choosephotos.photo.Item;
import com.photo.choosephotos.photo.PhotoAlbumActivity;
import com.photo.choosephotos.photoviewer.photoviewerinterface.ViewPagerActivity;
import com.photo.choosephotos.photoviewer.photoviewerinterface.ViewPagerDeleteActivity;
import com.photo.choosephotos.util.PictureManageUtil;
/**
 * �����Ǽ���;
 * @author share
 *
 */
public class FeedonlogActivity extends Activity implements OnClickListener {

	private RadioGroup group;
	private RadioButton rb_feed, rb_fix;
	String state;
	private Button bt_send;
	private EditText edt_num;
	private ClearEditText edt_content;
	private ImageView img_titleback;
	private Button img_search;
	private String numJson;
	private CustomProgressDialog dialog1, mdialog;
	private List<MiNum> list;
	private String url1 = "http://116.236.170.106:9001/RemoteData/InformationHandler.ashx?Type=11&";
	private TextView numCode, lineList, district;
	private LinearLayout ll_showNum;

	private String[] PicArray;

	SharedPreferences sp;
	SharedPreferences.Editor editor;

	/**
	 * 
	 * addpics
	 * 
	 */
	/* ������ʶ�������๦�ܵ�activity */
	private final int CAMERA_WITH_DATA = 3023;
	/* ������ʶ����gallery��activity */
	private final int PHOTO_PICKED_WITH_DATA = 3021;
	// GridViewԤ��ɾ��ҳ�����
	private final int PIC_VIEW_CODE = 2016;
	/* ���յ���Ƭ�洢λ�� */
	private final File PHOTO_DIR = new File(
			Environment.getExternalStorageDirectory()
					+ "/Android/data/com.photo.choosephotos");
	private File mCurrentPhotoFile;// ��������յõ���ͼƬ
	// ������ʾԤ��ͼ
	private ArrayList<Bitmap> microBmList = new ArrayList<Bitmap>();
	// ��ѡͼ����Ϣ(��Ҫ��·��)
	private ArrayList<Item> photoList = new ArrayList<Item>();
	private AddImageGridAdapter imgAdapter;
	private Bitmap addNewPic;
	private static final int msgKey1 = 1;
	private GridView gridView;// ��ʾ�����ϴ�ͼƬ
	private SelectPicPopupWindow menuWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedonlog);
		initViews();
		initEvents();

		if (!(PHOTO_DIR.exists() && PHOTO_DIR.isDirectory())) {
			PHOTO_DIR.mkdirs();
		}
		// ���ͼƬ
		gridView = (GridView) findViewById(R.id.allPic_feed);
		// �Ӻ�ͼƬ
		addNewPic = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.addimg);
		// addNewPic = PictureManageUtil.resizeBitmap(addNewPic, 180, 180);
		microBmList.add(addNewPic);
		imgAdapter = new AddImageGridAdapter(this, microBmList);
		gridView.setAdapter(imgAdapter);
		// �¼����������GridView���ͼƬʱ����ImageView����ʾ����
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				if (position == (photoList.size())) {
					menuWindow = new SelectPicPopupWindow(
							FeedonlogActivity.this, itemsOnClick);
					menuWindow.showAtLocation(FeedonlogActivity.this
							.findViewById(R.id.uploadPictureLayout_feed),
							Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // ����layout��PopupWindow����ʾ��λ��
				} else {
					Intent intent = new Intent(FeedonlogActivity.this,
							ViewPagerDeleteActivity.class);
					intent.putParcelableArrayListExtra("files", photoList);
					intent.putExtra(ViewPagerActivity.CURRENT_INDEX, position);
					startActivityForResult(intent, PIC_VIEW_CODE);
				}
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if (edt_num.getText().toString().trim() != null) {
				new Builder(FeedonlogActivity.this)
						.setMessage("���浽��ʷ��¼?")
						.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										DbHelper dbHelper = new DbHelper(
												FeedonlogActivity.this);
										SQLiteDatabase db = dbHelper
												.getWritableDatabase();

										ContentValues values = new ContentValues();
										values.put("CapitalNumber", edt_num
												.getText().toString().trim());
										values.put("Description", edt_content
												.getText().toString().trim());
										values.put("NowTime", getDate());
										values.put("State", state);
										db.insert(Welcome.userName, null,
												values);
										db.close();

										PicArray = new String[photoList.size()];

										for (int i = 0; i < photoList.size(); i++) {
											PicArray[i] = photoList.get(i)
													.getPhotoPath();
										}

										setSharedPreference1(
												"URL"
														+ getDate().replaceAll(
																" ", ""),
												PicArray);
										
										FeedonlogActivity.this.finish();
									}
								})
						.setNegativeButton("ȡ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										FeedonlogActivity.this.finish();
									}
								}).create().show();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	/**
	 * �������ݣ�
	 * 
	 * @param key
	 * @param values
	 */

	public void setSharedPreference1(String key, String[] values) {
		String regularEx = "#";
		String str = "";
		if (values != null && values.length > 0) {
			for (String value : values) {
				str += value;
				str += regularEx;
			}
			editor = sp.edit();
			editor.putString(key, str);
			editor.commit();
		}
	}

	private void initEvents() {
		// TODO Auto-generated method stub
		this.group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				// TODO Auto-generated method stub
				if (checkedId == rb_feed.getId()) {
					state = "1";
					System.out.println(state);
				}
				if (checkedId == rb_fix.getId()) {
					state = "0";
					System.out.println(state);
				}
			}
		});

		this.img_search.setOnClickListener(this);
		this.img_titleback.setOnClickListener(this);
		this.bt_send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if ((rb_feed.isChecked() || rb_fix.isChecked())
						&& !"".equals(edt_num.getText().toString())
						&& photoList.size()>0
						) {
					Builder builder = new Builder(
							FeedonlogActivity.this);
					builder.setTitle("��ʾ");
					builder.setMessage("ȷ��Ҫ������");
					builder.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									new HttpSendFeed()
											.execute("http://116.236.170.106:9001/RemoteData/ProcessRequestHandler.ashx");
									dialog.dismiss();
								}
							});
					builder.setNegativeButton("ȡ��",
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

				else if("".equals(edt_num.getText().toString())){
					Builder builder = new Builder(
							FeedonlogActivity.this);
					builder.setTitle("��ʾ");
					builder.setMessage("�Ǽ����ͻ��豸���Ϊ�գ�");
					builder.setPositiveButton("ȷ��",
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
				else if(photoList.size()==0){
					Builder builder = new Builder(
							FeedonlogActivity.this);
					builder.setTitle("��ʾ");
					builder.setMessage("ͼƬ����Ϊ�գ�");
					builder.setPositiveButton("ȷ��",
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
	}

	private void initViews() {
		list = new ArrayList<MiNum>();
		this.group = (RadioGroup) findViewById(R.id.rg_feedlog);
		rb_feed = (RadioButton) findViewById(R.id.rb_feed_feedhu);
		rb_fix = (RadioButton) findViewById(R.id.rb_feed_fix);
		edt_num = (EditText) findViewById(R.id.edt_feedlog_num);
		edt_content = (ClearEditText) findViewById(R.id.event_content_feedsend);
		bt_send = (Button) findViewById(R.id.bt_eventsend_feed);
		img_titleback = (ImageView) findViewById(R.id.img_titleback_feedlog);
		img_search = (Button) findViewById(R.id.img_feedonlog_searchnum);
		numCode = (TextView) findViewById(R.id.tv_feedlog_numcode);
		district = (TextView) findViewById(R.id.tv_feedlog_district);
		lineList = (TextView) findViewById(R.id.tv_feedlog_linelist);
		ll_showNum = (LinearLayout) findViewById(R.id.ll_feedlog_numll);

		sp = getSharedPreferences("FEEDHIS", MODE_PRIVATE);
	}

	private String getDate() {

		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd    HH:mm:ss");
		String date = sDateFormat.format(new java.util.Date());

		return date;
	}

	// Ϊ��������ʵ�ּ�����
	private OnClickListener itemsOnClick = new OnClickListener() {
		public void onClick(View v) {
			menuWindow.dismiss();
			switch (v.getId()) {
			case R.id.btn_take_photo: {
				String status = Environment.getExternalStorageState();
				if (status.equals(Environment.MEDIA_MOUNTED)) {
					// �ж��Ƿ���SD��
					doTakePhoto();// �û�����˴��������ȡ
				} else {
					Toast.makeText(FeedonlogActivity.this, "û��SD��",
							Toast.LENGTH_LONG).show();
				}
				break;
			}
			case R.id.btn_pick_photo: {
				// ��ѡ��ͼƬ����
				doPickPhotoFromGallery();
				break;
			}
			default:
				break;
			}
		}
	};

	/**
	 * ���ջ�ȡͼƬ
	 * 
	 */
	protected void doTakePhoto() {
		try {
			// ������Ƭ�Ĵ洢Ŀ¼
			mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());// �����յ���Ƭ�ļ�����
			final Intent intent = getTakePickIntent(mCurrentPhotoFile);
			// Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent, CAMERA_WITH_DATA);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(this, "�Ҳ������", Toast.LENGTH_LONG).show();
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

	// ����Gallery����
	protected void doPickPhotoFromGallery() {
		try {
			final ProgressDialog dialog;
			dialog = new ProgressDialog(this);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // ����ΪԲ��
			dialog.setMessage("���ݼ�����...");
			dialog.setIndeterminate(false);//
			// dialog.setCancelable(true); //�����˼�ȡ��
			dialog.show();
			Window window = dialog.getWindow();
			View view = window.getDecorView();
			// Tools.setViewFontSize(view,21);
			new Handler().postDelayed(new Runnable() {
				public void run() {
					// ��ʼ����ʾ��
					dialog.dismiss();
				}

			}, 1000);
			// final Intent intent = new
			// Intent(MainActivity.this,GetAllImgFolderActivity.class);
			final Intent intent = new Intent(FeedonlogActivity.this,
					PhotoAlbumActivity.class);
			startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(this, "ͼ�����Ҳ�����Ƭ", Toast.LENGTH_LONG).show();
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

	/**
	 * ��������ҳ�淵������
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case PHOTO_PICKED_WITH_DATA: {
			// ����Gallery���ص�
			ArrayList<Item> tempFiles = new ArrayList<Item>();
			if (data == null)
				return;
			tempFiles = data.getParcelableArrayListExtra("fileNames");
			Log.e("test", "��ѡ�е���Ƭ" + tempFiles.toString());

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
				microBmList.add(bitmap);
				photoList.add(tempFiles.get(i));
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
					// ��������򷵻ص�,�ٴε���ͼƬ��������ȥ�޼�ͼƬ
					// ȥ��GridView��ļӺ�
					microBmList.remove(addNewPic);
					Item item = new Item();
					item.setPhotoPath(mCurrentPhotoFile.getAbsolutePath());
					photoList.add(item);
					// ����·�����õ�һ��ѹ������Bitmap����߽ϴ�ı��500��������ѹ����
					Bitmap bitmap = PictureManageUtil
							.getCompressBm(mCurrentPhotoFile.getAbsolutePath());
					// ��ȡ��ת����
					int rotate = PictureManageUtil
							.getCameraPhotoOrientation(mCurrentPhotoFile
									.getAbsolutePath());
					// ��ѹ����ͼƬ������ת
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.img_titleback_feedlog:
			this.finish();
			break;

		case R.id.img_feedonlog_searchnum:
			String num = edt_num.getText().toString().trim();
			String url = url1 + "Number=" + num;

			if (!"".equals(num)) {
				dialog1 = new CustomProgressDialog(FeedonlogActivity.this,
						null, R.anim.frame);
				dialog1.show();
				new HttpGetMinNum().execute(url);
			} else {
				Toast.makeText(FeedonlogActivity.this, "�豸��Ų���Ϊ��!",
						Toast.LENGTH_SHORT).show();
			}

			break;
		}
	}

	class HttpGetMinNum extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			InputStream inputStream = null;
			ByteArrayOutputStream bos = null;

			try {
				URL url = new URL(params[0]);

				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();

				connection.setConnectTimeout(5000);
				connection.setRequestMethod("GET");

				if (connection.getResponseCode() == 200) {
					// System.out.println("<<<<<<<<<<<<<<<");
					inputStream = connection.getInputStream();
					// System.out.println(inputStream);

					bos = new ByteArrayOutputStream();

					byte[] buffer1 = new byte[1024];

					int len1 = -1;
					while ((len1 = inputStream.read(buffer1)) != -1) {
						bos.write(buffer1, 0, len1);
					}

					numJson = new String(bos.toByteArray(), "gb2312");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return numJson;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			list = PasueMinNumber.Pasue(result);

			if (list.size() != 0) {
				dialog1.dismiss();
				ll_showNum.setVisibility(View.VISIBLE);
				numCode.setText("վ����:  " + list.get(0).getStopId());
				district.setText(list.get(0).getDistrict() + " "
						+ list.get(0).getRoadName() + " "
						+ list.get(0).getStationAddress());
				lineList.setText(list.get(0).getLineList());
			}
		}
	}

	class HttpSendFeed extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String charset = HTTP.UTF_8;
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			// builder.setCharset(Charset.forName(charset));
			System.out.println("CapitalNumber="
					+ edt_num.getText().toString().trim());
			builder.addTextBody("CapitalNumber", edt_num.getText().toString()
					.trim(),
					ContentType.create("text/plain", Charset.forName(charset)));
			System.out.println("Description="
					+ edt_content.getText().toString().trim());
			builder.addTextBody("Description", edt_content.getText().toString()
					.trim(),
					ContentType.create("text/plain", Charset.forName("GBK")));
			System.out.println("NowTime=" + getDate().replaceAll(" ", ""));
			builder.addTextBody("NowTime", getDate(),
					ContentType.create("text/plain", Charset.forName(charset)));
			System.out.println("UserName=" + Welcome.userName);
			builder.addTextBody("UserName", Welcome.userName,
					ContentType.create("text/plain", Charset.forName(charset)));
			System.out.println(state);
			builder.addTextBody("State", state,
					ContentType.create("text/plain", Charset.forName(charset)));


			/**
			 * bitmap �����ͼƬ�ļ� ����ѹ��;
			 */

			for (int i = 0; i < photoList.size(); i++) {
				File file = new File(photoList.get(i).getPhotoPath());
				System.out.println(photoList.get(i).getPhotoPath());
				
				builder.addBinaryBody("images", file,
						ContentType.create("image"), i + ".jpg");
			}

			HttpEntity entity = builder.build();// ���� HTTP POST ʵ��
			String rebackHtml = UpLoadUtil.uploadFile(params[0], entity);
			System.out.println(rebackHtml);

			return rebackHtml;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (!"�ļ��ϴ�ʧ��".equals(result)) {
				mdialog.dismiss();
				new Builder(FeedonlogActivity.this)
						.setMessage("���ͳɹ�!")
						.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										FeedonlogActivity.this.finish();
									}
								}).show();
			} else {
				new Builder(FeedonlogActivity.this)
						.setMessage("����ʧ��")
						.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										FeedonlogActivity.this.finish();
									}
								}).show();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mdialog = new CustomProgressDialog(FeedonlogActivity.this, null,
					R.anim.frame);
			mdialog.show();
		}
	}

}
