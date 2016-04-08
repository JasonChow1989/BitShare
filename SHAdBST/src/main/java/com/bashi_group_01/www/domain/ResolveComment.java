package com.bashi_group_01.www.domain;

import java.util.List;

public class ResolveComment {
	private String resolveUser;
	private String state;
	private String comment;
	private String reason;
	private String handledate;
	private List<String> pictures;
	private List<String> voicepath;

	public String getResolveUser() {
		return resolveUser;
	}

	public void setResolveUser(String resolveUser) {
		this.resolveUser = resolveUser;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getHandledate() {
		return handledate;
	}

	public void setHandledate(String handledate) {
		this.handledate = handledate;
	}

	public List<String> getPictures() {
		return pictures;
	}

	public void setPictures(List<String> pictures) {
		this.pictures = pictures;
	}

	public List<String> getVoicepath() {
		return voicepath;
	}

	public void setVoicepath(List<String> voicepath) {
		this.voicepath = voicepath;
	}

}
