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
import com.bashi_group_01.www.fragment.UtilFragmentStatLine;
import com.bashi_group_01.www.fragment.UtilFragmentStatNum;
import com.bashi_group_01.www.fragment.UtilFragmentStatZone;
/**
 * ’æ≈∆≤È—Ø
 * @author share
 *
 */
public class StatpanselectActivity extends FragmentActivity implements
		OnClickListener {

	private ImageView imageView;
	private RadioGroup mRadioGroup;
	private RadioButton mRb_zone, mRb_line, mRb_num;
	private FragmentManager fm;
	private FragmentTransaction ft;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statpanselect);
		ininViews();
		initEvevt();
		mRadioGroup.check(R.id.rb_util_statzone);
		ft.add(R.id.fm_util_statzone, new UtilFragmentStatZone());
		ft.commit();
	}

	private void ininViews() {
		// TODO Auto-generated method stub
		fm = getSupportFragmentManager();
		ft = fm.beginTransaction();
		this.imageView = (ImageView) findViewById(R.id.titleback_statpanselect);
		this.mRadioGroup = (RadioGroup) findViewById(R.id.rp_stat);
		this.mRb_zone = (RadioButton) findViewById(R.id.rb_util_statzone);
		this.mRb_line = (RadioButton) findViewById(R.id.rb_util_statline);
		this.mRb_num = (RadioButton) findViewById(R.id.rb_util_statnum);
	}

	private void initEvevt() {
		imageView.setOnClickListener(this);
		mRb_zone.setOnClickListener(this);
		mRb_line.setOnClickListener(this);
		mRb_num.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		ft = fm.beginTransaction();
		Bundle bundle = new Bundle();
		switch (id) {
		case R.id.titleback_statpanselect:
			finish();
			break;
		case R.id.rb_util_statzone:
			ft.replace(R.id.fm_util_statzone, new UtilFragmentStatZone());
			break;
		case R.id.rb_util_statline:
			UtilFragmentStatLine utilFragmentStatLine = new UtilFragmentStatLine();
			bundle.putString("type", "Poles");
			utilFragmentStatLine.setArguments(bundle);
			ft.replace(R.id.fm_util_statzone, utilFragmentStatLine);
			break;
		case R.id.rb_util_statnum:
			UtilFragmentStatNum utilFragmentStatNum = new UtilFragmentStatNum();
			bundle.putString("type", "Poles");
			utilFragmentStatNum.setArguments(bundle);
			ft.replace(R.id.fm_util_statzone, utilFragmentStatNum);
			break;
		}
		ft.commit();
	}
}
