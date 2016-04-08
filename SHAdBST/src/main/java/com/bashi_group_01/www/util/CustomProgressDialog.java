package com.bashi_group_01.www.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bashi_group_01.www.activity.R;

/**
 * @Description:自定义对话框
 * @author http://blog.csdn.net/finddreams
 */
public class CustomProgressDialog extends ProgressDialog {

	private AnimationDrawable mAnimation;
	//private Context mContext;
	private ImageView mImageView;
	private int mResid;

	public CustomProgressDialog(Context context, String content, int id) {
		super(context);
		//this.mContext = context;
		this.mResid = id;
		setCanceledOnTouchOutside(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initData();
	}

	private void initData() {

		mImageView.setBackgroundResource(mResid);
		// 通过ImageView对象拿到背景显示的AnimationDrawable
		mAnimation = (AnimationDrawable) mImageView.getBackground();
		// 为了防止在onCreate方法中只显示第一帧的解决方案之一
		mImageView.post(new Runnable() {
			@Override
			public void run() {
				mAnimation.start();
			}
		});
		//mLoadingTv.setText(mLoadingTip);

	}

	public void setContent(String str) {
		//mLoadingTv.setText(str);
	}

	private void initView() {
		setContentView(R.layout.dialogloading);
		//mLoadingTv = (TextView) findViewById(R.id.dialogImg);
		mImageView = (ImageView) findViewById(R.id.dialogImg);
	}

	/*@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		mAnimation.start(); 
		super.onWindowFocusChanged(hasFocus);
	}*/
}
