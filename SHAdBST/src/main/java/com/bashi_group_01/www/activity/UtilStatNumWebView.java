package com.bashi_group_01.www.activity;

import android.app.Activity;
import android.app.ProgressDialog;
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

import com.bashi_group_01.www.childactivity.StatpanselectActivity;
import com.bashi_group_01.www.util.CustomProgressDialog;
/**
 * �ۺϲ�ѯվ����webview
 * @author share
 *
 */
public class UtilStatNumWebView extends Activity {

	private WebView webView;
	private ImageView imageView,img_homeback;
	private TextView textView;
	CustomProgressDialog dialog;
	private String url_type = "&District=&PstrType=";
	private String url_other = "&IsPaymentType=&CurrentFacturer=";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_util_stat_num_web_view);
		initViews();
		initEvents();
		
		Intent intent = getIntent();
		String titlename = intent.getStringExtra("titlename");
		String roadname = intent.getStringExtra("num");
		String url = intent.getStringExtra("url");
		String type = intent.getStringExtra("type");
		//System.out.println(url);
		textView.setText(titlename);
		try {

			String newroadname = java.net.URLEncoder.encode(roadname, "gb2312");
			StringBuffer sb = new StringBuffer(url);
			sb.append(newroadname);
			sb.append(url_type);
			sb.append(type);
			sb.append(url_other);
			System.out.println(sb);
			
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
						if (keyCode == KeyEvent.KEYCODE_BACK
								&& webView.canGoBack()) {
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
			loadUrl(sb.toString());

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
				if(webView.canGoBack()){
					webView.goBack ();
				}else {
					finish();
				}
			}
		});
		
		this.img_homeback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(UtilStatNumWebView.this, MainActivity.class);
				startActivity(intent);
				UtilStatNumWebView.this.finish();
			}
		});
	}

	private void initViews() {
		this.img_homeback = (ImageView) findViewById(R.id.img_backtohome03);
		this.webView = (WebView) findViewById(R.id.webview_util_stat_num);
		this.imageView = (ImageView) findViewById(R.id.titleback_webview_statnum);
		this.textView = (TextView) findViewById(R.id.titleback_txt_webview_num);
	}

	public void loadUrl(String url) {
		if (webView != null) {
			webView.loadUrl(url);
			dialog = new CustomProgressDialog(this, null, R.anim.frame);
			dialog.show();
		}
	}
}
