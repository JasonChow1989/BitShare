package com.bashi_group_01.www.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bashi_group_01.www.activity.R;
import com.bashi_group_01.www.activity.UtilStatNumWebView;
import com.bashi_group_01.www.sliding.lib.ClearEditText;

public class UtilFragmentStatNum extends Fragment {
	
	private ClearEditText textView;
	private Button button;
	private String url = "http://116.236.170.106:9001/MobileAdWeb/ShowDataAd.aspx?roadline=&roadname=&CapitalNumber=";
	private String type;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fm_util_stat_num, null);
		textView = (ClearEditText) view.findViewById(R.id.edt_util_stat_num);
		button = (Button) view.findViewById(R.id.util_stat_stat_num);
		Bundle bundle = this.getArguments();
		type = bundle.getString("type");
		
		System.out.println(type);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String num = textView.getText().toString();
				Intent intent = new Intent();
				intent.putExtra("titlename", "…Ë±∏±‡∫≈≤È—Ø");
				intent.putExtra("num", num);
				intent.putExtra("url", url);
				intent.putExtra("type", type);
				intent.setClass(getActivity(), UtilStatNumWebView.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.others_getin, R.anim.others_getout);
			}
		});
		return view;
	}
}
