package com.bashi_group_01.www.activity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.bashi_group_01.www.adapter.ViewPagerAdapter;
import com.bashi_group_01.www.fragment.EventFragment;
import com.bashi_group_01.www.fragment.FristFragment;
import com.bashi_group_01.www.fragment.StationFragment;
import com.bashi_group_01.www.fragment.UtilFragment;
import com.pgyersdk.update.PgyUpdateManager;

/**
 * 主Activity
 * 
 * @author share
 * 
 */
public class MainActivity extends FragmentActivity implements
		OnCheckedChangeListener, OnPageChangeListener {

	String appId = "32c176f53ff3861a1109dc2552e79c6a"; // 蒲公英注册或上传应用获取的AppId
	private RadioGroup mRadioGroup;
	private ViewPager viewPager;
	private long exitTime = 0;
	private final int HOME = 0;
	private final int STAT = 1;
	private final int UTIL = 2;
	private final int EVENT = 3;
	private final int ALL = 4;
	private List<Fragment> list = null;

	private SharedPreferences sp1,sp_event;
	private String userName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
		System.out.println("----------oncreate--------------");
		ViewPagerAdapter adapter = new ViewPagerAdapter(
				this.getSupportFragmentManager(), list);

		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(this);

		mRadioGroup.check(R.id.rb_head);
		mRadioGroup.setOnCheckedChangeListener(this);
		viewPager.setCurrentItem(HOME);
	}

	private void initViews() {
		viewPager = (ViewPager) findViewById(R.id.lin_content);
		mRadioGroup = (RadioGroup) findViewById(R.id.radiogroup);
		list = new ArrayList<Fragment>();
		list.add(new FristFragment());
		list.add(new StationFragment());
		list.add(new UtilFragment());
		list.add(new EventFragment());
		list.add(new AllItemsFragment());
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch (checkedId) {
		case R.id.rb_head:
			viewPager.setCurrentItem(HOME);
			break;
		case R.id.rb_station:
			viewPager.setCurrentItem(STAT);
			break;
		case R.id.rb_util:
			viewPager.setCurrentItem(UTIL);
			break;
		case R.id.rb_event:
			viewPager.setCurrentItem(EVENT);
			break;
		case R.id.rb_allitems:
			viewPager.setCurrentItem(ALL);
			break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		if (arg0 == HOME) {
			mRadioGroup.check(R.id.rb_head);
		} else if (arg0 == STAT) {
			mRadioGroup.check(R.id.rb_station);
		} else if (arg0 == UTIL) {
			mRadioGroup.check(R.id.rb_util);
		} else if (arg0 == EVENT) {
			mRadioGroup.check(R.id.rb_event);
		} else if (arg0 == ALL) {
			mRadioGroup.check(R.id.rb_allitems);
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		System.out.println("----------onstart---------");
		PgyUpdateManager.register(this, appId);
		sp1 = getSharedPreferences("userInfo", MODE_PRIVATE);
		
		sp_event = getSharedPreferences("event", MODE_PRIVATE);
		
		userName = sp1.getString("USER_NAME", "");
		System.out.println(userName);

		// 极光推送;
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
		JPushInterface.setAlias(this, userName, new TagAliasCallback() {
			@Override
			public void gotResult(int arg0, String arg1, Set<String> arg2) {
				// TODO Auto-generated method stub
				Log.i("test", "test" + arg0);
			}
		});

		System.out.println("-------Registration ID----------:"
				+ JPushInterface.getRegistrationID(this));
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		sp_event.edit().clear().commit();
	}
}
