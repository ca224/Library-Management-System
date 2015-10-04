package com.lms.model;

public class Publisher {
	private String PUBLISHERID;
	private String PUBNAME;
	private String ADDRESS;

	public String getId() {
		return PUBLISHERID;
	}
	
	public void setId(String id) {
		this.PUBLISHERID = id;
	}
	
	public String getName() {
		return PUBNAME;
	}
	
	public void setName(String Name) {
		this.PUBNAME = Name;
	}
	
	public void setAdd(String Address) {
		this.ADDRESS = Address;		
	}
	
	public String getAdd() {
		return ADDRESS;		
	}
	
	public String toString() {
		return getName();
	}
}
