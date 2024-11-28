<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    .icon-input {
        position: relative;
    }
    
    .icon-input input[type="text"] {
        text-align: start; /* Adjust this value based on the icon size */
        border: none; /* Remove border */
        border-radius: 0; /* Ensure no rounded corners */
        background-color: transparent; /* Make background transparent */
        outline: none; /* Remove outline */
        border-bottom: 1px solid #000; /* Add bottom border */
        box-shadow: none;
        margin-left: 28px;
        font-size: 10px
        
    }
    .icon-input input[type="file"] {
        text-align: start; /* Adjust this value based on the icon size */
        border: none; /* Remove border */
        border-radius: 0; /* Ensure no rounded corners */
        background-color: transparent; /* Make background transparent */
        outline: none; /* Remove outline */
        border-bottom: 1px solid #000; /* Add bottom border */
        box-shadow: none;
        margin-left: 28px;
        font-size: 10px
        
    }
    
    .icon-input i {
        position: absolute;
        left: 10px; /* Adjust this value to change the distance from the left edge */
        top: 50%;
        transform: translateY(-50%);
        z-index: 1;
    }
    .card
    {
    	box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
  	     padding: 30px; 
  		padding-right: 50px;
  		
  		
    	
    }
    .form-group
    {
    	margin-top: 20px; 
    }
    .btn1
    {
    	background-color: rgba(8, 78, 99, 0.726);
    	color: white;
    	border: none;
    	padding-left:20px;
    	padding-right: 20px;
    	padding-top: 5px;
    	padding-bottom: 5px;
    	font-weight: 100;
    	
    }
    
    
    
</style>
</head>
<body>
    <jsp:include page="profile-navbar.jsp"/>
    <jsp:include page="userMangamentSidebar.jsp"/>
    
    <div class="container ">
        <div class="container-fluid cont4">
        	<c:if test="${not empty model_success }">
        		<div class="row">
					<div class="alert alert-success alert-dismissible fade show" role="alert">
						${model_success}
						<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
					</div>
				</div>
        	
        	</c:if>
        	<c:if test="${not empty model_error }">
        		<div class="row">
					<div class="alert alert-success alert-dismissible fade show" role="alert">
						${model_error}
						<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
					</div>
				</div>
        	
        	</c:if>
            <div class="row ">
            	
                <div class="col-md-6 mx-auto">
                   <div class="card mt-3">
           
                    <form:form action="addContactDetails" method="post" enctype="multipart/form-data" modelAttribute="contact">
                    <h1 class="text-center mt-1">Add Contact</h1>
                        <div class="form-group ">
                            <div class="icon-input">
                                <i class="fa-solid fa-plus"></i>
                            	<form:input path="name" cssClass="form-control" placeholder="Enter Contact Name"/>
                            </div>
                        </div>
                
                        <div class="form-group">
                            <div class="icon-input">
                                <i class="fa-solid fa-user"></i>
                                <form:input path="nickName" cssClass="form-control" placeholder="Enter Nick Name"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="icon-input">
                                <i class="fa-solid fa-phone"></i>
                                <form:input path="phoneno" cssClass="form-control" placeholder="Enter Contact Number"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="icon-input">
                                <i class="fa-solid fa-envelope"></i>
                                <form:input path="email" cssClass="form-control" placeholder="Enter Email Address"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="icon-input">
								<i class="fa-solid fa-briefcase"></i>
                                <form:input path="experience" cssClass="form-control" placeholder="Enter Work Experience"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="icon-input">
                                <i class="fa-solid fa-upload"></i>
                                <form:input type="file" path="contactImage" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group text-center mt-4">
                            <button type="submit" class=" btn1">Add Contact</button>
                        </div>
                    </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>



