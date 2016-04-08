package com.bashi_group_01.www.childactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bashi_group_01.www.activity.MainActivity;
import com.bashi_group_01.www.activity.R;
import com.bashi_group_01.www.fragment.EvevtFaWenFragment;
import com.bashi_group_01.www.fragment.EvevtHeTongFragment;

/**
 * 
 * ÎÄ¼þÇ©·¢
 * 
 * @author share
 * 
 */
public class FileMakeActivity extends FragmentActivity implements
		OnClickListener {

	private ImageView imageView;
	private RadioGroup radioGroup;
	private RadioButton rb_fawen, rb_hetong;
	private FragmentManager fm;
	private FragmentTransaction ft;
	long exitTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_make);
		initviews();
		rb_fawen.setChecked(true);
		ft.replace(R.id.framell_evevtfilemake, new EvevtFaWenFragment());
		ft.commit();

		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(FileMakeActivity.this,
						MainActivity.class);
				startActivity(intent);
				FileMakeActivity.this.finish();
			}
		});
	}

	private void initviews() {
		// TODO Auto-generated method stub
		this.imageView = (ImageView) findViewById(R.id.event_filemake_titleback);
		this.radioGroup = (RadioGroup) findViewById(R.id.rg_eventfilemake);
		this.rb_fawen = (RadioButton) findViewById(R.id.rb_event_fawen);
		this.rb_hetong = (RadioButton) findViewById(R.id.rb_event_hetong);
		this.radioGroup.check(R.id.rb_event_fawen);

		fm = getSupportFragmentManager();
		ft = fm.beginTransaction();
		rb_fawen.setOnClickListener(this);
		rb_hetong.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		ft = fm.beginTransaction();
		switch (id) {
		case R.id.rb_event_fawen:
			ft.replace(R.id.framell_evevtfilemake, new EvevtFaWenFragment());
			break;
		case R.id.rb_event_hetong:
			ft.replace(R.id.framell_evevtfilemake, new EvevtHeTongFragment());
			break;
		}
		ft.commit();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			Intent intent = new Intent(FileMakeActivity.this,
					MainActivity.class);
			startActivity(intent);
			FileMakeActivity.this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
