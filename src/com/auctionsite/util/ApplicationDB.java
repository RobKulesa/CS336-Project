package com.auctionsite.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ApplicationDB {

	public Connection getConnection(){
		
		//Create a connection string
		String connectionUrl = "jdbc:mysql://localhost:3306/techauctionsitedb";
		Connection connection = null;
		
		try {
			//Load JDBC driver - the interface standardizing the connection procedure. Look at WEB-INF\lib for a mysql connector jar file, otherwise it fails.
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			//Create a connection to your
			String password = "pbE5whWP.3YmUiK";
			connection = DriverManager.getConnection(connectionUrl,"root", password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return connection;
		
	}
	
	public void closeConnection(Connection connection){
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		ApplicationDB db = new ApplicationDB();
		Connection connection = db.getConnection();
		System.out.println(connection);		
		db.closeConnection(connection);
	}
	
	

}
