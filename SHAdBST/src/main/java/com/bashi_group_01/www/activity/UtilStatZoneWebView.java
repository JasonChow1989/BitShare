package com.bashi_group_01.www.activity;

import com.bashi_group_01.www.util.CustomProgressDialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

public class UtilStatZoneWebView extends Activity {

	private WebView webView;
	private ImageView imageView,img_backtohome;
	private TextView textView;

	CustomProgressDialog dialog;
	private String url_num = "&CapitalNumber=";
	private String url_type = "&PstrType=";
	private String url_elect = "&IsPaymentType=";
	private String url_other = "&CurrentFacturer=";
	String elect ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_util_stat_zone_web_view);
		initViews();
		initEvents();
		Intent intent = getIntent();
		String titlename = intent.getStringExtra("titlename");
		String zone1 = intent.getStringExtra("zone1");
		String zone2 = intent.getStringExtra("zone2");
		String zone3 = intent.getStringExtra("zone3");
		String zone4 = intent.getStringExtra("zone4");
		String zone5 = intent.getStringExtra("zone5");
		String roadname1 = intent.getStringExtra("road1");
		String roadname2 = intent.getStringExtra("road2");
		String roadname3 = intent.getStringExtra("road3");
		String roadname4 = intent.getStringExtra("road4");
		String roadname5 = intent.getStringExtra("road5");
		String url = intent.getStringExtra("url");
		String url_road = intent.getStringExtra("url_road");
		String url_zone = intent.getStringExtra("url_zone");
		String type = intent.getStringExtra("type");
		if(!"".equals(intent.getStringExtra("elect"))){
			elect = intent.getStringExtra("elect");
		}
		textView.setText(titlename);
		
		StringBuffer sb = new StringBuffer(url);
		
		try {

			String newzone1 = java.net.URLEncoder.encode(zone1, "gb2312");
			String newzone2 = java.net.URLEncoder.encode(zone2, "gb2312");
			String newzone3 = java.net.URLEncoder.encode(zone3, "gb2312");
			String newzone4 = java.net.URLEncoder.encode(zone4, "gb2312");
			String newzone5 = java.net.URLEncoder.encode(zone5, "gb2312");
			String newroadname1 = java.net.URLEncoder.encode(roadname1,
					"gb2312");
			String newroadname2 = java.net.URLEncoder.encode(roadname2,
					"gb2312");
			String newroadname3 = java.net.URLEncoder.encode(roadname3,
					"gb2312");
			String newroadname4 = java.net.URLEncoder.encode(roadname4,
					"gb2312");
			String newroadname5 = java.net.URLEncoder.encode(roadname5,
					"gb2312");
			String newelect = java.net.URLEncoder.encode(roadname5,
					"gb2312");
			System.out.println(sb);
			
			sb.append(url_road);
			if(!"".equals(newroadname1)){
				sb.append(newroadname1);
			}
			if(!"".equals(newroadname2)){
				sb.append(";");
				sb.append(newroadname2);
			}if(!"".equals(newroadname3)){
				sb.append(";");
				sb.append(newroadname3);
			}if(!"".equals(newroadname4)){
				sb.append(";");
				sb.append(newroadname4);
			}if(!"".equals(newroadname5)){
				sb.append(";");
				sb.append(newroadname5);
			}
			sb.append(url_num);
			sb.append(url_zone);
			if(!"".equals(newzone1)){
				sb.append(newzone1);
			}
			if(!"".equals(newzone2)){
				sb.append(";");
				sb.append(newzone2);
			}if(!"".equals(newzone3)){
				sb.append(";");
				sb.append(newzone3);
			}if(!"".equals(newzone4)){
				sb.append(";");
				sb.append(newzone4);
			}if(!"".equals(newzone5)){
				sb.append(";");
				sb.append(newzone5);
			}
			sb.append(url_type);
			sb.append(type);
			sb.append(url_elect);
			if(!"".equals(newelect)){
				sb.append(newelect);
			}
			sb.append(url_other);
			
			System.out.println(sb);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		WebSettings settings = webView.getSettings();
		settings.setDefaultTextEncodingName("gb2312");
		settings.setDomStorageEnabled(true);
		settings.setSupportZoom(true); // 支持缩放
		settings.setBuiltInZoomControls(true); // 启用内置缩放装置
		settings.setDisplayZoomControls(false);
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

		if (webView != null) {
			webView.setWebViewClient(new WebViewClient() {

				@Override
				public void onPageFinished(WebView view, String url) {
					dialog.dismiss();
				}

			});
		}
		loadUrl(sb.toString());
		
	}

	private void initEvents() {
		this.imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(webView.canGoBack()){
					webView.goBack ();
				}else {
					finish();
				}
			}
		});
		
		
		this.img_backtohome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(UtilStatZoneWebView.this, MainActivity.class);
				startActivity(intent);
				UtilStatZoneWebView.this.finish();
			}
		});
	}

	private void initViews() {
		this.webView = (WebView) findViewById(R.id.webview111);
		this.imageView = (ImageView) findViewById(R.id.titleback_child1);
		this.img_backtohome = (ImageView) findViewById(R.id.img_backtohome01);
		this.textView = (TextView) findViewById(R.id.tv_titletxt111);
	}

	public void loadUrl(String url) {
		if (webView != null) {
			webView.loadUrl(url);
			dialog = new CustomProgressDialog(this, null, R.anim.frame);
			dialog.show();
			dialog.setOnKeyListener(onKeyListener);
		}
	}
	
	//监听返回键;
		private OnKeyListener onKeyListener = new OnKeyListener() {
	        @Override
	        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
	            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
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
