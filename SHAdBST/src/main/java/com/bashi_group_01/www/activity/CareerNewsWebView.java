package com.bashi_group_01.www.activity;

import com.bashi_group_01.www.util.CustomProgressDialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * @author share
 *	��ҵ������webview��
 */
public class CareerNewsWebView extends Activity {

	private WebView webView;
	private String url = "http://task.84000.com.cn:7070/news/";
	private String realUrl;
	CustomProgressDialog dialog;
	private ImageView imageView,homeBack;
	private TextView title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_career_news_web_view);
		this.imageView = (ImageView) findViewById(R.id.titleback_careernewsweb);
		this.homeBack = (ImageView) findViewById(R.id.img_careernewsweb_backtohome);
		this.webView = (WebView) findViewById(R.id.webview_careernews);
		this.title = (TextView) findViewById(R.id.title_careernewsweb);
		title.setText("��ҵ����");
		
		Intent intent = getIntent();
		String foldname = intent.getStringExtra("foldname");
		String id = intent.getStringExtra("id");
		realUrl = url + foldname + "/" + id;

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

		loadUrl(realUrl.toString());

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
				Intent intent = new Intent(CareerNewsWebView.this,
						MainActivity.class);
				startActivity(intent);
				CareerNewsWebView.this.finish();
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
