package com.bashi_group_01.www.fragment;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.bashi_group_01.www.activity.R;
import com.bashi_group_01.www.activity.UtilStatZoneWebView;
import com.bashi_group_01.www.db.DbHelper;
import com.bashi_group_01.www.sliding.lib.CharacterParser;
import com.bashi_group_01.www.sliding.lib.ClearEditText;
import com.bashi_group_01.www.sliding.lib.PinyinComparator;
import com.bashi_group_01.www.sliding.lib.SideBar;
import com.bashi_group_01.www.sliding.lib.SortAdapter;
import com.bashi_group_01.www.sliding.lib.SortModel;
import com.bashi_group_01.www.sliding.lib.SideBar.OnTouchingLetterChangedListener;
import com.bashi_group_01.www.util.PasueRoadName;

public class UtilFragmentLightZone extends Fragment implements OnClickListener{
	
	private Button button1, button2,button3;
	private ImageView imb_delzone1;
	private LinearLayout imb_delzone2, imb_delzone3, imb_delzone4,
			imb_delzone5, imb_delzone6;
	private ImageView imb_delroad1;
	private LinearLayout imb_delroad2, imb_delroad3, imb_delroad4,
			imb_delroad5, imb_delroad6;
	private ImageButton imb_addelect;
	private TextView etd_zone1, etd_zone2, etd_zone3, etd_zone4, etd_zone5;
	private LinearLayout llZone1, llZone2, llZone3, llZone4, llZone5;
	private TextView etd_road1, etd_road2, etd_road3, etd_road4, etd_road5;
	private TextView etd_elect;
	private String[] factor;
	private LinearLayout llroad1, llroad2, llroad3, llroad4, llroad5;
	private int zoneCount = 2, roadCount = 2;
	private String[] data;
	private String zonesnameJson;
	private List<String> list = new ArrayList<String>();
	private String factorJson;
	private String[] roadnameArray;
	private String factorurl = "http://116.236.170.106:9001/RemoteData/InformationHandler.ashx?Type=7";
	private String zonenameUrl = "http://116.236.170.106:9001/RemoteData/InformationHandler.ashx?Type=3";
	private String url = "http://116.236.170.106:9001/MobileAdWeb/ShowDataAd.aspx?roadline=&";
	private String url_road = "&roadname=";
	private String url_zone = "&District=";
	private String type = "Lamp";
	SharedPreferences sp;
	
	
	Dialog mdialog;
	Builder builder;
	
	// alertdialog����
	private ListView sortListView;
	private SortAdapter adapter;
	private EditText mClearEditText;

	/**
	 * slidingBar
	 */
	private SideBar sideBar;
	private TextView dialog;

	/**
	 * ����ת����ƴ������
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;

	/**
	 * ����ƴ��������ListView�����������
	 */
	private PinyinComparator pinyinComparator;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fm_util_light_zone, null);
		this.etd_zone1 = (TextView) view.findViewById(R.id.edt_addzone23);
		this.etd_zone2 = (TextView) view.findViewById(R.id.edt_addzone33);
		this.etd_zone3 = (TextView) view.findViewById(R.id.edt_addzone43);
		this.etd_zone4 = (TextView) view.findViewById(R.id.edt_addzone53);
		this.etd_zone5 = (TextView) view.findViewById(R.id.edt_addzone63);
		this.imb_delzone1 = (ImageView) view.findViewById(R.id.del013);
		this.imb_delzone2 = (LinearLayout) view.findViewById(R.id.del023);
		this.imb_delzone3 = (LinearLayout) view.findViewById(R.id.del033);
		this.imb_delzone4 = (LinearLayout) view.findViewById(R.id.del043);
		this.imb_delzone5 = (LinearLayout) view.findViewById(R.id.del053);
		this.imb_delzone6 = (LinearLayout) view.findViewById(R.id.del063);
		llZone2 = (LinearLayout) view.findViewById(R.id.llsedzone3);
		llZone3 = (LinearLayout) view.findViewById(R.id.llthridzone3);
		llZone4 = (LinearLayout) view.findViewById(R.id.llfozone3);
		llZone5 = (LinearLayout) view.findViewById(R.id.llfifzone3);
		llZone1 = (LinearLayout) view.findViewById(R.id.llsixzone3);
		this.etd_road1 = (TextView) view.findViewById(R.id.ed_addroad23);
		this.etd_road2 = (TextView) view.findViewById(R.id.ed_addroad33);
		this.etd_road3 = (TextView) view.findViewById(R.id.ed_addroad43);
		this.etd_road4 = (TextView) view.findViewById(R.id.ed_addroad53);
		this.etd_road5 = (TextView) view.findViewById(R.id.ed_addroad63);
		this.imb_delroad1 = (ImageView) view.findViewById(R.id.imb_addroad13);
		this.imb_delroad2 = (LinearLayout) view.findViewById(R.id.imb_addroad23);
		this.imb_delroad3 = (LinearLayout) view.findViewById(R.id.imb_addroad33);
		this.imb_delroad4 = (LinearLayout) view.findViewById(R.id.imb_addroad43);
		this.imb_delroad5 = (LinearLayout) view.findViewById(R.id.imb_addroad53);
		this.imb_delroad6 = (LinearLayout) view.findViewById(R.id.imb_addroad63);
		llroad2 = (LinearLayout) view.findViewById(R.id.llsedroad3);
		llroad3 = (LinearLayout) view.findViewById(R.id.llthrdroad3);
		llroad4 = (LinearLayout) view.findViewById(R.id.llforroad3);
		llroad5 = (LinearLayout) view.findViewById(R.id.llfifroad3);
		llroad1 = (LinearLayout) view.findViewById(R.id.llsixroad3);
		button1 = (Button) view.findViewById(R.id.bt_util_light_sure);
		button2 = (Button) view.findViewById(R.id.bt_util_light_clear);
		etd_elect = (TextView) view.findViewById(R.id.ed_light1);
		imb_addelect = (ImageButton) view.findViewById(R.id.imb_light1);
		initEvent();
		
		roadnameArray = getSharedPreference("ROADNAME");
		
		new ZoneName().execute(zonenameUrl,factorurl);
		return view;
	}
	
	private void initEvent() {
		this.imb_delzone1.setOnClickListener(this);
		this.imb_delzone2.setOnClickListener(this);
		this.imb_delzone3.setOnClickListener(this);
		this.imb_delzone4.setOnClickListener(this);
		this.imb_delzone5.setOnClickListener(this);
		this.imb_delzone6.setOnClickListener(this);
		this.imb_delroad1.setOnClickListener(this);
		this.imb_delroad2.setOnClickListener(this);
		this.imb_delroad3.setOnClickListener(this);
		this.imb_delroad4.setOnClickListener(this);
		this.imb_delroad5.setOnClickListener(this);
		this.imb_delroad6.setOnClickListener(this);
		this.button1.setOnClickListener(this);
		this.button2.setOnClickListener(this);
		this.etd_elect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Builder(getActivity()).setTitle("ѡ����������")
						.setItems(factor, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								etd_elect.setText(factor[which]);
								dialog.dismiss();
							}
						}).show();
			}
		});
		this.etd_zone1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Builder(getActivity()).setTitle("ѡ������")
						.setItems(data, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								etd_zone1.setText(data[which]);
								dialog.dismiss();
							}
						}).show();
			}
		});

		this.etd_zone2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Builder(getActivity()).setTitle("ѡ������")
						.setItems(data, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								etd_zone2.setText(data[which]);
								dialog.dismiss();
							}
						}).show();
			}
		});
		this.etd_zone3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Builder(getActivity()).setTitle("ѡ������")
						.setItems(data, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								etd_zone3.setText(data[which]);
								dialog.dismiss();
							}
						}).show();
			}
		});
		this.etd_zone4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Builder(getActivity()).setTitle("ѡ������")
						.setItems(data, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								etd_zone4.setText(data[which]);
								dialog.dismiss();
							}
						}).show();
			}
		});
		this.etd_zone5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Builder(getActivity()).setTitle("ѡ������")
						.setItems(data, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								etd_zone5.setText(data[which]);
								dialog.dismiss();
							}
						}).show();
			}
		});

		this.etd_road1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Builder builder ;
				LinearLayout viewroad = (LinearLayout) getLayoutInflater(
						getArguments()).inflate(R.layout.alterdialgbuilerlayou,
						null);
				viewroad.removeView(viewroad);
				
				button3 = (Button) viewroad.findViewById(R.id.bt_alterbuilder);
				button3.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						etd_road1.setText(mClearEditText.getText());
						mdialog.cancel();
					}
				});
				
				sortListView = (ListView) viewroad
						.findViewById(R.id.country_lvcountry1111);

				// ʵ��������תƴ����
				characterParser = CharacterParser.getInstance();

				pinyinComparator = new PinyinComparator();

				sideBar = (SideBar) viewroad.findViewById(R.id.sidrbar1111);
				dialog = (TextView) viewroad.findViewById(R.id.dialog1111);
				sideBar.setTextView(dialog);
				initChildViews();
				roadnameArray = getSharedPreference("ROADNAME");

				sortListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						mClearEditText.setText(((SortModel) adapter
								.getItem(position)).getName());
					}
				});

				SourceDateList = filledData(roadnameArray);
				// ����a-z��������Դ����
				Collections.sort(SourceDateList, pinyinComparator);
				adapter = new SortAdapter(getActivity(), SourceDateList);
				sortListView.setAdapter(adapter);

				mClearEditText = (ClearEditText) viewroad
						.findViewById(R.id.tv_alterbuilder);

				// �������������ֵ�ĸı�����������
				mClearEditText.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {
						// ������������ֵΪ�գ�����Ϊԭ�����б�����Ϊ���������б�
						filterData(s.toString());
					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {

					}

					@Override
					public void afterTextChanged(Editable s) {
					}
				});

				builder = new Builder(getActivity());
				builder.setView(viewroad);
				builder.create();
				mdialog = builder.show();
			}
		});

		this.etd_road2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Builder builder ;
				LinearLayout viewroad = (LinearLayout) getLayoutInflater(
						getArguments()).inflate(R.layout.alterdialgbuilerlayou,
						null);
				viewroad.removeView(viewroad);
				
				button3 = (Button) viewroad.findViewById(R.id.bt_alterbuilder);
				button3.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						etd_road2.setText(mClearEditText.getText());
						mdialog.cancel();
					}
				});
				
				sortListView = (ListView) viewroad
						.findViewById(R.id.country_lvcountry1111);

				// ʵ��������תƴ����
				characterParser = CharacterParser.getInstance();

				pinyinComparator = new PinyinComparator();

				sideBar = (SideBar) viewroad.findViewById(R.id.sidrbar1111);
				dialog = (TextView) viewroad.findViewById(R.id.dialog1111);
				sideBar.setTextView(dialog);
				initChildViews();
				roadnameArray = getSharedPreference("ROADNAME");

				sortListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						mClearEditText.setText(((SortModel) adapter
								.getItem(position)).getName());
					}
				});

				SourceDateList = filledData(roadnameArray);
				// ����a-z��������Դ����
				Collections.sort(SourceDateList, pinyinComparator);
				adapter = new SortAdapter(getActivity(), SourceDateList);
				sortListView.setAdapter(adapter);

				mClearEditText = (ClearEditText) viewroad
						.findViewById(R.id.tv_alterbuilder);

				// �������������ֵ�ĸı�����������
				mClearEditText.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {
						// ������������ֵΪ�գ�����Ϊԭ�����б�����Ϊ���������б�
						filterData(s.toString());
					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {

					}

					@Override
					public void afterTextChanged(Editable s) {
					}
				});

				builder = new Builder(getActivity());
				builder.setView(viewroad);
				builder.create();
				mdialog = builder.show();
			}
		});
		this.etd_road3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Builder builder ;
				LinearLayout viewroad = (LinearLayout) getLayoutInflater(
						getArguments()).inflate(R.layout.alterdialgbuilerlayou,
						null);
				viewroad.removeView(viewroad);
				
				button3 = (Button) viewroad.findViewById(R.id.bt_alterbuilder);
				button3.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						etd_road3.setText(mClearEditText.getText());
						mdialog.cancel();
					}
				});
				
				sortListView = (ListView) viewroad
						.findViewById(R.id.country_lvcountry1111);

				// ʵ��������תƴ����
				characterParser = CharacterParser.getInstance();

				pinyinComparator = new PinyinComparator();

				sideBar = (SideBar) viewroad.findViewById(R.id.sidrbar1111);
				dialog = (TextView) viewroad.findViewById(R.id.dialog1111);
				sideBar.setTextView(dialog);
				initChildViews();
				roadnameArray = getSharedPreference("ROADNAME");

				sortListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						mClearEditText.setText(((SortModel) adapter
								.getItem(position)).getName());
					}
				});

				SourceDateList = filledData(roadnameArray);
				// ����a-z��������Դ����
				Collections.sort(SourceDateList, pinyinComparator);
				adapter = new SortAdapter(getActivity(), SourceDateList);
				sortListView.setAdapter(adapter);

				mClearEditText = (ClearEditText) viewroad
						.findViewById(R.id.tv_alterbuilder);

				// �������������ֵ�ĸı�����������
				mClearEditText.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {
						// ������������ֵΪ�գ�����Ϊԭ�����б�����Ϊ���������б�
						filterData(s.toString());
					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {

					}

					@Override
					public void afterTextChanged(Editable s) {
					}
				});

				builder = new Builder(getActivity());
				builder.setView(viewroad);
				builder.create();
				mdialog = builder.show();
			}
		});
		this.etd_road4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Builder builder ;
				LinearLayout viewroad = (LinearLayout) getLayoutInflater(
						getArguments()).inflate(R.layout.alterdialgbuilerlayou,
						null);
				viewroad.removeView(viewroad);
				
				button3 = (Button) viewroad.findViewById(R.id.bt_alterbuilder);
				button3.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						etd_road4.setText(mClearEditText.getText());
						mdialog.cancel();
					}
				});
				
				sortListView = (ListView) viewroad
						.findViewById(R.id.country_lvcountry1111);

				// ʵ��������תƴ����
				characterParser = CharacterParser.getInstance();

				pinyinComparator = new PinyinComparator();

				sideBar = (SideBar) viewroad.findViewById(R.id.sidrbar1111);
				dialog = (TextView) viewroad.findViewById(R.id.dialog1111);
				sideBar.setTextView(dialog);
				initChildViews();
				roadnameArray = getSharedPreference("ROADNAME");

				sortListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						mClearEditText.setText(((SortModel) adapter
								.getItem(position)).getName());
					}
				});

				SourceDateList = filledData(roadnameArray);
				// ����a-z��������Դ����
				Collections.sort(SourceDateList, pinyinComparator);
				adapter = new SortAdapter(getActivity(), SourceDateList);
				sortListView.setAdapter(adapter);

				mClearEditText = (ClearEditText) viewroad
						.findViewById(R.id.tv_alterbuilder);

				// �������������ֵ�ĸı�����������
				mClearEditText.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {
						// ������������ֵΪ�գ�����Ϊԭ�����б�����Ϊ���������б�
						filterData(s.toString());
					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {

					}

					@Override
					public void afterTextChanged(Editable s) {
					}
				});

				builder = new Builder(getActivity());
				builder.setView(viewroad);
				builder.create();
				mdialog = builder.show();
			}
		});
		this.etd_road5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Builder builder ;
				LinearLayout viewroad = (LinearLayout) getLayoutInflater(
						getArguments()).inflate(R.layout.alterdialgbuilerlayou,
						null);
				viewroad.removeView(viewroad);
				
				button3 = (Button) viewroad.findViewById(R.id.bt_alterbuilder);
				button3.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						etd_road5.setText(mClearEditText.getText());
						mdialog.cancel();
					}
				});
				
				sortListView = (ListView) viewroad
						.findViewById(R.id.country_lvcountry1111);

				// ʵ��������תƴ����
				characterParser = CharacterParser.getInstance();

				pinyinComparator = new PinyinComparator();

				sideBar = (SideBar) viewroad.findViewById(R.id.sidrbar1111);
				dialog = (TextView) viewroad.findViewById(R.id.dialog1111);
				sideBar.setTextView(dialog);
				initChildViews();
				roadnameArray = getSharedPreference("ROADNAME");

				sortListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						mClearEditText.setText(((SortModel) adapter
								.getItem(position)).getName());
					}
				});

				SourceDateList = filledData(roadnameArray);
				// ����a-z��������Դ����
				Collections.sort(SourceDateList, pinyinComparator);
				adapter = new SortAdapter(getActivity(), SourceDateList);
				sortListView.setAdapter(adapter);

				mClearEditText = (ClearEditText) viewroad
						.findViewById(R.id.tv_alterbuilder);

				// �������������ֵ�ĸı�����������
				mClearEditText.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {
						// ������������ֵΪ�գ�����Ϊԭ�����б�����Ϊ���������б�
						filterData(s.toString());
					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {

					}

					@Override
					public void afterTextChanged(Editable s) {
					}
				});

				builder = new Builder(getActivity());
				builder.setView(viewroad);
				builder.create();
				mdialog = builder.show();
			}
		});

	}

	@Override
	public void onClick(View v) {
		// ����
		String tv_zone1 = etd_zone1.getText().toString();
		String tv_zone2 = etd_zone2.getText().toString();
		String tv_zone3 = etd_zone3.getText().toString();
		String tv_zone4 = etd_zone4.getText().toString();
		String tv_zone5 = etd_zone5.getText().toString();
		// ·��
		String road1 = etd_road1.getText().toString();
		String road2 = etd_road2.getText().toString();
		String road3 = etd_road3.getText().toString();
		String road4 = etd_road4.getText().toString();
		String road5 = etd_road5.getText().toString();
		int id = v.getId();
		// Intent intent = new Intent();
		switch (id) {
		case R.id.del013:
			System.out.println(zoneCount);
			if (zoneCount <= 5) {
				if (zoneCount == 1) {
					llZone2.setVisibility(View.VISIBLE);
				} else if (zoneCount == 2) {
					llZone3.setVisibility(View.VISIBLE);
					imb_delzone2.setVisibility(View.INVISIBLE);
				} else if (zoneCount == 3) {
					llZone4.setVisibility(View.VISIBLE);
					imb_delzone3.setVisibility(View.INVISIBLE);
				} else if (zoneCount == 4) {
					llZone5.setVisibility(View.VISIBLE);
					imb_delzone4.setVisibility(View.INVISIBLE);
				} else if (zoneCount == 5) {
					llZone1.setVisibility(View.VISIBLE);
					imb_delzone5.setVisibility(View.INVISIBLE);
				}
				zoneCount++;
			}
			break;
		case R.id.del063:
			if (zoneCount == 6 || zoneCount > 6) {
				llZone1.setVisibility(View.GONE);
				imb_delzone5.setVisibility(View.VISIBLE);
				etd_zone5.setText("");
				zoneCount = 5;
			}
			break;
		case R.id.del053:
			System.out.println(zoneCount);
			if (zoneCount == 5) {
				llZone5.setVisibility(View.GONE);
				imb_delzone4.setVisibility(View.VISIBLE);
				etd_zone4.setText("");
				zoneCount--;
			}
			break;
		case R.id.del043:
			if (zoneCount == 4) {
				llZone4.setVisibility(View.GONE);
				imb_delzone3.setVisibility(View.VISIBLE);
				etd_zone3.setText("");
				zoneCount--;
			}
			break;
		case R.id.del033:
			if (zoneCount == 3) {
				llZone3.setVisibility(View.GONE);
				imb_delzone2.setVisibility(View.VISIBLE);
				etd_zone2.setText("");
				zoneCount--;
			}
			break;
		case R.id.del023:
			if (zoneCount == 2) {
				llZone2.setVisibility(View.GONE);
				imb_delzone1.setVisibility(View.VISIBLE);
				etd_zone1.setText("");
				zoneCount--;
			}
			break;
		case R.id.imb_addroad13:
			if (roadCount <= 5) {
				if (roadCount == 1) {
					llroad2.setVisibility(View.VISIBLE);
				} else if (roadCount == 2) {
					llroad3.setVisibility(View.VISIBLE);
					imb_delroad2.setVisibility(View.INVISIBLE);
				} else if (roadCount == 3) {
					llroad4.setVisibility(View.VISIBLE);
					imb_delroad3.setVisibility(View.INVISIBLE);
				} else if (roadCount == 4) {
					llroad5.setVisibility(View.VISIBLE);
					imb_delroad4.setVisibility(View.INVISIBLE);
				} else if (roadCount == 5) {
					llroad1.setVisibility(View.VISIBLE);
					imb_delroad5.setVisibility(View.INVISIBLE);
				}
				roadCount++;
			}
			break;
		case R.id.imb_addroad63:
			if (roadCount == 6 || roadCount > 6) {
				llroad1.setVisibility(View.GONE);
				imb_delroad5.setVisibility(View.VISIBLE);
				etd_road5.setText("");
				roadCount = 5;
			}
			break;
		case R.id.imb_addroad53:
			if (roadCount == 5) {
				llroad5.setVisibility(View.GONE);
				imb_delroad4.setVisibility(View.VISIBLE);
				etd_road4.setText("");
				roadCount--;
			}
			break;
		case R.id.imb_addroad43:
			if (roadCount == 4) {
				llroad4.setVisibility(View.GONE);
				imb_delroad3.setVisibility(View.VISIBLE);
				etd_road3.setText("");
				roadCount--;
			}
			break;
		case R.id.imb_addroad33:
			if (roadCount == 3) {
				llroad3.setVisibility(View.GONE);
				imb_delroad2.setVisibility(View.VISIBLE);
				etd_road2.setText("");
				roadCount--;
			}
			break;
		case R.id.imb_addroad23:
			if (roadCount == 2) {
				llroad2.setVisibility(View.GONE);
				imb_delroad1.setVisibility(View.VISIBLE);
				etd_road1.setText("");
				roadCount--;
			}
			break;

		case R.id.bt_util_light_sure:
			Intent intent = new Intent();
			intent.putExtra("titlename", "����Ƭ��ѯ");
			intent.putExtra("url", url);
			intent.putExtra("url_road", url_road);
			intent.putExtra("url_zone", url_zone);
			intent.putExtra("zone1", tv_zone1);
			intent.putExtra("zone2", tv_zone2);
			intent.putExtra("zone3", tv_zone3);
			intent.putExtra("zone4", tv_zone4);
			intent.putExtra("zone5", tv_zone5);
			intent.putExtra("road1", road1);
			intent.putExtra("road2", road2);
			intent.putExtra("road3", road3);
			intent.putExtra("road4", road4);
			intent.putExtra("road5", road5);
			intent.putExtra("elect", etd_elect.getText().toString());
			intent.putExtra("type", type);
			intent.setClass(getActivity(), UtilStatZoneWebView.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.others_getin, R.anim.others_getout);
			break;
		case R.id.bt_util_light_clear:
			llZone1.setVisibility(View.INVISIBLE);
			llZone2.setVisibility(View.INVISIBLE);
			llZone3.setVisibility(View.GONE);
			llZone4.setVisibility(View.GONE);
			llZone5.setVisibility(View.GONE);
			llroad1.setVisibility(View.INVISIBLE);
			llroad2.setVisibility(View.INVISIBLE);
			llroad3.setVisibility(View.GONE);
			llroad4.setVisibility(View.GONE);
			llroad5.setVisibility(View.GONE);
			zoneCount = 1;
			roadCount = 1;
			break;
		}
	}

	
	class ZoneName extends AsyncTask<String, Integer, List<String>> {

		@Override
		protected List<String> doInBackground(String... params) {
			// TODO Auto-generated method stub

			InputStream inputStream = null;
			ByteArrayOutputStream bos = null;
			try {
				URL url = new URL(params[0]);
				URL url1 = new URL(params[1]);
				System.out.println(url);

				HttpURLConnection httpURLConnection = (HttpURLConnection) url
						.openConnection();
				HttpURLConnection httpURLConnection1 = (HttpURLConnection) url1
						.openConnection();
				httpURLConnection.setConnectTimeout(5000);
				httpURLConnection.setRequestMethod("GET");

				httpURLConnection1.setConnectTimeout(5000);
				httpURLConnection1.setRequestMethod("GET");
				
				if (httpURLConnection.getResponseCode() == 200) {

					inputStream = httpURLConnection.getInputStream();
					System.out.println(inputStream);

					bos = new ByteArrayOutputStream();

					byte[] buffer = new byte[1024];

					int len = -1;
					while ((len = inputStream.read(buffer)) != -1) {
						bos.write(buffer);
					}

					zonesnameJson = new String(bos.toByteArray(), "gb2312");
					// System.out.println(linesnameJson);
					list.add(zonesnameJson);
				}
				
				if (httpURLConnection1.getResponseCode() == 200) {

					inputStream = httpURLConnection1.getInputStream();
					System.out.println(inputStream);

					bos = new ByteArrayOutputStream();

					byte[] buffer = new byte[1024];

					int len1 = -1;
					while ((len1 = inputStream.read(buffer)) != -1) {
						bos.write(buffer);
					}

					factorJson = new String(bos.toByteArray(), "gb2312");
					// System.out.println(linesnameJson);
					list.add(factorJson);
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

			return list;
		}

		@Override
		protected void onPostExecute(List<String> result) {
			// TODO Auto-generated method stub
			// System.out.println(result);
			super.onPostExecute(result);
			
			String zone = list.get(0);
			String factor11 = list.get(1);
			
			try {
				JSONObject jsonObject = new JSONObject(zone);

				JSONObject jsonObject2 = jsonObject.getJSONObject("result");
				JSONArray array2 = jsonObject2.getJSONArray("district");
				data = new String[array2.length()];
				for (int i = 0; i < array2.length(); i++) {
					data[i] = array2.getString(i);
					// System.out.println(linenameArray[i]);
				}
				
				JSONObject jsonObject11 = new JSONObject(factor11);

				JSONObject jsonObject22 = jsonObject11.getJSONObject("result");
				JSONArray array22 = jsonObject22.getJSONArray("Facturer");
				factor = new String[array22.length()];
				for (int i = 0; i < array22.length(); i++) {
					factor[i] = array22.getString(i);
					// System.out.println(linenameArray[i]);
				}
				
				

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * spȡ����;
	 */

	public String[] getSharedPreference(String key) {
		String regularEx = "#";
		String[] str = null;
		String values;
		sp = getActivity().getSharedPreferences("event", getActivity().MODE_PRIVATE);
		values = sp.getString(key, "");
		str = values.split(regularEx);
		return str;
	}
	
	private void initChildViews() {

		// �����Ҳഥ������
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				// ����ĸ�״γ��ֵ�λ��
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					sortListView.setSelection(position);
				}

			}
		});
	}

	/**
	 * ΪListView�������
	 * 
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(String[] date) {
		List<SortModel> mSortList = new ArrayList<SortModel>();

		for (int i = 0; i < date.length; i++) {
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i]);
			// ����ת����ƴ��
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();

			// ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
			if (sortString.matches("[A-Z]")) {
				sortModel.setSortLetters(sortString.toUpperCase());
			} else {
				sortModel.setSortLetters("#");
			}

			mSortList.add(sortModel);
		}
		return mSortList;

	}

	/**
	 * ����������е�ֵ���������ݲ�����ListView
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

		// ����a-z��������
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}

}
