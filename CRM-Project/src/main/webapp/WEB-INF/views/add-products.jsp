<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Bootstrap Example</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>

<link rel="stylesheet" type="text/css" href="css/style.css" />
</head>
<body>
	<jsp:include page="header-admin.jsp" />

	<div class="container-fluid">
		<div class="container">
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
		
			<div class="row">
				<div class="col-3"></div>
				<div class="col-6 d-flex justify-content-center">
					<div class="addemp_div_design bg-light">
						<h3>Add Products</h3>
						<br />
						<form:form action="addCourseForm" method="post" modelAttribute="productAttr" enctype="multipart/form-data">
							<div class="mb-3">
								<label class="form-label">Course Name</label>
								 <form:input path="coursename" cssClass="form-control" />
								 <form:errors path="coursename" cssClass="error_message_design"/>
							</div>
							<div class="mb-3">
								<label class="form-label">Course Syllabus</label> 
								<form:textarea path="syallabus" cssClass="form-control" />
								 <form:errors path="syallabus" cssClass="error_message_design"/>
							</div>
							<div class="mb-3">
								<label class="form-label">Course Original Price</label> 
								<form:input path="originalprice" cssClass="form-control" />
								 <form:errors path="originalprice" cssClass="error_message_design"/>
							</div>
							<div class="mb-3">
								<label class="form-label">Course Discounted Price</label> 
								<form:input path="discountedprice" cssClass="form-control" />
								 <form:errors path="discountedprice" cssClass="error_message_design"/>
							</div>
							<div class="mb-3">
								<label class="form-label">Course Validity </label> 
								<form:input path="coursevalidity" cssClass="form-control" />
								 <form:errors path="coursevalidity" cssClass="error_message_design"/>
							</div>
							<div class="mb-3">
								<label class="form-label">Course Image</label> 
								<form:input type="file" path="courseimage" cssClass="form-control" />
								 <form:errors path="courseimage" cssClass="error_message_design"/>
							</div>
							<div class="mb-3">
								<label class="form-label">Trainer's Name</label> 
								<form:input path="trainersname" cssClass="form-control" />
								 <form:errors path="trainersname" cssClass="error_message_design"/>
							</div>
							<div class="mb-3">
								<label class="form-label">Trainer's Details</label> 
								<form:textarea path="trainersdetails" cssClass="form-control" />
								 <form:errors path="trainersdetails" cssClass="error_message_design"/>
							</div>
							<div class="mb-3">
								<label class="form-label">Trainer's Image</label> 
								<form:input type="file" path="trainersimage" cssClass="form-control" />
								 <form:errors path="trainersimage" cssClass="error_message_design"/>
							</div>
							<div class="mb-3">
								<label class="form-label">Other Details</label> 
								<form:textarea path="otherdetails" cssClass="form-control" />
								 <form:errors path="otherdetails" cssClass="error_message_design"/>
							</div>
							
							<input type="submit" value="Add Course" class="btn btn-primary" />
						</form:form>
					</div>
				</div>
				<div class="col-3"></div>
			</div>
		</div>
	</div>

</body>
</html>