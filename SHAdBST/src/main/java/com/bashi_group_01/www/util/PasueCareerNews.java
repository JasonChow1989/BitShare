package com.bashi_group_01.www.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bashi_group_01.www.domain.CareerNews;
import com.bashi_group_01.www.domain.News;

public class PasueCareerNews {

	public List<CareerNews> jsonArrayNews = null;

	public List<CareerNews> Pasue(String result) {

		try {
			jsonArrayNews =  new ArrayList<CareerNews>();
			JSONObject jsonObject = new JSONObject(result);
			JSONArray jsonArray = jsonObject.getJSONArray("result");
			System.out.println(jsonArray.length());
			for (int i = 0; i < jsonArray.length(); i++) {
				CareerNews careerNews = new CareerNews();
				JSONObject Object = (JSONObject) jsonArray.opt(i);
				careerNews.setCreateTime(Object.getString("createTime"));
				careerNews.setFolderName(Object.getString("folderName"));
				careerNews.setId(Object.getString("id"));
				careerNews.setLastUpdateTime(Object.getString("lastUpdateTime"));
				careerNews.setTitle(Object.getString("title"));
				jsonArrayNews.add(careerNews);
			}
			
			System.out.println(jsonArrayNews.size());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonArrayNews;
	}

}
