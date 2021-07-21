package com.slate;

public class TableVal {
	String SubID;
	int hour;
	int day;
	int flag=0;
	
	public TableVal(int day, int hour, String SubID) {
		this.day=day;
		this.hour=hour;
		flag=0;
		this.SubID=SubID;
	}
}
