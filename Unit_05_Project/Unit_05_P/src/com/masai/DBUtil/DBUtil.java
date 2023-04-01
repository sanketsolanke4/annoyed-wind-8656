package com.masai.DBUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	public static Connection provideConnection() {
		Connection connect = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String url = "jdbc:mysql://localhost:3306/OBMS";
		try{
			connect = DriverManager.getConnection(url, "root", "Sus@0213");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connect;
	}
}