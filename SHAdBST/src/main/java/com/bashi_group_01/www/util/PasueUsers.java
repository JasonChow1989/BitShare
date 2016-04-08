package com.bashi_group_01.www.util;

import org.json.JSONArray;
import org.json.JSONObject;

public class PasueUsers {
	public static String[] JsonUsersArray;

	public static String[] PsUsers(String result) {

		try {
			JSONObject jsonObject = new JSONObject(result);
			//System.out.println(jsonObject);
			JSONArray jsonArray = jsonObject.getJSONArray("result");
			//System.out.println(jsonArray);
			JsonUsersArray = new String[jsonArray.length()];
			for (int i = 1; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
				String id = jsonObject2.getString("id");
				String employeeId = jsonObject2.getString("employeeId");
				String mobilephone = jsonObject2.getString("mobilephone");
				String department = jsonObject2.getString("department");
				String name = jsonObject2.getString("name");
				String loginName = jsonObject2.getString("loginName");
				String order = jsonObject2.getString("order");
				String cornet = jsonObject2.getString("cornet");
				String position = jsonObject2.getString("position");
				String deptposition = jsonObject2.getString("deptposition");
				JsonUsersArray[i] = id + "#" + employeeId + "#" + mobilephone
						+ "#" + department + "#" + name + "#" + loginName + "#"
						+ order + "#" + cornet + "#" + position + "#"
						+ deptposition;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonUsersArray;
	}
}
