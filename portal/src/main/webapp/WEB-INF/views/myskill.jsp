<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>

	<div id="mainInterviewContainer" style="margin-top: 20px;">
		<div class="container">
			<h1 class="header-file">My Skills</h1>
			<h4 class="header-text">
				<span style="color: #0095DA;">Please define your skills to
					help us evaluate you better. We will ask one skill at a time. </span><span
					style="color: #58574b;">All fields are mandatory</span>
			</h4>
			<hr>
			<form id="myInterSkillForm" action="#" autocomplete="off">
				<input type="hidden" name="interviewsparam" id="interviewsparam"
					value="${interviewsparam}"> <input type="hidden"
					name="interviewid" id="interviewid" value="${interviewid}">
				<input type="hidden" name="edit" id="edit" value="${edit}">
				<input type="hidden" name="Skillid" id="Skillid"
					value="${updateskillList[0].skillid}"> <input type="hidden"
					name="interviewskillid" id="interviewskillid"
					value="${updateskillList[0].interviewskillid}">
				<div class="row">
					<div class="col-md-7">
						<div class="row">
							<div class="col-sm-12 col-md-12">
								<label>Skill Type</label>
								<div class="row">
									<div class="col-sm-6 col-md-6">
										<div class="form-group">
											<select class="form-control" name="skilltype" id="skilltype"
												onchange="showSkillList('skillname','skilltype','empty','${industryId}');">
												<option value="-1">Select</option>
												<c:forEach var="skilltypes" items="${skilltypeList}">
													<option value="${skilltypes.skilltypeid}"
														<c:if test="${skilltypes.skilltypeid==updateskillList[0].skilltypeid}">
													selected="selected"
													</c:if>>
														${skilltypes.skilltypename}</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 col-md-12">
								<label>Skill Name</label>
								<div class="row">
									<div class="col-sm-6 col-md-6">
										<div class="form-group">
											<select class="form-control" name="skillname" id="skillname">
												<option value="-1">Select</option>
											</select>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 col-md-12">
								<div class="row">
									<div class="col-sm-6 col-md-6 ">
										<label>Skill Rating</label>
										<div class="row">
											<div class="col-xs-10 col-sm-12 col-md-12 ">
												<div class="form-group">
													<select class="form-control" name="skillrating"
														id="skillrating">
														<option value="-1">Select</option>
														<c:forEach var="skills" items="${skillratinglkp}">
															<option value="${skills.ratingid}"
																<c:if test="${skills.ratingid==updateskillList[0].skillrating}">
													selected="selected"
													</c:if>>
																${skills.rating}</option>
														</c:forEach>
													</select>
												</div>
											</div>
										</div>
									</div>
									<div class="col-sm-6 col-md-6 ">
										<label>Years of Experience</label>
										<div class="row">
											<div class="col-xs-5 col-sm-6 col-md-6 ">
												<div class="form-group">
													<select class="form-control" name="exprienceyear"
														id="exprienceyear">
														<option value="-1">Year</option>
														<c:forEach var="yearsLst" items="${years}">
															<option value="${yearsLst.yearid}"
																<c:if test="${yearsLst.yearid==updateskillList[0].year}">
													selected="selected"
													</c:if>>
																${yearsLst.year}</option>
														</c:forEach>
													</select>
												</div>
											</div>
											<div class="col-xs-5 col-sm-6 col-md-6 ">
												<div class="form-group">
													<select class="form-control" name="expriencemonth"
														id="expriencemonth">
														<option value="-1">Month</option>
														<c:forEach var="monthLst" items="${monthsList}">
															<option value="${monthLst.monthid}"
																<c:if test="${monthLst.monthid==updateskillList[0].month}">
													selected="selected"
													</c:if>>
																${monthLst.monthid}</option>
														</c:forEach>
													</select>
												</div>
											</div>
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
							<button type="button" class="form-control mybackbtn"
								onclick="backSkills()" id="back">Back</button>
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
			</form>
		</div>
	</div>
	<script src="resources/js/myskill.js"></script>
	<script type="text/javascript">
		var myflag = 0;
		<c:if test="${not empty edit}">
		jQuery(document).ready(function() {
			myflag = 1;
			$('#myInterSkillForm').data('bootstrapValidator').validate();
			myflag = 0;
		});
		</c:if>
	</script>

	<script type="text/javascript">
		jQuery(document).ready(
				function() {
					<c:if test="${not empty updateskillList}">
					showSkillList('skillname', 'skilltype',
							'${updateskillList[0].skillnameid}',
							'${industryId}');

					</c:if>
				});
	</script>
	<form id="myotherSkills" action="#" method="post" autocomplete="off">
		<div class="modal" id="myModal" tabindex="-1" role="dialog"
			data-backdrop="static" aria-labelledby="myModalLabel"
			aria-hidden="true">
			<div class="modal-dialog  mymodal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">Enter other skill
							type</h4>
					</div>
					<div class="modal-body ">
						<div class="row ">
							<div class="col-md-12 ">
								<div class="row">
									<div class="col-sm-12 col-md-12">
										<div class="row">
											<div class="col-sm-7 col-sm-push-3 col-md-7 col-md-push-3">
												<div class="form-group ">
													<label>Enter other skill type</label> <input type="text"
														class="form-control" name="otherTypeName"
														id="otherTypeName">
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
													<label>Enter other skill name</label> <input type="text"
														class="form-control" name="otherName" id="otherName">
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
	<form id="myotherSkillsName" action="#" method="post"
		autocomplete="off">
		<div class="modal" id="myModalOthersName" tabindex="-1"
			data-backdrop="static" role="dialog" aria-labelledby="myModalLabel"
			aria-hidden="true">
			<div class="modal-dialog mymodal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							id="closeIdName" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">Enter other skill
							name</h4>
					</div>
					<div class="modal-body ">


						<div class="row ">
							<div class="col-md-12 ">
								<div class="row">
									<div class="col-sm-12 col-md-12">
										<div class="row">
											<div class="col-sm-7 col-sm-push-3 col-md-7 col-md-push-3">
												<div class="form-group ">
													<label>Enter other skill name</label> <input type="text"
														class="form-control" name="otherNameOnly"
														id="otherNameOnly">
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
							<div class="form-group">
								<button type="button" class="form-control mybackbtn"
									data-dismiss="modal" id=closeIdName>Close</button>
							</div>
						</div>
						<div
							class="col-xs-4 col-xs-push-2 col-sm-4 col-sm-push-2 col-md-4 col-md-push-2">
							<div class="form-group">
								<button type="submit" class="form-control mycontinuebtn"
									id="otherSkillNameId">Save</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
	<link rel="stylesheet" href="resources/css/skill.css" />
</body>