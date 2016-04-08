package com.bashi_group_01.www.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bashi_group_01.www.domain.Fawen;
import com.bashi_group_01.www.domain.Meetting;
import com.bashi_group_01.www.domain.News;

public class PasueFaWen {

	public static List<Fawen> list = null;

	public static List<Fawen> Pasue(String result) {

		try {
			list = new ArrayList<Fawen>();
			JSONObject jsonObject = new JSONObject(result);
			JSONArray array = jsonObject.getJSONArray("result");
			for (int i = 1; i < array.length(); i++) {
				Fawen fawen = new Fawen();
				JSONObject jsonObject2 = (JSONObject) array.opt(i);
				fawen.setTblRcdId(jsonObject2.getString("TblRcdId"));
				fawen.setFileTitle(jsonObject2.getString("FileTitle"));
				fawen.setDraftDate(jsonObject2.getString("DraftDate"));
				fawen.setFileCode(jsonObject2.getString("FileCode"));
				fawen.setSecretLevel(jsonObject2.getString("SecretLevel"));
				fawen.setDelayLevel(jsonObject2.getString("DelayLevel"));
				fawen.setState(jsonObject2.getString("State"));
				fawen.setPassflag(jsonObject2.getString("passflag"));
				list.add(fawen);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
