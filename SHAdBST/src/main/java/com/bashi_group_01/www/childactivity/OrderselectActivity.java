package com.bashi_group_01.www.childactivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.bashi_group_01.www.activity.R;
import com.bashi_group_01.www.activity.WebViewActivity;
import com.bashi_group_01.www.sliding.lib.CharacterParser;
import com.bashi_group_01.www.sliding.lib.ClearEditText;
import com.bashi_group_01.www.sliding.lib.PinyinComparator;
import com.bashi_group_01.www.sliding.lib.SideBar;
import com.bashi_group_01.www.sliding.lib.SortAdapter;
import com.bashi_group_01.www.sliding.lib.SortModel;
import com.bashi_group_01.www.sliding.lib.SideBar.OnTouchingLetterChangedListener;
/**
 * 订单查询;
 * @author share
 *
 */
public class OrderselectActivity extends Activity implements OnClickListener {

	private EditText mClearEditText;
	private ImageView imageView;
	private String[] OrderArray;
	private ListView sortListView;
	private SortAdapter adapter;
	SharedPreferences sp;
	private String url = "http://116.236.170.106:9001/MobileWeb/ShowData.aspx?which=ad&roadline=&registrationmark=&VehicleNumbering=&AdContent=&AdOrderId=";
	
	/**
	 * slidingBar
	 */
	private SideBar sideBar;
	private TextView dialog;

	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;

	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orderselect);
		initViews();
		initEvent();

		sortListView = (ListView) findViewById(R.id.listview_p_order);

		// 实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();

		pinyinComparator = new PinyinComparator();

		sideBar = (SideBar) findViewById(R.id.sidrbar_order);
		dialog = (TextView) findViewById(R.id.dialog_order);
		sideBar.setTextView(dialog);

		// 设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				// 该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					sortListView.setSelection(position);
				}

			}
		});

		OrderArray = getSharedPreference("ORDER");

		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mClearEditText.setText(((SortModel) adapter.getItem(position))
						.getName());
				Intent intent = new Intent();
				String lineNum = mClearEditText.getText().toString().trim();
				intent.putExtra("titlename", "订单查询");
				intent.putExtra("roadline", lineNum);
				intent.putExtra("url", url);
				intent.setClass(OrderselectActivity.this, WebViewActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.others_getin,
						R.anim.others_getout);
			}
		});

		SourceDateList = filledData(OrderArray);
		// 根据a-z进行排序源数据
		Collections.sort(SourceDateList, pinyinComparator);
		adapter = new SortAdapter(this, SourceDateList);
		sortListView.setAdapter(adapter);

		mClearEditText = (ClearEditText) findViewById(R.id.tv_selectnum_order);
		// 根据输入框输入值的改变来过滤搜索
		mClearEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
				filterData(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	private void initEvent() {
		this.imageView.setOnClickListener(this);
	}

	private void initViews() {
		this.imageView = (ImageView) findViewById(R.id.titleback_order);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.titleback_order:
			finish();
			break;
		}

	}

	/**
	 * 为ListView填充数据
	 * 
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(String[] date) {
		List<SortModel> mSortList = new ArrayList<SortModel>();

		for (int i = 0; i < date.length; i++) {
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i]);
			// 汉字转换成拼音
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();

			// 正则表达式，判断首字母是否是英文字母
			if (sortString.matches("#")) {
				sortModel.setSortLetters("#");
			} else {
				sortModel.setSortLetters(sortString.toUpperCase());
			}

			mSortList.add(sortModel);
		}
		return mSortList;

	}

	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * 
	 * @param filterStr
	 */
	private void filterData(String filterStr) {
		List<SortModel> filterDateList = new ArrayList<SortModel>();

		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = SourceDateList;
		} else {
			filterDateList.clear();
			for (SortModel sortModel : SourceDateList) {
				String name = sortModel.getName();
				if (name.indexOf(filterStr.toString()) != -1
						|| characterParser.getSelling(name).startsWith(
								filterStr.toString())) {
					filterDateList.add(sortModel);
				}
			}
		}

		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}

	/**
	 * 
	 * sp取数据;
	 */

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
