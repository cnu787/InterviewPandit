<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
	<div
		style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
		<div class="container">
			<img src="resources/images/icon_profile.png">&nbsp;&nbsp;Profile
		</div>
	</div>
	<div class="" style="margin-top: 20px;">
		<div class="container">
			<h2 class="profile-header">Where did you study?</h2>
			<h5 class="profile-text">If you have done multiple levels of
				education, we will ask about them one at a time</h5>
			<hr>
			<form id="myedu" action="#" method="post" autocomplete="off">
				<input type="hidden" name="edit" id="edit" value="${edit}">
				<input type="hidden" name="educationid" id="educationid"
					value="${updateeducationList[0].educationid}"> <input
					type="hidden" name="previousdegreeid" id="previousdegreeid"
					value="${updateeducationList[0].degreeid}"> <input
					type="hidden" name="previousuniversityid" id="previousuniversityid"
					value="${updateeducationList[0].universityid}">
				<div class="row">
					<div class="col-md-7">
						<div class="row">
							<div class="col-sm-12 col-md-12">
								<div class="form-group">
									<label>School/College/University</label> <input type="text"
										name="universityname" class="form-control" id="universityname"
										maxlength="80"
										onkeyup="return getAutoSuggestNames('universityname','universityid','autoSuggestUniversityNames.do');"
										value="${updateeducationList[0].universityname}" /> <input
										type="hidden" readonly name="universityid" id="universityid"
										value="${updateeducationList[0].universityid}" />
									<div class="search_suggest" id="search_universityname"></div>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-sm-12 col-md-12">
								<label>Graduated Year</label>
								<div class="row">
									<div class="col-sm-6 col-md-6 col-xs-6 ">
										<div class="form-group">
											<select class="form-control" name="graduateyears"
												id="graduateyears">
												<option value="-1">Year</option>
												<c:forEach var="yearsLst" items="${yearsList}">
													<option value="${yearsLst.graduateId}"
														<c:if test="${yearsLst.graduateId==updateeducationList[0].graduatedyear}">
													selected="selected"
													</c:if>>
														${yearsLst.graduateyear}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-sm-6 col-md-6 col-xs-6">
										<div class="form-group">
											<select class="form-control" name="graduatemonth"
												id="graduatemonth">
												<option value="-1">Month</option>
												<c:forEach var="monthLst" items="${monthsList}">
													<option value="${monthLst.monthid}"
														<c:if test="${monthLst.monthid==updateeducationList[0].graduatedmonth}">
													selected="selected"
													</c:if>>
														${monthLst.	months}</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>

							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 col-md-12">
								<div class="form-group">
									<label>Degree</label> <input type="text" name="degreename"
										class="form-control" id="degreename" maxlength="80"
										onkeyup="return getAutoSuggestNames('degreename','degreeid','autoSuggestDegreeName.do');"
										value="${updateeducationList[0].degreename}" /> <input
										type="hidden" readonly name="degreeid" id="degreeid"
										value="${updateeducationList[0].degreeid}" />
									<div class="search_suggest" id="search_degreename"></div>

								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 col-md-12">
								<div class="form-group">
									<label>Field of Study</label><input type="text"
										class="form-control" name="study" id="study" maxlength="60"
										value="${updateeducationList[0].fieldofstudy}">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 col-md-12">
								<div class="form-group">
									<label>Grade</label> <input type="text" class="form-control"
										name="schoolName" id="schoolName" maxlength="60"
										value="${updateeducationList[0].institutename}">
								</div>
							</div>
						</div>
					</div>
				</div>
				<hr>
				<div class="row">
					<c:if test="${empty param.edit}">
						<div
							class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
							<div class="form-group">
								<button type="button" class="form-control mybackbtn" id="back">Back</button>
							</div>
						</div>
						<div
							class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
							<div class="form-group">
								<input id="continue" type="submit"
									class="form-control mycontinuebtn" value="Continue" />

							</div>
						</div>
					</c:if>
					<c:if test="${param.edit==true}">
						<div
							class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
							<div class="form-group">
								<button type="button" class="form-control mybackbtn"
									onclick="window.location.href='studyPage.do?edit=true';"
									id="back">Cancel</button>
							</div>
						</div>
						<div
							class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
							<div class="form-group">
								<button id="continue" type="submit"
									class="form-control mycontinuebtn">Save</button>

							</div>
						</div>
					</c:if>
				</div>
			</form>
		</div>
		<script src="resources/js/applicantstudyonready.js"></script>
		<c:if test="${(empty edit)}">
			<script src="resources/js/applicantstudynonedit.js"></script>
		</c:if>
		<c:if test="${(not empty edit)}">
			<script src="resources/js/applicantstudyedit.js"></script>
		</c:if>
	</div>
	<script src="resources/js/applicantstudySuceess.js"></script>
	<Script>
		jQuery(document).ready(function() {
			/* $.ajaxSetup ({cache: false}); */

			$('#myedu').click(function() {
				$('#search_universityname').hide();
				$('#search_degreename').hide();
			});
		});
		var completeHandler = function(data) {
			$("#processImg").hide();
			if ('${param.edit}' == "true") {
				window.location.href = "studyPage.do?edit=true";
			} else {
				$("#mainProfileContainer").load('studyPage');
			}
		};
	</Script>
</body>