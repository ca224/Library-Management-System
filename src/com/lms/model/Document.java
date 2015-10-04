package com.lms.model;

public class Document {
	
	private String DOCID;
	private String TITLE;
	private String PDATE;	
	private String PUBLISHERID;
	private String PName;

	public String getName() {
		return TITLE;
	}
	public void setName(String name) {
		this.TITLE = name;
	}
	
	public String getPName() {
		return PName;
	}
	public void setPName(String name) {
		this.PName = name;
	}

	public String getDate() {
		return PDATE;
	}
	public void setDate(String date) {
		this.PDATE = date;
	}
	public String getId() {
		return DOCID;
	}
	public void setId(String id) {
		DOCID = id;
	}
	public String getPublisher() {
		return PUBLISHERID;
	}
	public void setPublisher(String publisherid) {
		this.PUBLISHERID = publisherid;
	}
	
	public String toString() {
		return getName();
	}
}
