package com.bashi_group_01.www.fragment;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.ls.LSInput;

import com.bashi_group_01.www.activity.FaWenDetailActivity;
import com.bashi_group_01.www.activity.R;
import com.bashi_group_01.www.activity.Welcome;
import com.bashi_group_01.www.adapter.FaWenAdapter;
import com.bashi_group_01.www.domain.Fawen;
import com.bashi_group_01.www.util.PasueFaWen;

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

public class EvevtFaWenFragment extends Fragment {

	private ListView listView;
	private String url = "http://116.236.170.106:8384/FunctionModule/RemoteData/GetSendFileHandler.ashx?startIndex=0&pagesize=1000&querytime=0&userkey=";
	private String fawenJson;
	private FaWenAdapter adapter;
	private List<Fawen> list;

	private SharedPreferences spPreferences;
	String username;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fm_eventfilemake_fawen, null);
		listView = (ListView) view
				.findViewById(R.id.event_filemake_fawenlistview);
		spPreferences = getActivity().getSharedPreferences("userInfo",
				getActivity().MODE_PRIVATE);
		username = spPreferences.getString("USER_NAME", "");
		list = new ArrayList<Fawen>();
		return view;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		new HttpGetFile().execute(url + username);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String tid = list.get(position).getTblRcdId();
				Intent intent = new Intent();
				intent.putExtra("id", tid);
				intent.putExtra("username", username);
				intent.setClass(getActivity(), FaWenDetailActivity.class);
				startActivity(intent);
			}
		});
	}

	class HttpGetFile extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			InputStream inputStream = null;
			ByteArrayOutputStream bos = null;
			try {
				URL url = new URL(params[0]);
				System.out.println(url);

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
					fawenJson = new String(bos.toByteArray(), "UTF-8");
					System.out.println(fawenJson);
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
			return fawenJson;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			list = PasueFaWen.Pasue(result);
			adapter = new FaWenAdapter(getActivity(), list);
			listView.setAdapter(adapter);
		}
	}
}
