package com.kushan.abclab.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectorFactoryImpl implements DbConnectorFactory {

    @Override
    public DbConnector getDbConnector(String dbType) {
        if (dbType.equalsIgnoreCase("mysql")) {
            return new MySqlConnectorImpl();
        } else {
            throw new IllegalArgumentException("Unsupported database type: " + dbType);
        }
    }
}