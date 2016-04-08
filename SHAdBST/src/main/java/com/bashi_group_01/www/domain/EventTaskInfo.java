package com.bashi_group_01.www.domain;

import java.util.List;

public class EventTaskInfo {

	private String taskId;
	private String taskName;
	private String state;
	private String location;
	private String taskType;
	private String createTime;
	private String createUser;
	private String resolveUser;
	private String auditUser;
	private String taskDesc;
	private String detailDesc;
	private String hasThumb;
	private List<String> picture;
	private List<String> voicePath;
	private List<ResolveComment> resolveComment;
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getResolveUser() {
		return resolveUser;
	}
	public void setResolveUser(String resolveUser) {
		this.resolveUser = resolveUser;
	}
	public String getAuditUser() {
		return auditUser;
	}
	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}
	public String getTaskDesc() {
		return taskDesc;
	}
	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}
	public String getDetailDesc() {
		return detailDesc;
	}
	public void setDetailDesc(String detailDesc) {
		this.detailDesc = detailDesc;
	}
	public String getHasThumb() {
		return hasThumb;
	}
	public void setHasThumb(String hasThumb) {
		this.hasThumb = hasThumb;
	}
	public List<String> getPicture() {
		return picture;
	}
	public void setPicture(List<String> picture) {
		this.picture = picture;
	}
	public List<String> getVoicePath() {
		return voicePath;
	}
	public void setVoicePath(List<String> voicePath) {
		this.voicePath = voicePath;
	}
	public List<ResolveComment> getResolveComment() {
		return resolveComment;
	}
	public void setResolveComment(List<ResolveComment> resolveComment) {
		this.resolveComment = resolveComment;
	}
	
}
