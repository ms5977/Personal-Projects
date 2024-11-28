<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<style >
.card
{
	box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
    transition: 0.3s;
   	
}
.card form {
    padding: 0 30px; /* Adjust left and right padding */
}
label
{
	color: black;
	font-weight: bold;
}

</style>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" >
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="css/style.css" type="text/css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
 <div class="container-fluid mt-0">
 <div class="container" id="container-2" >
 	<c:if test="${not empty model_success}">
				<div class="row">
					<div class="alert alert-success alert-dismissible fade show" role="alert">
						${model_success}
						<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
					</div>
				</div>
	</c:if>
 	
 	
 	<c:if test="${not empty model_error}">
				<div class="row">
					<div class="alert alert-danger alert-dismissible fade show" role="alert">
						${model_error}
						<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
					</div>
				</div>
			</c:if>
 
 
     <div class="row " >
     	<div class="col-3"></div>
          <div class="col-6 d-flex justify-content-center text-center"> 
          	<div class="addUser_div_design">
          		
          		<br>
          		<div class="card">
          		<h1>REGISTER</h1>
		      <form:form action="regForm" method="post" modelAttribute="user" enctype="multipart/form-data" > <%-- enctype="multipart/form-data" --%>
				 	  <div class="mb-3">
					    <label  class="form-label">Name</label>
					    <form:input path="name" cssClass="form-control"/>
					    <form:errors path="name" cssClass="error_message_design"/>
					  </div>
				 	  <div class="mb-3">
					    <label  class="form-label">Phone No</label>
					    <form:input path="phoneno" cssClass="form-control"/>
					    <form:errors path="phoneno" cssClass="error_message_design"/>
					  </div>
				 	  <div class="mb-3">
					    <label  class="form-label">Email</label>
					    <form:input path="email"  cssClass="form-control"/>
					    <form:errors path="email" cssClass="error_message_design"/>
					  </div>
				 	  <div class="mb-3">
					    <label  class="form-label">Gender</label>
					    <form:input path="gender" cssClass="form-control"/>
					    <form:errors path="gender" cssClass="error_message_design"/>
					  </div>
				 	  <div class="mb-3">
					    <label  class="form-label">Password</label>
					    <form:input path="password" type="password" cssClass="form-control"/>
					    <form:errors path="password" cssClass="error_message_design"/>
					  </div>
				 	  <div class="mb-3">
					    <label  class="form-label">City</label>
					    <form:input path="city" cssClass="form-control"/>
					    <form:errors path="city" cssClass="error_message_design"/>
					  </div>
				 	    <div class="mb-3">
					    <label  class="form-label">Image</label>
					    <form:input type="file" path="userImage" cssClass="form-control"/>
					  </div>
					  <button type="submit" class="btn btn-primary">Submit</button>
				</form:form>
				</div>
			</div>
          </div>
          <div class="col-3"></div>
     </div>
</div>
</div>
</body>
</html>