<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script src="resources/js/interviewrolesonready.js"></script>
<link rel="stylesheet" href="resources/css/interviewroles.css" />
<script type="text/javascript">
	$(document).ready(function() {
		$('.checkbox').hide();
	});
	function addfileName() {
		$('.checkbox').show();
		var filename = document.getElementById('resume').files[0].name;
		document.getElementById('uploadFile').value = filename;

	}
	function getAutoSuggestApplicantNamesNames(name, id, uploadFile,
			controllerName) {
		var xmlHttp;
		document.getElementById(id).value = "";
		$('#myinterview').bootstrapValidator('revalidateField', 'applicantId');
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlHttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		var str = escape((document.getElementById(name).value).trim());
		if (str.length == 0) {
			$("#search_" + name).hide();
		} else {

			xmlHttp.onreadystatechange = function() {
				if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
					var divID = document.getElementById('search_' + name);
					divID.style.display = 'block';
					divID.innerHTML = '';
					var str = JSON.parse(xmlHttp.responseText);
					if (str == "") {
						document.getElementById(id).value = "";
						$('#myinterview').bootstrapValidator('revalidateField',
								'applicantId');
						divID.style.display = 'none';
					}
					if (str.length <= 5) {
						divID.style.height = "auto";
					} else if (str.length >= 5) {
						divID.style.height = "80px";
						divID.style.overflowY = "scroll";
					}
					for (var i = 0; i < str.length; i++) {
						var suggest = '<div onmouseover="javascript:suggestOvers(this);" ';
						suggest += 'onmouseout="javascript:suggestOuts(this);" ';
						suggest += 'onclick="javascript:setSearchName1(\''
								+ str[i].emailid + '\',\'' + str[i].userid
								+ '\',\'' + str[i].resumeid + '\',\'' + name
								+ '\',\'' + id + '\',\'' + uploadFile
								+ '\');" ';
						suggest += 'class="suggest_link">' + str[i].appname

						+ '</div>';
						divID.innerHTML += suggest;
					}
				}
			};
			xmlHttp.open("POST", controllerName);
			xmlHttp.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded");
			xmlHttp.send("search=" + str, true);
		}
	}
	function suggestOvers(div_value) {
		div_value.className = 'suggest_link_over';
		div_value.style.background = '#0094DA';
	}
	//Mouse out function
	function suggestOuts(div_value) {
		div_value.className = 'suggest_link';
		div_value.style.background = 'white';
	}

	function setSearchName1(sname, sid, resumeid, name, id, uploadFile) {
		document.getElementById(name).value = sname;
		document.getElementById(id).value = sid;
		if (resumeid !== 'null') {
			document.getElementById(uploadFile).value = resumeid;
		}
		$('#myinterview').bootstrapValidator('revalidateField', 'applicantId');
		document.getElementById('search_' + name).innerHTML = '';
		document.getElementById('search_' + name).style.display = 'none';
	}
</script>
<body>
	<div class="" style="margin-top: 20px;">
		<div class="container">
			<h1 class="header-file">Interview</h1>
			<h4 class="header-text">
				<span style="color: #0095DA;">Please select company and
					position that you would like to be interviewed for and define the
					role of interviewer. 
			</h4>
			<hr>
			<form enctype="multipart/form-data" id="myinterview"
				action="addinterviewerdetails.do" method="post" autocomplete="off">
				<input type="hidden" name="admininterviewId" id="admininterviewId"
					value="${interviewid}">
				<div class="row">
					<div class="col-md-8">
						<div class="row">
							<div class="col-sm-12 col-md-12">
								<label>Applicant MailId</label>
								<div class="row">
									<div class="col-sm-6 col-md-6">
										<div class="form-group">
				
											<c:if test="${not empty profileDetails[0].emailId}">
												<input type="text" name="applicantName" class="form-control"
													readOnly id="applicantName" maxlength="60"
													value="${profileDetails[0].emailId}" />
											</c:if>
											<c:if test="${empty profileDetails[0].emailId}">
												<input type="text" name="applicantName" class="form-control"
													id="applicantName" maxlength="60"
													onkeyup="return getAutoSuggestApplicantNamesNames('applicantName','applicantId','uploadFile','autoSuggestUserName.do');"
													value="${profileDetails[0].emailId}" />
											</c:if>
											<input type="hidden" name="applicantId" id="applicantId"
												value="${profileDetails[0].userid}" />

											<div class="search_suggest" id="search_applicantName"></div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
								<label>Upload Resume</label>
								<div class="row">
									<div class="col-xs-12 col-sm-12 col-md-6">
										<div class="form-group">
											<input id="uploadFile" name="uploadFile" data-required="true"
												readonly value="${externalProfile[0].resume}"
												class="form-control" />
										</div>
									</div>
									<div class="col-xs-6 col-sm-3 col-md-3">

										<div
											style='background: url("resources/images/resumebrowse.png") no-repeat scroll 0% 0% transparent;'>
											<div class="form-group">
												<input class="form-control btn upploadbtn" type='file'
													id="resume" name="resume" class="form-control"
													value="${externalProfile[0].resume}"
													onchange="addfileName();" />
											</div>
										</div>
									</div>
								</div>

							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
								<div class="checkbox">
									<label><input name="update_resume" id="update_resume"
										value="true" type="checkbox">Would you like to update
										Resume in to your profile </label>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 col-md-12">
								<label>Industry</label>
								<div class="row">
									<div class="col-sm-6 col-md-6">
										<div class="form-group">
											<!-- onchange="showSubCategoryListInterview('domain','industry');showInterviewerRole('industry','interviewrole');"> -->
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
												name="domain" id="domain"
												onchange="enableOthersInterview('domain','otherDomainDiv');">
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
								<label>Are you a Fresher/ Campus Grad?</label>
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
								<label>Company Name</label>
								<div class="form-group" style="position: relative">
									<input type="text" name="companyname" class="form-control"
										id="companyname" maxlength="60"
										onkeyup="return getAutoSuggestNames('companyname','companyId','autoSuggestCompanyName.do');"
										value="${externalProfile[0].companynametxt}"> <input
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
										<label >Currency Type</label>
										<div class="form-group">											
											<select class="form-control" name="currencytype"
												id="currencytype">
												<option value="-1">Select</option>
												<c:forEach var="currencyLst" items="${currencytypeList}">
													<option value="${currencyLst.iplocation}"
														<c:if test="${currencyLst.iplocation==externalProfile[0].iplocation}">
													selected="selected"
													</c:if>>
														${currencyLst.currencytypename}</option>
												</c:forEach>
											</select>											
										</div>
									</div> 
								</div>
							</div>
						</div>
						<input type="hidden" name="location" id="location" value="1">
							<!-- user Interface showing taking more space -->
							 <!-- take locationid as 1 so we are passing value as hidden -->
						<%-- <div class="row">
							<div class="col-sm-12 col-md-12">
								<div class="row">									
									<div class="col-sm-6 col-md-6">
										<!-- uncomment below code when location select is needed -->
										<!-- <label style="visibility: hidden;">Interviewer's Location</label> -->
										<!-- comment below code when location select is needed -->
										<label style="visibility: hidden;">Interviewer's
											Location</label>
										<div class="form-group">
											<!-- uncomment below code when location select is needed -->
											<!-- <select class="form-control" name="location" id="location"> -->
											<!-- comment below code when location select is needed -->
											<select class="form-control" style="visibility: hidden;"
												name="location" id="location">
												<!-- uncomment below code when location select is needed -->
												<!-- <option value="-1">Select</option> -->
												<c:forEach var="interlocationLst"
													items="${interviewlocationsList}">
													<!-- comment below code when location select is needed -->
													<c:if test="${interlocationLst.locationid==1}">
														<option value="${interlocationLst.locationid}"
															selected="selected">${interlocationLst.locationname}</option>
													</c:if>
													<!-- uncomment below code when location select is needed -->
													<option value="${interlocationLst.locationid}"													
													<c:if test="${interlocationLst.locationid==externalProfile[0].interviewerlocationid}">
													selected="selected"
													</c:if>>
														${interlocationLst.locationname}</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>
							</div>
						</div> --%>
						<div class="row">
							<div class="col-sm-12 col-md-12">
								<div class="form-group">
									<label>Mode of Interview</label>
									<div class="row" id="intertype">
										<div class="col-xs-6 col-sm-4 col-md-4 ">
											<input type="radio" id="telephonic" name="telephonic"
												value="1" checked="checked"
												<c:if test="${externalProfile[0].interviewmodeid == '1'}"> checked </c:if>>
											Telephonic

										</div>
										<%-- <div class="col-xs-6 col-sm-4 col-md-4 ">
											<input type="radio" id="videoaudio" name="telephonic"
												value="2"
												<c:if test="${externalProfile[0].interviewmodeid == '2'}"> checked </c:if>>
											Video-Audio

										</div> --%>
										<div class="col-xs-6 col-sm-4 col-md-4 ">
											<input type="radio" id="videovideo" name="telephonic"
												value="3"
												<c:if test="${externalProfile[0].interviewmodeid == '3'}"> checked </c:if>>
											Video
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<hr>
				<input type="hidden" name="_dontcare">
				<c:if test="${empty profileDetails[0].emailId}">
					<div class="row">
						<div
							class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
							<div class="form-group">
								<button type="button" class="form-control mybackbtn" id="back"
									onclick="backToLanding();">Back</button>
							</div>
						</div>

						<div
							class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
							<div class="form-group">
								<button id="continue" type="submit"
									class="form-control mycontinuebtn">Continue</button>

							</div>
						</div>
					</div>
				</c:if>

				<c:if test="${not empty profileDetails[0].emailId}">
					<div class="row">
						<div
							class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-6">
							<div class="form-group">
								<button type="button" class="form-control mybackbtn" id="back"
									onclick="backToLanding();">Back</button>								
							</div>
						</div>

						<div
							class="col-xs-6 col-sm-3 col-sm-push-6 col-md-2 col-md-push-6">
							<div class="form-group">
								<button id="continue" type="button" onclick="oncancelBooking()"
									class="form-control mybackbtn">Cancel Interview</button>
							</div>
						</div>
						<div
							class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-6">
							<div class="form-group">
								<button id="continue" type="submit"
									class="form-control mycontinuebtn">Continue</button>

							</div>
						</div>
					</div>
				</c:if>


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
															applicantId : {
																excluded : false, // Don't ignore me
																validators : {
																	notEmpty : {
																		message : 'Please select the applicant name from dropdown.'
																	}
																}
															},
															industry : {
																validators : {
																	callback : {
																		message : "Please select listed industry.",
																		callback : function(
																				a,
																				b) {
																			var d = b
																					.getFieldElements(
																							"industry")
																					.val();
																			return -1 != d
																		}
																	}
																}
															},
															domain : {
																validators : {
																	callback : {
																		message : "Please select listed domain.",
																		callback : function(
																				a,
																				b) {
																			var d = b
																					.getFieldElements(
																							"domain")
																					.val();
																			return -1 != d
																					&& null != d
																		}
																	}
																}
															},
															interviewtype : {
																validators : {
																	callback : {
																		message : "Please select listed interview type.",
																		callback : function(
																				a,
																				b) {
																			var d = b
																					.getFieldElements(
																							"interviewtype")
																					.val();
																			return -1 != d
																		}
																	}
																}
															},
															interviewrole : {
																validators : {
																	callback : {
																		message : "Please select listed interviewer role.",
																		callback : function(
																				a,
																				b) {
																			var d = b
																					.getFieldElements(
																							"interviewrole")
																					.val();
																			return -1 != d
																		}
																	}
																}
															},
															currencytype : {
																validators : {
																	callback : {
																		message : "Please select listed currency type.",
																		callback : function(
																				a,
																				b) {
																			var d = b
																					.getFieldElements(
																							"currencytype")
																					.val();
																			return -1 != d
																		}
																	}
																}
															},
															resume : {
																validators : {
																	file : {
																		extension : "pdf,doc,docx",
																		type : "application/pdf,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document",
																		maxSize : 2097152,
																		message : "The selected file is not valid, it should be (pdf,doc,docx) and 2 MB at maximum."
																	}
																}
															}
														}
													}), $("#myinterview").on(
											"success.form.bv",
											function(a) {
												a.preventDefault(), 0 == myflag
														&& myajaxform()
											})
						});

		var myajaxform = function() {
			$("#processImg").show();
			var a = new FormData($("form")[0]);
			//	alert("hai");
			$.ajax({
				url : "tmiadminaddinterviewerdetails",
				type : "POST",
				xhr : function() {
					var a = $.ajaxSettings.xhr();
					return a.upload
							&& a.addEventListener("progress",
									progressHandlingFunction, !1), a
				},
				success : completeHandler,
				error : errorHandler,
				data : a,
				cache : !1,
				contentType : !1,
				processData : !1
			})
		}, errorHandler = function() {
		}, progressHandlingFunction = function() {
		};
		var completeHandler = function(data) {
			nextstep();
			$("#interviewBookingContainer").load('admintmislotBooking');
		};
		var errorHandler = function(data) {

		};
		var progressHandlingFunction = function(evt) {

		};
	</script>


	<script type="text/javascript">
		var myflag = 0;
		<c:if test="${externalProfile[0].industryid ne null}">
		jQuery(document).ready(function() {
			myflag = 1;
			$('#myinterview').data('bootstrapValidator').validate();
			myflag = 0;
		});
		</c:if>
	</script>

	<script type="text/javascript">
		jQuery(document).ready(
				function() {
					$('#myinterview').click(function() {
						$('#search_companyname').hide();
					});
					<c:if test="${not empty externalProfile[0].domainid}">
					showSubCategoryListInterviewnotOthers('domain', 'industry',
							'${externalProfile[0].domainid}');
					</c:if>
					<c:if test="${not empty externalProfile[0].interviewerroleid}">
					showInterviewerRolenotOthers('industry', 'interviewrole',
							'${externalProfile[0].interviewerroleid}');
					</c:if>
				});

		function oncancelBooking() {
			var interviewId = $('#admininterviewId').val();
			window.location.href = "tmiadminCancelInterview.do?interviewId="
					+ interviewId;

		}
		function backToLanding() {
			window.location.href = "tmiAdmCallCenterRepresentativeLanding.do";

		}
	</script>

	<script src="resources/js/interviewroles.js"></script>
</body>