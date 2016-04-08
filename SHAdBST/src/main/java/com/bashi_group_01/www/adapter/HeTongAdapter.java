package com.bashi_group_01.www.adapter;

import java.util.List;

import com.bashi_group_01.www.activity.R;
import com.bashi_group_01.www.adapter.MeetingAdapter.ViewHolder;
import com.bashi_group_01.www.domain.Fawen;
import com.bashi_group_01.www.domain.HeTong;
import com.bashi_group_01.www.domain.Meetting;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HeTongAdapter extends BaseAdapter{
	
	private Context context;
	private List<HeTong> list = null;

	public HeTongAdapter(Context context, List<HeTong> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LinearLayout.inflate(context, R.layout.item_hetong,
					null);
			viewHolder.title = (TextView) convertView
					.findViewById(R.id.event_hetongitem_cutomer);
			viewHolder.date = (TextView) convertView
					.findViewById(R.id.event_hetongitem_signDate);
			viewHolder.code = (TextView) convertView
					.findViewById(R.id.event_hetongitem_contractId);

			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.title.setText(list.get(position).getCutomer());
		viewHolder.date.setText(list.get(position).getSignDate());
		viewHolder.code.setText(list.get(position).getContractId());
		return convertView;
	}
	
	class ViewHolder{
		public TextView title;
		public TextView code;
		public TextView date;
	}
}
