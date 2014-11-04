package model;

import java.util.ArrayList;

public class Meter {

	private ArrayList<Reading> readings;
	String meterID;

	/**
	 * Constructor
	 * @param id
	 */
	public Meter(String id){
		this.meterID = id;
		this.readings = new ArrayList<Reading>();
	}

	public String getMeterID() {
		return meterID;
	}


	public void setMeterID(String meterID) {
		this.meterID = meterID;
	}

	public long getValueSum() {
		long valueSum = 0;
		for (Reading reading : readings) {
			valueSum += reading.getValue();
		}
		return valueSum;
	}
	
	public long getAverageValue() {
		return getValueSum()/readings.size();
	}
	


	public void addReading(Reading reading){
		this.readings.add(reading);
	}
	
	public String toString(){
		return "******"+meterID+"*********\n"+printReadings();
	}

	private String printReadings() {
		StringBuilder builder = new StringBuilder();
		for (Reading reading : readings) {
			builder.append("   "+reading.toString());
		}
		return builder.toString();
	}
}
