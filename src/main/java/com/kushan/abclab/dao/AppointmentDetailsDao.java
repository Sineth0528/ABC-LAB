package com.kushan.abclab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kushan.abclab.model.AppointmentDetailsModel;

public class AppointmentDetailsDao {

    private static DbConnector getDbConnector() {
        DbConnectorFactory factory = new DbConnectorFactoryImpl();
        return factory.getDbConnector("mysql");
    }

    public static List<AppointmentDetailsModel> getAppointmentUser(HttpServletRequest request) throws ClassNotFoundException, SQLException {
        String user_app_details_query = "SELECT a.userappo_id, b.name, a.date, a.time, a.`status`, b.charge FROM user_appoint a INNER JOIN appoint_type b ON a.appointment_id = b.id WHERE a.user_id = ? ORDER BY a.date DESC";
        List<AppointmentDetailsModel> typeList = new ArrayList<>();
        
        try (Connection connection = getDbConnector().getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(user_app_details_query)) {

            HttpSession session = request.getSession();
            if (session == null) {
                // Handle null session
                return typeList;
            }

            String userid = ((String) session.getAttribute("userid")).toUpperCase();
            preparedStatement.setString(1, userid); // Set status parameter
            
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    AppointmentDetailsModel appdetails = new AppointmentDetailsModel();

                    appdetails.setUserappo_id(rs.getString("userappo_id"));
                    appdetails.setAppo_name(rs.getString("name"));
                    appdetails.setDate(rs.getString("date"));
                    appdetails.setTime(rs.getString("time"));
                    appdetails.setStatus(rs.getInt("status"));
                    appdetails.setPrice(rs.getString("charge"));
                    appdetails.setUserId(userid);
                    typeList.add(appdetails);
                }
            }
            
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            // Handle or log the exception
            throw new SQLException("Error", e);
        }
        
        return typeList;
    }
    
    
    public static List<AppointmentDetailsModel> getAppointmentAll(HttpServletRequest request) throws ClassNotFoundException, SQLException {
        String user_app_details_query = "SELECT a.user_id, a.userappo_id, b.name, a.date, a.time, a.`status`, b.charge FROM user_appoint a INNER JOIN appoint_type b ON a.appointment_id = b.id ORDER BY a.date DESC";
        List<AppointmentDetailsModel> typeList = new ArrayList<>();
        
        try (Connection connection = getDbConnector().getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(user_app_details_query)) {
 
            
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    AppointmentDetailsModel appdetails = new AppointmentDetailsModel();

                    appdetails.setUserappo_id(rs.getString("userappo_id"));
                    appdetails.setAppo_name(rs.getString("name"));
                    appdetails.setDate(rs.getString("date"));
                    appdetails.setTime(rs.getString("time"));
                    appdetails.setStatus(rs.getInt("status"));
                    appdetails.setPrice(rs.getString("charge"));
                    appdetails.setUserId(rs.getString("user_id"));
                    typeList.add(appdetails);
                }
            }
            
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            // Handle or log the exception
            throw new SQLException("Error", e);
        }
            
        return typeList;
    }
    
    
    public static boolean setStatusDao(String userappo_id, int status, String userId, HttpServletRequest request) throws ClassNotFoundException, SQLException {
        String updateStatusQuery;

        if (status == 4) {
            updateStatusQuery = "UPDATE user_appoint SET status = ? WHERE userappo_id = ? AND user_id = ? AND status <> '2'";
        } else if (status == 2) {
            updateStatusQuery = "UPDATE user_appoint SET status = ? WHERE userappo_id = ? AND user_id = ? AND status <> '4' ";
        } else {
            updateStatusQuery = "UPDATE user_appoint SET status = ? WHERE userappo_id = ? AND user_id = ? ";
        }

        try (Connection connection = getDbConnector().getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateStatusQuery)) {

            preparedStatement.setInt(1, status);
            preparedStatement.setString(2, userappo_id);
            preparedStatement.setString(3, userId);

            int rowsUpdated = preparedStatement.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {
            // Handle or log the exception
            throw new SQLException("Error updating appointment status", e);
        }
    }

}
