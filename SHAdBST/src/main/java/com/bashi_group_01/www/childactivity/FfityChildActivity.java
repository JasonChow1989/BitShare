package com.bashi_group_01.www.childactivity;

import android.app.Activity;
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

import com.bashi_group_01.www.activity.R;

public class FfityChildActivity extends Activity {

	private WebView webView;
	private ImageView imageView;
	private TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ffity_child);
		
		initViews();
		initEvents();
		
		Intent intent = getIntent();
		String titlename = intent.getStringExtra("titlename");
		String zone = intent.getCharSequenceExtra("zone").toString();
		String roadname = intent.getStringExtra("road");
		String line = intent.getStringExtra("line");
		String num = intent.getStringExtra("num");
		String sf = intent.getStringExtra("sf");
		String url = intent.getStringExtra("url");
		String url_road = intent.getStringExtra("url_road");
		String url_line = intent.getStringExtra("url_line");
		String url_num = intent.getStringExtra("url_num");
		String url_zone = intent.getStringExtra("url_zone");
		String url_elect = intent.getStringExtra("url_elect");
		String url_uelect = intent.getStringExtra("url_uelect");

		textView.setText(titlename);
		try {

			String newzone = java.net.URLEncoder.encode(zone, "gb2312");
			String newroadname = java.net.URLEncoder.encode(roadname, "gb2312");
			String newline = java.net.URLEncoder.encode(line, "gb2312");
			String newnum = java.net.URLEncoder.encode(num, "gb2312");
			String newsf = java.net.URLEncoder.encode(sf, "gb2312");
			StringBuffer sb = new StringBuffer(url);
			sb.append(url_line);
			sb.append(newline);
			sb.append(url_road);
			sb.append(newroadname);
			sb.append(url_num);
			sb.append(newnum);
			sb.append(url_zone);
			sb.append(newzone);
			sb.append(url_elect);
			sb.append(url_uelect);
			sb.append(newsf);

			System.out.println(sb);
			WebSettings settings = webView.getSettings();
			settings.setDefaultTextEncodingName("gb2312");
			settings.setDomStorageEnabled(true);
			settings.setSupportZoom(true); // 支持缩放
			settings.setBuiltInZoomControls(true); // 启用内置缩放装置
			settings.setJavaScriptEnabled(true); // 启用JS脚本

			webView.setWebViewClient(new WebViewClient() {
				// 当点击链接时,希望覆盖而不是打开新窗口
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					view.loadUrl(url); // 加载新的url
					return true; // 返回true,代表事件已处理,事件流到此终止
				}
			});

			webView.setOnKeyListener(new View.OnKeyListener() {
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					if (event.getAction() == KeyEvent.ACTION_DOWN) {
						if (keyCode == KeyEvent.KEYCODE_BACK
								&& webView.canGoBack()) {
							webView.goBack(); // 后退
							return true; // 已处理
						}
					}
					return false;
				}
			});

			webView.loadUrl(sb.toString());

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private void initEvents() {
		this.imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(FfityChildActivity.this, HoucheselectActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	private void initViews() {
		this.webView = (WebView) findViewById(R.id.webview_fifty);
		this.imageView = (ImageView) findViewById(R.id.titleback_child_fifty);
		this.textView = (TextView) findViewById(R.id.tv_titletxt_fifty);
	}
	
}
