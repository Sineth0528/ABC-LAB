<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.time.LocalDate" %>

<%
LocalDate today = LocalDate.now();
LocalDate tomorrow = today.plusDays(1);
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Appointment</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="js/main.js"></script>
</head>

<body>

<jsp:include page="navigation.jsp" />

    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-lg-6">
                <div class="myappointment-form">
                    <h3>Request For Appointment</h3>
                    <br>
                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger" role="alert">
                            ${errorMessage}
                        </div>
                    </c:if>
                    <form action="RequestAppointment" method="POST">

                        <div class="form-group">
                            <select class="form-control" required name="type" id="type" onchange="clearinputs()">
                                <option value="" selected disabled>Select Type</option>
                                <c:forEach items="${dropDownData}" var="option">
                                    <option value="${option.id}">${option.label}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <br>
						<div class="form-group">
						    <label for="date">Select Date</label>
						    <input type="date" class="form-control" id="date" name="date" placeholder="Select Date" onchange="getType()" required
						        min=<%=tomorrow %>
						    >
						</div>




                        <div class="form-group">
                            <label for="Payment">Payment (Rs.)</label>
                            <input type="text" class="form-control" id="Payment" name="Payment" readonly>
                        </div>

                        <input type="hidden" name="request" value="request"/>
                        <button type="submit" class="btn btn-primary btn-block">Request For Appointment</button>

                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>

</html>
