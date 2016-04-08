package com.bashi_group_01.www.activity;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bashi_group_01.www.activity.EventGetAndDoActivity.AsyUpLoadingPic;
import com.bashi_group_01.www.childactivity.FeedonlogActivity;
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
 * ��Ϣ������������
 * @author share
 *
 */
public class MsgScreenTaskProgress extends Activity implements OnClickListener {

	private ImageView titleback;
	private TextView capitialnum, stitnum, district, stitname, address,
			stit_ting;
	private LinearLayout layout;
	private ClearEditText editText, task;
	private RadioGroup rButton;
	private RadioButton rb_install, rb_break;
	private Button mSend;
	CustomProgressDialog dialog;

	String id;
	String people;
	String location;
	int state;
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
		setContentView(R.layout.activity_msg_screen_task_progress);
		initViews();
		initEvent();
		Intent intent = getIntent();
		String capitial = intent.getStringExtra("capitalNum");
		if (capitial != null) {
			layout.setVisibility(View.VISIBLE);
			String shopId = intent.getStringExtra("shopId");
			String loc = intent.getStringExtra("district");
			String terminal_Name = intent.getStringExtra("terminal_Name");
			location = intent.getStringExtra("location");
			String pathDirection = intent.getStringExtra("pathDirection");
			state = intent.getIntExtra("state", 0);

			if ("False".equals(state)) {
				rButton.check(R.id.rb_msgscreen_insint);
			} else {
				rButton.check(R.id.rb_msgscreen_delete);
			}

			id = intent.getStringExtra("tblRcdId");
			people = intent.getStringExtra("installpeople");
			
			System.out.println(people);
			
			editText.setText(capitial);
			capitialnum.setText("�ʲ����: " + capitial);
			stitnum.setText("վ����: " + shopId);
			district.setText("����: " + loc);
			stitname.setText("վ����:" + terminal_Name);
			address.setText("��ַ: " + location);
			stit_ting.setText("��ʿվͤ: " + pathDirection);
		}

		mSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Builder builder = new Builder(
						MsgScreenTaskProgress.this);
				builder.setTitle("��ʾ");
				builder.setMessage("ȷ��Ҫ������");
				builder.setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

								if (state == 1) {
									new HttpSendInstallData()
											.execute("http://114.215.236.58:8006/Interface/InstallEquipment.ashx");
								} else {
									new HttpSendInstallData()
											.execute("http://114.215.236.58:8006/Interface/InstallEquipmentRemove.ashx");
								}

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
		});

		if (!(PHOTO_DIR.exists() && PHOTO_DIR.isDirectory())) {
			PHOTO_DIR.mkdirs();
		}
		// ���ͼƬ
		gridView = (GridView) findViewById(R.id.allPic_msgscreen);
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
							MsgScreenTaskProgress.this, itemsOnClick);
					menuWindow.showAtLocation(MsgScreenTaskProgress.this
							.findViewById(R.id.uploadPictureLayout_msg),
							Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // ����layout��PopupWindow����ʾ��λ��
				} else {
					Intent intent = new Intent(MsgScreenTaskProgress.this,
							ViewPagerDeleteActivity.class);
					intent.putParcelableArrayListExtra("files", photoList);
					intent.putExtra(ViewPagerActivity.CURRENT_INDEX, position);
					startActivityForResult(intent, PIC_VIEW_CODE);
				}
			}
		});
	}

	private void initViews() {
		// TODO Auto-generated method stub
		this.titleback = (ImageView) findViewById(R.id.titleback_msgscreentaskprog);
		this.capitialnum = (TextView) findViewById(R.id.tv_msgscreen_capitalcode);
		this.stitnum = (TextView) findViewById(R.id.tv_msgscreen_stitcode);
		this.district = (TextView) findViewById(R.id.tv_msgscreen_district);
		this.stitname = (TextView) findViewById(R.id.tv_msgscreen_stitname);
		this.address = (TextView) findViewById(R.id.tv_msgscreen_address);
		this.stit_ting = (TextView) findViewById(R.id.tv_msgscreen_stit_ting);
		this.layout = (LinearLayout) findViewById(R.id.ll_msgscreen_numll);
		this.editText = (ClearEditText) findViewById(R.id.edt_msgscreen_num);
		this.rButton = (RadioGroup) findViewById(R.id.rg_msgscreen);
		this.rb_install = (RadioButton) findViewById(R.id.rb_msgscreen_insint);
		this.rb_break = (RadioButton) findViewById(R.id.rb_msgscreen_delete);
		this.mSend = (Button) findViewById(R.id.bt_eventsend_msgscreen);
		this.task = (ClearEditText) findViewById(R.id.tv_msg_screen_txt);
	}

	private void initEvent() {
		this.titleback.setOnClickListener(this);
		mSend.setOnClickListener(this);
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
					Toast.makeText(MsgScreenTaskProgress.this, "û��SD��",
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
			final Intent intent = new Intent(MsgScreenTaskProgress.this,
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
		case R.id.titleback_msgscreentaskprog:
			MsgScreenTaskProgress.this.finish();
			break;
		}
	}

	class HttpSendInstallData extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String charset = HTTP.UTF_8;
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			// builder.setCharset(Charset.forName(charset));
			System.out.println(id);
			builder.addTextBody("ID", id,
					ContentType.create("text/plain", Charset.forName(charset)));
			builder.addTextBody("InstallName", people,
					ContentType.create("text/plain", Charset.forName(charset)));
			builder.addTextBody("Location", location,
					ContentType.create("text/plain", Charset.forName(charset)));
			builder.addTextBody("Remark", task.getText().toString().trim(),
					ContentType.create("text/plain", Charset.forName(charset)));

			/**
			 * bitmap �����ͼƬ�ļ� ����ѹ��;
			 */

			for (int i = 0; i < photoList.size(); i++) {
				File file = new File(photoList.get(i).getPhotoPath());
				builder.addBinaryBody("images", file,
						ContentType.create("image"), i + ".jpg");
			}
			HttpEntity entity = builder.build();// ���� HTTP POST ʵ��
			String rebackHtml = UpLoadUtil.uploadFile(params[0], entity);
			return rebackHtml;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (!"�ļ��ϴ�ʧ��".equals(result)) {
				dialog.dismiss();
				new Builder(MsgScreenTaskProgress.this)
						.setMessage("���ͳɹ�!")
						.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										MsgScreenTaskProgress.this.finish();
									}
								}).show();
			} else {
				new Builder(MsgScreenTaskProgress.this)
						.setMessage("����ʧ��!")
						.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										MsgScreenTaskProgress.this.finish();
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
			dialog = new CustomProgressDialog(MsgScreenTaskProgress.this, null,
					R.anim.frame);
			dialog.show();
		}

	}

}
