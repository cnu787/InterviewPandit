<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="resources/js/md5.js"></script>

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
				<h4>Welcome to Interview Pandit. Login to Access Your Profile</h4>
				<br>
				<div>
					<c:if test="${not empty param['login_error']}">
						<c:if
							test="${SPRING_SECURITY_LAST_EXCEPTION.message == 'User is disabled'}">
							<img style="width: 30px;" src="resources/images/warning.png" />&nbsp;Please click the link sent to your Email Address to Login<br>
							<br>
						</c:if>
						<c:if
							test="${SPRING_SECURITY_LAST_EXCEPTION.message == 'Bad credentials'}">
							<img style="width: 30px;" src="resources/images/warning.png" />&nbsp;Bad credentials<br>
							<br>
						</c:if>
					</c:if>
					<c:if test="${not empty loginmessage}">
						<img style="width: 30px;" src="resources/images/warning.png" />&nbsp;${loginmessage}<br>
						<br>
					</c:if>
					<c:if test="${not empty resetPasswordSuccessMsg}">
						<img style="width: 30px;" src="resources/images/ok.png" />&nbsp;${resetPasswordSuccessMsg}<br>
						<br>
					</c:if>

				</div>
			</div>
			<form id="loginForm" action="j_spring_security_check" method="post"
				autocomplete="off">
				<input type="hidden" name="authType" value="${authType}" />
				<div class="row marginauto">
					<div
						class="col-xs-12 col-xs-offset-0 col-sm-6 col-sm-offset-3 col-md-4 col-md-offset-4 myform-body centered">
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
										<input type="text" class="form-control" name="j_username"
											id="j_username" placeholder="Email Id" maxlength="50">
										<div class="input-group-addon">
											<img alt="" src="resources/images/icon_username.png" />
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 col-md-12">
								<div class="form-group">
									<div class="input-group">
										<input type="password" class="form-control" name="j_password"
											id="j_password" placeholder="Password" maxlength="15">
										<div class="input-group-addon">
											<img alt="" src="resources/images/icon_password.png" />
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 col-md-12">
								<p style="text-align: center;">
									<a href="forgotPassword.do?authType=${authType}"><u>Forgot
											your password?</u></a>
								</p>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 col-md-12">
								<div class="form-group">

									<label class="checkbox-inline"
										style="margin-top: 0px; color: #3e454c; font-size: 12px; font-family: robotoregular;">
										<input style="margin-top: 2px;" type="checkbox">
										Remember me &nbsp;&nbsp;|&nbsp;&nbsp; All fields are mandatory
									</label>

								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 col-md-12" style="margin-bottom: 10px;">
								<div class="form-group">
									<button class="mybtn btn  btn-lg" id="sub" name="sub"
										type="submit">Login</button>

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
	<script type="text/javascript">
		jQuery(document).ready(function() {
			$('#loginForm1').on('success.form.bv', function(e) {
				e.preventDefault();

			});
		});
	</script>
</body>