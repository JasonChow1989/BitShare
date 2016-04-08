package com.bashi_group_01.www.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bashi_group_01.www.domain.Weather;

public class PasueWeather {
	private static List<Weather> list = null;

	public static List<Weather> pasue(String result) {

		list = new ArrayList<Weather>();
		try {
			JSONObject jsonObject = new JSONObject(result);

			JSONObject jsonObject2 = jsonObject.getJSONObject("data");
			String wendu = jsonObject2.getString("wendu");
			JSONArray array = jsonObject2.getJSONArray("forecast");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject3 = (JSONObject) array.opt(i);
				Weather weather = new Weather();
				weather.setWendu(wendu);
				weather.setType(jsonObject3.getString("type"));
				weather.setHigh(jsonObject3.getString("high"));
				weather.setLow(jsonObject3.getString("low"));
				list.add(weather);
			}
			//System.out.println(list.size());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
