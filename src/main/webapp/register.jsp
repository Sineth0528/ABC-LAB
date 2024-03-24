<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.time.LocalDate" %>
    
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ABC laboratory Register</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
</head>

<body>
    <nav class="navbar navbar-expand-md navbar-dark fixed-top" style="background-color: #007bff; max-height: 60px;">

        <div class="navbar-nav mx-auto">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link mx-auto active" href="login.jsp">Login</a>
                </li>

            </ul>
        </div>

    </nav>
    <div class="reg-form">
        <h2 style="text-align: center;"><img src=" images/logo.png" alt="" width="300"></h2>
        <h3 style="text-align: center; color:#0062E6;">Register</h3>
        <form id="registerForm" action="UserRegisterController" method="POST">

            <div class="form-group">
                <input type="text" class="form-control" id="firstname" name="firstname" placeholder="First Name"
                    required>
            </div>

            <div class="form-group">
                <input type="text" class="form-control" id="lastname" name="lastname" placeholder="Last Name" required>
            </div>
            
            <%
            LocalDate today = LocalDate.now();
			%>

            <div class="form-group">
                <input type="date" class="form-control" id="birthday" max=<%=today %> name="birthday" placeholder="Birthday" required>
            </div>
             

            <div class="form-group">
                <input type="text" class="form-control" id="phonenumber" name="phonenumber"  placeholder="Mobile Number (07XXXXXXXX)"
                    pattern="^0\d{9}$" required>
            </div>

            <div class="form-group">
                <input type="text" class="form-control" id="email" name="email" placeholder="Email" required>
            </div>

            <div class="form-group">
                <select id="gender" class="form-control" name="gender" required>
                    <option value="" selected disabled>Select Gender</option>
                    <option value="male">Male</option>
                    <option value="female">Female</option>
                    <option value="other">Other</option>
                </select>
            </div>



            <div class="form-group">
                <input type="password" class="form-control" id="password" name="password" placeholder="Password"
                    required>
            </div>

            <div class="form-group">
                <input type="password" class="form-control" id="reenterpass" name="reenterpass"
                    placeholder="Re Enter Password" required>
                <span id="passwordError" class="error" style="display: none; color: red;">Passwords do not match</span>
            </div>

            <button type="submit" class="btn btn-primary btn-block">Register</button>
        </form>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="js/main.js"></script>
</body>

</html>