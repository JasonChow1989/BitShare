package com.bashi_group_01.www.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bashi_group_01.www.activity.R;
import com.bashi_group_01.www.childactivity.ChenumselectActivity;
import com.bashi_group_01.www.childactivity.ChepanselectActivity;
import com.bashi_group_01.www.childactivity.LineselectActivity;
import com.bashi_group_01.www.childactivity.OrderselectActivity;
import com.bashi_group_01.www.childactivity.PinpanselectActivity;
import com.bashi_group_01.www.childactivity.RoadNameActivity;
import com.bashi_group_01.www.childactivity.XianluselectActivity;

public class StationFragment extends Fragment {

	private LinearLayout layout1,layout2,layout3,layout4,layout5;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fm_station, null);
		layout1 = (LinearLayout) view.findViewById(R.id.layout1);
		layout2 = (LinearLayout) view.findViewById(R.id.layout2);
		layout3 = (LinearLayout) view.findViewById(R.id.layout3);
		layout4 = (LinearLayout) view.findViewById(R.id.layout4);
		layout5 = (LinearLayout) view.findViewById(R.id.layout5);
		layout1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), XianluselectActivity.class);
				startActivity(intent);
			}
		});

		layout2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), ChepanselectActivity.class);
				startActivity(intent);
			}
		});
		
		layout3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), PinpanselectActivity.class);
				startActivity(intent);
			}
		});

		
		layout4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), ChenumselectActivity.class);
				startActivity(intent);
			}
		});

		layout5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), OrderselectActivity.class);
				startActivity(intent);
			}
		});

		return view;
	}
}
