package com.bashi_group_01.www.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bashi_group_01.www.domain.Fawen;
import com.bashi_group_01.www.domain.HeTong;
import com.bashi_group_01.www.domain.Meetting;
import com.bashi_group_01.www.domain.News;

public class PasueHeTong {

	public static List<HeTong> list = null;

	public static List<HeTong> Pasue(String result) {

		try {
			list = new ArrayList<HeTong>();
			JSONObject jsonObject = new JSONObject(result);
			JSONArray array = jsonObject.getJSONArray("result");
			System.out.println(array);
			
			for (int i = 1; i < array.length(); i++) {
				HeTong heTong = new HeTong();
				JSONObject jsonObject2 = (JSONObject) array.opt(i);
				heTong.setTblRcdId(jsonObject2.getString("TblRcdId"));
				heTong.setCutomer(jsonObject2.getString("Cutomer"));
				System.out.println(jsonObject2.getString("Cutomer"));
				heTong.setContractType(jsonObject2.getString("ContractType"));
				heTong.setContractId(jsonObject2.getString("ContractId"));
				heTong.setSeller(jsonObject2.getString("Seller"));
				heTong.setSignDate(jsonObject2.getString("SignDate"));
				heTong.setState(jsonObject2.getString("State"));
				heTong.setPassflag(jsonObject2.getString("passflag"));
				list.add(heTong);
			}
			System.out.println(list.size());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
