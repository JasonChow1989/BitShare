package com.bashi_group_01.www.util;

import org.json.JSONArray;
import org.json.JSONObject;

public class PasueEventCheck {

	private static String[] eventArray;

	public static String[] Pasue(String result) {
		try {
			JSONObject jsonObject = new JSONObject(result);
			JSONArray jsonArray = jsonObject.getJSONArray("result");
			eventArray = new String[jsonArray.length()];
			for (int i = 1; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
				String type = jsonObject2.getString("type");
				String taskId = jsonObject2.getString("taskId");
				String taskname = jsonObject2.getString("taskName");
				String taskType = jsonObject2.getString("taskType");
				String statecha = jsonObject2.getString("StateCha");
				String createtime = jsonObject2.getString("createTime");
				String createUser = jsonObject2.getString("createUser");
				String location = jsonObject2.getString("location");
				String taskDesc = jsonObject2.getString("taskDesc");
				String taskPicUrl = jsonObject2.getString("taskPicUrl");
				String hasThumb = jsonObject2.getString("hasThumb");
				String lastComment = jsonObject2.getString("lastComment");
				eventArray[i] = type + "#" + taskId + "#" + taskname + "#"
						+ taskType + "#" + statecha + "#" + createtime + "#"
						+ createUser + "#" + location + "#" + taskDesc + "#"
						+ taskPicUrl + "#" + hasThumb + "#" + lastComment;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return eventArray;
	}
}
