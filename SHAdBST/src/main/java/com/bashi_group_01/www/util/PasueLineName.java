package com.bashi_group_01.www.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Environment;

public class PasueLineName {

	public static String[] JsonLineNameArray;
	static String result = null;

	public static String[] PsLineName(String result) {
				try{
				JSONObject jsonObject = new JSONObject(result);

				JSONObject jsonObject2 = jsonObject.getJSONObject("result");
				JSONArray array2 = jsonObject2.getJSONArray("RoadLine");
				JsonLineNameArray = new String[array2.length()];
				for (int i = 0; i < array2.length(); i++) {
					JsonLineNameArray[i] = array2.getString(i);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		return JsonLineNameArray;
	}
}
