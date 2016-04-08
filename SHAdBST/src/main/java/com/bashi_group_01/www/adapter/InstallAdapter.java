package com.bashi_group_01.www.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bashi_group_01.www.activity.R;
import com.bashi_group_01.www.domain.Install;

public class InstallAdapter extends BaseAdapter{
	
	Context context;
	List<Install> list;
	
	public InstallAdapter(Context context,List<Install> list){
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
		// TODO Auto-generated method stub
		ViewHoler holder = null;
		
		if(convertView == null){
			holder = new ViewHoler();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_install, null);
			holder.title = (TextView) convertView.findViewById(R.id.item_install_title);
			holder.state = (TextView) convertView.findViewById(R.id.item_install_state);
			holder.code = (TextView) convertView.findViewById(R.id.item_install_code);
			holder.date = (TextView) convertView.findViewById(R.id.item_install_date);
		
			convertView.setTag(holder);
		}else {
			holder = (ViewHoler) convertView.getTag();
		}
		
		System.out.println(list.get(position).getTerminal_Name());
		
		
		holder.title.setText(list.get(position).getTerminal_Name());
		if("False".equals(list.get(position).getInstallStatus())){
			holder.state.setText("待安装");
		}else {
			holder.state.setText("已安装");
		}
		
		holder.code.setText("编号:  "+list.get(position).getCapitalNumber());
		holder.date.setText(list.get(position).getCreateDate());
		return convertView;
	}
	
	class ViewHoler{
		TextView title;
		TextView state;
		TextView code;
		TextView date;
	}

}
