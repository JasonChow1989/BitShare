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
import com.bashi_group_01.www.childactivity.HoucheselectActivity;
import com.bashi_group_01.www.childactivity.ShangingselectActivity;
import com.bashi_group_01.www.childactivity.StatpanselectActivity;
import com.bashi_group_01.www.childactivity.ZhangpanselectActivity;

public class UtilFragment extends Fragment implements OnClickListener{
	
	private LinearLayout llinear1,llinear2,llinear3,llinear4;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fm_util, null);
		llinear1 = (LinearLayout) view.findViewById(R.id.llinear21);
		llinear2 = (LinearLayout) view.findViewById(R.id.llinear22);
		llinear3 = (LinearLayout) view.findViewById(R.id.llinear23);
		llinear4 = (LinearLayout) view.findViewById(R.id.llinear24);
		initEvent();
		return view;
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		llinear1.setOnClickListener(this);
		llinear2.setOnClickListener(this);
		llinear3.setOnClickListener(this);
		llinear4.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		Intent intent = new Intent();
		switch (id) {
		case R.id.llinear21:
			intent.setClass(getActivity(), StatpanselectActivity.class);
		break;
		case R.id.llinear22:
			intent.setClass(getActivity(), HoucheselectActivity.class);
			break;
		case R.id.llinear23:
			intent.setClass(getActivity(), ZhangpanselectActivity.class);
			break;
		case R.id.llinear24:
			intent.setClass(getActivity(), ShangingselectActivity.class);
			break;
		}
		startActivity(intent);
	}
}
