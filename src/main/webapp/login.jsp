<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ABC laboratory Login</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
</head>

<body>
    <div class="login-form">
        <h2><img src="images/logo.png" alt="" width="300"></h2>
        <form action="UserLoginController" method="POST">
        <%
		    String errorMessage = (String) request.getAttribute("errorMessage");
		    if (errorMessage != null) {
		%>
		    <div class="alert alert-danger" role="alert">
		        <%= errorMessage %>
		    </div>
		<%
		    }
		%>
            <div class="form-group">
                <input type="text" class="form-control" id="username" name="username" placeholder="User Name" required>
            </div>
            <div class="form-group">
                <input type="password" class="form-control" id="password" name="password" placeholder="Password"
                    required>
            </div>
            <button type="submit" class="btn btn-primary btn-block">Login</button>
            <br>
            <div class="form-group" style="text-align: right;">
                Not Register Yet?<a href="register.jsp" style="font-weight: 800;"> Register Here</a>
            </div>
        </form>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="js/main.js"></script>
</body>

</html>