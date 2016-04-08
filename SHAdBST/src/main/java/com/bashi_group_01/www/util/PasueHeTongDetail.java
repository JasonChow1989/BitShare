package com.bashi_group_01.www.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.bashi_group_01.www.domain.HeTongDetail;

public class PasueHeTongDetail {

	public static List<HeTongDetail> list = null;

	public static List<HeTongDetail> Pasue(String result) {

		try {
			list = new ArrayList<HeTongDetail>();
			JSONObject jsonObject = new JSONObject(result);
			JSONObject jsonObject2 = jsonObject.getJSONObject("result");
			HeTongDetail heTongDetail = new HeTongDetail();
			heTongDetail.setContractId(jsonObject2.getString("ContractId"));
			heTongDetail.setCutomer(jsonObject2.getString("Cutomer"));
			heTongDetail.setMainObject(jsonObject2.getString("MainObject"));
			heTongDetail.setAdContent(jsonObject2.getString("AdContent"));
			heTongDetail.setContractType(jsonObject2.getString("ContractType"));
			heTongDetail.setContractMoney(jsonObject2.getString("ContractMoney"));
			heTongDetail.setStrartDate(jsonObject2.getString("StrartDate"));
			heTongDetail.setEndDate(jsonObject2.getString("EndDate"));
			heTongDetail.setPayType(jsonObject2.getString("PayType"));
			heTongDetail.setSeller(jsonObject2.getString("Seller"));
			heTongDetail.setKeywords(jsonObject2.getString("Keywords"));
			heTongDetail.setSubmitType(jsonObject2.getString("SubmitType"));
			heTongDetail.setSignDate(jsonObject2.getString("SignDate"));
			heTongDetail.setBigType(jsonObject2.getString("BigType"));
			heTongDetail.setKCFText(jsonObject2.getString("KCFText"));
			heTongDetail.setPassflag(jsonObject2.getBoolean("passflag"));
			list.add(heTongDetail);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
