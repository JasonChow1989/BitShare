package com.bashi_group_01.www.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.bashi_group_01.www.childactivity.HostroyActivity;
import com.bashi_group_01.www.db.DbHelper;
import com.bashi_group_01.www.domain.MiNum;
import com.bashi_group_01.www.sliding.lib.ClearEditText;
import com.bashi_group_01.www.util.CustomProgressDialog;
import com.bashi_group_01.www.util.PasueMinNumber;
import com.bashi_group_01.www.util.UpLoadUtil;
import com.photo.choosephotos.adapter.AddImageGridAdapter;
import com.photo.choosephotos.util.PictureManageUtil;
/**
 * ����ʷ��¼�У��ٴ������Ǽ�;
 * @author share
 *
 */
public class FeedSendAgainActivity extends Activity implements OnClickListener {

	String captialNum;
	String dsec;
	String date;
	SharedPreferences sp;
	String[] strs;

	private ClearEditText mCaptialNum;
	private ClearEditText mContent;
	private Button mCheck, mSend;
	private ImageView title_back;

	private LinearLayout ll_showNum;
	private TextView numCode;
	private TextView district;
	private TextView lineList;
	List<Bitmap> microBmList = null;
	private GridView gridView;
	private AddImageGridAdapter imgAdapter;
	private String url1 = "http://116.236.170.106:9001/RemoteData/InformationHandler.ashx?Type=11&";
	private CustomProgressDialog dialog1, mdialog;
	private String numJson;
	private List<MiNum> list;
	private RadioGroup group;
	private RadioButton rb_feed, rb_fix;
	private List<String> photoList;
	String state;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feed_send_again);
		initViews();
		getData();
		initEvents();
	}

	private void initEvents() {
		// TODO Auto-generated method stub
		this.mSend.setOnClickListener(this);
		this.mCheck.setOnClickListener(this);
		this.title_back.setOnClickListener(this);
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

	}

	private void initViews() {
		// TODO Auto-generated method stub
		this.mCaptialNum = (ClearEditText) findViewById(R.id.edt_feedlog_num_again);
		this.mContent = (ClearEditText) findViewById(R.id.event_content_feedsend_again);
		this.mSend = (Button) findViewById(R.id.bt_eventsend_feed_again);
		this.mCheck = (Button) findViewById(R.id.img_feedonlog_searchnum_again);
		list = new ArrayList<MiNum>();
		this.ll_showNum = (LinearLayout) findViewById(R.id.ll_feedlog_numll_again);
		this.numCode = (TextView) findViewById(R.id.tv_feedlog_numcode_again);
		this.district = (TextView) findViewById(R.id.tv_feedlog_district_again);
		this.lineList = (TextView) findViewById(R.id.tv_feedlog_linelist);
		rb_feed = (RadioButton) findViewById(R.id.rb_feed_feedhu_again);
		rb_fix = (RadioButton) findViewById(R.id.rb_feed_fix_again);
		this.group = (RadioGroup) findViewById(R.id.rg_feedlog_again);
		this.title_back = (ImageView) findViewById(R.id.img_titleback_feedlog_again);
	}

	private void getData() {
		// TODO Auto-generated method stub
		microBmList = new ArrayList<Bitmap>();
		Intent intent = getIntent();
		captialNum = intent.getStringExtra("captialNum");
		dsec = intent.getStringExtra("desc");
		date = intent.getStringExtra("date");
		strs = getSharedPreference("URL" + date.replaceAll(" ", ""));
		photoList = new ArrayList<String>();
		for (int i = 0; i < strs.length; i++) {
			photoList.add(strs[i]);
			Bitmap bitmap = PictureManageUtil.getCompressBm(strs[i]);
			// ��ȡ��ת����
			int rotate = PictureManageUtil.getCameraPhotoOrientation(strs[i]);
			// ��ѹ����ͼƬ������ת
			bitmap = PictureManageUtil.rotateBitmap(bitmap, rotate);
			microBmList.add(bitmap);
		}

		System.out.println(microBmList.size());
		mCaptialNum.setText(captialNum);
		mContent.setText(dsec);
		gridView = (GridView) findViewById(R.id.allPic_feed_again);
		imgAdapter = new AddImageGridAdapter(this, microBmList);
		gridView.setAdapter(imgAdapter);
	}

	/**
	 * 
	 * spȡ����;
	 * 
	 * 
	 */

	public String[] getSharedPreference(String key) {
		String regularEx = "#";
		String[] str = null;
		String values;
		sp = getSharedPreferences("FEEDHIS", MODE_PRIVATE);
		values = sp.getString(key, "");
		str = values.split(regularEx);
		return str;
	}

	private String getDate() {

		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd    HH:mm:ss");
		String date = sDateFormat.format(new java.util.Date());

		return date;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.img_feedonlog_searchnum_again:
			String num = mCaptialNum.getText().toString().trim();
			String url = url1 + "Number=" + num;

			if (!"".equals(num)) {
				dialog1 = new CustomProgressDialog(FeedSendAgainActivity.this,
						null, R.anim.frame);
				dialog1.show();
				new HttpGetMinNum().execute(url);
			} else {
				Toast.makeText(FeedSendAgainActivity.this, "�豸��Ų���Ϊ��!",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.bt_eventsend_feed_again:
			if ((rb_feed.isChecked() || rb_fix.isChecked())
					&& !"".equals(mCaptialNum.getText().toString())) {
				Builder builder = new Builder(
						FeedSendAgainActivity.this);
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

			else {
				Builder builder = new Builder(
						FeedSendAgainActivity.this);
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
					+ mCaptialNum.getText().toString().trim());
			builder.addTextBody("CapitalNumber", mCaptialNum.getText()
					.toString().trim(),
					ContentType.create("text/plain", Charset.forName(charset)));
			System.out.println("Description="
					+ mContent.getText().toString().trim());
			builder.addTextBody("Description", mContent.getText().toString()
					.trim(),
					ContentType.create("text/plain", Charset.forName(charset)));
			System.out.println("NowTime=" + getDate());
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
				File file = new File(photoList.get(i));
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
				new Builder(FeedSendAgainActivity.this)
						.setMessage("���ͳɹ�!")
						.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// ɾ��������ʷ��¼;

										DbHelper dbOpenHelper = new DbHelper(
												FeedSendAgainActivity.this);
										SQLiteDatabase db = dbOpenHelper
												.getWritableDatabase();
										db.delete(Welcome.userName,
												"CapitalNumber=?", new String[] { ""
														+ mCaptialNum.getText()
																.toString()
																.trim() });

										db.close();
										FeedSendAgainActivity.this.finish();
									}
								}).show();
			} else {
				new Builder(FeedSendAgainActivity.this)
						.setMessage("����ʧ��")
						.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										FeedSendAgainActivity.this.finish();
									}
								}).show();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mdialog = new CustomProgressDialog(FeedSendAgainActivity.this,
					null, R.anim.frame);
			mdialog.show();
		}
	}
}
