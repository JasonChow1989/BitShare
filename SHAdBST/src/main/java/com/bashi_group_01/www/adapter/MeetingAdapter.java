package com.bashi_group_01.www.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bashi_group_01.www.activity.R;
import com.bashi_group_01.www.adapter.NewsAdapter.ViewHolder;
import com.bashi_group_01.www.domain.Meetting;

public class MeetingAdapter extends BaseAdapter {

	private Context context;
	private List<Meetting> list = null;

	public MeetingAdapter(Context context, List<Meetting> list) {
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
		return this.list.get(position);
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
			convertView = LinearLayout.inflate(context, R.layout.item_meetingnotiy,
					null);
			viewHolder.title = (TextView) convertView
					.findViewById(R.id.meeting_title);
			viewHolder.date = (TextView) convertView
					.findViewById(R.id.meeting_date);

			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.title.setText(list.get(position).getTitle());
		viewHolder.date.setText(list.get(position).getMeetingTime());

		return convertView;
	}

	static class ViewHolder {
		public TextView title;
		public TextView date;
	}

}
