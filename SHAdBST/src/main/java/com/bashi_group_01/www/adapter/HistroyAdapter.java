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
import com.bashi_group_01.www.domain.EventTaskInfo;
import com.bashi_group_01.www.domain.ResolveComment;

public class HistroyAdapter extends BaseAdapter {

	private List<ResolveComment> list;
	private Context context;

	public HistroyAdapter(Context context, List<ResolveComment> list) {
		this.context = context;
		this.list = list;
		
		//System.out.println("adapterµÄ"+list.size());
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
		// TODO Auto-generated method stub
		ViewHolder holder = null;

		if (convertView == null) {
			holder = new ViewHolder();

			convertView = LinearLayout.inflate(context,
					R.layout.item_eventresolvehistory, null);

			holder.date = (TextView) convertView.findViewById(R.id.item_event_histroy_date);
			holder.fenxi = (TextView) convertView.findViewById(R.id.item_event_histroy_fenxi);
			holder.desc = (TextView) convertView.findViewById(R.id.item_event_histroy_taskdesc);
		
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		holder.date.setText(list.get(position).getHandledate().substring(6, 16));
		holder.fenxi.setText(list.get(position).getReason());
		holder.desc.setText(list.get(position).getComment());
		
		return convertView;
	}

	class ViewHolder {
		TextView date;
		TextView fenxi;
		TextView desc;
	}

}
