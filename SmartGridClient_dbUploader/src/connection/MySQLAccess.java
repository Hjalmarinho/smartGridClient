package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.*;

//import javax.swing.JOptionPane;

public class MySQLAccess {
	
		final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        private static String driverName = "com.mysql.jdbc.Driver";
        private static String DB_CONNECTION="jdbc:mysql://144.76.57.108/oystemol_demosteinkjer";
        private static String DB_USER="oystemol";
        private static String DB_PASSWORD="oysteinmolnes";
        
        private Connection dbConnection;
        
        public MySQLAccess(){
        	this.dbConnection = getDBConnection();
        }
       
        /**
         * Set up database connection 
         * 
         * @return dbConnection
         */
    	private static Connection getDBConnection() {
    		 
    		Connection dbConnection = null;
    		try {
    			Class.forName(driverName);
    		} catch (ClassNotFoundException e) {
    			System.out.println(e.getMessage());
    		}
    		try {
    			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,DB_PASSWORD);
    			return dbConnection;
    		} catch (SQLException e) {
    			System.out.println(e.getMessage());
    		}
    		return dbConnection;
     
    	}

    	/**
    	 * Insert all readings for the meter into the database
    	 * 
    	 * @param meter
    	 * @throws SQLException
    	 */
        public void insertMeterData(Meter meter) throws SQLException {
        	java.sql.Statement statement = null;
        	StringBuilder queryString = new StringBuilder();
        	
				
			ArrayList<Reading> readings = meter.getReadings();
	
			for (int j = 0; j < readings.size(); j++) {
				Reading reading = readings.get(j);
	
				String meterID = meter.getMeterID();
				String timestamp = dateFormat.format(reading.getTimeStamp().getTime());
				long value = reading.getValue();
	
				queryString.append("(\"" + meterID + "\", '" + timestamp + "', "+ value + "),");
			}
        	
        	String query = queryString.substring(0, queryString.length()-1);
        	
        	String insertQuery = "INSERT INTO reading (meterID, timestamp, value) VALUES "+query+";";
        	try {
        		statement = dbConnection.createStatement();
        		statement.executeUpdate(insertQuery);
        	} catch (SQLException e) {
        		e.printStackTrace();
        	}
        	finally {
        		
        		if (statement != null) {
        			statement.close();
        		}
        	}
        }
		
        /**
         * Close database connection
         * 
         * @throws SQLException
         */
		public void closeConnection() throws SQLException {
			if (dbConnection != null) {
				dbConnection.close();
			}
		}


}