<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	jQuery(document).ready(function() {
		$("#evalUnavaillink").parent().addClass("active");
	});
	function backInterviewLanding() {
		window.location.href = "tmiAdmCallCenterRepresentativeLanding.do";
	}
</script>
<body>
	<div
		style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
		<div class="container">Home > Interviews</div>
	</div>
	<div class="" style="margin-top: 20px;">
		<div class="container">
			<div
				style="border: 1px solid #D3D3D3; background: none repeat scroll 0% 0% #F2F2F2; padding: 10px 20px">
				<form id="dashboardsearch" action="tmiAdmGetDashboardReport.do"
					method="post" autocomplete="off">
					<label>What are you looking for?</label>
					<div class="row">
						<div class="col-md-3">
							<div class="form-group ">
								<select class="form-control" name="dashboard" id="dashboard">
									<option value="-1">Select</option>
									<c:forEach var="dashboardLst" items="${dashboardsearchtypelkp}">
										<option value="${dashboardLst.dashboardsearchtypeid}"
											<c:if test="${dashboardLst.dashboardsearchtypeid==dashboardId}">
													selected="selected"
													</c:if>>
											${dashboardLst.dashboardsearchname}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group ">
								<input type="text" class="form-control" name="enterString"
									id="enterString" value="${enterString}" />
							</div>
						</div>
						<div class="col-md-2">
							<button type="submit" class="form-control mybackbtn">Search</button>
						</div>
					</div>
				</form>
				<script type="text/javascript">
					$(document)
							.ready(
									function() {
										$("#dashboardsearch")
												.bootstrapValidator(
														{
															feedbackIcons : {
																valid : "glyphicon glyphicon-ok",
																invalid : "glyphicon glyphicon-remove",
																validating : "glyphicon glyphicon-refresh"
															},
															fields : {
																dashboard : {
																	validators : {
																		callback : {
																			message : "Please select listed looking type.",
																			callback : function(
																					a,
																					b) {
																				var d = b
																						.getFieldElements(
																								"dashboard")
																						.val();
																				return -1 != d
																			}
																		}
																	}
																},
																enterString : {
																	validators : {
																		notEmpty : {
																			message : "Enter Name is required."
																		}
																	}
																}
															}
														})
									});
				</script>
			</div>
			<c:if test="${not empty userDetails}">
				<h1 class="header-file">
					<span style="color: #F26F21">Interview</span>:<span
						class="changecolor">${userDetails[0].interviewtmiid}</span>
				</h1>
				<hr>
				<div class="row">
					<div class="col-md-10">
						<div class="row">
							<div class="col-md-4">
								<label>Applicant Name</label>
							</div>
							<div class="col-md-4">
								<label style="color: #0095DA;">${userDetails[0].UserName}</label>
							</div>
						</div>
						<div class="row">
							<div class="col-md-4">
								<label>Industry</label>
							</div>
							<div class="col-md-4">
								<label class="changecolor">${userDetails[0].industryname}</label>
							</div>
						</div>
						<c:if test="${not empty domainList}">
							<div class="row">
								<div class="col-md-4">
									<label>Domain</label>
								</div>
								<c:set var="i" value="0" />
								<div class="col-md-8">
									<label class="changecolor"> <c:forEach var="domLst"
											items="${domainList}">
											<c:if test="${i != '0'}">
												<br>
											</c:if> 
								${domLst.domainname}
								<c:set var="i" value="${i+1}" />
										</c:forEach>
									</label>
								</div>
							</div>
						</c:if>
						<c:if test="${not empty userDetails[0].designation}">
						<div class="row">
							<div class="col-md-4">
								<label>Position</label>
							</div>
							<div class="col-md-4">
								<label class="changecolor">${userDetails[0].designation}</label>
							</div>
						</div>
						</c:if>
						<div class="row">
							<div class="col-md-4">
								<label>Role of Interviewer</label>
							</div>
							<div class="col-md-4">
								<label class="changecolor">${userDetails[0].typeofinterview}</label>
							</div>
						</div>
						<div class="row">
							<div class="col-md-4">
								<label>Location</label>
							</div>
							<div class="col-md-4">
								<label class="changecolor">${userDetails[0].locationname}</label>
							</div>
						</div>
						<div class="row">
							<div class="col-md-4">
								<label>Type of Interview</label>
							</div>
							<div class="col-md-4">
								<label class="changecolor">${userDetails[0].interviewtypename}</label>
							</div>
						</div>
						<div class="row">
							<div class="col-md-4">
								<label>Slot Booked</label>
							</div>
							<div class="col-md-4">
								<label class="changecolor">${userDetails[0].date}|${userDetails[0].timeslot}</label>
							</div>
						</div>
						<c:if test="${not empty skillsDetails}">
							<div class="row">
								<div class="col-md-4">
									<label>Skills for Evaluation</label>
								</div>
								<div class="col-md-4">
									<c:set var="j" value="0" />
									<label class="changecolor"> <c:forEach var="skillLst"
											items="${skillsDetails}">
											<c:if test="${j != '0'}">
												<br>
											</c:if> 
								${skillLst.skillname}
								<c:set var="j" value="${i+1}" />
										</c:forEach></label>
								</div>
							</div>
						</c:if>
					</div>
				</div>
			</c:if>
			<c:if test="${ empty userDetails && not empty enterString}">
				<hr>
				<div style="text-align: center;">
					<img style="width: 30px;" src="resources/images/warning.png" />&nbsp;
					No records available.
				</div>
			</c:if>
			<hr>
			<div class="row">
				<div
					class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-10">
					<div class="form-group">
						<button type="button" class="form-control mybackbtn" id="back" onclick="backInterviewLanding()">Back</button>
					</div>
				</div>
			
			</div>
		</div>
	</div>	
</body>