package com.lms.model;

public class Operater {
	
	private String ANAME;
	private String APWD;
	private String TYPE;

	public String getName() {
		return ANAME;
	}
	
	public void setName(String name) {
		this.ANAME = name;
	}
	
	public String getPassword() {
		return APWD;
	}
	
	public void setPassword(String password) {
		this.APWD = password;
	}
	
	public String getType(){
		return TYPE;
	}
	
	public void setType(String type){
		TYPE = type;
	}
}