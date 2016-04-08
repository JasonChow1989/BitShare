package com.bashi_group_01.www.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.data.JPushLocalNotification;

import com.bashi_group_01.www.domain.Meetting;
import com.bashi_group_01.www.util.PasueMeettings;

/**
 * 消息通知服务;
 * 
 * @author share
 * 
 */
public class NotificationService extends Service {

	private SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private String url = "http://116.236.170.106:8384/FunctionModule/RemoteData/InformationHandler.ashx?startIndex=0&pageSize=100&type=1&userkey=";
	List<Meetting> list = null;

	String userName;
	SharedPreferences sp;
	NotificationManager manager;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		sp = getSharedPreferences("userInfo", MODE_PRIVATE);
		userName = sp.getString("USER_NAME", "");
		new Thread() {
			public void run() {
				// 请求数据;
				try {
					list = GetMeettingData(userName);
					while (true) {
						sleep(1000);
						String s = dateFormat.format(new Date());
						if (list.size() != 0) {
							for (int i = 0; i < list.size(); i++) {
								// System.out.println(list.get(i).getMeetingTime());

								// System.out.println("<><><><><><>"
								// + s.substring(0, 10));
								//System.out.println(">>>??????"
									//	+ s.substring(11, 19));

								if (s.substring(0, 10).equals(
										list.get(i).getMeetingTime()
												.substring(0, 10))
										&& s.substring(11, 19).equals(
												"07:30:00")) {

									JPushLocalNotification ln = new JPushLocalNotification();
									ln.setBuilderId(1);
									ln.setContent("会议通知：您今天有会议需要参加");
									ln.setTitle("广告巴士通(测)");
									ln.setNotificationId(11111111);
									Map<String, Object> map = new HashMap<String, Object>();
									map.put("key", "0");
									JSONObject json = new JSONObject(map);
									ln.setExtras(json.toString());
									JPushInterface.addLocalNotification(
											getApplicationContext(), ln);
								}
							}
						}
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();

		return super.onStartCommand(intent, flags, startId);
	}

	private List<Meetting> GetMeettingData(String userName) {
		// TODO Auto-generated method stub
		InputStream inputStream = null;
		ByteArrayOutputStream bos = null;
		try {
			URL newUrl = new URL(url + userName);
			System.out.println(newUrl);

			HttpURLConnection httpURLConnection = (HttpURLConnection) newUrl
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

				String mettingjson = new String(bos.toByteArray(), "UTF-8");
				System.out.println(mettingjson);
				list = PasueMeettings.Pasue(mettingjson);
			}
		} catch (Exception e) {
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
}
