<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="resources/css/intlTelInput.css">
<script src="resources/js/intlTelInput.js"></script>
<c:if
	test="${externalProfile[0].usertypeid eq 2 && empty externalProfile[0].resumeid }">
	<script src="resources/js/profileaboutme.js"></script>
</c:if>
<c:if
	test="${externalProfile[0].usertypeid eq 1 ||  not empty externalProfile[0].resumeid }">
	<script src="resources/js/profileaboutmeApplicant.js"></script>
</c:if>

<link rel="stylesheet" href="resources/css/profileaboutme.css">
<script src="resources/js/timezones.full.js"></script>
<script>
	jQuery(document).ready(function() {
		$('#localtimezone').timezones();
		$('#localtimezone').val('${externalProfile[0].timezone}');
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
</style>
<body>
	<div id="mainProfileContainer">
		<div
			style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
			<div class="container">
				<img src="resources/images/icon_profile.png">&nbsp;&nbsp;Profile
			</div>
		</div>
		<div class="" style="margin-top: 20px;">
			<div class="container">
				<div class="row">					
					<div class="col-md-12 col-lg-12">
						<c:if test="${not empty maxFileUploadMsg}">
							<img style="width: 30px;" src="resources/images/error.png" />&nbsp;${maxFileUploadMsg}<br>
							<br>
						</c:if>
						<h2 class="profile-header">More about yourself</h2>
						<hr>
						<form enctype="multipart/form-data" id="myprofile"
							action="applicantProfile.do" method="post" autocomplete="off">
							<div class="row">								
								<div class="col-md-7 col-lg-8">
									<div class="row">
										<div class="col-sm-6 col-md-6">
											<div class="form-group">
												<label>First Name</label> <input type="text"
													class="form-control" name="firstName"
													value="${externalProfile[0].firstname}" id="firstName"
													maxlength="50">

											</div>
										</div>
										<div class="col-sm-6 col-md-6">
											<div class="form-group">
												<label>Last Name</label> <input type="text"
													class="form-control" name="lastName" id="lastName"
													maxlength="50" value="${externalProfile[0].lastname}">

											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6 col-md-6">
											<div class="form-group">
												<label>Email Address</label> <input type="text"
													class="form-control" name="emailaddress" id="emailaddress"
													maxlength="50" readonly
													value="${externalProfile[0].emailid}">
											</div>
										</div>
										<script type="text/javascript">
											$(document)
													.ready(
															function() {
																$(
																		".myCountryISD")
																		.text(
																				'${externalProfile[0].countrycode}');
															});
										</script>
										<div class="col-sm-6 col-md-6">
											<div class="form-group">
												<label>Mobile No</label> <input type="tel"
													class="form-control" name="phoneNumber" id="phoneNumber"
													maxlength="24" value="${externalProfile[0].mobileno}">
												<input type="hidden" id="myCountryCode" name="myCountryCode"
													value="${externalProfile[0].countrycode}" />

											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6 col-md-6">
											<div class="form-group">
												<label>Time Zone </label> <select id="localtimezone"
													name="localtimezone" class="form-control"></select>
											</div>
										</div>
										<div class="col-sm-6 col-md-6">
											<div class="form-group">
												<label>Screen Name</label> <input type="text"
													class="form-control" name="screenname" id="screenname"
													maxlength="20" value="${externalProfile[0].screenName}">
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-12 col-md-12">
											<label>Industry</label>
											<div class="row">
												<div class="col-sm-6 col-md-6">
													<div class="form-group">
														<select class="form-control" name="industry" id="industry"
															onchange="showSubCategoryList('domain','industry','otherIndustryDiv','otherDomainDiv');">
															<option value="-1">Select</option>
															<c:forEach var="industryLst" items="${industryList}">
																<option value="${industryLst.industryid}"
																	<c:if test="${industryLst.industryid==externalProfile[0].industryid}">
													selected="selected"
													</c:if>>
																	${industryLst.industryname}</option>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="col-sm-6 col-md-6">
													<div class="form-group" id="otherIndustryDiv"
														<c:if test="${externalProfile[0].industryid==0}">
											style="display:block;"
											</c:if>
														<c:if test="${externalProfile[0].industryid!=0}">
											style="display:none;"
											</c:if>>
														<input type="text" class="form-control"
															name="otherIndustry" id="otherIndustry" maxlength="32"
															value="${externalProfile[0].otherIndustry}">

													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-12 col-md-12">
											<label>Domain</label>
											<div class="row">
												<div class="col-sm-6 col-md-6">
													<div class="form-group">
														<select class="form-control" multiple="multiple"
															name="domain" id="domain"
															onchange="enableOthersFieldProfile('domain','otherDomainDiv');">
														</select>
													</div>
												</div>
												<div class="col-sm-6 col-md-6">
													<div class="form-group" id="otherDomainDiv"
														style="display: none;">
														<input type="text" class="form-control" name="otherDomain"
															id="otherDomain" maxlength="32"
															value="${externalProfile[0].otherdomain}">
													</div>
												</div>
											</div>
										</div>
									</div>



									<div class="row">
										<div class="col-sm-12 col-md-12">
											<label>Add Media</label>
											<div class="row">
												<div class="col-sm-4 col-md-4">
													<div class="form-group"
														style='background: url("resources/images/addvideo.png") no-repeat scroll 0% 0% transparent;'>

														<input class="form-control btn upploadbtn" type='file'
															name="profileVideo" id="profileVideo" />
													</div>
												</div>
												<div class="col-sm-4 col-md-4">
													<div class="form-group"
														style='background: url("resources/images/addphoto.png") no-repeat scroll 0% 0% transparent;'>

														<input class="form-control btn upploadbtn" type='file'
															name="profileImage" id="profileImage" />
													</div>
												</div>
												<div class="col-sm-4 col-md-4">
													<div class="form-group"
														style='background: url("resources/images/addresume.png") no-repeat scroll 0% 0% transparent;'>

														<input class="form-control btn upploadbtn" type='file'
															name="profileResume" id="profileResume" />
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<hr>
							<div class="row">
								<div
									class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
									<div class="form-group">
										<button
											onclick="window.location.href='myCompleteProfile.do';"
											id="cancel" type="button" class="form-control mybackbtn">Cancel</button>
									</div>
								</div>
								<div
									class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
									<div class="form-group">
										<button id="continue" type="submit"
											class="form-control mycontinuebtn">Save</button>
									</div>
								</div>
							</div>
						</form>
					</div>
					

				</div>
			</div>
		</div>

		<br> <br>
		<script type="text/javascript">
			var completeHandler = function(data) {
				$("#processImg").hide();
				if ('${edit}' == "true") {
					window.location.href = "myCompleteProfile.do";
				} else {
					$("#mainProfileContainer").load('applicantStudyView');
				}
			};
		</script>

		<form id="myotherIndustry" action="#" method="post" autocomplete="off">
			<div class="modal" id="myModal" tabindex="-1" role="dialog"
				data-backdrop="static" aria-labelledby="myModalLabel"
				aria-hidden="true">
				<div class="modal-dialog mymodal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title " id="myModalLabel">Enter
								other industry name</h4>
						</div>
						<div class="modal-body ">
							<div class="row ">
								<div class="col-md-12 ">
									<div class="row">
										<div class="col-sm-12 col-md-12">
											<div class="row">
												<div class="col-sm-7 col-sm-push-3 col-md-7 col-md-push-3">
													<div class="form-group ">
														<label>Enter other industry name</label> <input
															type="text" class="form-control" name="otherIndustry"
															id="otherIndustry" maxlength="32">
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-sm-12 col-md-12">
											<div class="row">
												<div class="col-sm-7 col-sm-push-3 col-md-7 col-md-push-3">
													<div class="form-group">
														<label>Enter other domain name</label> <input type="text"
															class="form-control" name="otherDomain" id="otherDomain"
															maxlength="32">
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<div
								class="col-xs-4 col-xs-push-2 col-sm-4 col-sm-push-2 col-md-4 col-md-push-2">
								<button type="button" class="form-control mybackbtn"
									id="othercloseId" data-dismiss="modal">Close</button>
							</div>
							<div
								class="col-xs-4 col-xs-push-2 col-sm-4 col-sm-push-2 col-md-4 col-md-push-2">
								<button type="submit" class="form-control mycontinuebtn"
									id="otherTypeId">Save</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
		<form id="myotherDomain" action="#" method="post" autocomplete="off">
			<div class="modal" id="myModalOthersDomain" tabindex="-1"
				data-backdrop="static" role="dialog" aria-labelledby="myModalLabel"
				aria-hidden="true">
				<div class="modal-dialog mymodal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								id="closeIdName" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title " id="myModalLabel">Enter
								other domain name</h4>
						</div>
						<div class="modal-body ">


							<div class="row ">
								<div class="col-md-12 ">
									<div class="row">
										<div class="col-sm-12 col-md-12">
											<div class="row">
												<div class="col-sm-7 col-sm-push-3 col-md-7 col-md-push-3">
													<div class="form-group ">
														<label>Enter other domain name</label> <input type="text"
															name="otherDomainName" class="form-control"
															id="otherDomainName" maxlength="32">
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<div
								class="col-xs-4 col-xs-push-2 col-sm-4 col-sm-push-2 col-md-4 col-md-push-2">
								<button type="button" class="form-control mybackbtn"
									data-dismiss="modal" id=closeIdName>Close</button>
							</div>
							<div
								class="col-xs-4 col-xs-push-2 col-sm-4 col-sm-push-2 col-md-4 col-md-push-2">
								<button type="submit" class="form-control mycontinuebtn"
									id="otherSkillNameId">Save</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
		<script src="resources/js/profileabout.js"></script>
		<script type="text/javascript">
			jQuery(document).ready(
					function() {						
						showSubCategoryList('domain', 'industry',
								'otherIndustryDiv', 'otherDomainDiv',
								'${externalProfile[0].domainid}');
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
	</div>
</body>