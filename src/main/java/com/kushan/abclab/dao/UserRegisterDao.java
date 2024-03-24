package com.kushan.abclab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.mindrot.jbcrypt.BCrypt;

import com.kushan.abclab.model.UserRegisterModel;

public class UserRegisterDao {
	
	
	private static DbConnector getDbConnector() {
        DbConnectorFactory factory = new DbConnectorFactoryImpl();
        return factory.getDbConnector("mysql");
    }
	
	private Connection getConnection() throws ClassNotFoundException, SQLException {
		DbConnector connector =  getDbConnector();
		return connector.getDbConnection();
	}

    public boolean insertUserDetails(UserRegisterModel Userregistermodel) throws ClassNotFoundException, SQLException{
    	
    	Connection connection = getConnection();
    	
        try (connection) {
            String query = "INSERT INTO `user`(`user_id`, `first_name`, `last_name`, `birthday`, `phone_number`, `email`, `gender`,  `password`) VALUES (?,?,?,?,?,?,?,?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
            	
            	Statement st = connection.createStatement();
            	String userIdQuery = "SELECT COALESCE(MAX(id) + 1, 1) AS id FROM user";
            	ResultSet resultId = st.executeQuery(userIdQuery);
            	
            	if (resultId.next()) {
                    Userregistermodel.setUserid(resultId.getInt("id"));
                }
            		
                statement.setString(1, Userregistermodel.getUserid());
                statement.setString(2, Userregistermodel.getFirstName());
                statement.setString(3, Userregistermodel.getLastName());
                statement.setString(4, Userregistermodel.getBirthDay());
                statement.setString(5, Userregistermodel.getPhoneNumber());
                statement.setString(6, Userregistermodel.getEmail());
                statement.setString(7, Userregistermodel.getGender());
                
                String hashedPassword = BCrypt.hashpw(Userregistermodel.getPassword(), BCrypt.gensalt());
                statement.setString(8, hashedPassword);
                
                int rowsInserted = statement.executeUpdate();
                
                statement.close();  
                connection.close();
                
                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions
            e.printStackTrace();
            return false;
        }
    }
    
}
