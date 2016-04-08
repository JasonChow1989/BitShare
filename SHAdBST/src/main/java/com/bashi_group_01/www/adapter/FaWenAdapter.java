package com.bashi_group_01.www.adapter;

import java.util.List;

import com.bashi_group_01.www.activity.R;
import com.bashi_group_01.www.adapter.MeetingAdapter.ViewHolder;
import com.bashi_group_01.www.domain.Fawen;
import com.bashi_group_01.www.domain.Meetting;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FaWenAdapter extends BaseAdapter{
	
	private Context context;
	private List<Fawen> list = null;

	public FaWenAdapter(Context context, List<Fawen> list) {
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
			convertView = LinearLayout.inflate(context, R.layout.item_fawen,
					null);
			viewHolder.title = (TextView) convertView
					.findViewById(R.id.event_fawenitem_title);
			viewHolder.date = (TextView) convertView
					.findViewById(R.id.event_fawenitem_date);
			viewHolder.code = (TextView) convertView
					.findViewById(R.id.event_fawenitem_code);

			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.title.setText(list.get(position).getFileTitle());
		viewHolder.date.setText(list.get(position).getDraftDate());
		viewHolder.code.setText(list.get(position).getFileCode());
		return convertView;
	}
	
	class ViewHolder{
		public TextView title;
		public TextView code;
		public TextView date;
	}
}
