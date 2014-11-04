package db_uploader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import model.*;

/**
 * Read DemoSteinkjer-data from csv-file and upload to SQL-DB
 * 
 * @author Øystein Molnes
 *
 */
public class UploaderProgram {
	
	public static void main(String[] args) {
		
    	long startTime = System.currentTimeMillis();
    	String filename = "C:/Users/oyste_000/Dropbox/Skole/Masterthesis/smartgrid-datavisualization/SmartGrid-ProjectMaterial/DemoSteinkjer/ds_full_history.csv";	
		
    	//Create meter-objects, each having a list of all it's readings 
    	ArrayList<Meter> meters = createMeterReadings(200, filename);
    	
    	
    	
    	long average = 0, totalSum = 0;
    	
    	for (Iterator<Meter> iterator = meters.iterator(); iterator.hasNext();) {
    	    Meter meter = iterator.next();
    	    if (meter.getAverageValue() == 0) {
    	        iterator.remove();
    	    }
    	    else {
				totalSum += meter.getAverageValue();
			}
    	}
    	    	
    	average = totalSum / meters.size();
    	
        System.out.println("Meters: "+meters.size());
        System.out.println("Average: "+average);
        System.out.println("Time spent: "+(System.currentTimeMillis() - startTime)+" ms");

	}

/** 
 * This method reads all meterReadings from a csv file containing all readings from all meters
 * 
 *  CSV file has structure, we want the ActivePlus readings
 * 		'meterID'
 * 		ActivePlus
 * 		date, date, date, date, date
 * 		read, read, read, read, read
 * 		ReactivePlus
 * 		date, date, date, date, date
 * 		read, read, read, read, read
 * 
 * 	
 * @param linesNeeded
 * @param filename
 * @return meters : List of meter-objects
 */
	private static ArrayList<Meter> createMeterReadings(int linesNeeded, String filename) {
    	BufferedReader bf = null;
    	ArrayList<Meter> meters = new ArrayList<Meter>();
    	
    	try {
			bf = new BufferedReader(new FileReader(filename));
			String line = "", timestamps="", values="";
			String[] timestampArray, valueArray;
			int linesRead = 0;
			
			while ((line = bf.readLine()) != null && linesRead < linesNeeded) {
				
				//Find meterID:  'fd0fad8fac3a487b92fc77bc41054bbb'
				if (line.startsWith("'") && line.endsWith("'")) {
					linesRead++;
					bf.readLine(); 				//Not interestet in line with "ActivePlus"
					timestamps = bf.readLine(); //Read line with all dates for this meter
					values = bf.readLine(); 	//Read line with all values for this meter
					
					timestampArray = timestamps.split(";");
					valueArray = values.split(";");
					
					//Create object for the meter
					Meter meter = new Meter(line.substring(1, line.length()-1));
					
					//Add all it's readings
					for (int i = 1; i < timestampArray.length; i++) {
						
						double value = Double.parseDouble(valueArray[i]) - Double.parseDouble(valueArray[i-1]);
						meter.addReading(new Reading(timestampArray[i],  (long) value));
					}
					meters.add(meter);
				}
			}
			bf.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return meters;		
	}

}
