package model;

import java.util.Calendar;

public class Reading {

	private Calendar timeStamp;
	private long value;
	private String readingType;

	
	/**
	 * Constructor
	 * @param timeStamp
	 * @param value
	 * @param type
	 */
	public Reading(Calendar timeStamp, long value){
		this.timeStamp = timeStamp;
		this.value = value;
	}
	
	public Calendar getTimeStamp(){
		return this.timeStamp;
	}
	
	public void setTimeStamp(Calendar timeStamp){
		this.timeStamp = timeStamp;
	}
	
	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public String getReadingType() {
		return readingType;
	}

	public void setReadingType(String readingType) {
		this.readingType = readingType;
	}

	public String toString(){
		return this.timeStamp+", "+this.value+", "+this.readingType+"\n";
	}
}
