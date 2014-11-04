package model;

public class Reading {

	private String timeStamp;
	private long value;
	private String readingType;

	
	/**
	 * Constructor
	 * @param timeStamp
	 * @param value
	 * @param type
	 */
	public Reading(String timeStamp, long value){
		this.timeStamp = timeStamp;
		this.value = value;
	}
	
	public String getTimeStamp(){
		return this.timeStamp;
	}
	
	public void setTimeStamp(String timeStamp){
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
