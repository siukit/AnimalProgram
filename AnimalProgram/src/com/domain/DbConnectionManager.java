package com.domain;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbConnectionManager {
	private static String url = "jdbc:mysql://localhost:3306/animal_schema";    
//    private static String driverName = "com.mysql.jdbc.Driver";   
    private static String username = "root";   
    private static String password = "pw";
    private static Connection connection;
//    private static String urlstring;

    public static Connection getConnection() {
    	try {
    		connection = DriverManager.getConnection(url, username, password);
    	    System.out.println("Database connected!");
    	    
    	    
    	} catch (SQLException e) {
    	    throw new IllegalStateException("Cannot connect the database!", e);
    	}
        return connection;
    }
}
