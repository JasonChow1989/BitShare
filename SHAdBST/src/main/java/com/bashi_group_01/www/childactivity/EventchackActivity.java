package com.bashi_group_01.www.childactivity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bashi_group_01.www.activity.R;
import com.bashi_group_01.www.fragment.EvevtAllFragment;
import com.bashi_group_01.www.fragment.EvevtSanFragment;
import com.bashi_group_01.www.fragment.EvevtSanSFragment;
import com.bashi_group_01.www.fragment.EvevtSevenFragment;
/**
 * 事务查询类;
 * @author share
 *
 */
public class EventchackActivity extends FragmentActivity implements
		OnClickListener {

	private ImageView imageView;
	private RadioGroup radioGroup;
	private RadioButton rb_three, rb_seven, rb_thrid, rb_all;
	private FragmentManager fm;
	private FragmentTransaction ft;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_eventchack);
		initviews();
		ft.replace(R.id.framell_evevtcheck, new EvevtSanFragment());
		ft.commit();

		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EventchackActivity.this.finish();
			}
		});
	}

	private void initviews() {
		// TODO Auto-generated method stub
		this.imageView = (ImageView) findViewById(R.id.titleback_eventchack);
		this.radioGroup = (RadioGroup) findViewById(R.id.rg_eventchack);
		this.rb_three = (RadioButton) findViewById(R.id.rb_san);
		this.rb_seven = (RadioButton) findViewById(R.id.rb_qi);
		this.rb_thrid = (RadioButton) findViewById(R.id.rb_sanshi);
		this.rb_all = (RadioButton) findViewById(R.id.rb_all);
		this.radioGroup.check(R.id.rb_san);
		
		fm = getSupportFragmentManager();
		ft = fm.beginTransaction();

		rb_three.setOnClickListener(this);
		rb_seven.setOnClickListener(this);
		rb_thrid.setOnClickListener(this);
		rb_all.setOnClickListener(this);
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		ft = fm.beginTransaction();
		switch (id) {
		case R.id.rb_san:
			ft.replace(R.id.framell_evevtcheck, new EvevtSanFragment());
			break;
		case R.id.rb_qi:
			ft.replace(R.id.framell_evevtcheck, new EvevtSevenFragment());
			break;

		case R.id.rb_sanshi:
			ft.replace(R.id.framell_evevtcheck, new EvevtSanSFragment());
			break;

		case R.id.rb_all:
			ft.replace(R.id.framell_evevtcheck, new EvevtAllFragment());
			break;
		}
		ft.commit();
	}
}
