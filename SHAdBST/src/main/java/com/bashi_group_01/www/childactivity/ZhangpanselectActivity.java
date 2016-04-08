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
import com.bashi_group_01.www.fragment.UtilFragmentFifZone;
import com.bashi_group_01.www.fragment.UtilFragmentStatLine;
import com.bashi_group_01.www.fragment.UtilFragmentStatNum;
/**
 * ’æ≈∆≤È—Ø
 * @author share
 *
 */
public class ZhangpanselectActivity extends FragmentActivity implements OnClickListener{

	private ImageView imageView;
	private RadioGroup mRadioGroup;
	private RadioButton mRb_zone, mRb_line, mRb_num;
	private FragmentManager fm;
	private FragmentTransaction ft;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zhangpanselect);
		initViews();
		initEvents();
		mRadioGroup.check(R.id.rb_util_fifzone);
		ft.add(R.id.fm_util_fifzone, new UtilFragmentFifZone());
		ft.commit();
	}
	
	private void initViews() {
		// TODO Auto-generated method stub
		fm = getSupportFragmentManager();
		ft = fm.beginTransaction();
		this.imageView = (ImageView) findViewById(R.id.titleback_zhanpanselect);
		this.mRadioGroup = (RadioGroup) findViewById(R.id.rp_fif);
		this.mRb_zone = (RadioButton) findViewById(R.id.rb_util_fifzone);
		this.mRb_line = (RadioButton) findViewById(R.id.rb_util_fifline);
		this.mRb_num = (RadioButton) findViewById(R.id.rb_util_fifnum);
	}
	
	private void initEvents() {
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
		case R.id.titleback_zhanpanselect:
			finish();
			break;
		case R.id.rb_util_fifzone:
			ft.replace(R.id.fm_util_fifzone, new UtilFragmentFifZone());
			break;
		case R.id.rb_util_fifline:
			UtilFragmentStatLine utilFragmentStatLine = new UtilFragmentStatLine();
			bundle.putString("type", "LCD");
			utilFragmentStatLine.setArguments(bundle);
			ft.replace(R.id.fm_util_fifzone, utilFragmentStatLine);
			break;
		case R.id.rb_util_fifnum:
			UtilFragmentStatNum utilFragmentStatNum = new UtilFragmentStatNum();
			bundle.putString("type", "LCD");
			utilFragmentStatNum.setArguments(bundle);
			ft.replace(R.id.fm_util_fifzone, utilFragmentStatNum);
			break;
		}
		ft.commit();
	}
}
