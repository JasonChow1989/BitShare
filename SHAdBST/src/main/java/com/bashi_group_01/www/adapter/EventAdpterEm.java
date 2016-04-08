package com.bashi_group_01.www.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bashi_group_01.www.activity.R;
import com.bashi_group_01.www.domain.EventUser;

public class EventAdpterEm extends BaseAdapter{

	Context context;
	List<EventUser> list;

	public EventAdpterEm(Context context, List<EventUser> list) {
		super();
		this.list = list;
		this.context = context;
	}

	public final class ViewHolder {

		public TextView uname;
		public TextView unum;
		public TextView uphone;
		public TextView uposition;

	}
	
	/**
	 * ��ListView���ݷ����仯ʱ,���ô˷���������ListView
	 * @param list
	 */
	public void updateListView(List<EventUser> list){
		this.list = list;
		notifyDataSetChanged();
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
		return list.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;

		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_eventenemy, null);
			viewHolder.uname = (TextView) convertView
					.findViewById(R.id.enemery_name);
			viewHolder.unum = (TextView) convertView
					.findViewById(R.id.enemery_tel);
			viewHolder.uphone = (TextView) convertView
					.findViewById(R.id.enemery_phone);
			viewHolder.uposition = (TextView) convertView
					.findViewById(R.id.enemery_position);

			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.uname.setText(list.get(position).getName());
		viewHolder.uposition.setText(list.get(position).getDeptposition());
		viewHolder.unum.setText(list.get(position).getCornet());
		viewHolder.uphone.setText(list.get(position).getMobilephone());
		
		return convertView;
	}

	/**
	 * ����ListView�ĵ�ǰλ�û�ȡ���������ĸ��Char asciiֵ
	 *//*
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	*//**
	 * ���ݷ��������ĸ��Char asciiֵ��ȡ���һ�γ��ָ�����ĸ��λ��
	 *//*
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		
		return -1;
	}
	
	*//**
	 * ��ȡӢ�ĵ�����ĸ����Ӣ����ĸ��#���档
	 * 
	 * @param str
	 * @return
	 *//*
	private String getAlpha(String str) {
		String  sortStr = str.trim().substring(0, 1).toUpperCase();
		// ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}*/
}
