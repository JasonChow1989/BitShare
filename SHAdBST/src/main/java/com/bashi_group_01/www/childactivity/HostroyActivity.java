package com.bashi_group_01.www.childactivity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bashi_group_01.www.activity.FeedSendAgainActivity;
import com.bashi_group_01.www.activity.R;
import com.bashi_group_01.www.activity.Welcome;
import com.bashi_group_01.www.db.DbHelper;
import com.bashi_group_01.www.domain.Feed;
/**
 * 历史记录
 * @author share
 *
 */
public class HostroyActivity extends Activity {

	private List<Feed> list;
	private ListView listView;
	private BaseAdapter adapter;
	private ImageView imageView;

	SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hostroy);
		initViews();
		HistroyQueryData();
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				HostroyActivity.this.finish();
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("captialNum",
						((Feed) adapter.getItem(position)).getCapitalNumber());
				intent.putExtra("desc",
						((Feed) adapter.getItem(position)).getDescription());
				intent.putExtra("date",
						((Feed) adapter.getItem(position)).getNowTime());

				intent.setClass(HostroyActivity.this,
						FeedSendAgainActivity.class);
				startActivity(intent);
			}
		});

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				// TODO Auto-generated method stub
				AlertDialog.Builder ab = new AlertDialog.Builder(
						HostroyActivity.this);
				ab.setTitle("删除");
				ab.setMessage("确定要删除该记录吗？");
				ab.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								DbHelper dbOpenHelper = new DbHelper(
										HostroyActivity.this);
								SQLiteDatabase db = dbOpenHelper
										.getWritableDatabase();
								db.delete(Welcome.userName, "NowTime=?",
										new String[] { ""
												+ list.get(position)
														.getNowTime() });

								db.close();
								HistroyQueryData();
							}
						});
				ab.setNegativeButton("取消", null);
				ab.create();
				ab.show();
				return true;
			}
		});

	}

	private void HistroyQueryData() {
		// TODO Auto-generated method stub
		DbHelper dbHelper = new DbHelper(this);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String sql = "select * from " + Welcome.userName;
		Cursor cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			Feed feed = new Feed();
			// 取值。
			feed.setCapitalNumber(cursor.getString(cursor
					.getColumnIndex("CapitalNumber")));
			feed.setDescription(cursor.getString(cursor
					.getColumnIndex("Description")));
			feed.setNowTime(cursor.getString(cursor.getColumnIndex("NowTime")));
			feed.setState(cursor.getString(cursor.getColumnIndex("State")));
			list.add(feed);
		}
		// 关闭游标
		cursor.close();
		db.close();
		adapter = new MyAdapter(HostroyActivity.this, list);
		adapter.notifyDataSetChanged();
		listView.setAdapter(adapter);
	}

	private void initViews() {
		// TODO Auto-generated method stub
		list = new ArrayList<Feed>();
		this.imageView = (ImageView) findViewById(R.id.img_titleback_histroy);
		this.listView = (ListView) findViewById(R.id.listview_feedhistroy);
	}

	class MyAdapter extends BaseAdapter {

		Context context;
		List<Feed> list;

		public MyAdapter(Context context, List<Feed> list) {
			this.context = context;
			this.list = list;
		}

		public void updateListView(List<Feed> list) {
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
			Viewholder viewholder = null;

			if (convertView == null) {
				viewholder = new Viewholder();

				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_feedhistry, null);
				viewholder.capitNum = (TextView) convertView
						.findViewById(R.id.tv_item_feedhistroy_captinum);
				viewholder.desc = (TextView) convertView
						.findViewById(R.id.tv_item_feedhistroy_desc);
				viewholder.date = (TextView) convertView
						.findViewById(R.id.tv_item_feedhistroy_date);
				viewholder.state = (TextView) convertView
						.findViewById(R.id.tv_item_feedhistroy_state);

				convertView.setTag(viewholder);
			} else {
				viewholder = (Viewholder) convertView.getTag();
			}

			viewholder.capitNum.setText("设备编号:  "
					+ list.get(position).getCapitalNumber());
			viewholder.desc.setText(list.get(position).getDescription());
			viewholder.date.setText(list.get(position).getNowTime()
					.substring(5, 19));
			if ("1".equals(list.get(position).getState())) {
				viewholder.state.setText("设备养护");
			} else if ("0".equals(list.get(position).getState())) {
				viewholder.state.setText("设备维修");
			}

			return convertView;
		}

		class Viewholder {
			TextView capitNum;
			TextView desc;
			TextView date;
			TextView state;
		}
	}

	public String[] getSharedPreference(String key) {
		String regularEx = "#";
		String[] str = null;
		String values;
		sp = getSharedPreferences("event", MODE_PRIVATE);
		values = sp.getString(key, "");
		str = values.split(regularEx);
		return str;
	}
}
