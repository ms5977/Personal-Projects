<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
    <style>
.card {
    box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
    transition: 0.3s;
}
.userImage {
    width: 220px;
    border-radius: 100%;
    height: 200px;
    margin-left: 87px;
}
.card-body {
    padding: 20px; /* Add padding for better spacing */
}
.label {
    font-weight: bold;
}
.details {
    justify-self: end; /* Align details to the start */
}
.grid-container {
    display: grid; /* Use grid layout */
    grid-template-columns: auto 1fr; /* Auto for label, 1fr for details */
    grid-gap: 10px; /* Adjust the gap between columns */
}

    </style>
</head>
<body>
	<jsp:include page="profile-navbar.jsp"/>
	<jsp:include page="userMangamentSidebar.jsp"/>
      
<div class="container cont3">
    <div class="container-fluid">
        <div class="row">
            <div class="col">
                <h1 class="text-center mt-1">Contact User Profile</h1>
                <div class="row justify-content-center mt-3">
                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-body">
                                <img class="userImage mb-2" alt="User Image" src="${contactDetailsOfSavedUser.getContactImage()}">
                                <hr>
                                <div class="grid-container">
                                    <div class="label">Contact ID</div>
                                    <div class="details">${contactDetailsOfSavedUser.getId()}</div>
                                </div>
                                <hr>
                                <div class="grid-container">
                                    <div class="label">Name</div>
                                    <div class="details">${contactDetailsOfSavedUser.getName()}</div>
                                </div>
                                <hr>
                                <div class="grid-container">
                                    <div class="label">NickName</div>
                                    <div class="details">${contactDetailsOfSavedUser.getNickName()}</div>
                                </div>
                                <hr>
                                <div class="grid-container">
                                    <div class="label">PhoneNo</div>
                                    <div class="details">${contactDetailsOfSavedUser.getPhoneno()}</div>
                                </div>
                                <hr>
                                <div class="grid-container">
                                    <div class="label">Email</div>
                                    <div class="details">${contactDetailsOfSavedUser.getEmail()}</div>
                                </div>
                                <hr>
                                <div class="grid-container">
                                    <div class="label">Experience</div>
                                    <div class="details">${contactDetailsOfSavedUser.getExperience()}</div>
                                </div>
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