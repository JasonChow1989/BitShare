package com.bashi_group_01.www.domain;

public class EventCheck {
	private String type; // ���� 3�������� 4�ֳ��칫
	private String taskId;// ����ID
	private String taskName;// ��������
	private String taskType;// ��������
	private String StateCha;// ����ǰ״̬
	private String createTime;// ����ʱ��
	private String createUser;// ������ɫ
	private String location;// ����λ��
	private String taskDesc;// ��������
	private String taskPicUrl;// ��������ͼƬ�������򽫵�һ��ͼƬ��URL���ڴ˴�
	private String hasThumb;// ͼƬ�Ƿ�������ͼ 1 �� 0 ��
	private String lastComment;// ���������һ������˵��

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getStateCha() {
		return StateCha;
	}

	public void setStateCha(String stateCha) {
		StateCha = stateCha;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}

	public String getTaskPicUrl() {
		return taskPicUrl;
	}

	public void setTaskPicUrl(String taskPicUrl) {
		this.taskPicUrl = taskPicUrl;
	}

	public String getHasThumb() {
		return hasThumb;
	}

	public void setHasThumb(String hasThumb) {
		this.hasThumb = hasThumb;
	}

	public String getLastComment() {
		return lastComment;
	}

	public void setLastComment(String lastComment) {
		this.lastComment = lastComment;
	}

}
