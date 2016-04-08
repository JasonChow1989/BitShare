package com.bashi_group_01.www.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bashi_group_01.www.domain.CareerNews;
import com.bashi_group_01.www.domain.MediaNews;
import com.bashi_group_01.www.domain.News;

public class PasueMediaNews {

	public List<MediaNews> jsonArrayNews = null;

	public List<MediaNews> Pasue(String result) {

		try {
			jsonArrayNews =  new ArrayList<MediaNews>();
			JSONObject jsonObject = new JSONObject(result);
			JSONArray jsonArray = jsonObject.getJSONArray("result");
			for (int i = 0; i < jsonArray.length(); i++) {
				MediaNews mediaNews = new MediaNews();
				JSONObject Object = (JSONObject) jsonArray.opt(i);
				mediaNews.setAuthor(Object.getString("author"));
				mediaNews.setContent(Object.getString("content"));
				mediaNews.setCreateTime(Object.getString("createTime"));
				mediaNews.setId(Object.getString("id"));
				mediaNews.setLastUpdateTime(Object.getString("lastUpdateTime"));
				mediaNews.setPath(Object.getString("path"));
				mediaNews.setTitle(Object.getString("title"));
				jsonArrayNews.add(mediaNews);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonArrayNews;
	}

}
