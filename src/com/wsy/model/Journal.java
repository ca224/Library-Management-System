package com.wsy.model;

public class Journal extends Document{
	
	private String JVOLUME;
	private String Eid;
	private String ENAME;
	private String SCOPE;
	private String IENAME;
	private String Iss;
	
	public String getJvolume() {
		return JVOLUME;
	}
	public void setJvolume(String jvolume) {
		JVOLUME = jvolume;
	}
	
	public String getIss() {
		return Iss;
	}
	public void setIss(String iss) {
		Iss = iss;
	}
	
	public String getEName() {
		return ENAME;
	}
	public void setEName(String ename) {
		this.ENAME = ename;
	}
	public String getScope() {
		return SCOPE;
	}
	public void setScope(String scope) {
		this.SCOPE = scope;
	}
	
	public String getIEname() {
		return IENAME;
	}
	
	public void setIEname(String string) {
		IENAME = string;
	}
	
	public void setEid(String eid){
		Eid = eid;
	}
	
	public String getEid(){
		return Eid;
	}
}
