package com.bashi_group_01.www.fragment;

import java.io.ByteArrayOutputStream;
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

import com.bashi_group_01.www.activity.EventHeTongDetailActivity;
import com.bashi_group_01.www.activity.R;
import com.bashi_group_01.www.activity.Welcome;
import com.bashi_group_01.www.adapter.HeTongAdapter;
import com.bashi_group_01.www.domain.HeTong;
import com.bashi_group_01.www.util.PasueHeTong;

public class EvevtHeTongFragment extends Fragment {

	private ListView listView;
	private String url = "http://116.236.170.106:8384/FunctionModule/RemoteData/GetContractHandler.ashx?startIndex=0&pagesize=1000&querytime=0&userkey=";
	private String heTongJson;
	private HeTongAdapter adapter;
	private List<HeTong> list;

	private SharedPreferences spPreferences;
	String username;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fm_eventfilemake_hetong, null);
		listView = (ListView) view
				.findViewById(R.id.event_filemake_hetonglistview);
		spPreferences = getActivity().getSharedPreferences("userInfo",
				getActivity().MODE_PRIVATE);
		username = spPreferences.getString("USER_NAME", "");
		list = new ArrayList<HeTong>();

		return view;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		new HttpHeTong().execute(url + username);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String rid = list.get(position).getTblRcdId();
				Intent intent = new Intent();
				intent.putExtra("id", rid);
				intent.putExtra("username", username);
				intent.setClass(getActivity(), EventHeTongDetailActivity.class);
				startActivity(intent);
			}
		});
	}

	class HttpHeTong extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			InputStream inputStream = null;
			ByteArrayOutputStream bos = null;
			try {
				URL url = new URL(params[0]);
				// System.out.println(url);

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
					heTongJson = new String(bos.toByteArray(), "UTF-8");
					System.out.println(heTongJson);

				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				try {
					if (bos != null) {
						bos.close();
					}
					if (inputStream != null) {
						inputStream.close();
					}
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
			return heTongJson;

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			list = PasueHeTong.Pasue(result);
			adapter = new HeTongAdapter(getActivity(), list);
			listView.setAdapter(adapter);
		}
	}
}
