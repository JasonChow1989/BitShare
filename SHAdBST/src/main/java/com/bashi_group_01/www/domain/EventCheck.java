package com.bashi_group_01.www.domain;

public class EventCheck {
	private String type; // 类型 3交办事务 4现场办公
	private String taskId;// 事务ID
	private String taskName;// 事务名称
	private String taskType;// 事务类型
	private String StateCha;// 事务当前状态
	private String createTime;// 创建时间
	private String createUser;// 创建角色
	private String location;// 创建位置
	private String taskDesc;// 事务描述
	private String taskPicUrl;// 若事务有图片描述，则将第一张图片的URL置于此处
	private String hasThumb;// 图片是否有缩略图 1 有 0 无
	private String lastComment;// 该事务最后一条处理说明

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
