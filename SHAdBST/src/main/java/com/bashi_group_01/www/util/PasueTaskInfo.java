package com.bashi_group_01.www.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bashi_group_01.www.domain.EventTaskInfo;
import com.bashi_group_01.www.domain.ResolveComment;

public class PasueTaskInfo {

	private static List<EventTaskInfo> taskInfoList = null;

	public static List<EventTaskInfo> PsTaskInfo(String taskInfoJson) {
		// TODO Auto-generated method stub
		taskInfoList = new ArrayList<EventTaskInfo>();
		EventTaskInfo eventTaskInfo = new EventTaskInfo();

		List<ResolveComment> comments = new ArrayList<ResolveComment>();
		List<String> picUrl = new ArrayList<String>();
		List<String> voiceUrl = new ArrayList<String>();

		try {
			JSONObject jsonObject = new JSONObject(taskInfoJson);
			JSONObject jsonObject2 = jsonObject.getJSONObject("result");
			eventTaskInfo.setTaskId(jsonObject2.getString("taskId"));
			eventTaskInfo.setTaskName(jsonObject2.getString("taskName"));
			eventTaskInfo.setState(jsonObject2.getString("State"));
			eventTaskInfo.setLocation(jsonObject2.getString("location"));
			eventTaskInfo.setTaskType(jsonObject2.getString("taskType"));
			eventTaskInfo.setCreateTime(jsonObject2.getString("createTime"));
			eventTaskInfo.setCreateUser(jsonObject2.getString("createUser"));
			eventTaskInfo.setResolveUser(jsonObject2.getString("resolveUser"));
			eventTaskInfo.setAuditUser(jsonObject2.getString("auditUser"));
			eventTaskInfo.setTaskDesc(jsonObject2.getString("taskDesc"));
			eventTaskInfo.setDetailDesc(jsonObject2.getString("detailDesc"));
			eventTaskInfo.setHasThumb(jsonObject2.getString("hasThumb"));
			JSONArray arrayPic = jsonObject2.getJSONArray("pictures");
			if (arrayPic.length() != 0) {
				for (int i = 0; i < arrayPic.length(); i++) {
					JSONObject object = (JSONObject) arrayPic.opt(i);
					String url = object.getString("picUrl");
					picUrl.add(url);
				}
				eventTaskInfo.setPicture(picUrl);
			} else {
				picUrl.add("");
				eventTaskInfo.setPicture(picUrl);
			}

			JSONArray arrayAudio = jsonObject2.getJSONArray("Voicepath");
			if (arrayAudio.length() != 0) {
				for (int i = 0; i < arrayAudio.length(); i++) {
					JSONObject object = (JSONObject) arrayAudio.opt(i);
					String VoicepahtUrl = object.getString("VoicepahtUrl");
					voiceUrl.add(VoicepahtUrl);
				}
				eventTaskInfo.setVoicePath(voiceUrl);
			} else {
				voiceUrl.add("");
				eventTaskInfo.setVoicePath(voiceUrl);
			}

			JSONArray arrayresolveComment = jsonObject2
					.getJSONArray("resolveComment");
			
			for (int i = 0; i < arrayresolveComment.length(); i++) {
				JSONObject object = (JSONObject) arrayresolveComment.opt(i);
				ResolveComment resolveComment = new ResolveComment();
				resolveComment.setResolveUser(object.getString("resolveUser"));
				System.out.println(object.getString("resolveUser"));
				resolveComment.setState(object.getString("state"));
				resolveComment.setComment(object.getString("comment"));
				resolveComment.setReason(object.getString("reason"));
				resolveComment.setHandledate(object.getString("handledate"));
				JSONArray arrayResPicUrl = object.getJSONArray("pictures");
				if (arrayResPicUrl.length() != 0) {
					List<String> resPicUrl = new ArrayList<String>();
					for (int j = 0; j < arrayResPicUrl.length(); j++) {
						JSONObject object2 = (JSONObject) arrayResPicUrl.opt(j);
						String picUrl1 = object2.getString("picUrl");
						System.out.println(picUrl1);
						resPicUrl.add(picUrl1);
					}
					System.out.println(resPicUrl.size());
					resolveComment.setPictures(resPicUrl);
				} else {
					List<String> resPicUrl = new ArrayList<String>();
					resPicUrl.add("");
					resolveComment.setPictures(resPicUrl);
				}
				JSONArray arrayResVoiceUrl = object.getJSONArray("Voicepath");
				if (arrayResVoiceUrl.length() != 0) {
					// System.out.println(arrayResVoiceUrl.length());
					List<String> resVoiceUrl = new ArrayList<String>();
					JSONObject object2 = (JSONObject) arrayResVoiceUrl.opt(0);
					String voice = object2.getString("VoicepahtUrl");
					System.out.println(voice);
					resVoiceUrl.add(voice);
					System.out.println(resVoiceUrl.size());
					resolveComment.setVoicepath(resVoiceUrl);
				} else {
					List<String> resVoiceUrl = new ArrayList<String>();
					resVoiceUrl.add("");
					resolveComment.setVoicepath(resVoiceUrl);
				}
				comments.add(resolveComment);
			}
			eventTaskInfo.setResolveComment(comments);

			taskInfoList.add(eventTaskInfo);
			
			System.out.println("xxxxxxx"+taskInfoList.size());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return taskInfoList;
	}
}
