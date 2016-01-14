<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="resources/css/intlTelInput.css">
<script src="resources/js/intlTelInput.js"></script>
<script src="resources/js/timezones.full.js"></script>
<script src="resources/js/md5.js"></script>
<script>
	jQuery(document).ready(function() {
		$('#localtimezone').timezones();
	});
</script>
<style>
.intl-tel-input input,.intl-tel-input input[type="text"],.intl-tel-input input[type="tel"]
	{
	position: relative;
	z-index: 0;
	margin-top: 0px !important;
	margin-bottom: 0px !important;
	padding-left: 85px;
	margin-left: 0px;
	transition: background-color 100ms ease-out 0s;
}

.form-control-feedback {
	position: absolute;
	top: 0px;
	right: 0px;
	z-index: 1;
	display: block;
	width: 34px;
	height: 34px;
	line-height: 34px;
	text-align: left;
	pointer-events: none;
}

.selected-flag .myCountryISD {
	margin-left: 35px;
	padding-top: 7px;
	color: #555;
	font-size: 14px;
}

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

@media ( min-width : 357px) and (max-width: 500px) {
	.intl-tel-input input,.intl-tel-input input[type="text"],.intl-tel-input input[type="tel"]
		{
		position: relative;
		z-index: 0;
		margin-top: 0px !important;
		margin-bottom: 0px !important;
		padding-left: 205px !important;
		margin-left: 0px;
		transition: background-color 100ms ease-out 0s;
	}
}
</style>
<body>

	<div class="non-authentic-body">
		<div class="container">
			<div
				style="text-align: center; color: #FFF; font-family: robotocondensedregular;">
				<h4>Register to Create Your Account</h4>
				<br>
				<div>
					<c:if test="${not empty userEmailExistsMsg}">
						<img style="width: 30px;" src="resources/images/warning.png" />&nbsp;${userEmailExistsMsg}<br>
						<br>
					</c:if>
					<c:if test="${not empty registrationSuccessMsg}">
						<img style="width: 30px;" src="resources/images/ok.png" />&nbsp;${registrationSuccessMsg}<br>
						<br>
					</c:if>
				</div>
			</div>
			<form id="registerForm" action="register.do" method="post">
				<input type="hidden" name="referralId" id="referralId"
					value="${param.referralId}" /> <input type="hidden"
					name="registrationType" id="registrationType" value="both" />
				<div class="row marginauto">
					<div class="col-md-6 col-md-offset-3 myform-body centered">
						<div class="row">
							<div class="col-md-12" style="margin-top: 10px;">
								<img class="imagemarginauto" style="padding: 5px;"
									src="resources/images/icon_register_big.png">
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<hr>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<p class="alertmsg">All fields are mandatory</p>
							</div>
						</div>
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
									<label>Time Zone</label> <select id="localtimezone"
										name="localtimezone" class="form-control"></select>
								</div>
							</div>
							<div class="col-sm-6 col-md-6">
								<div class="form-group">
									<label>Choose your Screen Name</label> <input type="text"
										class="form-control" name="chooseyourUsername"
										id="chooseyourUsername" maxlength="20">
								</div>
							</div>
						</div>
						<input type="hidden" id="chooseyourRole" name="chooseyourRole"
							value="${usertypelkp[0].usertypeid}" />
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
								<div class="form-group">
									<label>Mobile No</label> <input type="tel"
										class="form-control " name="phoneNumber" id="phoneNumber"
										maxlength="24"> <input type="hidden"
										id="myCountryCode" name="myCountryCode"
										value="${externalProfile[0].countrycode}" />

								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label>Email Address</label> <input type="text"
										class="form-control" name="emailaddress" id="emailaddress"
										data-toggle="tooltip" data-placement="bottom"
										title="example@example.com" maxlength="50">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6 col-md-6">
								<div class="form-group">
									<label>Password</label> <input type="password"
										class="form-control" name="password" id="password"
										data-html="true" data-toggle="tooltip" data-placement="bottom"
										title="Password Tips
<br>- From 6 to 15 characters
<br>- At least one number
<br>- At least one capital letter
<br>- At least one lower case letter"
										maxlength="15">
								</div>
							</div>
							<div class="col-sm-6 col-md-6">
								<div class="form-group">
									<label>Confirm Password</label> <input type="password"
										class="form-control" name="confirmPassword"
										id="confirmPassword" data-html="true" data-toggle="tooltip"
										data-placement="bottom"
										title="Password Tips
<br>- From 6 to 15 characters
<br>- At least one number
<br>- At least one capital letter
<br>- At least one lower case letter"
										maxlength="15">
								</div>
							</div>
						</div>
						<div class="row">
							<div
								class="col-xs-12 col-sm-6 col-sm-offset-3 col-md-6 col-md-offset-3">
								<div class="form-group">
									<button class="mybtn btn btn-lg" id="sub" type="submit">
										Sign Up</button>

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
		$(function() {
			$('[data-toggle="tooltip"]').tooltip();
		});
	</script>
	<script type="text/javascript">
		$("#phoneNumber").intlTelInput({
			allowExtensions : 0,
			autoFormat : !1,
			autoHideDialCode : 0,
			defaultCountry : "auto",
			ipinfoToken : "",
			nationalMode : 1,
			numberType : "MOBILE",
			preferredCountries : [ "us", "gb", "in" ],
			utilsScript : "resources/js/utils.js"
		});
	</script>
</body>