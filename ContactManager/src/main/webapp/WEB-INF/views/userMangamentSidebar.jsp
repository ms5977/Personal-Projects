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
        .cont1
        {
            position: absolute;
            top: 190px;
            left: 10px;
            
        }
        ul li
        {
           margin-top: 20px;
           list-style-type: none;
           text-decoration: none;
            
        }
        ul a
        {
          
           text-decoration: none;
           font-size: 13px;
           color: black;
           margin-left: 10px; 
        
            
        }
	     .col-4 {
		    
		    width: 50.333333%;
		}
		
		
    </style>
</head>
<body>
	<div class="container cont1">
        <div class="conatiner-fluid">
            <div class="row">
                <div class="col-4">
                    <ul>
                        <li><i class="fa-solid fa-user"></i><a  href="userProfile">Profile</a></li>
                        <li><i class="fa-solid fa-address-book"></i><a href="showContact">Show Contact</a></li>
                        <li><i class="fa-regular fa-address-book"></i><a href="addContact">Add Contact</a></li>
                        <li><i class="fa-solid fa-right-from-bracket"></i><a href="exit">Exit</a></li>
                    </ul>
                	
                </div>
            </div>
        </div>
      </div>
 