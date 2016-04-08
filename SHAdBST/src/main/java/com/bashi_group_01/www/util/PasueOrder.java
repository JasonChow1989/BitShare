package com.bashi_group_01.www.util;

import org.json.JSONArray;
import org.json.JSONObject;

public class PasueOrder {

	public static String[] orderJsonArray;

	public static String[] PsOrder(String order) {
		// TODO Auto-generated method stub
		try {

			JSONObject jsonObject = new JSONObject(order);
			JSONObject jsonObject2 = jsonObject.getJSONObject("result");
			JSONArray array2 = jsonObject2.getJSONArray("AdorderId");
			orderJsonArray = new String[array2.length()];
			for (int i = 0; i < array2.length(); i++) {
				orderJsonArray[i] = array2.getString(i);
				//System.out.println(carnumJsonArray[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderJsonArray;
	}
}
