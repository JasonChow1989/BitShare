package com.bashi_group_01.www.activity;

import android.app.Application;
import android.util.Log;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.bashi_group_01.www.db.DbHelper;

/**
 * ��Application
 */
public class LocationApplication extends Application {
	public LocationClient mLocationClient;
	public MyLocationListener mMyLocationListener;
	public TextView mLocationResult,logMsg;
	
	public static double latitude;
	public static double longitude;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
	}

	
	/**
	 * ʵ��ʵλ�ص�����
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			//Receive Location 
			
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			
			StringBuffer sb = new StringBuffer(256);
			if (location.getLocType() == BDLocation.TypeGpsLocation){
				sb.append(location.getAddrStr());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
				sb.append(location.getAddrStr());
			}
			logMsg(sb.toString());
		}
	}
	
	
	/**
	 * ��ʾ�����ַ���
	 * @param str
	 */
	public void logMsg(String str) {
		try {
			if (mLocationResult != null)
				mLocationResult.setText(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �߾��ȵ���Χ���ص�
	 * @author jpren
	 *
	 */
	
}
