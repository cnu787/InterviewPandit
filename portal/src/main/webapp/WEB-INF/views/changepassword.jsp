<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="resources/js/md5.js"></script>
<body>


	<div
		style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
		<div class="container">
			<img src="resources/images/icon_profile.png">&nbsp;&nbsp;Change
			Password
		</div>
	</div>
	<div class="container">
		<div style="text-align: center; font-family: robotocondensedregular;">
			<br>
			<div>
				<c:if test="${not empty passsuccessmsg}">
					<img style="width: 30px;" src="resources/images/ok.png" />&nbsp;${passsuccessmsg}<br>
					<br>
				</c:if>
				<c:if test="${not empty passerrmsg}">
					<img style="width: 30px;" src="resources/images/error.png" />&nbsp;${passerrmsg}<br>
					<br>
				</c:if>
			</div>
		</div>
		<form id="changepasswordform" action="changePasswordSubmit.do"
			method="post">
			<div class="row marginauto">
				<div
					class="col-xs-10 col-xs-offset-1 col-sm-6 col-sm-offset-3 col-md-4 col-md-offset-4 myform-body centered">
					<div class="row">
						<div class="col-sm-12 col-md-12">
							<br>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12 col-md-12">
							<div class="form-group">
								<input type="password" placeholder="Current Password"
									data-html="true" class="form-control" name="currentPassword"
									id="currentPassword" maxlength="15">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12 col-md-12">
							<div class="form-group">
								<input type="password" placeholder="New Password"
									class="form-control" name="newPassword" id="newPassword"
									data-html="true" data-toggle="tooltip" data-placement="bottom"
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
						<div class="col-sm-12 col-md-12">
							<div class="form-group">
								<input type="password" placeholder="Confirm Password"
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
						<div class="col-sm-12 col-md-12" style="margin-bottom: 10px;">
							<div class="form-group">
								<input class="mybtn btn btn-primary btn-lg" id="sub"
									type="submit" value="Change Password">

							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
		<div></div>


		<br>
	</div>
	<script type="text/javascript">
		$('#changepasswordform').on(
				'success.form.bv',
				function(e) {
					if ($('#confirmPassword').val().length != 0
							&& $('#newPassword').val().length != 0
							&& $('#currentPassword').val().length != 0) {
						$("#processImg").show();
						var strMD5 = CryptoJS.MD5('${mySesProfile[0].emailid}'
								+ $('#newPassword').val());
						$('#newPassword').val(strMD5);
						strMD5 = CryptoJS.MD5('${mySesProfile[0].emailid}'
								+ $('#confirmPassword').val());
						$('#confirmPassword').val(strMD5);
						strMD5 = CryptoJS.MD5('${mySesProfile[0].emailid}'
								+ $('#currentPassword').val());
						$('#currentPassword').val(strMD5);
					}
				});
		$(function() {
			$('[data-toggle="tooltip"]').tooltip();
		});
	</script>
</body>
