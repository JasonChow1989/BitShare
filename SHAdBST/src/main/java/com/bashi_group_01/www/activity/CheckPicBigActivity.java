package com.bashi_group_01.www.activity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.bashi_group_01.www.util.CustomProgressDialog;
import com.photo.choosephotos.adapter.AddImageGridAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * œ‘ æ¥ÛÕº;
 * @author share
 *
 */
public class CheckPicBigActivity extends Activity {

	private ImageView imageView,titleback;
	Bitmap bitmap;
	String pic2;
	CustomProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_pic_big);
		this.imageView = (ImageView) findViewById(R.id.showpicevent);
		this.titleback = (ImageView) findViewById(R.id.pic3content_titleback);
		Intent intent = getIntent();
		pic2 = intent.getStringExtra("pic");
		System.out.println(pic2);
		new AsyEventChackPic().execute(pic2);
		titleback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CheckPicBigActivity.this.finish();
			}
		});
	}

	class AsyEventChackPic extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			dialog = new CustomProgressDialog(CheckPicBigActivity.this, null, R.anim.frame);
			dialog.show();
			dialog.setOnKeyListener(onKeyListener);
		}

		@Override
		protected Bitmap doInBackground(String... params) {
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
					System.out.println("<<<<<<<<<");
					inputStream = httpURLConnection.getInputStream();
					System.out.println(inputStream);
					bitmap = BitmapFactory.decodeStream(inputStream);
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

			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			System.out.println(result);
			imageView.setImageBitmap(result);
			dialog.cancel();
		}
	}
	
	private OnKeyListener onKeyListener = new OnKeyListener() {
		@Override
		public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK
					&& event.getAction() == KeyEvent.ACTION_DOWN) {
				CheckPicBigActivity.this.dialog.cancel();
			}
			return false;
		}
	};
	
}
