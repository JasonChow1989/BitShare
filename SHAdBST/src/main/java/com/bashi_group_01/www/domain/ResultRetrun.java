package com.bashi_group_01.www.domain;

import java.util.List;

import org.json.JSONArray;

public class ResultRetrun {
	private JSONArray result;
	private Integer resultCode;
	private String resultDesc;
	public JSONArray getResult() {
		return result;
	}
	public void setResult(JSONArray result) {
		this.result = result;
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
