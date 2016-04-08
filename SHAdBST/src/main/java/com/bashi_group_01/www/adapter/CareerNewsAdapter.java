package com.bashi_group_01.www.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bashi_group_01.www.activity.R;
import com.bashi_group_01.www.domain.CareerNews;
import com.bashi_group_01.www.domain.News;

public class CareerNewsAdapter extends BaseAdapter {

	private Context context;
	private List<CareerNews> list = null;

	public CareerNewsAdapter(Context context, List<CareerNews> list) {
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
			convertView = LinearLayout.inflate(context,
					R.layout.items_careernews, null);
			viewHolder.title = (TextView) convertView
					.findViewById(R.id.careernews_items_title);
			viewHolder.date = (TextView) convertView
					.findViewById(R.id.careernews_items_date);

			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.date
				.setText(list.get(position).getFolderName()
						+ " "
						+ changeDate(Long.parseLong(list.get(position)
								.getCreateTime())));
		viewHolder.title.setText(list.get(position).getTitle());

		return convertView;
	}

	static class ViewHolder {
		public TextView title;
		public TextView date;
	}

	// 添加数据
	public void addItem(CareerNews i) {
		list.add(i);
	}

	private String changeDate(long ms) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");// 初始化Formatter的转换格式。
		String hms = formatter.format(ms);

		return hms;
	}
}
