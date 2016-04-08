package com.bashi_group_01.www.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bashi_group_01.www.activity.EventGetAndDoActivity;
import com.bashi_group_01.www.activity.EventLiveWorkingActivity;
import com.bashi_group_01.www.activity.R;
import com.bashi_group_01.www.childactivity.EnmeryActivity;
import com.bashi_group_01.www.childactivity.EventchackActivity;
import com.bashi_group_01.www.childactivity.FileMakeActivity;
import com.bashi_group_01.www.childactivity.MeetnotiyActivity;

public class EventFragment extends Fragment implements OnClickListener {

	private String[] items = { "交办事务", "现场办公" };
	private View view;
	private LinearLayout layout1, layout2, layout3, layout4, layout5;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fm_event, null);
		initViews();
		ininEvent();
		return view;
	}

	private void ininEvent() {
		layout1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(getActivity()).setItems(items,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								Intent intent = new Intent();
								switch (which) {
								case 0:
									intent.setClass(getActivity(), EventGetAndDoActivity.class);
									break;
								case 1:
									intent.setClass(getActivity(), EventLiveWorkingActivity.class);
									break;
								}
								dialog.cancel();
								startActivity(intent);
							}
						}).create().show();
			}
		});
		layout2.setOnClickListener(this);
		layout3.setOnClickListener(this);
		layout4.setOnClickListener(this);
		layout5.setOnClickListener(this);
	}

	private void initViews() {
		this.layout1 = (LinearLayout) view.findViewById(R.id.llinear31);
		this.layout2 = (LinearLayout) view.findViewById(R.id.llinear32);
		this.layout3 = (LinearLayout) view.findViewById(R.id.llinear33);
		this.layout4 = (LinearLayout) view.findViewById(R.id.llinear34);
		this.layout5 = (LinearLayout) view.findViewById(R.id.llinear35);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		Intent intent = new Intent();
		switch (id) {
		case R.id.llinear32:
			intent.setClass(getActivity(), EventchackActivity.class);
			break;
		case R.id.llinear33:
			intent.setClass(getActivity(), EnmeryActivity.class);
			break;
		case R.id.llinear34:
			intent.setClass(getActivity(), MeetnotiyActivity.class);
			break;
		case R.id.llinear35:
			intent.setClass(getActivity(), FileMakeActivity.class);
			break;
		}
		startActivity(intent);
	}
}
