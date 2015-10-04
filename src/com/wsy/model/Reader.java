package com.wsy.model;

public class Reader {
	private String Rname;
	private String Rtype;
	private String Rid;
	private String Radd;
	
	public String getRid() {
		return Rid;
	}
	
	public void setRid(String id) {
		Rid = id;
	}
	
	public String getName() {
		return Rname;
	}
	
	public void setName(String name) {
		Rname = name;
	}
	
	public String getRtype() {
		return Rtype;
	}
	
	public void setRtype(String type) {
		Rtype = type;
	}
	
	public String getRadd() {
		return Radd;
	}
	
	public void setRadd(String add) {
		Radd = add;
	}
}
