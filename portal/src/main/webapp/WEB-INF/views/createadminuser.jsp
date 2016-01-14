<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="resources/css/intlTelInput.css">
<!-- <script src="resources/js/intlTelInput.js"></script> -->
<script src="resources/js/createadminuser.js"></script>
<link rel="stylesheet" href="resources/css/profileaboutme.css">
<script src="resources/js/timezones.full.js"></script>
<script>
	$(document).ready(function() {
		$("#createuserlink").parent().addClass("active");
		$('#localtimezone').timezones();
	});
</script>
<body>
	<div
		style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
		<div class="container">
			<img src="resources/images/icon_profile.png">&nbsp;&nbsp;Create
			User
		</div>
	</div>
	<div class="" style="margin-top: 20px;">
		<div class="container">
			<div class="row">

				<div class="col-md-12 col-lg-8">

					<!-- <h4 class="profile-header">Create User</h4> -->
					<hr>
					<br>
					<div>
						<c:if test="${not empty userEmailExistsMsg}">
							<img style="width: 30px;" src="resources/images/warning.png" />&nbsp;${userEmailExistsMsg}<br>
							<br>
						</c:if>
						<c:if test="${not empty adminUserRegistrationSuccessMsg}">
							<img style="width: 30px;" src="resources/images/ok.png" />&nbsp;${adminUserRegistrationSuccessMsg}<br>
							<br>
						</c:if>
					</div>
					<form id="myadminuser" action="tmiAdmAddAdminUserDetails.do"
						method="post" autocomplete="off">
						<div class="row">
							<div class="col-md-7 col-lg-12">
								<div class="row">
									<div class="col-sm-6 col-md-6">
										<div class="form-group">
											<label>First Name</label> <input type="text"
												class="form-control" name="firstName" id="firstName"
												maxlength="50">

										</div>
									</div>
									<div class="col-sm-6 col-md-6">
										<div class="form-group">
											<label>Last Name</label> <input type="text"
												class="form-control" name="lastName" id="lastName"
												maxlength="50">

										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-sm-6 col-md-6">
										<div class="form-group">
											<label>Mobile No</label> <input type="tel"
												class="form-control" name="phoneNumber" id="phoneNumber"
												maxlength="24">

										</div>
									</div>
									<div class="col-sm-6 col-md-6">
										<div class="form-group">
											<label>User Role</label> <select class="form-control"
												name="userRole" id="userRole">
												<option value="">Select</option>
												<c:forEach var="userroleLst" items="${userroleList}">

													<option value="${userroleLst.roleid}">
														${userroleLst.userrolename}</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6 col-md-6">
										<div class="form-group">
											<label>Email Address</label> <input type="text"
												class="form-control" name="emailaddress" id="emailaddress"
												maxlength="50">
										</div>
									</div>
									<div class="col-sm-6 col-md-6">
										<div class="form-group">
											<label>Time Zone </label> <select id="localtimezone"
												name="localtimezone" class="form-control"></select>
										</div>
									</div>
								</div>
							</div>
						</div>
						<hr>
						<div class="row">
							<div
								class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-10">
								<div class="form-group">

									<button id="continue" type="submit"
										class="form-control mycontinuebtn">Create</button>

								</div>
							</div>
						</div>
					</form>
				</div>

			</div>
		</div>
	</div>

	<br>
	<br>
	<script type="text/javascript">
		var completeHandler = function(data) {
			$("#processImg").hide();

		};
	</script>


</body>