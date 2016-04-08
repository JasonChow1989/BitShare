package com.bashi_group_01.www.activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.bashi_group_01.www.db.DbHelper;
import com.bashi_group_01.www.service.NotificationService;
import com.bashi_group_01.www.util.PasueCarnum;
import com.bashi_group_01.www.util.PasueCarpai;
import com.bashi_group_01.www.util.PasueEventCheck;
import com.bashi_group_01.www.util.PasueLineName;
import com.bashi_group_01.www.util.PasueOrder;
import com.bashi_group_01.www.util.PasuePlLine;
import com.bashi_group_01.www.util.PasuePlName;
import com.bashi_group_01.www.util.PasueRoadName;
import com.bashi_group_01.www.util.PasueUsers;
/**
 * 欢迎界面;
 * @author share
 *
 */
public class Welcome extends Activity {

	private String roadurl = "http://116.236.170.106:9001/RemoteData/InformationHandler.ashx?Type=1";
	private String lineurl = "http://116.236.170.106:9001/RemoteData/InformationHandler.ashx?Type=0";
	private String pinpaiurl = "http://116.236.170.106:9001/RemoteData/InformationHandler.ashx?Type=2";
	private String piline = "http://116.236.170.106:9001/RemoteData/InformationHandler.ashx?Type=4";

	private String carnumurl = "http://116.236.170.106:9001/RemoteData/InformationHandler.ashx?Type=8";
	private String carpaiurl = "http://116.236.170.106:9001/RemoteData/InformationHandler.ashx?Type=9";
	private String orderurl = "http://116.236.170.106:9001/RemoteData/InformationHandler.ashx?Type=10";

	private String url_3 = "http://116.236.170.106:8384/FunctionModule/RemoteData/TransHandler.ashx?startIndex=0&pageSize=100&queryTime=1&queryState=0&";
	private String url_7 = "http://116.236.170.106:8384/FunctionModule/RemoteData/TransHandler.ashx?startIndex=0&pageSize=100&queryTime=2&queryState=0&";
	private String url_30 = "http://116.236.170.106:8384/FunctionModule/RemoteData/TransHandler.ashx?startIndex=0&pageSize=100&queryTime=3&queryState=0&";
	private String url_all = "http://116.236.170.106:8384/FunctionModule/RemoteData/TransHandler.ashx?startIndex=0&pageSize=100&queryTime=0&queryState=0&";

	private String url_users = "http://116.236.170.106:8384/FunctionModule/RemoteData/InformationHandler.ashx?startIndex=0&pageSize=100&type=11&";

	private String roadnameJson;
	private String linenameJson;
	private String plnameJson;
	private String pllineJson;
	private String carnumJson;
	private String carpaiJson;
	private String orderJson;
	private String eventCheckJson3;
	private String eventCheckJson7;
	private String eventCheckJson30;
	private String eventCheckJsonall;

	private String usersjson;

	private String[] roadnameArray;
	private String[] linenameArray;
	private String[] plnameArray;
	private String[] pllineArray;
	private String[] carnumArray;
	private String[] carpaiArray;
	private String[] orderArray;

	private String[] eventArray3;
	private String[] eventArray7;
	private String[] eventArray30;
	private String[] eventArrayall;

	private String[] users;

	SharedPreferences sp1;
	/*
	 * SharedPreferences spDate; SharedPreferences.Editor editor_data;
	 */
	DbHelper dbHelper = null;
	private List<String> list, listevent;
	int loginnum;
	public static String userName;

	SharedPreferences spEvent;
	SharedPreferences.Editor editor_event;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		sp1 = getSharedPreferences("userInfo", MODE_PRIVATE);
		loginnum = sp1.getInt("loginNum", 0);
		userName = sp1.getString("USER_NAME", "");
		spEvent = getSharedPreferences("event", MODE_PRIVATE);
		editor_event = spEvent.edit();

		boolean network = NetWorkConnection();
		if (network) {
			list = new ArrayList<String>();
			listevent = new ArrayList<String>();
			new RoadnamePasJson().execute(roadurl, lineurl, pinpaiurl, piline,
					carnumurl, carpaiurl, orderurl);
			new HttpEventCheck().execute(url_3 + "userkey=" + userName, url_7
					+ "userkey=" + userName, url_30 + "userkey=" + userName,
					url_all + "userkey=" + userName);
			new HttpUserName().execute(url_users + "userkey=" + userName);
			timer();
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		JPushInterface.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		JPushInterface.onResume(this);
		startService(new Intent(this, NotificationService.class));
	}

	private void timer() {
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				if (loginnum == 1) {
					startA();
				} else {
					startActivity();
				}
				Welcome.this.finish();
			}
		};
		Timer timer = new Timer();
		timer.schedule(task, 1000);
	}

	private void startActivity() {
		startActivity(new Intent(Welcome.this, LoginActivity.class));
	}

	private void startA() {
		startActivity(new Intent(Welcome.this, MainActivity.class));
	}

	class RoadnamePasJson extends AsyncTask<String, Void, List<String>> {

		@SuppressLint("NewApi")
		@Override
		protected List<String> doInBackground(String... params) {
			InputStream inputStream = null;
			ByteArrayOutputStream bos = null;
			try {
				URL urlroadname = new URL(params[0]);
				URL urllinename = new URL(params[1]);
				URL urlpinpainame = new URL(params[2]);
				URL urlpilinename = new URL(params[3]);
				URL urlcarnumname = new URL(params[4]);
				URL urlcarpainame = new URL(params[5]);
				URL urlordername = new URL(params[6]);

				HttpURLConnection httpURLConnection1 = (HttpURLConnection) urlroadname
						.openConnection();
				HttpURLConnection httpURLConnection2 = (HttpURLConnection) urllinename
						.openConnection();
				HttpURLConnection httpURLConnection3 = (HttpURLConnection) urlpinpainame
						.openConnection();
				HttpURLConnection httpURLConnection4 = (HttpURLConnection) urlpilinename
						.openConnection();
				HttpURLConnection httpURLConnection5 = (HttpURLConnection) urlcarnumname
						.openConnection();
				HttpURLConnection httpURLConnection6 = (HttpURLConnection) urlcarpainame
						.openConnection();
				HttpURLConnection httpURLConnection7 = (HttpURLConnection) urlordername
						.openConnection();

				httpURLConnection1.setConnectTimeout(5000);
				httpURLConnection1.setRequestMethod("GET");

				if (httpURLConnection1.getResponseCode() == 200) {
					// System.out.println("<<<<<<<<<<<<<<<");
					inputStream = httpURLConnection1.getInputStream();
					// System.out.println(inputStream);

					bos = new ByteArrayOutputStream();

					byte[] buffer1 = new byte[1024];

					int len1 = -1;
					while ((len1 = inputStream.read(buffer1)) != -1) {
						bos.write(buffer1, 0, len1);
					}

					roadnameJson = new String(bos.toByteArray(), "gb2312");
					list.add(roadnameJson);
				}
				httpURLConnection2.setConnectTimeout(5000);
				httpURLConnection2.setRequestMethod("GET");

				if (httpURLConnection2.getResponseCode() == 200) {

					inputStream = httpURLConnection2.getInputStream();
					System.out.println(inputStream);

					bos = new ByteArrayOutputStream();

					byte[] buffer2 = new byte[1024];

					int len2 = -1;
					while ((len2 = inputStream.read(buffer2)) != -1) {
						bos.write(buffer2, 0, len2);
					}

					linenameJson = new String(bos.toByteArray(), "gb2312");
					list.add(linenameJson);
				}

				httpURLConnection3.setConnectTimeout(5000);
				httpURLConnection3.setRequestMethod("GET");

				if (httpURLConnection3.getResponseCode() == 200) {

					inputStream = httpURLConnection3.getInputStream();
					System.out.println(inputStream);

					bos = new ByteArrayOutputStream();

					byte[] buffer3 = new byte[1024];

					int len3 = -1;
					while ((len3 = inputStream.read(buffer3)) != -1) {
						bos.write(buffer3, 0, len3);
					}

					plnameJson = new String(bos.toByteArray(), "gb2312");
					list.add(plnameJson);
				}

				httpURLConnection4.setConnectTimeout(5000);
				httpURLConnection4.setRequestMethod("GET");

				if (httpURLConnection4.getResponseCode() == 200) {

					inputStream = httpURLConnection4.getInputStream();
					System.out.println(inputStream);

					bos = new ByteArrayOutputStream();

					byte[] buffer4 = new byte[1024];

					int len4 = -1;
					while ((len4 = inputStream.read(buffer4)) != -1) {
						bos.write(buffer4, 0, len4);
					}

					pllineJson = new String(bos.toByteArray(), "gb2312");
					list.add(pllineJson);
				}

				httpURLConnection5.setConnectTimeout(5000);
				httpURLConnection5.setRequestMethod("GET");

				if (httpURLConnection5.getResponseCode() == 200) {

					inputStream = httpURLConnection5.getInputStream();
					System.out.println(inputStream);

					bos = new ByteArrayOutputStream();

					byte[] buffer5 = new byte[1024];

					int len5 = -1;
					while ((len5 = inputStream.read(buffer5)) != -1) {
						bos.write(buffer5, 0, len5);
					}

					carnumJson = new String(bos.toByteArray(), "gb2312");
					list.add(carnumJson);
					// System.out.println(carnumJson);
				}

				httpURLConnection6.setConnectTimeout(5000);
				httpURLConnection6.setRequestMethod("GET");

				if (httpURLConnection6.getResponseCode() == 200) {

					inputStream = httpURLConnection6.getInputStream();
					System.out.println(inputStream);

					bos = new ByteArrayOutputStream();

					byte[] buffer6 = new byte[1024];

					int len6 = -1;
					while ((len6 = inputStream.read(buffer6)) != -1) {
						bos.write(buffer6, 0, len6);
					}

					carpaiJson = new String(bos.toByteArray(), "gb2312");
					list.add(carpaiJson);
				}

				httpURLConnection7.setConnectTimeout(5000);
				httpURLConnection7.setRequestMethod("GET");

				if (httpURLConnection7.getResponseCode() == 200) {

					inputStream = httpURLConnection7.getInputStream();
					System.out.println(inputStream);

					bos = new ByteArrayOutputStream();

					byte[] buffer7 = new byte[1024];

					int len7 = -1;
					while ((len7 = inputStream.read(buffer7)) != -1) {
						bos.write(buffer7, 0, len7);
					}

					orderJson = new String(bos.toByteArray(), "gb2312");
					list.add(orderJson);
				}

				String roadname = list.get(0);
				String linename = list.get(1);
				String plname = list.get(2);
				String plline = list.get(3);
				String carnum = list.get(4);
				String carpai = list.get(5);
				String order = list.get(6);
				roadnameArray = new String[roadname.length()];
				linenameArray = new String[linename.length()];
				plnameArray = new String[plname.length()];
				pllineArray = new String[plline.length()];
				carnumArray = new String[carnum.length()];
				carpaiArray = new String[carpai.length()];
				orderArray = new String[order.length()];
				roadnameArray = PasueRoadName.PsRoadName(roadname);
				linenameArray = PasueLineName.PsLineName(linename);
				plnameArray = PasuePlName.PsRoadName(plname);
				pllineArray = PasuePlLine.PsRoadName(plline);

				carnumArray = PasueCarnum.PsCarnum(carnum);
				carpaiArray = PasueCarpai.PsCarpai(carpai);
				orderArray = PasueOrder.PsOrder(order);

				setSharedPreference1("ROADNAME", roadnameArray);
				setSharedPreference1("LINENAME", linenameArray);
				setSharedPreference1("PLNAME", plnameArray);
				setSharedPreference1("PLLINE", pllineArray);
				setSharedPreference1("CARNUM", carnumArray);
				setSharedPreference1("CARPAI", carpaiArray);
				setSharedPreference1("ORDER", orderArray);

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
				}
			}
			return list;
		}
	}

	/**
	 * 
	 * sp存放数据;
	 * 
	 */
	/*
	 * 
	 * public void setSharedPreference(String key, String[] values) { String
	 * regularEx = "#"; String str = ""; if (values != null && values.length >
	 * 0) { for (String value : values) { str += value; str += regularEx; }
	 * editor_data = spDate.edit(); editor_data.putString(key, str);
	 * editor_data.commit(); } }
	 */

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
			editor_event = spEvent.edit();
			editor_event.putString(key, str);
			editor_event.commit();
		}
	}

	// 判断是否联网；
	private boolean NetWorkConnection() {
		boolean netw = false;
		ConnectivityManager con = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
		boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.isConnectedOrConnecting();
		boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.isConnectedOrConnecting();
		if (wifi | internet) {
			// 执行相关操作
			netw = true;

		} else {
			Toast.makeText(getApplicationContext(), "亲，您没有连接网络！",
					Toast.LENGTH_LONG).show();
		}
		return netw;
	}

	class HttpEventCheck extends AsyncTask<String, Void, List<String>> {

		@Override
		protected List<String> doInBackground(String... params) {
			// TODO Auto-generated method stub
			InputStream inputStream = null;
			ByteArrayOutputStream bos = null;
			try {
				URL url = new URL(params[0]);
				URL url1 = new URL(params[1]);
				URL url2 = new URL(params[2]);
				URL url3 = new URL(params[3]);

				HttpURLConnection httpURLConnection = (HttpURLConnection) url
						.openConnection();
				HttpURLConnection httpURLConnection1 = (HttpURLConnection) url1
						.openConnection();
				HttpURLConnection httpURLConnection2 = (HttpURLConnection) url2
						.openConnection();
				HttpURLConnection httpURLConnection3 = (HttpURLConnection) url3
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
					listevent.add(eventCheckJson3);
				}
				httpURLConnection1.setConnectTimeout(5000);
				httpURLConnection1.setRequestMethod("GET");

				if (httpURLConnection1.getResponseCode() == 200) {

					inputStream = httpURLConnection1.getInputStream();
					// System.out.println(inputStream);

					bos = new ByteArrayOutputStream();

					byte[] buffer1 = new byte[1024];

					int len1 = -1;
					while ((len1 = inputStream.read(buffer1)) != -1) {
						bos.write(buffer1, 0, len1);
					}

					eventCheckJson7 = new String(bos.toByteArray(), "UTF-8");
					listevent.add(eventCheckJson7);
				}

				httpURLConnection2.setConnectTimeout(5000);
				httpURLConnection2.setRequestMethod("GET");

				if (httpURLConnection2.getResponseCode() == 200) {

					inputStream = httpURLConnection2.getInputStream();
					// System.out.println(inputStream);

					bos = new ByteArrayOutputStream();

					byte[] buffer2 = new byte[1024];

					int len2 = -1;
					while ((len2 = inputStream.read(buffer2)) != -1) {
						bos.write(buffer2, 0, len2);
					}

					eventCheckJson30 = new String(bos.toByteArray(), "UTF-8");
					listevent.add(eventCheckJson30);
				}
				httpURLConnection3.setConnectTimeout(5000);
				httpURLConnection3.setRequestMethod("GET");

				if (httpURLConnection3.getResponseCode() == 200) {

					inputStream = httpURLConnection3.getInputStream();
					// System.out.println(inputStream);

					bos = new ByteArrayOutputStream();

					byte[] buffer3 = new byte[1024];

					int len3 = -1;
					while ((len3 = inputStream.read(buffer3)) != -1) {
						bos.write(buffer3, 0, len3);
					}

					eventCheckJsonall = new String(bos.toByteArray(), "UTF-8");
					list.add(eventCheckJsonall);
				}

				eventArray3 = PasueEventCheck.Pasue(eventCheckJson3);
				eventArray7 = PasueEventCheck.Pasue(eventCheckJson7);
				eventArray30 = PasueEventCheck.Pasue(eventCheckJson30);
				eventArrayall = PasueEventCheck.Pasue(eventCheckJsonall);
				setSharedPreference1("EVENT3", eventArray3);
				setSharedPreference1("EVENT7", eventArray7);
				setSharedPreference1("EVENT30", eventArray30);
				setSharedPreference1("EVENTAll", eventArrayall);

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
			return listevent;
		}
	}

	class HttpUserName extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			InputStream inputStream = null;
			ByteArrayOutputStream bos = null;
			try {
				HttpClient client = new DefaultHttpClient();

				HttpGet httpGet = new HttpGet(params[0]);

				HttpResponse httpResponse = client.execute(httpGet);

				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					// System.out.println("<<<<<<<<<<<<<<<");
					inputStream = httpResponse.getEntity().getContent();
					// System.out.println(inputStream);
					bos = new ByteArrayOutputStream();

					byte[] buffer = new byte[1024];

					int len = -1;
					while ((len = inputStream.read(buffer)) != -1) {
						bos.write(buffer, 0, len);
					}
					usersjson = new String(bos.toByteArray(), "UTF-8");

					// System.out.println(usersjson);

					users = PasueUsers.PsUsers(usersjson);

					setSharedPreference1("USERS", users);
				}
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
			return usersjson;
		}
	}

	/**
	 * 
	 * sp取数据;
	 */

	public String[] getSharedPreference(String key) {
		String regularEx = "#";
		String[] str = null;
		String values;
		spEvent = this.getSharedPreferences("event", this.MODE_PRIVATE);
		values = spEvent.getString(key, "");
		str = values.split(regularEx);
		return str;
	}

	// 开启提醒服务;
}
