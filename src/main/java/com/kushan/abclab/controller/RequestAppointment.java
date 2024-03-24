package com.kushan.abclab.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kushan.abclab.service.RequestAppService;
import com.kushan.abclab.model.AppointmentInfo;
import com.kushan.abclab.model.ReqAppModel;
import com.kushan.abclab.service.RegisterEmailService;

import java.util.List;

public class RequestAppointment extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private RequestAppService reqAppService;

    public RequestAppointment() {
        this.reqAppService = RequestAppService.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	String error = request.getParameter("error");
        
        if (error != null) {
            request.setAttribute("errorMessage", error);
        }
        	
        String aptype = request.getParameter("aptype");
        String selectedDate = request.getParameter("date");
        
        if((aptype != null) && (selectedDate != null)) {
            try {
                AppointmentInfo appinfo =  reqAppService.getAvilableDateSer(aptype, selectedDate);
                String maxdate = appinfo.getDate();
                String price = appinfo.getPrice();
                
                
                LocalDate today = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                if (maxdate != null && !maxdate.equals("0")) {
                    if (LocalDate.parse(maxdate, formatter).isBefore(today)) {
                        maxdate = today.plusDays(1).format(formatter);
                    }
                }
                
                //System.out.println(maxdate);
                
                String responseString = maxdate + "," + price;
                response.getWriter().write(responseString); 
                return; 
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }   
        }
        
        try {
            List<ReqAppModel> dropDownData = reqAppService.GetTypeName();
            request.setAttribute("dropDownData", dropDownData);
            request.getRequestDispatcher("request_appointment.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error occurred");
        }      
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	String formtype = request.getParameter("request");
    	
    	if(formtype != null && formtype.equals("request")) {
    		addReq(request, response);
    	}
    	 
    }
    
    private void addReq(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	int type = Integer.parseInt(request.getParameter("type"));
    	String date = request.getParameter("date");
    	Double payment = Double.parseDouble(request.getParameter("Payment"));
    	
    	ReqAppModel reqappmodel = new ReqAppModel(type, date, payment);
    	
    	boolean success = false;
    	try {
			success = reqAppService.insertReqApp(reqappmodel, request);
		} catch(ClassNotFoundException e){
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    		
    	if(success) {
    		
    		
    		String apid = reqappmodel.getAppid();
    		String time = reqappmodel.getTime();
    		String userEmail = reqappmodel.getEmail();
    		//String userEmail = "kushan.seanarathna@gmail.com";
    		String subject = "ABC LAB Appointment Details";
    		
    		String messageBody = "Thank you for scheduling an appointment with ABC Laboratories!\n\n";
    		messageBody += "Your appointment details are as follows:\n";
    		messageBody += "Appointment ID: " + apid + "\n";
    		messageBody += "Date: " + date + "\n";
    		messageBody += "Time: " + time + "\n";
    		messageBody += "Price: " + payment + "\n\n";
    		messageBody += "We appreciate your trust in our services and look forward to seeing you.\n";
			 
			RegisterEmailService RegisterEmailService = new RegisterEmailService();
			RegisterEmailService.sendRegistrationEmail(userEmail,  messageBody, subject);
    		
    		response.sendRedirect("AppointmentDetailsController");
    	}else {
    		String errorMessage = "Please select another day! You have an appointment on your selected date. Thank you!";
    	    response.sendRedirect("RequestAppointment?error=" + errorMessage);
    	}
    	
    	
    	
    	
    }
        
}
