package com.kushan.abclab.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadPdfController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String uploadDirectory;

    @Override
    public void init() throws ServletException {
        super.init();
        // Load the config.properties file to get the upload directory
        Properties prop = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            prop.load(input);
            uploadDirectory = prop.getProperty("upload.dir");
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new ServletException("Error loading config.properties file");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String appointmentId = request.getParameter("appointmentId");
        //System.out.println(appointmentId);
        
        // Construct the file path using the upload directory and appointmentId
        String pdfFilePath = uploadDirectory + "/" + appointmentId + ".pdf";
           
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + appointmentId + ".pdf");
        
        try (InputStream inputStream = new FileInputStream(pdfFilePath);
             OutputStream outputStream = response.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

