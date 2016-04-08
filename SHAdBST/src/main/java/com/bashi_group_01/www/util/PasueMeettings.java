package com.bashi_group_01.www.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bashi_group_01.www.domain.Meetting;
import com.bashi_group_01.www.domain.News;

public class PasueMeettings {

	public static List<Meetting> list = null;

	public static List<Meetting> Pasue(String result) {

		try {
			list = new ArrayList<Meetting>();
			JSONObject jsonObject = new JSONObject(result);
			JSONArray jsonArray = jsonObject.getJSONArray("result");
			//System.out.println(jsonArray);
			for(int i = 1;i<jsonArray.length();i++){
				JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
				Meetting meetting = new Meetting();
				meetting.setTblrcdid(jsonObject2.getString("tblrcdid"));
				meetting.setTitle(jsonObject2.getString("Title"));
				meetting.setMeetingLevel(jsonObject2.getString("MeetingLevel"));
				meetting.setMeetingTime(jsonObject2.getString("MeetingTime"));
				meetting.setIssuer(jsonObject2.getString("Issuer"));
				meetting.setIssueDate(jsonObject2.getString("IssueDate"));
				meetting.setState(jsonObject2.getString("State"));
				list.add(meetting);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
