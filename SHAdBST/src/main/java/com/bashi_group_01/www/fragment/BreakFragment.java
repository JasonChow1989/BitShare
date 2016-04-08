package com.bashi_group_01.www.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bashi_group_01.www.activity.MsgScreenTaskProgress;
import com.bashi_group_01.www.activity.R;
import com.bashi_group_01.www.activity.Welcome;
import com.bashi_group_01.www.adapter.BreakAdapter;
import com.bashi_group_01.www.adapter.InstallAdapter;
import com.bashi_group_01.www.domain.Break;
import com.bashi_group_01.www.util.PasueBreak;

public class BreakFragment extends Fragment {
	
	private String url = "http://114.215.236.58:8006/Interface/QueryInfoAllRemove.ashx";
	private List<NameValuePair> list;
	private String result;
	private TextView count;
	private List<Break> mList;
	private ListView listView;
	private BaseAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fm_break, null);
		list = new ArrayList<NameValuePair>();
		mList = new ArrayList<Break>();
		
		count = (TextView) view.findViewById(R.id.tv_fmbreak_count);
		listView = (ListView) view.findViewById(R.id.listview_fm_break);
		new HttpBreak().execute(url);
		return view;
	}
	
	class HttpBreak extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				HttpPost httpRequst = new HttpPost(params[0]);// 创建HttpPost对象

				list.add(new BasicNameValuePair("InstallName", Welcome.userName));
				if (list != null) {
					httpRequst
							.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
				}
				HttpResponse httpResponse = new DefaultHttpClient()
						.execute(httpRequst);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					System.out.println("===================");
					HttpEntity httpEntity = httpResponse.getEntity();
					result = EntityUtils.toString(httpEntity);// 取出应答字符串
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			System.out.println(result);
			mList = PasueBreak.Pasue(result);
			adapter = new BreakAdapter(getActivity(), mList);
			listView.setAdapter(adapter);
			
			count.setText("待处理信息: "+adapter.getCount());
			
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.putExtra("capitalNum", mList.get(position).getCapitalNumber());
					intent.putExtra("shopId", mList.get(position).getStopId());
					intent.putExtra("district", mList.get(position).getDistrict());
					intent.putExtra("terminal_Name", mList.get(position).getTerminal_Name());
					intent.putExtra("location", mList.get(position).getStationAddress());
					intent.putExtra("pathDirection", mList.get(position).getPathDirection());
					intent.putExtra("tblRcdId", mList.get(position).getTblRcdID());
					intent.putExtra("installpeople", mList.get(position).getInstallPeople());
					intent.putExtra("state", 2);
					intent.setClass(getActivity(), MsgScreenTaskProgress.class);
					startActivity(intent);
				}
			});
		}
	}
}
