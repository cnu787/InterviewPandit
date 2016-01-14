<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
body {
	background-color: #0095DA;
}

.input-group-addon {
	background-color: #59574B;
	color: #FFF;
	border: 1px solid #59574B;
}

hr {
	border-color: #57574B;
}

form {
	color: #3e454c;
	font-size: 12px;
	font-family: robotoregular;
}

@media ( min-width : 768px) {
	.row {
		margin-right: 0px;
		margin-left: 0px;
	}
}
</style>
<body>


	<div class="non-authentic-body">
		<div class="container">
			<div
				style="text-align: center; color: #FFF; font-family: robotocondensedregular;">
				<h4>Forgot Password</h4>
				<br>
				<div>
					<c:if test="${not empty resetPasswordErrMsg}">
						<img style="width: 30px;" src="resources/images/error.png" />&nbsp;${resetPasswordErrMsg}<br>
						<br>
					</c:if>
				</div>
			</div>
			<form id="forgotForm" action="resetPassword.do" method="post">
				<input type="hidden" name="authType" value="${param.authType}" />
				<div class="row marginauto">
					<div
						class="col-xs-10 col-xs-offset-1 col-sm-6 col-sm-offset-3 col-md-4 col-md-offset-4 myform-body centered">
						<div class="row">
							<div class="col-sm-12 col-md-12" style="margin-top: 10px;">
								<img class="imagemarginauto" style="padding: 5px; width: 53px;"
									src="resources/images/icon_login_login_page.png">
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 col-md-12">
								<hr>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 col-md-12">
								<div class="form-group">

									<div class="input-group">
										<input type="text" class="form-control" name="emailid"
											placeholder="Email Id" maxlength="50">
										<div class="input-group-addon">
											<img alt="" src="resources/images/icon_username.png" />
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 col-md-12" style="margin-bottom: 10px;">
								<div class="form-group">
									<input class="mybtn btn btn-primary btn-lg" id="sub"
										type="submit" value="Submit">

								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
			<div></div>

		</div>
		<br>
	</div>
</body>
