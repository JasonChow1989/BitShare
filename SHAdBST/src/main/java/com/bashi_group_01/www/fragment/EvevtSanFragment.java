package com.bashi_group_01.www.fragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.bashi_group_01.www.activity.EventCheckItemsChuLiActivity;
import com.bashi_group_01.www.activity.EventCheckItemsFinishActivity;
import com.bashi_group_01.www.activity.EventCheckItemsShenHeActivity;
import com.bashi_group_01.www.activity.R;
import com.bashi_group_01.www.activity.RefreshableView;
import com.bashi_group_01.www.activity.Welcome;
import com.bashi_group_01.www.activity.RefreshableView.PullToRefreshListener;
import com.bashi_group_01.www.adapter.EventCheckAdapter;
import com.bashi_group_01.www.domain.EventCheck;
import com.bashi_group_01.www.util.PasueEventCheck;

public class EvevtSanFragment extends Fragment {

	private ArrayList<EventCheck> list;
	private ListView listView;
	private String[] eventArray;
	private EventCheckAdapter adapter;
	SharedPreferences sp;
	SharedPreferences.Editor editor_event;
	RefreshableView refreshableView;
	String eventCheckJson3;
	String[] eventArray3;

	public String taskId;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fm_eventcheck_san, null);
		listView = (ListView) view.findViewById(R.id.listview_eventcheck_san);
		refreshableView = (RefreshableView) view
				.findViewById(R.id.event_refreshable_view_san);
		eventArray = getSharedPreference("EVENT3");
		// System.out.println(eventArray.length);
		parData(eventArray);
		showList(list);

		refreshableView.setOnRefreshListener(new PullToRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				// 请求最新数据;
				new HttpNewDataThree()
						.execute("http://116.236.170.106:8384/FunctionModule/RemoteData/TransHandler.ashx?startIndex=0&pageSize=100&queryTime=1&queryState=0&userkey="
								+ Welcome.userName);
			}
		}, 4);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				System.out.println(position);

				if ("待分发".equals(((EventCheck) adapter.getItem(position))
						.getStateCha())) {
					return;
				}
				Intent intent = new Intent();
				intent.putExtra("type",
						((EventCheck) adapter.getItem(position)).getType());
				intent.putExtra("taskId",
						((EventCheck) adapter.getItem(position)).getTaskId());
				intent.putExtra("taskName",
						((EventCheck) adapter.getItem(position)).getTaskName());
				intent.putExtra("StateCha",
						((EventCheck) adapter.getItem(position)).getStateCha());
				intent.putExtra("createUser", ((EventCheck) adapter
						.getItem(position)).getCreateUser());
				intent.putExtra("location",
						((EventCheck) adapter.getItem(position)).getLocation());
				intent.putExtra("createTime", ((EventCheck) adapter
						.getItem(position)).getCreateTime());
				intent.putExtra("taskDesc",
						((EventCheck) adapter.getItem(position)).getTaskDesc());
				intent.putExtra("taskPicUrl", ((EventCheck) adapter
						.getItem(position)).getTaskPicUrl());
				intent.putExtra("taskType",
						((EventCheck) adapter.getItem(position)).getTaskType());
				intent.putExtra("hasThumb",
						((EventCheck) adapter.getItem(position)).getHasThumb());
				intent.putExtra("eventDay", 3);

				if ("待处理".equals(((EventCheck) adapter.getItem(position))
						.getStateCha())) {
					intent.setClass(EvevtSanFragment.this.getActivity(),
							EventCheckItemsChuLiActivity.class);
				}

				if ("待审核".equals(((EventCheck) adapter.getItem(position))
						.getStateCha())) {
					intent.setClass(EvevtSanFragment.this.getActivity(),
							EventCheckItemsShenHeActivity.class);
				}

				if ("已完成".equals(((EventCheck) adapter.getItem(position))
						.getStateCha())) {
					intent.setClass(EvevtSanFragment.this.getActivity(),
							EventCheckItemsFinishActivity.class);
				}
				startActivity(intent);
			}
		});
		return view;
	}

	private void showList(ArrayList<EventCheck> list) {

		// System.out.println(list.size());

		if (adapter == null) {
			adapter = new EventCheckAdapter(getActivity(), list);
			listView.setAdapter(adapter);
		} else {
			adapter.updateListView(list);
		}
	}

	private List<EventCheck> parData(String[] eventArray) {
		list = new ArrayList<EventCheck>();
		for (int i = 1; i < eventArray.length; i += 12) {
			EventCheck eventCheck = new EventCheck();
			eventCheck.setType(eventArray[i]);
			eventCheck.setTaskId(eventArray[i + 1]);
			eventCheck.setTaskName(eventArray[i + 2]);
			eventCheck.setTaskType(eventArray[i + 3]);
			eventCheck.setStateCha(eventArray[i + 4]);
			eventCheck.setCreateTime(eventArray[i + 5]);
			eventCheck.setCreateUser(eventArray[i + 6]);
			eventCheck.setLocation(eventArray[i + 7]);
			eventCheck.setTaskDesc(eventArray[i + 8]);
			eventCheck.setTaskPicUrl(eventArray[i + 9]);
			eventCheck.setHasThumb(eventArray[i + 10]);
			list.add(eventCheck);
		}
		return list;
	}

	/**
	 * 
	 * sp取数据;
	 */

	public String[] getSharedPreference(String key) {
		String regularEx = "#";
		String[] str = null;
		String values;
		sp = getActivity().getSharedPreferences("event",
				getActivity().MODE_PRIVATE);
		values = sp.getString(key, "");
		str = values.split(regularEx);
		return str;
	}

	// http请求最新数据；

	class HttpNewDataThree extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			InputStream inputStream = null;
			ByteArrayOutputStream bos = null;

			try {
				URL url = new URL(params[0]);

				HttpURLConnection httpURLConnection = (HttpURLConnection) url
						.openConnection();
				httpURLConnection.setConnectTimeout(5000);
				httpURLConnection.setRequestMethod("GET");

				if (httpURLConnection.getResponseCode() == 200) {

					inputStream = httpURLConnection.getInputStream();
					// System.out.println(inputStream);

					bos = new ByteArrayOutputStream();

					byte[] buffer = new byte[1024];

					int len = -1;
					while ((len = inputStream.read(buffer)) != -1) {
						bos.write(buffer, 0, len);
					}

					eventCheckJson3 = new String(bos.toByteArray(), "UTF-8");
					// System.out.println(eventCheckJson3);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if (bos!=null) {
					try {
						bos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}if(inputStream != null){
					try {
						inputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			return eventCheckJson3;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			eventArray3 = PasueEventCheck.Pasue(result);
			sp = getActivity().getSharedPreferences("event",
					getActivity().MODE_PRIVATE);
			setSharedPreference1("EVENT3", eventArray3);
			eventArray3 = getSharedPreference("EVENT3");
			parData(eventArray3);
			showList(list);
			refreshableView.finishRefreshing();
		}
	}

	/**
	 * 
	 * sp存放数据;
	 * 
	 */

	public void setSharedPreference1(String key, String[] values) {
		String regularEx = "#";
		String str = "";
		if (values != null && values.length > 0) {
			for (String value : values) {
				str += value;
				str += regularEx;
			}
			editor_event = sp.edit();
			editor_event.putString(key, str);
			editor_event.commit();
		}
	}

}
