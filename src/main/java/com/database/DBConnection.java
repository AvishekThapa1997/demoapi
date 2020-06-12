package com.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	public static Connection getConnection() {
			Connection dbConnection=null;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/heruko","root","root");
			} catch (Exception e) {
				e.printStackTrace();
			}
		return dbConnection;
	}
}
