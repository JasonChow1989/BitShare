package com.bashi_group_01.www.fragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.webkit.WebView.FindListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bashi_group_01.www.activity.R;
import com.bashi_group_01.www.activity.SettingActivity;
import com.bashi_group_01.www.domain.Weather;
import com.bashi_group_01.www.util.PasueWeather;

public class FristFragment extends Fragment {

	private View view;
	private ImageView weatherReflush;
	private ImageButton imb_setting;
	private String url = "http://wthrcdn.etouch.cn/weather_mini?citykey=101020100";
	private TextView today, nextday, img2;
	private String weatherJson;
	private List<Weather> list;
	private LinearLayout llweatherreflush;
	private ImageView img1;

	SharedPreferences sp;
	SharedPreferences.Editor editor;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fm_first, null);
		initViews();
		reflushDate();
		sp = getActivity().getSharedPreferences("weather",
				getActivity().MODE_PRIVATE);
		editor = sp.edit();

		String weater = sp.getString("weather", "晴   29℃/35℃");

		today.setText(weater);

		new HttpWeather().execute(url);

		imb_setting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), SettingActivity.class);
				startActivity(intent);
			}
		});

		llweatherreflush.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new HttpWeather().execute(url);
				reflushDate();
			}

		});
		return view;
	}

	private void initViews() {
		this.img1 = (ImageView) view.findViewById(R.id.img_01);
		this.img2 = (TextView) view.findViewById(R.id.img_02);

		list = new ArrayList<Weather>();
		this.weatherReflush = (ImageView) view
				.findViewById(R.id.weather_reflush);
		this.llweatherreflush = (LinearLayout) view
				.findViewById(R.id.llweatherreflush);
		this.today = (TextView) view.findViewById(R.id.tv_01000);
		this.nextday = (TextView) view.findViewById(R.id.tv_02000);
		this.imb_setting = (ImageButton) view
				.findViewById(R.id.imb_maintitie_setting);
	}

	class HttpWeather extends AsyncTask<String, Void, List<Weather>> {

		@Override
		protected List<Weather> doInBackground(String... params) {
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
					// System.out.println("<<<<<<<<<<<<<<<");
					inputStream = httpURLConnection.getInputStream();
					// System.out.println(inputStream);

					bos = new ByteArrayOutputStream();

					byte[] buffer1 = new byte[1024];

					int len1 = -1;
					while ((len1 = inputStream.read(buffer1)) != -1) {
						bos.write(buffer1);
					}

					weatherJson = new String(bos.toByteArray(), "utf-8");

					// System.out.println(weatherJson);
				}
				list = PasueWeather.pasue(weatherJson);

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				if (bos != null) {
					try {
						bos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (inputStream != null) {
						try {
							inputStream.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			return list;
		}

		@Override
		protected void onPostExecute(List<Weather> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result.size() != 0) {
				String high = result.get(0).getHigh().substring(3);
				String low = result.get(0).getLow().substring(3);
				today.setText(result.get(0).getType() + "  " + high + "/" + low);
				editor.putString("weather", result.get(0).getType() + "  "
						+ high + "/" + low);

				img2.setText(result.get(0).getWendu() + "°");

				// System.out.println(result.get(0).getWendu());
				if ("小雨".equals(result.get(0).getType())
						|| "中雨".equals(result.get(0).getType())) {
					img1.setImageResource(R.drawable.yu);
				} else if ("大雨".equals(result.get(0).getType())) {
					img1.setImageResource(R.drawable.dayu);
				} else if ("多云".equals(result.get(0).getType())) {
					img1.setImageResource(R.drawable.duoyun);
				} else if ("多云转晴".equals(result.get(0).getType())) {
					img1.setImageResource(R.drawable.duoyunzhuanqing);
				} else if ("多云转阴".equals(result.get(0).getType())
						|| "阴".equals(result.get(0).getType())) {
					img1.setImageResource(R.drawable.duoyunzhuangyin);
				} else if ("晴".equals(result.get(0).getType())) {
					img1.setImageResource(R.drawable.qing);
				} else if ("小雪".equals(result.get(0).getType())
						|| "中雪".equals(result.get(0).getType())
						|| "大雪".equals(result.get(0).getType())
						|| "雨夹雪".equals(result.get(0).getType())) {
					img1.setImageResource(R.drawable.xue);
				} else if("阵雨".equals(result.get(0).getType())|| "雷阵雨".equals(result.get(0).getType())){
					img1.setImageResource(R.drawable.zhenyu);
				}
			}
		}
	}

	private void reflushDate() {
		// TODO Auto-generated method stub
		Animation operatingAnim = AnimationUtils.loadAnimation(getActivity(),
				R.anim.tip);
		LinearInterpolator lin = new LinearInterpolator();
		operatingAnim.setInterpolator(lin);
		if (operatingAnim != null) {
			weatherReflush.startAnimation(operatingAnim);
		}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("已更新   "
				+ "MM-dd  HH:mm");
		nextday.setText(simpleDateFormat.format(new Date()));
	}
}
