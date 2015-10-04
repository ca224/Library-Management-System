package com.wsy.model;

public class Reserve extends Copy{
	
	private String RESUMBER;
	private String READERID;
	private String DTIME;

	public String getRno() {
		return RESUMBER;
	}
	public void setRno(String Rno) {
		RESUMBER = Rno;
	}
	public String getRid() {
		return READERID;
	}
	public void setRid(String Rid) {
		READERID = Rid;
	}
	public String getDtime() {
		return DTIME;
	}
	public void setDtime(String DTIME) {
		this.DTIME = DTIME;
	}
}

