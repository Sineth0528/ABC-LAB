<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>

<%
HttpSession sessionindex = request.getSession(false); // Pass false to prevent creating a new session if it doesn't exist

if (sessionindex == null || session.getAttribute("userid") == null) {
    response.sendRedirect("login.jsp");
} else {
    String userid = (String) session.getAttribute("userid");
    response.getWriter().println("Welcome, " + userid);
}
%>
