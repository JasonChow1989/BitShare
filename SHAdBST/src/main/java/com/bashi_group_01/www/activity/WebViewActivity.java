package com.bashi_group_01.www.activity;

import java.io.UnsupportedEncodingException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bashi_group_01.www.util.CustomProgressDialog;

public class WebViewActivity extends Activity {

	private WebView webView;
	private ImageView imageView ,homeBack;
	private StringBuffer newUrl;
	private TextView textView;

	CustomProgressDialog dialog;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);
		this.textView = (TextView) findViewById(R.id.tv_titletxt11);
		this.webView = (WebView) findViewById(R.id.webview);
		this.imageView = (ImageView) findViewById(R.id.titleback_child);
		this.homeBack = (ImageView) findViewById(R.id.img_home_back);
		Intent intent = getIntent();
		CharSequence roadline = intent.getCharSequenceExtra("roadline");
		String url = intent.getStringExtra("url");
		String pinpai = roadline.toString();
		String titlename = intent.getStringExtra("titlename");
		textView.setText(titlename);

		try {
			String newpinpai = java.net.URLEncoder.encode(pinpai, "gb2312");
			newUrl = new StringBuffer(url);
			newUrl.append(newpinpai);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebSettings settings = webView.getSettings();
		settings.setDefaultTextEncodingName("gb2312");
		settings.setDomStorageEnabled(true);
		settings.setSupportZoom(true); // ֧������
		settings.setBuiltInZoomControls(true); // ������������װ��
		settings.setDisplayZoomControls(false);
		settings.setJavaScriptEnabled(true); // ����JS�ű�

		webView.setWebViewClient(new WebViewClient() {
			// ���������ʱ,ϣ�����Ƕ����Ǵ��´���
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url); // �����µ�url
				return true; // ����true,�����¼��Ѵ���,�¼���������ֹ
			}
		});

		webView.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
						webView.goBack(); // ����
						return true; // �Ѵ���
					}
				}
				return false;
			}
		});

		if (webView != null) {
			webView.setWebViewClient(new WebViewClient() {

				@Override
				public void onPageFinished(WebView view, String url) {
					dialog.dismiss();
				}

			});
		}

		loadUrl(newUrl.toString());

		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (webView.canGoBack()) {
					webView.goBack();
				} else {
					finish();
				}
			}
		});
		
		homeBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(WebViewActivity.this, MainActivity.class);
				startActivity(intent);
				WebViewActivity.this.finish();
			}
		});

	}

	public void loadUrl(String url) {
		if (webView != null) {
			webView.loadUrl(url);
			dialog = new CustomProgressDialog(this, null, R.anim.frame);
			dialog.show();
			dialog.setOnKeyListener(onKeyListener);
		}
	}

	// �������ؼ�;
	private OnKeyListener onKeyListener = new OnKeyListener() {
		@Override
		public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK
					&& event.getAction() == KeyEvent.ACTION_DOWN) {
				dismissDialog();
			}
			return false;
		}
	};

	public void dismissDialog() {
		if (isFinishing()) {
			return;
		}
		if (null != dialog && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

}
