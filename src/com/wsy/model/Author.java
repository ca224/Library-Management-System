package com.wsy.model;

public class Author {
	private String AUTHORID;
	private String ANAME;

	public String getId() {
		return AUTHORID;
	}
	
	public void setId(String id) {
		this.AUTHORID = id;
	}
	
	public String getName() {
		return ANAME;
	}
	
	public void setName(String Name) {
		this.ANAME = Name;
	}
	
	public String toString() {
		return getName();
	}
}
