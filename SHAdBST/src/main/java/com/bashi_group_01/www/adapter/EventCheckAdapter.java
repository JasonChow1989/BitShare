package com.bashi_group_01.www.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bashi_group_01.www.activity.R;
import com.bashi_group_01.www.domain.EventCheck;
import com.bashi_group_01.www.sliding.lib.SortModel;

public class EventCheckAdapter extends BaseAdapter {

	private Context context;
	private List<EventCheck> list;

	public EventCheckAdapter(Context context, List<EventCheck> list) {
		super();
		this.context = context;
		this.list = list;
	}

	public void updateListView(List<EventCheck> list) {
		this.list = list;
		this.notifyDataSetChanged();
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
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LinearLayout.inflate(context,
					R.layout.item_eventcheck, null);
			viewHolder.layout = (LinearLayout) convertView
					.findViewById(R.id.ll_eventcheck);
			viewHolder.pic = (ImageView) convertView
					.findViewById(R.id.tv_eventcheck_item_pic);
			viewHolder.contenttype = (TextView) convertView
					.findViewById(R.id.tv_eventcheck_item_statetask);
			viewHolder.date = (TextView) convertView
					.findViewById(R.id.tv_eventcheck_item_date);
			viewHolder.tasktype = (TextView) convertView
					.findViewById(R.id.tv_eventcheck_item_tasktype);
			viewHolder.createname = (TextView) convertView
					.findViewById(R.id.tv_eventcheck_item_createname);
			viewHolder.location = (TextView) convertView
					.findViewById(R.id.tv_eventcheck_item_location);
			viewHolder.content = (TextView) convertView
					.findViewById(R.id.tv_eventcheck_item_taskcontent);
			viewHolder.view = convertView
					.findViewById(R.id.tv_eventcheck_item_line);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		String stat = list.get(position).getStateCha();
		viewHolder.date
				.setText(list.get(position).getCreateTime().substring(5));
		viewHolder.createname.setText(list.get(position).getCreateUser());
		viewHolder.location.setText(list.get(position).getLocation());
		if ("待处理".equals(stat)) {
			viewHolder.pic.setImageResource(R.drawable.deal);
			viewHolder.contenttype.setText(stat);
			viewHolder.view.setBackgroundColor(Color.parseColor("#ff6600"));
			viewHolder.content.setText(list.get(position).getTaskName() + stat);
			viewHolder.contenttype.setTextColor(Color.parseColor("#ff6600"));
			viewHolder.layout
					.setBackgroundResource(R.drawable.eventcheck_chuli);
		} else if ("待审核".equals(stat)) {
			viewHolder.pic.setImageResource(R.drawable.examine);
			viewHolder.contenttype.setText(stat);
			viewHolder.content.setText(list.get(position).getTaskName() + stat);
			viewHolder.layout
					.setBackgroundResource(R.drawable.eventcheck_shenhe);
			viewHolder.view.setBackgroundColor(Color.parseColor("#aac1de"));
			viewHolder.contenttype.setTextColor(Color.parseColor("#1166bb"));

		} else if ("已完成".equals(stat)) {
			viewHolder.pic.setImageResource(R.drawable.complete);
			viewHolder.contenttype.setText(stat);
			viewHolder.content.setText(list.get(position).getTaskName() + stat);
			viewHolder.layout
					.setBackgroundResource(R.drawable.eventcheck_wancheng);
			viewHolder.view.setBackgroundColor(Color.parseColor("#009944"));
			viewHolder.contenttype.setTextColor(Color.parseColor("#009944"));
		} else if ("待分发".equals(stat)) {
			viewHolder.pic.setImageResource(R.drawable.distribute);
			viewHolder.contenttype.setText(stat);
			viewHolder.content.setText(list.get(position).getTaskDesc());
			viewHolder.layout
					.setBackgroundResource(R.drawable.eventcheck_fenfa);
			viewHolder.view.setBackgroundColor(Color.parseColor("#0099ff"));
			viewHolder.contenttype.setTextColor(Color.parseColor("#0099ff"));
		}

		if ("3".equals(list.get(position).getType())) {
			viewHolder.tasktype.setText("交办事务");
			viewHolder.tasktype.setTextColor(Color.parseColor("#0099ff"));
		} else if ("4".equals(list.get(position).getType())) {
			viewHolder.tasktype.setText("现场办公");
			viewHolder.tasktype.setTextColor(Color.parseColor("#0099ff"));
		}

		return convertView;
	}

	static class ViewHolder {
		LinearLayout layout;
		View view;
		ImageView pic;
		TextView contenttype;
		TextView date;
		TextView tasktype;
		TextView createname;
		TextView location;
		TextView content;
	}

}
