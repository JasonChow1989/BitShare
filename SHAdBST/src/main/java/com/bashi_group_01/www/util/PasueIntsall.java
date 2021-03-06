package com.bashi_group_01.www.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bashi_group_01.www.domain.CareerNews;
import com.bashi_group_01.www.domain.Install;
import com.bashi_group_01.www.domain.News;

public class PasueIntsall {

	public static List<Install> jsonArray = null;

	public static List<Install> Pasue(String result) {

		try {
			jsonArray =  new ArrayList<Install>();
			JSONObject jsonObject = new JSONObject(result);
			JSONObject jsonObject2 = jsonObject.getJSONObject("result");
			JSONArray array = jsonObject2.getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				Install install = new Install();
				JSONObject Object = (JSONObject) array.opt(i);
				install.setTerminal_Id(Object.getString("Terminal_Id"));
				install.setTerminal_Name(Object.getString("Terminal_Name"));
				install.setTerminal_Status(Object.getString("Terminal_Status"));
				install.setTerminal_Whereabouts(Object.getString("Terminal_Whereabouts"));
				install.setZt_Num(Object.getString("ZT_Num"));
				install.setInstall_Date(Object.getString("Install_Date"));
				install.setRoadName(Object.getString("RoadName"));
				install.setDistrict(Object.getString("District"));
				install.setFacility_Num(Object.getString("Facility_Num"));
				install.setPathDirection(Object.getString("PathDirection"));
				install.setStopId(Object.getString("StopId"));
				install.setStationAddress(Object.getString("StationAddress"));
				install.setStationType(Object.getString("StationType"));
				install.setStationNumber(Object.getString("StationNumber"));
				install.setCreateDate(Object.getString("CreateDate"));
				install.setCreatePerson(Object.getString("CreatePerson"));
				install.setInstallPeople(Object.getString("InstallPeople"));
				install.setLineList(Object.getString("LineList"));
				install.setLineList_1(Object.getString("LineList_1"));
				install.setTblRcdID(Object.getString("TblRcdID"));
				install.setInstallStatus(Object.getString("InstallStatus"));
				install.setIsApproval(Object.getString("IsApproval"));
				install.setCapitalNumber(Object.getString("CapitalNumber"));
				install.setRideMetroRoadName(Object.getString("RideMetroRoadName"));
				install.setArea(Object.getString("Area"));
				
				jsonArray.add(install);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonArray;
	}

}
