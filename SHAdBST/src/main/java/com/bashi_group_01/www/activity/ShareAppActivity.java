package com.bashi_group_01.www.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
/**
 * APP∑÷œÌ
 * @author share
 *
 */
public class ShareAppActivity extends Activity {

	private Button button;
	private ImageView titleback, homeback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shareapp);
		this.button = (Button) findViewById(R.id.shareapp_systemhelp);
		this.titleback = (ImageView) findViewById(R.id.titleback_shareapp);
		this.homeback = (ImageView) findViewById(R.id.img_backhome_share);
		this.button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String url_help = "http://116.236.170.106:9001/Helper.aspx";
				String titlename_help = "œµÕ≥∞Ô÷˙";
				Intent intent = new Intent();
				intent.putExtra("url", url_help);
				intent.putExtra("titlename", titlename_help);
				intent.setClass(ShareAppActivity.this,
						FirstWebViewsHengActivity.class);
				startActivity(intent);
				ShareAppActivity.this.overridePendingTransition(
						R.anim.others_getin, R.anim.others_getout);
			}
		});

		this.titleback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShareAppActivity.this.finish();
			}
		});

		this.homeback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ShareAppActivity.this,
						MainActivity.class);
				startActivity(intent);
			}
		});
	}
}
