package com.kushan.abclab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;

import com.kushan.abclab.model.AppointmentInfo;
import com.kushan.abclab.model.ReqAppModel;

public class ReqAppDao {
	
	private static DbConnector getDbConnector() {
        DbConnectorFactory factory = new DbConnectorFactoryImpl();
        return factory.getDbConnector("mysql");
    }
	
	
	//for insert
	public static double timeToHours(String time) {
	    String[] parts = time.split(":");
	    int hours = Integer.parseInt(parts[0]);
	    int minutes = Integer.parseInt(parts[1]);
	    return hours + (minutes / 60.0);
	}
    
    
    public List<ReqAppModel> getAllAppointName() throws SQLException, ClassNotFoundException {
    	
    	String SELECT_APPOINTMENT_TYPES = "SELECT * FROM appoint_type WHERE status = ?";
        List<ReqAppModel> typeList = new ArrayList<>();
        
        try (Connection connection = getDbConnector().getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_APPOINTMENT_TYPES)) {
            preparedStatement.setString(1, "1"); // Set status parameter
            
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    ReqAppModel typeapp = new ReqAppModel();
                    typeapp.setId(rs.getInt("id"));
                    typeapp.setLabel(rs.getString("name"));
                    typeList.add(typeapp);
                }
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            // Handle or log the exception
            throw new SQLException("Error fetching appointment types", e);
        }
        return typeList;
    }
    
    
    
    
    public AppointmentInfo CheckAvilabaleDate(String appointmentId, String selectedDate) throws SQLException, ClassNotFoundException {
        String date = null;
        String price = null;
 
        String query = "SELECT DISTINCT IF((SELECT COUNT(*) FROM user_appoint WHERE appointment_id = ? AND date = ? ) <= b.count_day, a.date , '0' ) AS maxdate FROM user_appoint a INNER JOIN appoint_type b ON a.appointment_id = b.id WHERE a.appointment_id = ? AND a.date = ? AND b.status = '1' AND a.status <> '4' ";
        
        AppointmentInfo appointmentInfo = null;
        try (Connection connection = getDbConnector().getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, appointmentId);
            preparedStatement.setString(2, selectedDate);
            preparedStatement.setString(3, appointmentId);
            preparedStatement.setString(4, selectedDate);
            
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    date = rs.getString("maxdate");
                }
            }
            

            String priceQuery = "SELECT charge FROM appoint_type WHERE id = ?";
            try (PreparedStatement preparedStatement1 = connection.prepareStatement(priceQuery)) {
                preparedStatement1.setString(1, appointmentId);
                try (ResultSet rs1 = preparedStatement1.executeQuery()) {
                    while (rs1.next()) {
                        price = rs1.getString("charge");
                    }
                }
                preparedStatement1.close();
            }
            
            appointmentInfo = new AppointmentInfo(date, price);
            
            preparedStatement.close();  
            connection.close();
            
        } catch (SQLException e) {
            // Handle or log the exception
            throw new SQLException("Error fetching appointment types", e);
        }
        return appointmentInfo;
    }
    
    
    public boolean insertNewReq(ReqAppModel reqAppModel, HttpServletRequest request) throws ClassNotFoundException, SQLException {
        
        HttpSession session = request.getSession();
        
        String CheckDoubleInput = "SELECT COUNT(*) AS count FROM user_appoint WHERE user_id = ? AND `date` = ? AND appointment_id = ?";
        
        try (Connection connection = getDbConnector().getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CheckDoubleInput)) {
            
            int id = reqAppModel.getId();
            String date = reqAppModel.getDate();
            String userid = ((String) session.getAttribute("userid")).toUpperCase();
            
            preparedStatement.setString(1, userid);
            preparedStatement.setString(2, date);
            preparedStatement.setInt(3, id);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                int rowCount = 0;
                while (resultSet.next()) {
                    rowCount = resultSet.getInt("count");
                } 
                           
                if (rowCount == 0) {
                	
                	String getTimeQuery = "SELECT b.duration, a.time, b.charge FROM user_appoint a INNER JOIN appoint_type b ON a.appointment_id = b.id WHERE a.appointment_id = ? AND a.date = ? ORDER BY a.time DESC LIMIT 1";
                    try (PreparedStatement preparedStatement1 = connection.prepareStatement(getTimeQuery)) {
                        preparedStatement1.setInt(1, id);
                        preparedStatement1.setString(2, date);
                        
                        try (ResultSet resultSet1 = preparedStatement1.executeQuery()) {
                        	
                        	double totalDurationHours = 0.0;
                        	String startTime = "08:00:00";
                        	String charge = "";
                        	String email = "";
                            
                            
                            while (resultSet1.next()) {
                            	int duration = resultSet1.getInt("duration");
                            	startTime = resultSet1.getString("time");
                            	charge = resultSet1.getString("charge");
                                totalDurationHours += duration / 60.0; // Add the duration to the total duration   
                            } 
                            
                            
                            double newTimeHours = totalDurationHours + timeToHours(startTime);

	                         // Convert the new time to hours and minutes
	                         int hours = (int) newTimeHours;
	                         int minutes = (int) ((newTimeHours - hours) * 60);
	
	                         // Format the new time
	                         String newTimeStr = String.format("%02d:%02d:00", hours, minutes);
	
	                         ZoneId zoneId = ZoneId.of("Asia/Colombo");
	                         LocalDateTime currentDateTime = LocalDateTime.now(zoneId);
	                         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmm");
	                         String formattedDateTime = currentDateTime.format(formatter);
	                         
	                         //getting email
	                         String emailQuery = "SELECT email FROM user WHERE user_id = ?";
	                         PreparedStatement preparedStatementEmail = connection.prepareStatement(emailQuery);
	                         preparedStatementEmail.setString(1, userid);
	                         ResultSet resultSetEmail = preparedStatementEmail.executeQuery();
	                         while (resultSetEmail.next()) {
	                             email = resultSetEmail.getString("email");
	                         }

	                         String userappo_id = userid  + formattedDateTime + id;
	                         reqAppModel.setAppid(userappo_id);
	                         reqAppModel.setAppid(userappo_id);
	                         reqAppModel.setTime(newTimeStr);
	                         reqAppModel.setEmail(email);
                            
	                         
                             String insertQuery = "INSERT INTO `user_appoint`( `userappo_id`, `appointment_id`, `user_id`, `date`, `time`) VALUES (?,?,?,?,?)";

                             try (PreparedStatement statement3 = connection.prepareStatement(insertQuery)) {
                                 statement3.setString(1, userappo_id);
                                 statement3.setInt(2, id);
                                 statement3.setString(3, userid);
                                 statement3.setString(4, date);
                                 statement3.setString(5, newTimeStr);
                                                 
                                 int rowsInserted = statement3.executeUpdate();
                                 if (rowsInserted > 0) {
                                    return true;
                                 }
                                 
                                 statement3.close();  
                                 
                             } catch (SQLException e) {
                                throw new SQLException("Error in Insert Query:", e);
                            }
                        } catch (SQLException e) {
                            throw new SQLException("Error in query getting time:", e);
                        } 
                        preparedStatement1.close();                          
                    } 
                } else {
                	return false;
                }
            } 
            
            preparedStatement.close();  
            connection.close();
            
        } catch (SQLException e) {
            throw new SQLException("Error in query check user appointment:", e);
        }
		return false;
    }
 
}

