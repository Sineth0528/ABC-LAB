    <%@ page import="javax.servlet.http.HttpSession" %>
    
    <%
		HttpSession sessionindex = request.getSession(false); // Pass false to prevent creating a new session if it doesn't exist
		
		if (sessionindex == null || session.getAttribute("userid") == null) {
		    response.sendRedirect("login.jsp");
		}
	%>
    
    <nav class="navbar navbar-expand-md navbar-dark fixed-top" style="background-color: #007bff; max-height: 60px;">

        <div class="navbar-brand">
            <img src="images/logowhite.png" alt="" width="200">
        </div>

        <% 
        if(session.getAttribute("admin") == null) {
        %>
        <div class="navbar-nav mx-auto">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link active" href="AppointmentDetailsController">My Appointment</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active"  href="RequestAppointment">New Appointment</a>
                </li>
            </ul>
        </div>
		<% } %>
        <!-- Logout link on the right -->
        <div class="navbar-nav ml-auto">
            <ul class="navbar-nav">
                <li class="nav-item">
                <form action="LogOutController" method="POST">
                	<button class="btn btn-danger" onclick='return Quit();' name="logout">Log Out</button>
                </form>   
                </li>
            </ul>
        </div>
</nav>