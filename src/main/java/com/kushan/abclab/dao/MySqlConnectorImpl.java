package com.kushan.abclab.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnectorImpl implements DbConnector {

	
	public Connection getDbConnection() throws ClassNotFoundException, SQLException {
		
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/abc_lab";
        String username = "root";
        String password = "";
		
		Connection connection = DriverManager.getConnection(url, username, password);
		return connection;
	}
	
	public boolean isMySqlDatabase() {
        return true; // Since this is a MySQL connector, return true
    }

	
}
