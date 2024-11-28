<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.card {
	box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
	margin-left: 40px;
}

button {
	margin-top: 5px;
	border: none;
	color: blue;
	background: none;
}
.page
{
	margin-top: -15px
}
</style>
<script >
	function deleteContact(id)
	{
		var confirmation = confirm("Are you sure you want to delete this contact?");
	    if (confirmation) {
	        // Execute deletion or perform any other action
	        // For example, redirect to deleteContact endpoint with contactId as a parameter
	        window.location.href = "/deleteContact?id=" + id;
	    } else {
	        // Cancel deletion or perform any other action
	        // For example, do nothing or show a message
	    }
	}
	function editContact(id)
	{
		let encodedId=encodeURIComponent(id);
		console.log(encodedId);
		window.location.href="/editContact?id="+encodedId;
	}
</script>
</head>
<body>
	<jsp:include page="profile-navbar.jsp" />
	<jsp:include page="userMangamentSidebar.jsp" />
	<div class="container">
		<div class="conatiner-fluid">
			<div class="row">
				<div class="col-md-10 mx-auto mt-3">
					<div class="card">
						<div class="table-responsive">
							<h2 class="text-center mt-2">Your Contact</h2>
							<table class="table table-striped table-hover mt-2">
								<thead class="table-primary">
									<tr>
										<th scope="col">ID</th>
										<th scope="col">Name</th>
										<th scope="col">Phone No</th>
										<th scope="col">Email</th>
										<th scope="col">Experience</th>
										<th scope="col">Update</th>
										<th scope="col">Delete</th>

									</tr>
								</thead>
								<tbody>
									<c:forEach var="contact" items="${contactDetails}">
										<tr>
											<th scope="row">${contact.getId()}</th>

											<td>${contact.getName()}</td>

											<td>${contact.getPhoneno()}</td>

											<td><a href="savedContactDetails?email=${contact.getEmail()}">${contact.getEmail()}</a></td>

											<td>${contact.getExperience()}</td>

											<td>
												  <button onclick="editContact(${contact.getId()})">
	                                            		<i class="fa-solid fa-pen-nib"></i>
	                                       		 </button>
											</td>
											<td>
												<button onclick="deleteContact(${contact.getId()})">
													<i class="fa-solid fa-trash" ></i>
												</button>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>

						</div>
						<div class="page">
							<ul class="pagination" >
							    <c:choose>
							    	<c:when test="${model_current_page>1}">
							    		<li class="page-item">
									      <a class="page-link" href="/showContact?page=${model_current_page-1}">Previous</a>
									    </li>
							    	</c:when>
							    	<c:when test="${model_current_page==1}">
							    		<li class="page-item disabled" >
									      <a class="page-link" href="/showContact?page=${model_current_page-1}">Previous</a>
									    </li>
							    	</c:when>
							    </c:choose>
							    
							    <c:forEach begin="1" end="${model_total_pages}" var="pageNumber">
							    	<c:choose>
							    		<c:when test="${model_current_page==pageNumber}">
							    			<li class="page-item active" aria-current="page">
											      <a class="page-link" href="/showContact?page=${pageNumber}">${pageNumber}</a>
											  </li>
							    		</c:when>
							    		<c:when test="${model_current_page!=pageNumber}">
							    			<li class="page-item" aria-current="page">
											      <a class="page-link" href="/showContact?page=${pageNumber}">${pageNumber}</a>
											 </li>
							    		</c:when>
							    	</c:choose>
							    </c:forEach>
							    
							    <c:choose>
							    	<c:when test="${model_current_page<model_total_pages}">
							    		<li class="page-item">
									      <a class="page-link" href="/showContact?page=${model_current_page+1}">Next</a>
									    </li>
							    	</c:when>
							    	<c:when test="${model_current_page == model_total_pages}">
							    		<li class="page-item disabled" >
									      <a class="page-link" href="/showContact?page=${model_current_page+1}">Next</a>
									    </li>
							    	</c:when>
							    </c:choose>
							 </ul>
					   </div>
					</div>

				</div>
			</div>
		</div>
	</div>

</body>
</html>