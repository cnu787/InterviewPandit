<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>


	<div
		style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
		<div class="container">
			<img src="resources/images/icon_profile.png">&nbsp;&nbsp;Refer
			a Friend
		</div>
	</div>
	<div class="container">
		<div style="text-align: center; font-family: robotocondensedregular;">
			<br>
			<div>
				<c:if test="${not empty referrerSuccessMsg}">
					<img style="width: 30px;" src="resources/images/ok.png" />&nbsp;${referrerSuccessMsg}<br>
					<br>
				</c:if>
			</div>
		</div>
		<form id="referrerForm" action="javascript:referfriendsubmit()"
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
								<div class="input-group">
									<input type="text" class="form-control" name="emailid"
										id="emailid" placeholder="Email Id" maxlength="50">
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
								<select id="chooseyourRole" name="chooseyourRole"
									class="form-control">
									<option value="">Choose Referred Position</option>
									<c:forEach var="list" items="${usertypelkp}">
										<option value="${list.usertypeid}">${list.usertype}</option>
									</c:forEach>
								</select>

							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12 col-md-12" style="margin-bottom: 10px;">
							<div class="form-group">
								<input class="mybtn btn btn-primary btn-lg" id="sub"
									type="submit" value="Refer">

							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
		<div></div>
		<script type="text/javascript">
			function referfriendsubmit() {
				var emailid = $('#emailid').val();
				var chooseyourRole = $('#chooseyourRole').val();
				$("#processImg").show();
				window.location.href = "referfriendsubmit.do?emailid="
						+ emailid + "&chooseyourRole=" + chooseyourRole;
			}
		</script>

		<br>
	</div>
</body>
