package com.bashi_group_01.www.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

/**
 * 设置的类;
 * 
 * @author share
 * 
 */
public class SettingActivity extends Activity {

	private long exitTime = 0;
	private LinearLayout ll_update, ll_exist, ll_share;
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		initviews();

		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SettingActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});

		ll_update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String appId = "32c176f53ff3861a1109dc2552e79c6a"; // 蒲公英注册或上传应用获取的AppId
				PgyUpdateManager.register(SettingActivity.this, appId,
						new UpdateManagerListener() {

							@Override
							public void onUpdateAvailable(final String result) {
								// 调用sdk的默认下载，apk下载地址为result字符串中downloadURL对应的值
								String downloadUrl = "http://www.pgyer.com/apiv1/app/install?aId=32c176f53ff3861a1109dc2552e79c6a&_api_key=fe01fe238dd8e29b8d47d70710b0f4e1";
								startDownloadTask(SettingActivity.this,
										downloadUrl);
							}

							@Override
							public void onNoUpdateAvailable() {
								Toast.makeText(SettingActivity.this, "已是最新版本",
										Toast.LENGTH_LONG).show();
							}
						});
			}
		});

		ll_exist.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(SettingActivity.this)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										Intent intent = new Intent();
										intent.setClass(SettingActivity.this,
												LoginActivity.class);
										startActivity(intent);
										SettingActivity.this.finish();
									}
								})
						.setMessage("确定要注销吗？")
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										dialog.dismiss();
									}
								}).show();
			}
		});

		ll_share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SettingActivity.this,
						ShareAppActivity.class);
				startActivity(intent);
				SettingActivity.this.overridePendingTransition(
						R.anim.others_getin, R.anim.others_getout);
			}
		});

	}

	private void initviews() {
		// TODO Auto-generated method stub
		ll_share = (LinearLayout) findViewById(R.id.llshare);
		ll_update = (LinearLayout) findViewById(R.id.llupdate);
		ll_exist = (LinearLayout) findViewById(R.id.llexist);
		imageView = (ImageView) findViewById(R.id.titleback_setting);
	}
}