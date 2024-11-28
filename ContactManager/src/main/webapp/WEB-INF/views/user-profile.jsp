<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
    <style>
		.card
		{
		 box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
  		 transition: 0.3s;
		}
		 
		.card-body p span
		{
		   margin-left: 200px; 
		
		}
		.userImage{
			width: 250px;
			border-radius: 100%;
			height: 230px;
		}
		
    </style>
</head>
<body>
	<jsp:include page="profile-navbar.jsp"/>
	<jsp:include page="userMangamentSidebar.jsp"/>
      
<div class="container cont3">
    <div class="conatiner-fluid">
        <div class="row">
            <div class="col">
                <h1 class="text-center mt-1">Welcome</h1>
                <h2 class="text-center">Smart Contact Manager</h2>
                <div class="row justify-content-center mt-3">
                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-body text-center">
                                <img class="userImage   mb-3" alt="" src="${session_profile.getUserImage()}">
                                <hr>
                                <p class="mb-1 text-start">ID      :   <span style="margin-left: 250px">${session_profile.getId()}</span></p> 
                                <hr>
                                <p class="mb-1 text-start">Name    :   <span>${session_profile.getName()}</span></p>
                                <hr>
                                <p class="mb-1 text-start">Email   :   <span>${session_profile.getEmail()}</span></p>
                                <hr>
                                <p class="mb-1 text-start">Gender  :   <span>${session_profile.getGender()}</span></p>
                                <hr>
                                <p class="mb-1 text-start">City    :   <span>${session_profile.getCity()}</span></p>
                                <hr>
                                <p class="mb-1 text-start">Phone No:   <span>${session_profile.getPhoneno()}</span></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
     
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
  </body>
  
</html>      