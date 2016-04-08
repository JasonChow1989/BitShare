package com.bashi_group_01.www.activity;

import com.bashi_group_01.www.fragment.BreakFragment;
import com.bashi_group_01.www.fragment.FixFragment;
import com.bashi_group_01.www.fragment.IntantFragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
/**
 * 信息屏管理，任务列表;
 * @author share
 *
 */
public class MsgScreenTask extends FragmentActivity implements OnClickListener {

	private ImageView titleback;
	private RadioGroup group;
	private RadioButton rb_instant, rb_break;
	private FragmentManager fm;
	private FragmentTransaction ft;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_msg_screen_task);
		initviews();
		initEvents();
	}

	private void initEvents() {
		// TODO Auto-generated method stub
		this.titleback.setOnClickListener(this);
		rb_instant.setOnClickListener(this);
		rb_break.setOnClickListener(this);
		
		ft = fm.beginTransaction();
		ft.add(R.id.frame_msgscreen, new IntantFragment());
		ft.commit();
	}

	private void initviews() {
		// TODO Auto-generated method stub
		group = (RadioGroup) findViewById(R.id.rg_msgscreen_task);
		this.titleback = (ImageView) findViewById(R.id.titleback_msgscreentask);
		this.rb_instant = (RadioButton) findViewById(R.id.rb_msgscreen_task_instant);
		this.rb_break = (RadioButton) findViewById(R.id.rb_msgscreen_task_break);
		group.check(R.id.rb_msgscreen_task_instant);

		fm = getSupportFragmentManager();
		ft = fm.beginTransaction();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		ft = fm.beginTransaction();
		switch (id) {
		case R.id.titleback_msgscreentask:
			MsgScreenTask.this.finish();
			break;
		case R.id.rb_msgscreen_task_instant:
			ft.replace(R.id.frame_msgscreen, new IntantFragment());
			break;
		case R.id.rb_msgscreen_task_break:
			ft.replace(R.id.frame_msgscreen, new BreakFragment());
			break;
		}
		ft.commit();
	}
}
