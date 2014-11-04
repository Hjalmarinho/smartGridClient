package model;

import java.util.Calendar;

public class DayAverage {

	private Calendar date;
	private long value;
	
	public DayAverage(Calendar date, long value){
		this.date = date;
		this.value = value;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}
	
	public String toString(){
		return this.date+", "+this.value;
	}
}
