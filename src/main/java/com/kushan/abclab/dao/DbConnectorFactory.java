package com.kushan.abclab.dao;

public interface DbConnectorFactory {

	DbConnector getDbConnector(String dbType);
}
