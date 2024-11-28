<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" >
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://kit.fontawesome.com/2a29ec13df.js" crossorigin="anonymous"></script>
    <style>
		.cont2
		{
			text-align: center;
			margin-top: 20px;
		
		}
		button 
		{
			 background-color: white; 
			 border-style: solid;
			 border-color:rgba(8, 78, 99, 0.726);
			 color: rgba(8, 78, 99, 0.726);
			 padding: 16px 32px;
			 text-decoration: none;
			 font-weight: bold;
		}
    </style>
</head>
<body>
	<jsp:include page="profile-navbar.jsp"/>
	<jsp:include page="userMangamentSidebar.jsp"/>
      
       <div class="container cont2">
        <div class="conatiner-fluid">
            <div class="row">
                <div class="col">
                   <h2>Start Adding your contact</h2>
                   <a href="addContact"><button>ADD NEW CONTACT</button></a>
                </div>
            </div>
        </div>
      </div>
</body>
</html>