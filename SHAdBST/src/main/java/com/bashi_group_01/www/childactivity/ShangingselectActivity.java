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
import com.bashi_group_01.www.fragment.UtilFragmentLightZone;
import com.bashi_group_01.www.fragment.UtilFragmentStatLine;
import com.bashi_group_01.www.fragment.UtilFragmentStatNum;
/**
 * ����Ƭ��ѯ
 * @author share
 *
 */
public class ShangingselectActivity extends FragmentActivity implements OnClickListener {
	
	private ImageView imageView;
	private RadioGroup mRadioGroup;
	private RadioButton mRb_zone, mRb_line, mRb_num;
	private FragmentManager fm;
	private FragmentTransaction ft;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shangingselect);
		initViews();
		initEvent();
		mRadioGroup.check(R.id.rb_util_ligthzone);
		ft.add(R.id.fm_util_ligthzone, new UtilFragmentLightZone());
		ft.commit();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		fm = getSupportFragmentManager();
		ft = fm.beginTransaction();
		this.imageView = (ImageView) findViewById(R.id.titleback_shangingselect);
		this.mRadioGroup = (RadioGroup) findViewById(R.id.rp_ligth);
		this.mRb_zone = (RadioButton) findViewById(R.id.rb_util_ligthzone);
		this.mRb_line = (RadioButton) findViewById(R.id.rb_util_ligthline);
		this.mRb_num = (RadioButton) findViewById(R.id.rb_util_ligthnum);
	}

	private void initEvent() {
		// TODO Auto-generated method stub
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
		case R.id.titleback_shangingselect:
			finish();
			break;
		case R.id.rb_util_ligthzone:
			ft.replace(R.id.fm_util_ligthzone, new UtilFragmentLightZone());
			break;
		case R.id.rb_util_ligthline:
			UtilFragmentStatLine utilFragmentStatLine = new UtilFragmentStatLine();
			bundle.putString("type", "Lamp");
			utilFragmentStatLine.setArguments(bundle);
			ft.replace(R.id.fm_util_ligthzone, utilFragmentStatLine);
			break;
		case R.id.rb_util_ligthnum:
			UtilFragmentStatNum utilFragmentStatNum = new UtilFragmentStatNum();
			bundle.putString("type", "Lamp");
			utilFragmentStatNum.setArguments(bundle);
			ft.replace(R.id.fm_util_ligthzone, utilFragmentStatNum);
			break;
		}
		ft.commit();
	}
}
