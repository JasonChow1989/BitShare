package com.bashi_group_01.www.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bashi_group_01.www.domain.FaWenDetail;
import com.bashi_group_01.www.domain.Meetting;
import com.bashi_group_01.www.domain.News;

public class PasueFaWenDetail {

	public static List<FaWenDetail> list = null;

	public static List<FaWenDetail> Pasue(String result) {

		try {
			list = new ArrayList<FaWenDetail>();
			JSONObject jsonObject = new JSONObject(result);
			JSONObject jsonObject2 = jsonObject.getJSONObject("result");
			System.out.println(jsonObject2);
			FaWenDetail faWenDetail = new FaWenDetail();
			faWenDetail.setFileCode(jsonObject2.getString("FileCode"));
			faWenDetail.setDraftUser(jsonObject2.getString("DraftUser"));
			faWenDetail.setFileTitle(jsonObject2.getString("FileTitle"));
			faWenDetail.setDraftDept(jsonObject2.getString("DraftDept"));
			faWenDetail.setDraftDate(jsonObject2.getString("DraftDate"));
			faWenDetail.setSecretLevel(jsonObject2.getString("SecretLevel"));
			System.out.println(jsonObject2.getString("SecretLevel"));
			faWenDetail.setDelayLevel(jsonObject2.getString("DelayLevel"));
			faWenDetail.setKeywords(jsonObject2.getString("Keywords"));
			faWenDetail.setSubmitType(jsonObject2.getString("SubmitType"));
			faWenDetail.setBigType(jsonObject2.getString("BigType"));
			faWenDetail.setPassflag(jsonObject2.getBoolean("passflag"));
			list.add(faWenDetail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
