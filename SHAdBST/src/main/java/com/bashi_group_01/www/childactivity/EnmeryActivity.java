package com.bashi_group_01.www.childactivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.bashi_group_01.www.activity.EventGetAndDoActivity;
import com.bashi_group_01.www.activity.R;
import com.bashi_group_01.www.adapter.EventAdpterEm;
import com.bashi_group_01.www.domain.EventUser;
import com.bashi_group_01.www.sliding.lib.ClearEditText;
/**
 * 紧急事务类;
 * @author share
 *
 */
public class EnmeryActivity extends Activity implements OnClickListener {

	private ImageView imageView;
	private ListView lv_content;
	private List<EventUser> list = null;
	private EventAdpterEm adapter;
	EventUser eventUser;
	private String[] usersArray;
	private SharedPreferences sp;
	private String[] items;
	String[] username;

	private LinearLayout group1, group2;

	/**
	 * dialog
	 */
	String content;
	Dialog mdialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enmery);
		initViews();
		initEvents();

		this.lv_content.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				items = new String[2];
				items[0] = list.get(position).getCornet();
				items[1] = list.get(position).getMobilephone();

				if (position == 0) {
					return;
				}

				TextView title;
				RadioGroup rb_enmery;
				final RadioButton bt_tel;
				final RadioButton bt_msg;
				Button button1, button2;

				final AlertDialog builder;
				WindowManager manager = getWindowManager();
				Display display = manager.getDefaultDisplay();
				int width = display.getWidth();
				int height = display.getHeight();

				builder = new AlertDialog.Builder(EnmeryActivity.this).create();
				LayoutInflater inflater = getLayoutInflater();
				view = inflater.inflate(R.layout.dialog_enmery, null);
				builder.setView(view, 0, 0, 0, 0);

				title = (TextView) view.findViewById(R.id.dialog_enmery_title);
				rb_enmery = (RadioGroup) view.findViewById(R.id.rgroup_enmery);
				bt_tel = (RadioButton) view
						.findViewById(R.id.dialog_enmery_rb01);
				bt_msg = (RadioButton) view
						.findViewById(R.id.dialog_enmery_rb02);
				button1 = (Button) view.findViewById(R.id.dialog_enmery_bt_tel);
				button2 = (Button) view.findViewById(R.id.dialog_enmery_bt_msg);

				title.setText(list.get(position).getName());

				bt_tel.setText(list.get(position).getCornet());
				bt_msg.setText(list.get(position).getMobilephone());
				rb_enmery
						.setOnCheckedChangeListener(new OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(RadioGroup group,
									int checkedId) {
								// TODO Auto-generated method stub
								if (checkedId == bt_tel.getId()) {
									content = bt_tel.getText().toString();
								}
								if (checkedId == bt_msg.getId()) {
									content = bt_msg.getText().toString();
								}
							}
						});

				button1.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setAction("android.intent.action.CALL");
						intent.setData(Uri.parse("tel:" + content));
						startActivity(intent);
						content = "";
						builder.dismiss();
					}
				});

				button2.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Uri uri = Uri.parse("smsto:" + content);
						Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
						startActivity(intent);
						content = "";
						builder.dismiss();
					}
				});

				builder.show();
				builder.getWindow().setLayout(width, height / 2 + 5);
			}
		});

	}

	private void initViews() {
		this.imageView = (ImageView) findViewById(R.id.titleback_eventemeny);
		this.lv_content = (ListView) findViewById(R.id.enemery_listview);
		this.group1 = (LinearLayout) findViewById(R.id.ll_enmery_center);
		this.group2 = (LinearLayout) findViewById(R.id.ll_enmery_livingpick);

		list = new ArrayList<EventUser>();
		usersArray = getSharedPreference("USERS");
		for (int i = 1; i < usersArray.length; i += 10) {
			EventUser eventUser = new EventUser();
			eventUser.setId(usersArray[i]);
			eventUser.setEmployeeId(usersArray[i + 1]);
			eventUser.setMobilephone(usersArray[i + 2]);
			eventUser.setDepartment(usersArray[i + 3]);
			eventUser.setName(usersArray[i + 4]);
			eventUser.setLoginName(usersArray[i + 5]);
			eventUser.setOrder(usersArray[i + 6]);
			eventUser.setCornet(usersArray[i + 7]);
			eventUser.setPosition(usersArray[i + 8]);
			eventUser.setDeptposition(usersArray[i + 9]);
			list.add(eventUser);
		}

		Collections.sort(list, new Comparator<EventUser>() {
			@Override
			public int compare(EventUser lhs, EventUser rhs) {
				// TODO Auto-generated method stub
				String con1 = lhs.getOrder();
				String con2 = rhs.getOrder();

				if (con1.isEmpty() && con2.isEmpty()) {
					return 0;
				}
				if (con1.isEmpty()) {
					return -1;
				}
				if (con2.isEmpty()) {
					return 1;
				}
				return con1.compareTo(con2);
			}
		});

		adapter = new EventAdpterEm(this, list);
		lv_content.setAdapter(adapter);
	}

	private void initEvents() {
		imageView.setOnClickListener(this);
		group1.setOnClickListener(this);
		group2.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.titleback_eventemeny:
			EnmeryActivity.this.finish();
			break;
		case R.id.ll_enmery_center:
			Intent intent1 = new Intent();
			intent1.setAction("android.intent.action.CALL");
			intent1.setData(Uri.parse("tel:" + "02153857846"));
			startActivity(intent1);

			break;
		case R.id.ll_enmery_livingpick:
			Intent intent = new Intent();
			intent.setClass(EnmeryActivity.this, EventGetAndDoActivity.class);
			startActivity(intent);
			break;
		}
	}

	/**
	 * 
	 * sp取数据;
	 */

	public String[] getSharedPreference(String key) {
		String regularEx = "#";
		String[] str = null;
		String values;
		sp = EnmeryActivity.this.getSharedPreferences("event",
				EnmeryActivity.this.MODE_PRIVATE);
		values = sp.getString(key, "");
		str = values.split(regularEx);
		return str;
	}
}
