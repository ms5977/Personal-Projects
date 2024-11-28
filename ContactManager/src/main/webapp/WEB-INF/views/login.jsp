<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<!DOCTYPE html>
<html>
<head>
<style>

.card
{
	box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
    transition: 0.3s;
    width: 300px;
    margin-left: 270px;
    margin-top: 30px;
   	
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
<link rel="stylesheet" href="css/style.css" type="text/css">
</head>
<body>
	<jsp:include page="navbar.jsp"/>
	
		<div class="container-fluid">
        
        <div class="container" >
            <div class="row">
             
                <div class="col" id="logDesign">
                <!--Java Code  -->
                <c:if test="${not empty model_error }">
                	<h5 style="color: red;">${model_error}</h5>
                	
                </c:if>
                <!--Java Code  -->
                   <div class="card"> 
                   	<h1 class="text-center mt-4">Login Form</h1>
	                    <form action="loginForm" method="post">
	                        
	                          <div class="form-group">
	                            <label for="exampleInputEmail1">Email</label>
	                            <input type="text" class="form-control" name="email1" placeholder="Enter email">
	                          </div>
	                          <div class="form-group mt-3">
	                            <label for="exampleInputPassword1">Password</label>
	                            <input type="password" name="pass1" class="form-control"  placeholder="Password">
	                          </div>
	                          <div class="text-center mt-2 p-4">
	                          	<button type="submit" class="btn btn-primary">Submit</button>
	                          </div>
	                    </form>
                    </div>
                </div>
            </div>
        </div>
    </div>


</body>
</html>