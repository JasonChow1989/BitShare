package com.bashi_group_01.www.util;

import org.json.JSONArray;
import org.json.JSONObject;

public class PasueCarpai {
	public static String[] carpaiJsonArray;

	public static String[] PsCarpai(String carpai) {
		// TODO Auto-generated method stub
		try {

			JSONObject jsonObject = new JSONObject(carpai);

			JSONObject jsonObject2 = jsonObject.getJSONObject("result");
			JSONArray array2 = jsonObject2.getJSONArray("RegistrationMark");
			carpaiJsonArray = new String[array2.length()];
			for (int i = 0; i < array2.length(); i++) {
				carpaiJsonArray[i] = array2.getString(i).replaceAll("»¦", "");
				//System.out.println(carpaiJsonArray[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return carpaiJsonArray;
	}
	
	
}
