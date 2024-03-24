package com.kushan.abclab.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;
import com.kushan.abclab.model.AppointmentDetailsModel;
import com.kushan.abclab.model.ReqAppModel;
import com.kushan.abclab.model.UserLoginModel;
import com.kushan.abclab.service.AppointmentDetailsService;
import com.kushan.abclab.service.RegisterEmailService;

public class AppointmentDetailsController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private AppointmentDetailsService appointmentService;

    public void init() {
        appointmentService = new AppointmentDetailsService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         
        try {
        		HttpSession session = request.getSession();
	        	 if (session.getAttribute("admin") != null && (Boolean) session.getAttribute("admin")) {
	        		 List<AppointmentDetailsModel> appointmentDetails = appointmentService.getAppointmentall(request);
	            	 request.setAttribute("appointmentDetails", appointmentDetails);
	             } else {
	            	 List<AppointmentDetailsModel> appointmentDetails = appointmentService.getAppointmentUser(request);
	            	 request.setAttribute("appointmentDetails", appointmentDetails);
	             }
        	
	        	 request.getRequestDispatcher("user_details.jsp").forward(request, response);
              
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching appointment details");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String status = request.getParameter("status");
        String userId = request.getParameter("userId");

        try {
            boolean statusSet = setStatus(request, response, id, status, userId);
            
            if (statusSet) {
                response.getWriter().write("1");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error setting appointment status");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error setting appointment status");
        }
    }
    
    private boolean setStatus(HttpServletRequest request, HttpServletResponse response, String id, String status, String userId) throws ServletException, IOException {
        try {
            int appointmentStatus = Integer.parseInt(status);
            
            boolean statusSet = appointmentService.setStatusSer(id, appointmentStatus, userId, request);
            
            /*
            if(statusSet && appointmentStatus == 4) {
        		//String userEmail = "kushan.seanarathna@gmail.com";
        		String subject = "ABC LAB Appointment Cancellation";
        		
        		String messageBody = "Dear " + userId + ",\n\n";
        		messageBody += "Your appointment with ABC Laboratories has been cancelled.\n\n";
        		messageBody += "Appointment ID: " + id + "\n\n";
        		messageBody += "We apologize for any inconvenience caused. For further assistance, please contact us.\n\n";
        		messageBody += "Thank you.\n\n";
        		messageBody += "Warm regards,\n";
        		messageBody += "ABC Laboratories";
    			
        		System.out.println(userEmail);
    			//RegisterEmailService RegisterEmailService = new RegisterEmailService();
    			//RegisterEmailService.sendRegistrationEmail(userEmail,  messageBody, subject);
            }*/
            
            
            return statusSet;
        } catch (NumberFormatException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
