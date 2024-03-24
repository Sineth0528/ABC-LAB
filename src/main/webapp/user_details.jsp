<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
   
    <div class="detail-card">
        <div class="table-wrapper">
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th scope="col">Appointment ID</th>
                    <th scope="col">Appointment Type</th>
                    <th scope="col">Date</th>
                    <th scope="col">Time</th>
                    <th scope="col">Charge</th>       
                </tr>
            </thead>
            <tbody>
                <c:forEach var="appointment" items="${appointmentDetails}">
                    <tr>
                        <td>${appointment.userappo_id}</td>
                        <td>${appointment.appo_name}</td>
                        <td>${appointment.date}</td>
                        <td>${appointment.time}</td>
                        <td>${appointment.price}</td>
						<% 
						if(session.getAttribute("admin") == null) {
					    %>
					    <td style="text-align: Center;">
					        <c:choose>
							    <c:when test="${appointment.status eq 1}">
							        <label class="btn btn-warning">Pending Payment</label>
							    </c:when>
							    <c:when test="${appointment.status eq 2}">
							        <label class="btn btn-primary">Paid and Processing</label>
							    </c:when>
							    <c:when test="${appointment.status eq 3}">
							        <label class="btn btn-success">Completed</label>
							    </c:when>
							    <c:when test="${appointment.status eq 4}">
							        <label class="btn btn-danger">Canceled</label>
							    </c:when>
							</c:choose>
						</td>
						<td style="text-align: Center;">
					        <c:choose>
					            <c:when test="${appointment.status eq 1}">
					                <a type="button" class="btn btn-danger" onclick="updatebtn('${appointment.userappo_id}','4', '${appointment.userId}')">Cancel</a>
					            </c:when>
					            <c:when test="${appointment.status eq 3}">
					               <a type="button" class="btn btn-info" onclick="downloadPdf('${appointment.userappo_id}')">Download PDF</a>
					            </c:when>
					        </c:choose>
					   </td>
						<% }else { %>
						<td>
						    <c:choose>
						        <c:when test="${appointment.status eq 1}">
						            <a type="button" class="btn btn-warning" onclick="updatebtn('${appointment.userappo_id}','2', '${appointment.userId}')">Pending Payment</a>
						        </c:when>
						        <c:when test="${appointment.status eq 2}">
						            <div>
						                <form action="PdfUploadController" method="post"  enctype="multipart/form-data">
						                    <label class="btn btn-secondary btn-sm">
						                        <span>Choose PDF</span>
            									<input type="file" name="file" id="file" style="display: none;">
						                    </label>
						                    <label class="btn btn-primary btn-sm">
						                    <input type="submit" value="Upload PDF" name="submit" class="btn btn-primary btn-sm">
						                    </label>
						                    <input type="hidden" name="userappoid" value="${appointment.userappo_id}">
						                    <input type="hidden" name="status" value="3">
						                    <input type="hidden" name="userId" value="${appointment.userId}">
						                </form>
						            </div>
						        </c:when>
						        <c:when test="${appointment.status eq 3}">
						            <label class="btn btn-success">Complete</label>
						        </c:when>
						        <c:when test="${appointment.status eq 4}">
						            <label class="btn btn-danger">Canceled</label>
						        </c:when>
						    </c:choose>
						</td>
						<% } %>   
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        </div>
    </div>
    <!-- Bootstrap JS and dependencies -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
