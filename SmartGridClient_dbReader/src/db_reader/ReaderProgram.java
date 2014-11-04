package db_reader;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

import connection.MySQLReader;
import model.*;

public class ReaderProgram extends JPanel implements ActionListener{
	
	private static MySQLReader con;
	private JButton btnMeterHour, btnMeterDay, btnTotalHour, btnTotalDay;
	
	public ReaderProgram(){

		btnMeterHour = new JButton("btnMeterHour"); 
		btnMeterHour.addActionListener(this);
		add(btnMeterHour);

		btnMeterDay = new JButton("btnMeterDay"); 
		btnMeterDay.addActionListener(this);
		add(btnMeterDay);

		btnTotalHour = new JButton("btnTotalHour"); 
		btnTotalHour.addActionListener(this);
		add(btnTotalHour);

		btnTotalDay = new JButton("btnTotalDay"); 
		btnTotalDay.addActionListener(this);
		add(btnTotalDay);

	}
	
	public static void main(String[] args) {
		//Run database
    	con = new MySQLReader();
    	printDBinfo();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
			        JFrame frame = new JFrame("ReaderPanel");
			        frame.getContentPane().add(new ReaderProgram());
			        frame.pack();
			        frame.setVisible(true); 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void printDBinfo() {
		try {
			System.out.println("Number of meters: "+con.getNrOfMeters());
			ArrayList<String> meterIDs = con.getMeterIDs();
			for (String id : meterIDs) {
				System.out.println(id);
			}
			System.out.println();
			
			con.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == btnMeterHour) {
				ArrayList<Average> meterAverageHour;
				meterAverageHour = con.getMeterAverageHour("18b80a6173204cda987136b527fdec6e");
				System.out.println("\n********** Meter hour average **************");
				for (Average average : meterAverageHour) {
					System.out.println(average.toString());
				}
			}
			else if (e.getSource() == btnMeterDay) {
				ArrayList<Average> meterAverageDay = con.getMeterAverageDay("18b80a6173204cda987136b527fdec6e");
				System.out.println("\n********** Meter day average **************");
				for (Average average : meterAverageDay) {
					System.out.println(average.toString());
				}
			}
			else if (e.getSource() == btnTotalHour) {
				ArrayList<Average> totalAverageHour = con.getTotalAverageHour();
				System.out.println("\n********** Total hour average **************");
				for (Average average : totalAverageHour) {
					System.out.println(average.toString());
				}
			}
			else if (e.getSource() == btnTotalDay) {
				ArrayList<Average> totalAverageDay = con.getTotalAverageDay();
				System.out.println("\n********** Total day average **************");
				for (Average average : totalAverageDay) {
					System.out.println(average.toString());
				}
			}
			con.closeConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
