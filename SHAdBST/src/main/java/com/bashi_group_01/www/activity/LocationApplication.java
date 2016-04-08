package com.bashi_group_01.www.activity;

import android.app.Application;
import android.util.Log;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.bashi_group_01.www.db.DbHelper;

/**
 * 主Application
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
	 * 实现实位回调监听
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
	 * 显示请求字符串
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
	 * 高精度地理围栏回调
	 * @author jpren
	 *
	 */
	
}
