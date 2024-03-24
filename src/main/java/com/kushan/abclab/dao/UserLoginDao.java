package com.kushan.abclab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import com.kushan.abclab.model.UserLoginModel;

public class UserLoginDao {
    
	private static DbConnector getDbConnector() {
        DbConnectorFactory factory = new DbConnectorFactoryImpl();
        return factory.getDbConnector("mysql");
    }
    
    private Connection getConnection() throws ClassNotFoundException, SQLException {
        DbConnector connector = getDbConnector();
        return connector.getDbConnection();
    }
    
    public boolean checkUserLogin(UserLoginModel userLoginModel, HttpServletRequest request) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            String userCheckQuery = "SELECT * FROM user WHERE user_id = ?";
            statement = connection.prepareStatement(userCheckQuery);
            statement.setString(1, userLoginModel.getUserName());
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("password");
                String providedPassword = userLoginModel.getPassword();

                if (BCrypt.checkpw(providedPassword, hashedPassword)) {
                    return true; 
                }
            } else { // Check admin table
                String adminCheckQuery = "SELECT * FROM admin WHERE uid = ?";
                statement = connection.prepareStatement(adminCheckQuery);
                statement.setString(1, userLoginModel.getUserName());
                resultSet = statement.executeQuery();

                
                if (resultSet.next()) {
                    String hashedPassword = resultSet.getString("password");
                    String providedPassword = userLoginModel.getPassword();
                    
                    if (providedPassword.equals(hashedPassword)) {
                        HttpSession session = request.getSession();
                        session.setAttribute("userid", resultSet.getString("uid"));
                        session.setAttribute("admin", true);
                        return true;
                    }
                }
            }
            
            statement.close();  
            connection.close();
            
            return false; // No matching user or admin or incorrect password
        } catch (SQLException e) {
            // Handle any SQL exceptions more gracefully
            e.printStackTrace(); // Consider logging instead
            return false;
        } finally {
            // Close resources in finally block
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace(); // Consider logging instead
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace(); // Consider logging instead
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace(); // Consider logging instead
                }
            }
        }
    }
}
