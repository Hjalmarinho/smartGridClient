package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import model.*;

public class MySQLReader {
	
		final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        private static String driverName = "com.mysql.jdbc.Driver";
        private static String DB_CONNECTION="jdbc:mysql://144.76.57.108/oystemol_demosteinkjer";
        private static String DB_USER="oystemol";
        private static String DB_PASSWORD="oysteinmolnes";
        
        private Connection dbConnection;
        
        public MySQLReader(){
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
    	 * Get all distinct meterIDs
    	 * 
    	 * @return meterIDs
    	 * @throws SQLException
    	 */
    	public ArrayList<String> getMeterIDs() throws SQLException {
    		Connection dbConnection = null;
    		Statement statement = null;
    		
    		String selectQuery = "SELECT DISTINCT meterID FROM reading;";
    		ArrayList<String> meterIDs = new ArrayList<String>();
    		
    		try {
    			dbConnection = getDBConnection();
    			statement = dbConnection.createStatement();
     
    			System.out.println(selectQuery);
     
    			// execute select SQL stetement
    			ResultSet rs = statement.executeQuery(selectQuery);
     
    			while (rs.next()) {
    				meterIDs.add(rs.getString("meterID"));
    			}
    		} catch (SQLException e) {
    			System.out.println(e.getMessage());
    		} finally {
     
    			if (statement != null) {
    				statement.close();
    			}
    			if (dbConnection != null) {
    				dbConnection.close();
    			}
    		}
    		return meterIDs; 		
    	}
    	
    	/**
    	 * Get the number of meterIDs
    	 * 
    	 * @return nrOfMeters
    	 * @throws SQLException
    	 */
    	public int getNrOfMeters() throws SQLException{
    		Connection dbConnection = null;
    		Statement statement = null;
    		
    		String selectQuery = "SELECT COUNT(*) AS COUNT FROM reading;";
    		int nrOfMeters = 0;
    		
    		try {
    			dbConnection = getDBConnection();
    			statement = dbConnection.createStatement();
     
    			System.out.println(selectQuery);
     
    			// execute select SQL stetement
    			ResultSet rs = statement.executeQuery(selectQuery);
     
    			while (rs.next()) {
    				nrOfMeters = rs.getInt("COUNT");
    			}
    		} catch (SQLException e) {
    			System.out.println(e.getMessage());
    		} finally {
     
    			if (statement != null) {
    				statement.close();
    			}
    			if (dbConnection != null) {
    				dbConnection.close();
    			}
    		}
    		return nrOfMeters;
    	}
    	
    	/**
    	 * Get the average value per hour for specific meter 
    	 * 
    	 * @param meterID
    	 * @return hourAverages
    	 * @throws SQLException
    	 */
    	
		public ArrayList<Average> getMeterAverageHour(String meterID) throws SQLException {
    		Connection dbConnection = null;
    		Statement statement = null;
    		ArrayList<Average> hourAverages = new ArrayList<Average>();
    		
    		String selectQuery = "SELECT meterID, timestamp, AVG(value) AS avg FROM reading WHERE meterID = \""+meterID+"\" GROUP BY hour(timestamp);";
    		
    		try {
    			dbConnection = getDBConnection();
    			statement = dbConnection.createStatement();
     
    			System.out.println(selectQuery);
     
    			// execute select SQL stetement
    			ResultSet rs = statement.executeQuery(selectQuery);
     
    			while (rs.next()) {
    				rs.getString("meterID");
    				java.util.Date date = null;
    				Timestamp timestamp = rs.getTimestamp("timestamp");
    				long value = rs.getInt("avg");
    				if (timestamp != null)
    				    date = new java.util.Date(timestamp.getTime());
    				Calendar cal = Calendar.getInstance();
    				cal.setTime(date);
    				Average avg = new Average(cal, value);
    				hourAverages.add(avg);
    			}
    		} catch (SQLException e) {
    			System.out.println(e.getMessage());
    		} finally {
     
    			if (statement != null) {
    				statement.close();
    			}
    			if (dbConnection != null) {
    				dbConnection.close();
    			}
    		}
    		return hourAverages;			
		}
		
    	/**
    	 * Get the average value per day for specific meter 
    	 * 
    	 * @param meterID
    	 * @return dayAverages
    	 * @throws SQLException
    	 */
		public ArrayList<Average> getMeterAverageDay(String meterID) throws SQLException {
    		Connection dbConnection = null;
    		Statement statement = null;
    		ArrayList<Average> dayAverages = new ArrayList<Average>();
    		
    		String selectQuery = "SELECT meterID, timestamp, AVG(value) AS avg FROM reading WHERE meterID = \""+meterID+"\" GROUP BY day(timestamp);";
    		
    		try {
    			dbConnection = getDBConnection();
    			statement = dbConnection.createStatement();
     
    			System.out.println(selectQuery);
     
    			// execute select SQL stetement
    			ResultSet rs = statement.executeQuery(selectQuery);
     
    			while (rs.next()) {
    				rs.getString("meterID");
    				java.util.Date date = null;
    				Timestamp timestamp = rs.getTimestamp("timestamp");
    				long value = rs.getInt("avg");
    				if (timestamp != null)
    				    date = new java.util.Date(timestamp.getTime());
    				Calendar cal = Calendar.getInstance();
    				cal.setTime(date);
    				Average avg = new Average(cal, value);
    				dayAverages.add(avg);
    			}
    		} catch (SQLException e) {
    			System.out.println(e.getMessage());
    		} finally {
     
    			if (statement != null) {
    				statement.close();
    			}
    			if (dbConnection != null) {
    				dbConnection.close();
    			}
    		}
    		return dayAverages;			
		}
		
		
		
		
		
    	/**
    	 * Get the average value per hour for all meters
    	 * 
    	 * @param meterID
    	 * @return hourAverages
    	 * @throws SQLException
    	 */
		public ArrayList<Average> getTotalAverageHour() throws SQLException {
    		Connection dbConnection = null;
    		Statement statement = null;
    		ArrayList<Average> hourAverages = new ArrayList<Average>();
    		
    		String selectQuery = "SELECT meterID, timestamp, AVG(value) AS avg FROM reading  GROUP BY hour(timestamp);";
    		
    		try {
    			dbConnection = getDBConnection();
    			statement = dbConnection.createStatement();
     
    			System.out.println(selectQuery);
     
    			// execute select SQL stetement
    			ResultSet rs = statement.executeQuery(selectQuery);
     
    			while (rs.next()) {
    				rs.getString("meterID");
    				java.util.Date date = null;
    				Timestamp timestamp = rs.getTimestamp("timestamp");
    				long value = rs.getInt("avg");
    				if (timestamp != null)
    				    date = new java.util.Date(timestamp.getTime());
    				Calendar cal = Calendar.getInstance();
    				cal.setTime(date);
    				Average avg = new Average(cal, value);
    				hourAverages.add(avg);
    			}
    		} catch (SQLException e) {
    			System.out.println(e.getMessage());
    		} finally {
     
    			if (statement != null) {
    				statement.close();
    			}
    			if (dbConnection != null) {
    				dbConnection.close();
    			}
    		}
    		return hourAverages;			
		}
		
    	/**
    	 * Get the average value per day for specific meter 
    	 * 
    	 * @param meterID
    	 * @return dayAverages
    	 * @throws SQLException
    	 */
		public ArrayList<Average> getTotalAverageDay() throws SQLException {
    		Connection dbConnection = null;
    		Statement statement = null;
    		ArrayList<Average> dayAverages = new ArrayList<Average>();
    		
    		String selectQuery = "SELECT meterID, timestamp, AVG(value) AS avg FROM reading  GROUP BY day(timestamp);";
    		
    		try {
    			dbConnection = getDBConnection();
    			statement = dbConnection.createStatement();
     
    			System.out.println(selectQuery);
     
    			// execute select SQL stetement
    			ResultSet rs = statement.executeQuery(selectQuery);
     
    			while (rs.next()) {
    				rs.getString("meterID");
    				java.util.Date date = null;
    				Timestamp timestamp = rs.getTimestamp("timestamp");
    				long value = rs.getInt("avg");
    				if (timestamp != null)
    				    date = new java.util.Date(timestamp.getTime());
    				Calendar cal = Calendar.getInstance();
    				cal.setTime(date);
    				Average avg = new Average(cal, value);
    				dayAverages.add(avg);
    			}
    		} catch (SQLException e) {
    			System.out.println(e.getMessage());
    		} finally {
     
    			if (statement != null) {
    				statement.close();
    			}
    			if (dbConnection != null) {
    				dbConnection.close();
    			}
    		}
    		return dayAverages;			
		}		
		
		
		/**
		 * Close connection
		 * 
		 * @throws SQLException
		 */
		public void closeConnection() throws SQLException {
			if (dbConnection != null) {
				dbConnection.close();
			}
		}


}