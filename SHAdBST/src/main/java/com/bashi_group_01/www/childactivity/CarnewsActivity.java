package com.bashi_group_01.www.childactivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.bashi_group_01.www.activity.CareerNewsWebView;
import com.bashi_group_01.www.activity.FirstWebViewsActivity;
import com.bashi_group_01.www.activity.R;
import com.bashi_group_01.www.activity.Welcome;
import com.bashi_group_01.www.adapter.CareerNewsAdapter;
import com.bashi_group_01.www.adapter.NewsAdapter;
import com.bashi_group_01.www.childactivity.ComplnewsActivity.ButtonClickListener;
import com.bashi_group_01.www.childactivity.ComplnewsActivity.HttpGetNews;
import com.bashi_group_01.www.childactivity.ComplnewsActivity.OnScrollListener;
import com.bashi_group_01.www.domain.CareerNews;
import com.bashi_group_01.www.domain.News;
import com.bashi_group_01.www.util.PasueCareerNews;
import com.bashi_group_01.www.util.PasueNews;
/**
 * ��ҵ������;
 * @author share
 *
 */
public class CarnewsActivity extends Activity implements OnClickListener {

	private ImageView titleback;
	private ListView listView;
	private CareerNewsAdapter adapter;
	private List<CareerNews> list;
	private int startIndex = 0;
	private String pageSize = "10";

	private String careerNewsurl = "http://task.84000.com.cn:7070/BusTrackerServer/AndroidTextNews?";
	private String startIndexUrl = "start=";
	private String pageSizeUrl = "pageSize=";
	private String realUrl;
	private Button loadMore;
	private String careerNewsJson;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_carnews);
		initViews();
		initEvents();

		// ��ӵײ���ť
		View bottomView = getLayoutInflater().inflate(R.layout.bottom1, null);
		loadMore = (Button) bottomView.findViewById(R.id.load1);
		loadMore.setOnClickListener(new ButtonClickListener());

		listView.addFooterView(bottomView);
		// setListAdapter(adapter);
		listView.setAdapter(adapter);
		// ��listView�����¼�
		listView.setOnScrollListener(new OnScrollListener());
	
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("foldname", ((CareerNews)adapter.getItem(position)).getFolderName());
				intent.putExtra("id", ((CareerNews)adapter.getItem(position)).getId());
				intent.setClass(CarnewsActivity.this, CareerNewsWebView.class);
				startActivity(intent);
			}
		});
	
	}

	private void initEvents() {
		// TODO Auto-generated method stub
		this.titleback.setOnClickListener(this);
		list = new ArrayList<CareerNews>();
		HttpCareerNews httpGetNews = new HttpCareerNews();
		httpGetNews.execute(MakeUrl(startIndex, pageSize));

		try {
			list = httpGetNews.get();
			adapter = new CareerNewsAdapter(CarnewsActivity.this, list);
			listView.setAdapter(adapter);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String MakeUrl(int start, String size) {
		realUrl = careerNewsurl + startIndexUrl + start + "&" + pageSizeUrl
				+ size;

		return realUrl;
	}

	private void initViews() {
		// TODO Auto-generated method stub
		this.titleback = (ImageView) findViewById(R.id.img_titleback_careernews);
		this.listView = (ListView) findViewById(R.id.listview_careernews);
	}

	class ButtonClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			loadMore.setText("���ݼ�����");
			loadData();
			adapter.notifyDataSetChanged();
			loadMore.setText("���ظ���");
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.img_titleback_careernews:
			CarnewsActivity.this.finish();
			break;
		default:
			break;
		}
	}

	// ��������
	public void loadData() {
		startIndex = startIndex + 10;
		HttpCareerNews getNews = new HttpCareerNews();
		getNews.execute(MakeUrl(startIndex, pageSize));
		try {
			list = getNews.get();
			for (int i = 0; i < 10; i++) {
				adapter.addItem(list.get(i));
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// �Ƿ񵽴�ListView�ײ�
	boolean isLastRow = false;

	/**
	 * ����ʱ�������¼�
	 * 
	 * @author dell
	 * 
	 */
	class OnScrollListener implements
			AbsListView.OnScrollListener {

		// ������ʱ��һֱ�ص���ֱ��ֹͣ����ʱ��ֹͣ�ص�������ʱ�ص�һ��
		// firstVisibleItem:��ǰ�ۿ����ĵ�һ���б���ID(��0��ʼ,С���Ҳ��)
		// visibleItemCount����ǰ�ܿ������б������(С���Ҳ��)
		// totalItemCount���б����ܹ���
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// �ж��Ƿ���������һ��
			if (firstVisibleItem + visibleItemCount == totalItemCount
					&& totalItemCount > 0) {
				isLastRow = true;
			}
		}

		// ���ڹ���ʱ�ص����ص�2-3�Σ���ָû����ص�2�Σ�scrollState=2����β��ص�
		// �ص�˳�����£�
		// ��һ�Σ�scrollState=SCROLL_STATE_TOUCH_SCROLL(1)���ڹ���
		// �ڶ��Σ�scrollState = SCROLL_STATE_FLING(2)��ָ�����׵Ķ�������ָ�뿪��Ļǰ����������һ�£�
		// �����Σ�scrollState = SCROLL_STATE_IDLE(0) ֹͣ����

		// ����Ļֹͣ����ʱΪ0������Ļ�������û�ʹ�õĴ�������ָ������Ļ��ʱΪ1��
		// �����û��Ĳ�������Ļ�������Ի���ʱΪ2
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// �����������һ�в���ֹͣ����ʱ��ִ�м���
			if (isLastRow && scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
				// ִ�м��ش���
				isLastRow = false;
			}
		}
	}

	class HttpCareerNews extends AsyncTask<String, Void, List<CareerNews>> {

		@Override
		protected List<CareerNews> doInBackground(String... params) {
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

					System.out.println("ssssssssssssss");

					bos = new ByteArrayOutputStream();

					byte[] buffer = new byte[1024];

					int len = -1;
					while ((len = inputStream.read(buffer)) != -1) {
						bos.write(buffer, 0, len);
					}

					careerNewsJson = new String(bos.toByteArray(), "UTF-8");
					list = new PasueCareerNews().Pasue(careerNewsJson);
				}
			} catch (Exception e) {
				// TODO: handle exception
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

			return list;
		}
	}

}
