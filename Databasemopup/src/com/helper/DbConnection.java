package com.helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {
	
	static	Connection conn = null;
	public static Connection getConnection(){
		 Properties prop = new Properties();
		try {
			InputStream input = new 
					 FileInputStream("E:\\data1\\2019\\workspace\\April\\15\\servlet\\generate_excel\\apr2020\\workspace\\Databasemopup\\src\\db.properties");
			
		        prop.load(input);
		   //   url=prop.getProperty("url");
			Class.forName(prop.getProperty("driver"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		/*String url       = "jdbc:mysql://localhost:3306/demo";
		String user      = "root";
		String password  = "cdil";*/
		try {
			conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("user"),
					prop.getProperty("password"));
		} catch(SQLException e) {
		   System.out.println(e.getMessage());
		}
		return conn;
	}

}
