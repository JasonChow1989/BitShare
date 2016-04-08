package com.bashi_group_01.www.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Environment;

public class PasuePlLine {

	public static String[] JsonPLNameArray;

	public static String[] PsRoadName(String result) {

		try {

			JSONObject jsonObject = new JSONObject(result);

			JSONObject jsonObject2 = jsonObject.getJSONObject("result");
			JSONArray array2 = jsonObject2.getJSONArray("roadLine");
			JsonPLNameArray = new String[array2.length()];
			for (int i = 0; i < array2.length(); i++) {
				JsonPLNameArray[i] = array2.getString(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonPLNameArray;
	}
}
