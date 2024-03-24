package com.kushan.abclab.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.kushan.abclab.service.AppointmentDetailsService;

import javax.servlet.annotation.MultipartConfig;

@MultipartConfig
public class PdfUploadController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String UPLOAD_DIR; 
	
	private AppointmentDetailsService appointmentService;

   
	@Override
    public void init() throws ServletException {
        // Load configuration from properties file
		
		appointmentService = new AppointmentDetailsService();
		
        Properties prop = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            prop.load(input);
            UPLOAD_DIR = prop.getProperty("upload.dir");

            // Create the upload directory if it doesn't exist
            File uploadDir = new File(UPLOAD_DIR);

            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new ServletException("Error loading configuration", ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Process each part of the multi-part request
        for (Part part : request.getParts()) {
            // Get the file name
            String fileName = getFileName(part);

            // Check if the file name is not null and ends with ".pdf"
            if (fileName != null && fileName.endsWith(".pdf")) {
                // Write the file to the designated folder
                part.write(UPLOAD_DIR + File.separator + fileName);
                
                String userappoid = request.getParameter("userappoid");
                String status = request.getParameter("status");
                String userId = request.getParameter("userId");
                setStatus(request, response, userappoid, status, userId);
            }
        }

        // Redirect to index.jsp after file upload
        response.sendRedirect(request.getContextPath() + "/AppointmentDetailsController");
    }

    // Method to extract the file name from the content disposition header
    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return null;
    }
    
    private boolean setStatus(HttpServletRequest request, HttpServletResponse response, String id, String status, String userId) throws ServletException, IOException {
        try {
            int appointmentStatus = Integer.parseInt(status);
            
            boolean statusSet = appointmentService.setStatusSer(id, appointmentStatus, userId, request);
            
            return statusSet;
        } catch (NumberFormatException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}


