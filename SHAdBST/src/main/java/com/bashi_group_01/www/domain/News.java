package com.bashi_group_01.www.domain;

public class News {
	private String type;
	private String totalCount;
	private String list;
	private String TbRcdId;
	private String title;
	private String IssueDate;
	private String url;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public String getList() {
		return list;
	}
	public void setList(String list) {
		this.list = list;
	}
	public String getTbRcdId() {
		return TbRcdId;
	}
	public void setTbRcdId(String tbRcdId) {
		TbRcdId = tbRcdId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIssueDate() {
		return IssueDate;
	}
	public void setIssueDate(String issueDate) {
		IssueDate = issueDate;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
