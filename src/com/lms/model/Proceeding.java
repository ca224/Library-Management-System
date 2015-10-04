package com.lms.model;

public class Proceeding extends Document{
	private String CDATE;
	private String CLOCATION;
	private String CEDITOR;
	
	public String getCdate() {
		return CDATE;
	}
	public void setCdate(String cdate) {
		CDATE = cdate;
	}
	public String getCName() {
		return CEDITOR;
	}
	public void setCName(String cname) {
		this.CEDITOR = cname;
	}
	public String getLoc() {
		return CLOCATION;
	}
	public void setLoc(String loc) {
		this.CLOCATION = loc;
	}
}
