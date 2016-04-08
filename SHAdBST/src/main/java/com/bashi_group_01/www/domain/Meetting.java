package com.bashi_group_01.www.domain;

public class Meetting {
	private String tblrcdid;
	private String Title;
	private String MeetingLevel;
	private String MeetingTime;
	private String Issuer;
	private String IssueDate;
	private String State;
	
	
	public String getTblrcdid() {
		return tblrcdid;
	}
	public void setTblrcdid(String tblrcdid) {
		this.tblrcdid = tblrcdid;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getMeetingLevel() {
		return MeetingLevel;
	}
	public void setMeetingLevel(String meetingLevel) {
		MeetingLevel = meetingLevel;
	}
	public String getMeetingTime() {
		return MeetingTime;
	}
	public void setMeetingTime(String meetingTime) {
		MeetingTime = meetingTime;
	}
	public String getIssuer() {
		return Issuer;
	}
	public void setIssuer(String issuer) {
		Issuer = issuer;
	}
	public String getIssueDate() {
		return IssueDate;
	}
	public void setIssueDate(String issueDate) {
		IssueDate = issueDate;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
}
