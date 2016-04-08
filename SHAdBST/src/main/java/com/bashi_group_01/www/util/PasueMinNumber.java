package com.bashi_group_01.www.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bashi_group_01.www.domain.Fawen;
import com.bashi_group_01.www.domain.Meetting;
import com.bashi_group_01.www.domain.MiNum;
import com.bashi_group_01.www.domain.News;

public class PasueMinNumber {

	public static List<MiNum> list = null;

	public static List<MiNum> Pasue(String result) {

		try {
			list = new ArrayList<MiNum>();
			JSONObject jsonObject = new JSONObject(result);
			JSONArray array = jsonObject.getJSONArray("result");
			MiNum miNum = new MiNum();
			JSONObject jsonObject2 = (JSONObject) array.opt(0);
			miNum.setDistrict(jsonObject2.getString("District"));
			miNum.setRoadName(jsonObject2.getString("RoadName"));
			miNum.setStationName(jsonObject2.getString("StationName"));
			miNum.setStationAddress(jsonObject2.getString("StationAddress"));
			miNum.setLineList(jsonObject2.getString("LineList"));
			miNum.setStopId(jsonObject2.getString("StopId"));
			miNum.setPathDirection(jsonObject2.getString("PathDirection"));
			list.add(miNum);

			
			System.out.println(list.size());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
