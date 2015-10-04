package com.lms.model;

public class Branch {
	
	private String LNAME;
	private String LIBID;
	private String LLOCATION;

	public String getId() {
		return LIBID;
	}
	public void setId(String Id) {
		LIBID = Id;
	}
	public String getLoc() {
		return LLOCATION;
	}
	public void setLoc(String loc) {
		LLOCATION = loc;
	}
	public String getName() {
		return LNAME;
	}
	public void setName(String name) {
		this.LNAME = name;
	}
	public String toString(){
		return LNAME;
	}

}
