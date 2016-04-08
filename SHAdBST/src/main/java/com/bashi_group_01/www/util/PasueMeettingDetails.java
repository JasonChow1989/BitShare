package com.bashi_group_01.www.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bashi_group_01.www.domain.Meetting;
import com.bashi_group_01.www.domain.MeettingDetail;
import com.pgyersdk.utils.o;

public class PasueMeettingDetails {

	public static List<MeettingDetail> list = null;

	public static List<MeettingDetail> Pasue(String result) {

		List<String> participants;
		
		try {
			list = new ArrayList<MeettingDetail>();
			JSONObject jsonObject = new JSONObject(result);
			JSONObject jsonObject2 = jsonObject.getJSONObject("result");
				MeettingDetail meetting = new MeettingDetail();
				meetting.setMeetingLevel(jsonObject2.getString("MeetingLevel"));
				meetting.setTitle(jsonObject2.getString("Title"));
				meetting.setMeetingTime(jsonObject2.getString("MeetingTime"));
				meetting.setMeetingPlace(jsonObject2.getString("MeetingPlace"));
				meetting.setIssuer(jsonObject2.getString("Issuer"));
				meetting.setIssueDept(jsonObject2.getString("IssueDept"));
				meetting.setIssueDate(jsonObject2.getString("IssueDate"));
				meetting.setIssueEndDate(jsonObject2.getString("IssueEndDate"));
				meetting.setKCFText(jsonObject2.getString("KCFText"));
				JSONArray array = jsonObject2.getJSONArray("Participants");
				participants = new ArrayList<String>();
				for(int i=0;i<array.length();i++){
					JSONObject object = (JSONObject) array.opt(i);
					String participant = object.getString("Participants");
					participants.add(participant);
				}
				meetting.setParticipants(participants);
				
				list.add(meetting);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
