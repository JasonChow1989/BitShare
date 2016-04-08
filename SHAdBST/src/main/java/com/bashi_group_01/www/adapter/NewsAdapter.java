package com.bashi_group_01.www.adapter;

import java.util.List;
import java.util.zip.Inflater;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bashi_group_01.www.activity.R;
import com.bashi_group_01.www.adapter.EventAdpterEm.ViewHolder;
import com.bashi_group_01.www.domain.News;

public class NewsAdapter extends BaseAdapter {

	private Context context;
	private List<News> list = null;

	public NewsAdapter(Context context, List<News> list) {
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
			convertView = LinearLayout.inflate(context, R.layout.items_news,
					null);
			viewHolder.title = (TextView) convertView
					.findViewById(R.id.news_items_title);
			viewHolder.date = (TextView) convertView
					.findViewById(R.id.news_items_date);

			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.title.setText(list.get(position).getTitle());
		viewHolder.date.setText(list.get(position).getIssueDate());

		return convertView;
	}

	static class ViewHolder {
		public TextView title;
		public TextView date;
	}

	// Ìí¼ÓÊý¾Ý
	public void addItem(News i) {
		list.add(i);
	}
}
