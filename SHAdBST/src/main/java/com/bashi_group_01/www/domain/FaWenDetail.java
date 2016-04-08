package com.bashi_group_01.www.domain;

public class FaWenDetail {
	private String fileCode;
	private String draftUser;
	private String fileTitle;
	private String draftDept;
	private String draftDate;
	private String secretLevel;
	private String delayLevel;
	private String keywords;
	private String submitType;
	private String bigType;
	private boolean passflag;
	
	public boolean isPassflag() {
		return passflag;
	}
	public void setPassflag(boolean passflag) {
		this.passflag = passflag;
	}
	public String getFileCode() {
		return fileCode;
	}
	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}
	public String getDraftUser() {
		return draftUser;
	}
	public void setDraftUser(String draftUser) {
		this.draftUser = draftUser;
	}
	public String getFileTitle() {
		return fileTitle;
	}
	public void setFileTitle(String fileTitle) {
		this.fileTitle = fileTitle;
	}
	public String getDraftDept() {
		return draftDept;
	}
	public void setDraftDept(String draftDept) {
		this.draftDept = draftDept;
	}
	public String getDraftDate() {
		return draftDate;
	}
	public void setDraftDate(String draftDate) {
		this.draftDate = draftDate;
	}
	public String getSecretLevel() {
		return secretLevel;
	}
	public void setSecretLevel(String secretLevel) {
		this.secretLevel = secretLevel;
	}
	public String getDelayLevel() {
		return delayLevel;
	}
	public void setDelayLevel(String delayLevel) {
		this.delayLevel = delayLevel;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getSubmitType() {
		return submitType;
	}
	public void setSubmitType(String submitType) {
		this.submitType = submitType;
	}
	public String getBigType() {
		return bigType;
	}
	public void setBigType(String bigType) {
		this.bigType = bigType;
	}
}
