package com.bashi_group_01.www.activity;

import com.bashi_group_01.www.activity.R;
import com.bashi_group_01.www.fragment.FristFragment;
import com.bashi_group_01.www.util.CustomProgressDialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * webview横屏
 * @author share
 *
 */
public class FirstWebViewsHengActivity extends Activity {

	private String url = "";
	private ImageView imageView,homeback;
	private WebView webView;
	private TextView textView;
	private String titlename;
	private CustomProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_carmid);
		this.imageView = (ImageView) findViewById(R.id.titleback_carmid);
		this.homeback = (ImageView) findViewById(R.id.img_fristweb_backtohome);
		this.webView = (WebView) findViewById(R.id.webview_tableselect);
		this.textView = (TextView) findViewById(R.id.titlenametxt);
		Intent intent = getIntent();
		url = intent.getStringExtra("url");
		titlename = intent.getStringExtra("titlename");
		textView.setText(titlename);

		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		homeback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(FirstWebViewsHengActivity.this, MainActivity.class);
				startActivity(intent);
				FirstWebViewsHengActivity.this.finish();
			}
		});

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
					if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
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

		loadUrl(url.toString());

	}

	public void loadUrl(String url) {
		if (webView != null) {
			webView.loadUrl(url);
			if (null == dialog) {
				dialog = new CustomProgressDialog(this, null, R.anim.frame);
				dialog.show();
				dialog.setOnKeyListener(onKeyListener);
			}
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
