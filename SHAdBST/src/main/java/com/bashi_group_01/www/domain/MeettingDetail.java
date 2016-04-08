package com.bashi_group_01.www.domain;

import java.util.List;

public class MeettingDetail {
	private String meetingLevel;
	private String title;
	private String meetingTime;
	private String meetingPlace;
	private String issuer;
	private String issueDept;
	private String issueDate;
	private String issueEndDate;
	private String KCFText;
	private List<String> participants;

	public String getMeetingLevel() {
		return meetingLevel;
	}

	public void setMeetingLevel(String meetingLevel) {
		this.meetingLevel = meetingLevel;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMeetingTime() {
		return meetingTime;
	}

	public void setMeetingTime(String meetingTime) {
		this.meetingTime = meetingTime;
	}

	public String getMeetingPlace() {
		return meetingPlace;
	}

	public void setMeetingPlace(String meetingPlace) {
		this.meetingPlace = meetingPlace;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getIssueDept() {
		return issueDept;
	}

	public void setIssueDept(String issueDept) {
		this.issueDept = issueDept;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String getIssueEndDate() {
		return issueEndDate;
	}

	public void setIssueEndDate(String issueEndDate) {
		this.issueEndDate = issueEndDate;
	}

	public String getKCFText() {
		return KCFText;
	}

	public void setKCFText(String kCFText) {
		KCFText = kCFText;
	}

	public List<String> getParticipants() {
		return participants;
	}

	public void setParticipants(List<String> participants) {
		this.participants = participants;
	}
}
