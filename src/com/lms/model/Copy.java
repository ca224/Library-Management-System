package com.lms.model;

public class Copy {
	
	private String DOCID;
	private String TITLE;
	private String LNAME;
	private String COPYNO;
	private String LIBID;
	private String POSITION;
	private String STATUS;
	
	public String getTitle() {
		return TITLE;
	}
	
	public void setTitle(String title) {
		TITLE = title;
	}
	
	public String getLname() {
		return LNAME;
	}
	
	public void setLname(String lname) {
		LNAME = lname;
	}
	
	public String getId() {
		return DOCID;
	}
	
	public void setId(String id) {
		DOCID = id;
	}
	
	public String getS() {
		return STATUS;
	}
	
	public void setS(String s) {
		STATUS = s;
	}
	
	public String getNo() {
		return COPYNO;
	}
	
	public void setNo(String no) {
		COPYNO = no;
	}
	
	public String getLid() {
		return LIBID;
	}
	
	public void setLid(String id) {
		LIBID = id;
	}
	
	public String getLoc() {
		return POSITION;
	}
	
	public void setLoc(String loc) {
		POSITION = loc;
	}
}
