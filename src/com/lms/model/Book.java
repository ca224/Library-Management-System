package com.lms.model;

public class Book extends Document{
	
	private String ISBN;
	private String ANAME;
	private String Aid;
	
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String isbn) {
		ISBN = isbn;
	}

	public String getAName() {
		return ANAME;
	}
	public void setAName(String name) {
		this.ANAME = name;
	}
	
	public void setAid(String aid){
		Aid = aid;
	}
	
	public String getAid(){
		return Aid;
	}
}
