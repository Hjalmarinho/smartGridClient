package model;

import java.util.Calendar;

public class Average {

	private Calendar timestamp;
	private long value;
	
	public Average(Calendar timestamp, long value){
		this.timestamp = timestamp;
		this.value = value;
	}

	public Calendar getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Calendar timestamp) {
		this.timestamp = timestamp;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}
	
	public String toString(){
		return this.timestamp.getTime()+", "+this.value;
	}
}
