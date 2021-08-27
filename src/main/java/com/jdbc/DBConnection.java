package com.jdbc;


import java.sql.*;
import java.util.*;

public class DBConnection {
	
	public static Connection getConnection() {
		try {
		
            Class.forName("com.mysql.jdbc.Driver");
        } 
        catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found !!");
        }
        System.out.println("MySQL JDBC Driver Registered!");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdb", "root", "root");
            System.out.println("SQL Connection to database established!");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
        } 
        return connection;
	}
}
