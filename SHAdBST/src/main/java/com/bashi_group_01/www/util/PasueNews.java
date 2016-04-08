package com.bashi_group_01.www.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bashi_group_01.www.domain.News;

public class PasueNews {

	public List<News> jsonArrayNews = null;

	public List<News> Pasue(String result) {

		try {
			jsonArrayNews =  new ArrayList<News>();
			JSONObject jsonObject = new JSONObject(result);
			JSONArray jsonArray = jsonObject.getJSONArray("result");
			System.out.println(jsonArray.length());
			for (int i = 1; i < jsonArray.length(); i++) {
				News news = new News();
				JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
				news.setTitle(jsonObject2.getString("title"));
				news.setIssueDate(jsonObject2.getString("IssueDate"));
				news.setUrl(jsonObject2.getString("URL"));
				jsonArrayNews.add(news);
			}
			
			System.out.println(jsonArrayNews.size());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonArrayNews;
	}

}
