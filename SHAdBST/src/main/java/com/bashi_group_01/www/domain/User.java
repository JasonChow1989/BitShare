package com.bashi_group_01.www.domain;

public class User {
	private String userAuthor;
	private String username;
	private Integer resultCode;
	private String resultDesc;
	
	
	public String getUserAuthor() {
		return userAuthor;
	}
	public void setUserAuthor(String userAuthor) {
		this.userAuthor = userAuthor;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getResultCode() {
		return resultCode;
	}
	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
}
