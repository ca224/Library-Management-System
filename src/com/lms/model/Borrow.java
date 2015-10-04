package com.lms.model;

public class Borrow extends Copy{
	
	private String BORNUMBER;
	private String READERID;
	private String BDTIME;
	private String RDTIME;

	public String getBno() {
		return BORNUMBER;
	}
	public void setBno(String Bno) {
		BORNUMBER = Bno;
	}
	public String getRid() {
		return READERID;
	}
	public void setRid(String Rid) {
		READERID = Rid;
	}
	public String getBtime() {
		return BDTIME;
	}
	public void setBtime(String BDTIME) {
		this.BDTIME = BDTIME;
	}
	public String getRtime() {
		return RDTIME;
	}
	public void setRtime(String Rtime) {
		this.RDTIME = Rtime;
	}
}
