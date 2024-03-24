package com.kushan.abclab.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kushan.abclab.model.UserRegisterModel;
import com.kushan.abclab.service.RegisterEmailService;
import com.kushan.abclab.service.UserRegisterSer;


public class UserRegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstName = request.getParameter("firstname");
	    String lastName = request.getParameter("lastname");
	    String birthday = request.getParameter("birthday");
	    String phoneNumber = request.getParameter("phonenumber");
	    String email = request.getParameter("email");
	    String gender = request.getParameter("gender");
	    String password = request.getParameter("password");
	    

		UserRegisterModel userregistermodel = new UserRegisterModel(firstName, lastName, birthday, phoneNumber, email,  gender,  password);
		UserRegisterSer userRegisterSer = new UserRegisterSer();
		
		boolean success = false;
		try {
			
			success = userRegisterSer.insertNewUser(userregistermodel);
			
		} catch(ClassNotFoundException e){
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 if (success) {
			 
			 String uid = userregistermodel.getUserid();
			 String messageBody = "Thank you for registering with ABC Laboratories!\n\n";
			 messageBody += "Your username: " + uid + "\n";
			 messageBody += "Your password: " + password + "\n";
			 messageBody += "We appreciate your interest in our services and look forward to serving you.\n";
			 String subject = "Registration Successful";
			 
			 RegisterEmailService RegisterEmailService = new RegisterEmailService();
			 RegisterEmailService.sendRegistrationEmail(email,  messageBody, subject);
			 
	         response.sendRedirect("login.jsp"); // Redirect to index.jsp after successful insertion
	        } else {
	            // Handle failure
	            response.getWriter().println("Failed to add.");
	        }
       
	}

}
