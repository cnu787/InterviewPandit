<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- shyam added on 12-05-2015 - Start -->
<link rel="stylesheet" href="resources/css/datepicker.css" />
<script src="resources/js/bootstrap-datepicker.js"></script>
<!-- shyam added on 12-05-2015 - End -->
<link rel="stylesheet" href="resources/css/intlTelInput.css">
<link rel="stylesheet" href="resources/css/internalcalender.css">
<script src="resources/js/intlTelInput.js"></script>
<script type="text/javascript">
	jQuery(document).ready(function() {
		$("#bulkuploadlink").parent().addClass("active");
	});
</script>
<body>
	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$('#addStartHour0').html(timeSlotOptionTxt);
							$('#endTime0').val('1 AM');
							$('#startDate ')
									.datepicker({
										format : 'dd/mm/yyyy',
										startDate : '+0d'
									})
									.on(
											'changeDate',
											function(ev) {
												$('#myinterview')
														.bootstrapValidator(
																'revalidateField',
																'startDate');
												$(".datepicker").hide();
											});

							$(".tmiInterviewSlot")
									.change(
											function() {
												var endTimeId = 'endTime'
														+ jQuery
																.trim(
																		$(this)
																				.attr(
																						"id"))
																.substring(
																		12,
																		$(this)
																				.attr(
																						"id").length);
												$('#' + endTimeId)
														.val(
																timeSlotMap[$(
																		this)
																		.find(
																				":selected")
																		.text()]);
											});

							$('#myinterview').click(function() {
								$('#search_companyname').hide();
							});
						});
		var timeSlotArray = [];
		var timeSlotMap = {};
		var slotIdMap = {};
		<c:forEach items="${timeSlotList}" var="timeslot" varStatus="loop">
		timeSlotArray.push("${timeslot.startTime}");
		timeSlotMap["${timeslot.startTime}"] = "${timeslot.endTime}";
		slotIdMap["${timeslot.startTime}"] = "${timeslot.slotid}";
		</c:forEach>
		var timeSlotOptionTxt = "";
		var timeSlotOptionArray = [];
		<c:forEach var="timeslot" items="${timeSlotList}">
		timeSlotOptionTxt += "<option  value='${timeslot.slotid}'>${timeslot.startTime}</option>";
		timeSlotOptionArray['${timeslot.slotid}'] = "<option  value='${timeslot.slotid}'>${timeslot.startTime}</option>";
		</c:forEach>
		function tmiInterviewSlotChange(element) {
			var endTimeId = 'endTime'
					+ jQuery.trim($('#' + element).attr("id")).substring(12,
							$('#' + element).attr("id").length);
			$('#' + endTimeId).val(
					timeSlotMap[$('#' + element).find(":selected").text()]);
		}
		function addfileName() {
			var filename = document.getElementById('BrowserHidden').files[0].name;
			document.getElementById('uploadFile').value = filename;
		}
	</script>
	<div
		style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
		<div class="container">Bulk Upload</div>
	</div>
	<div class="" style="margin-top: 20px;">
		<div class="container">
			<form id="myinterview" class="bv-form" enctype="multipart/form-data"
				action="tmiAdmuploadFiles.do" method="post" autocomplete="off">
				<div class="row">
					<div class="col-md-9">
						<c:if test="${count ==1}">
							<label><img style="width: 30px;"
								src="resources/images/ok.png" />&nbsp;Interview has been
								successfully scheduled for the applicants listed in the uploaded
								file.</label>
						</c:if>
						<br>
						<div class="row">
							<div class="col-sm-12 col-md-12">
								<div class="bg-danger">
									<c:if test="${not empty unregisteredMembersEval}">
										<div class="row">
											<div class="col-sm-12 col-md-12">
												<div class="bg-danger">
													<c:if test="${not empty unregisteredMembersEval}">
														<strong><img style="width: 30px;"
															src="resources/images/warning.png" />&nbsp;Following
															Evaluators are not registered with Interview Pandit. Please rectify
															the file and uploaded again.<br></strong>
														<c:forTokens items="${unregisteredMembersEval}" delims=","
															var="name">
															<c:set var="evalName"
																value="${fn:replace(name, 
                                '[', '')}" />
															<c:set var="evalmembers"
																value="${fn:replace(evalName, 
                                ']', '')}" />
															<label><c:out value="${evalmembers}" /></label>
															<br>
														</c:forTokens>
													</c:if>
												</div>
											</div>
										</div>
									</c:if>
									<%-- </c:if>	 --%>
									<c:if test="${not empty unregisteredMembersApp}">
										<strong><img style="width: 30px;"
											src="resources/images/warning.png" />&nbsp;Following
											Applicants are not registered with Interview Pandit. Please rectify the
											file and uploaded again.<br></strong>
										<c:forTokens items="${unregisteredMembersApp}" delims=","
											var="appname">
											<c:set var="appName1"
												value="${fn:replace(appname, 
                                '[', '')}" />
											<c:set var="appmembers"
												value="${fn:replace(appName1, 
                                ']', '')}" />
											<label> <c:out value="${appmembers}" /></label>
											<br>
										</c:forTokens>
									</c:if>
								</div>
							</div>
						</div>

						<br>
						<div class="row">
							<div class="col-md-4 pull-right">

								<button id="continue"
									onclick="window.location.href='tmiAdmBulkGroup.do';"
									type="button" class="form-control mybackbtn">Bulk
									Upload History</button>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6 col-md-6">
								<label>Group Name</label>
								<div class="form-group">
									<input type="text" class="form-control" name="groupName"
										id="groupName" maxlength="50">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-7 col-sm-6 col-md-6">
								<label>Set Time Period</label>
								<div class="row">
									<div class="col-sm-12 col-md-12">
										<div class="form-group">
											<div class="input-group date " id='datepicker'>
												<input type="text" name="startDate" readonly
													class="form-control" id="startDate"> <span
													class="input-group-addon" id="startdateicon"> <span
													class="glyphicon glyphicon-calendar"></span>
												</span>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 col-md-12">
								<div class="row">
									<div class="col-sm-3 col-md-3">
										<label>From</label>
										<div class="form-group">
											<select id="addStartHour0" name="addStartHour"
												class="form-control tmiInterviewSlot">
											</select>
										</div>
									</div>
									<div class="col-sm-3 col-md-3 ">
										<label>To</label>
										<div class="form-group">
											<input type="text" class="form-control" id="endTime0"
												name="endTime" size="7px" value="1 AM" readonly>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
								<label>Upload Bulk Email</label>
								<div class="row">
									<div class="col-xs-7 col-sm-6 col-md-6">
										<div class="form-group">
											<input id="uploadFile" name="uploadFile" data-required="true"
												readonly class="form-control" />
										</div>
									</div>
									<div class="col-xs-6 col-sm-3 col-md-3">
										<div
											style='background: url("resources/images/resumebrowse.png") no-repeat scroll 0% 0% transparent;'>
											<div class="form-group">
												<input type='file' class="form-control btn upploadbtn"
													name="BrowserHidden" id="BrowserHidden"
													onchange="addfileName();" />
											</div>
										</div>
									</div>
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
												onchange="showSubCategoryListInterviewnotOthers('domain','industry');showInterviewerRolenotOthers('industry','interviewrole');">
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
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 col-md-12">
								<label>Domain</label>
								<div class="row">
									<div class="col-sm-6 col-md-6">
										<div class="form-group">
											<select multiple="multiple" class="form-control"
												name="domain" id="domain">
												<option value="-1">Select</option>
											</select>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 col-md-12">
								<label>Interview type</label>
								<div class="row">
									<div class="col-sm-6 col-md-6">
										<div class="form-group">
											<select class="form-control" name="interviewtype"
												id="interviewtype">
												<option value="-1">Select</option>
												<c:forEach var="interviewLst" items="${interviewtypesList}">
													<option value="${interviewLst.interviewtypeid}"
														<c:if test="${interviewLst.interviewtypeid==externalProfile[0].interviewtypeid}">
													selected="selected"
													</c:if>>
														${interviewLst.typeofinterview}</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
								<label>Are you a Fresher?</label>
								<div class="form-group">
									<div class="row">
										<div class="col-xs-3 col-sm-3 col-md-2 ">
											<input type="radio" name="career" id="careeryes" value="1"
												checked="checked"
												<c:if test="${externalProfile[0].careerstatus == '1'}"> checked </c:if>>
											Yes
										</div>
										<div class="col-xs-3 col-sm-3 col-md-2 ">
											<input type="radio" name="career" id="careerno" value="0"
												<c:if test="${externalProfile[0].careerstatus == '0'}"> checked </c:if>>
											No
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 col-md-12">
								<label>Organization Name</label>
								<div class="form-group" style="position: relative">
									<input type="text" name="companyname" class="form-control"
										id="companyname" maxlength="60"
										onkeyup="return getAutoSuggestNames('companyname','companyId','autoSuggestCompanyName.do');"
										value="${externalProfile[0].companynametxt}" /> <input
										type="hidden" readonly name="companyId" id="companyId"
										value="${externalProfile[0].companyname}" /> <input
										type="hidden" name="previousCompanyId" id="previousCompanyId"
										value="${externalProfile[0].companyname}">
									<div class="search_suggest" id="search_companyname"></div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 col-md-12">
								<label>Designation/ Position you would like to apply for</label>
								<div class="form-group">
									<input type="text" class="form-control" name="position"
										id="position" maxlength="50"
										value="${externalProfile[0].designation}">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 col-md-12">
								<div class="row">
									<div class="col-sm-6 col-md-6">
										<label>Interviewer's Role</label>
										<div class="form-group">
											<select class="form-control" name="interviewrole"
												id="interviewrole"
												onchange="enableOthersInterviewRoles('interviewrole','otherDomainDiv');">
												<option value="-1">Select</option>
											</select>
										</div>
									</div>
									<div class="col-sm-6 col-md-6">
										<label>Interviewer's Location</label>
										<div class="form-group">
											<select class="form-control" name="location" id="location">
												<option value="-1">Select</option>
												<!-- comment when other countries are needed -->
												<option value="1">India</option>
												<!-- uncomment when other countries are needed -->
												<%-- <c:forEach var="interlocationLst"
													items="${interviewlocationsList}">
													<option value="${interlocationLst.locationid}"
														<c:if test="${interlocationLst.locationid==externalProfile[0].interviewerlocationid}">
													selected="selected"
													</c:if>>
														${interlocationLst.locationname}</option>
												</c:forEach> --%>
											</select>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 col-md-12">
								<div class="form-group">
									<label>Mode of Interview</label>
									<div class="row" id="intertype">
										<div class="col-xs-6 col-sm-3 col-md-3 ">
											<input type="radio" id="facetoface" name="telephonic"
												value="4" checked="checked"> Face to Face
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<hr>
				<input type="hidden" name="_dontcare">
				<div class="row">
					<div
						class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
					</div>
					<div
						class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
						<div class="form-group">
							<button id="continue" type="submit"
								class="form-control mycontinuebtn">Upload</button>

						</div>
					</div>
				</div>

			</form>
		</div>
	</div>
	<script type="text/javascript">
		$(document)
				.ready(
						function() {

							$("#myinterview")
									.bootstrapValidator(
											{
												feedbackIcons : {
													valid : "glyphicon glyphicon-ok",
													invalid : "glyphicon glyphicon-remove",
													validating : "glyphicon glyphicon-refresh"
												},
												fields : {
													startDate : {
														validators : {
															notEmpty : {
																message : "Date is required."
															}
														}
													},
													industry : {
														validators : {
															callback : {
																message : "Please select listed industry.",
																callback : function(
																		a, b) {
																	var d = b
																			.getFieldElements(
																					"industry")
																			.val();
																	return -1 != d;
																}
															}
														}
													},
													domain : {
														validators : {
															callback : {
																message : "Please select listed domain.",
																callback : function(
																		a, b) {
																	var d = b
																			.getFieldElements(
																					"domain")
																			.val();
																	return -1 != d
																			&& null != d;
																}
															}
														}
													},
													interviewtype : {
														validators : {
															callback : {
																message : "Please select listed interview type.",
																callback : function(
																		a, b) {
																	var d = b
																			.getFieldElements(
																					"interviewtype")
																			.val();
																	return -1 != d
																}
															}
														}
													},
													groupName : {
														validators : {
															notEmpty : {
																message : "GroupName is required."
															}
														}
													},
													interviewrole : {
														validators : {
															callback : {
																message : "Please select listed interviewer role.",
																callback : function(
																		a, b) {
																	var d = b
																			.getFieldElements(
																					"interviewrole")
																			.val();
																	return -1 != d
																}
															}
														}
													},
													location : {
														validators : {
															callback : {
																message : "Please select listed interviewer location.",
																callback : function(
																		a, b) {
																	var d = b
																			.getFieldElements(
																					"location")
																			.val();
																	return -1 != d
																}
															}
														}
													},
													BrowserHidden : {
														validators : {
															notEmpty : {
																message : "Bulk Email Excel is required."
															},
															file : {
																extension : "xls,xlsx",
																type : "application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
																maxSize : 5e6,
																message : "The selected file is not valid, it should be (xls,xlsx) and 5 MB at maximum."
															}
														}
													}

												}
											})
						});
		function myDropdownCheckType() {
			$("#myinterview").bootstrapValidator("enableFieldValidators",
					"industry", !1), $("#myinterview").bootstrapValidator(
					"enableFieldValidators", "industry", !0), $("#myinterview")
					.bootstrapValidator("validateField", "industry");
			var a = document.getElementById(subcatId);
			removeOptions(a), myDropdownCheck()
		}
		function myDropdownInterviewCheck() {
			$("#myinterview").bootstrapValidator("enableFieldValidators",
					"interviewrole", !1), $("#myinterview").bootstrapValidator(
					"enableFieldValidators", "interviewrole", !0), $(
					"#myinterview").bootstrapValidator("validateField",
					"interviewrole")
		}
		myDropdownCheck = function() {
			$("#myinterview").bootstrapValidator("enableFieldValidators",
					"domain", !1), $("#myinterview").bootstrapValidator(
					"enableFieldValidators", "domain", !0), $("#myinterview")
					.bootstrapValidator("validateField", "domain"), $(
					"#myinterview").bootstrapValidator("enableFieldValidators",
					"interviewrole", !1), $("#myinterview").bootstrapValidator(
					"enableFieldValidators", "interviewrole", !0), $(
					"#myinterview").bootstrapValidator("validateField",
					"interviewrole")
		};
	</script>
</body>
</html>