package com.bashi_group_01.www.util;

import org.json.JSONArray;
import org.json.JSONObject;

public class PasueCarnum {

	public static String[] carnumJsonArray;
	
	public static String[] PsCarnum(String carnum) {
		// TODO Auto-generated method stub
		
		try {

			JSONObject jsonObject = new JSONObject(carnum);

			JSONObject jsonObject2 = jsonObject.getJSONObject("result");
			JSONArray array2 = jsonObject2.getJSONArray("VehicleNumbering");
			carnumJsonArray = new String[array2.length()];
			for (int i = 0; i < array2.length(); i++) {
				carnumJsonArray[i] = array2.getString(i);
				//System.out.println(carnumJsonArray[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return carnumJsonArray;
	}
	
}
