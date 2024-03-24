package com.kushan.abclab.dao;

public class MySqlDbConnectorFactoryImpl implements DbConnectorFactory{

	@Override
	public DbConnector getDbConnector(String dbType) {
		return new MySqlConnectorImpl();
	}

	
}
