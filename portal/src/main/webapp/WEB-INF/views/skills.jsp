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
			<h2 class="profile-header">Tell us more about your Skills</h2>
			<c:if test="${empty edit}">
				<h5 class="profile-text">Please set your domain and industry in
					your profile summary to view the options for technical skills
					selection.</h5>
			</c:if>
			<hr>
			<form id="myedu" action="#" method="post" autocomplete="off">
				<input type="hidden" name="edit" id="edit" value="${edit}">
				<input type="hidden" name="Skillid" id="Skillid"
					value="${updateskillList[0].skillid}">
				<div class="row">
					<div class="col-md-7">
						<div class="row">
							<div class="col-sm-12 col-md-12">
								<label>Skill Type</label>
								<div class="row">
									<div class="col-sm-6 col-md-6">
										<div class="form-group">
											<select class="form-control" name="skilltype" id="skilltype"
												onchange="showSkillListProfile('skillname','skilltype','otherskillDiv','otherSkillnameDiv','${industryId}');">
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
									<div class="col-sm-6 col-md-6">
										<div class="form-group" id="otherskillDiv"
											<c:if test="${updateskillList[0].skilltypeid==0}">
											style="display:block;"
											</c:if>
											<c:if test="${updateskillList[0].skilltypeid!=0}">
											style="display:none;"
											</c:if>>
											<input type="text" class="form-control" name="otherSkillType"
												id="otherSkillType" maxlength="50"
												value="${updateskillList[0].otherSkillType}">
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
											<select class="form-control" name="skillname" id="skillname"
												onchange="enableOthersFieldSkills('skillname','otherSkillnameDiv');">
												<option value="-1">Select</option>
											</select>
										</div>
									</div>
									<div class="col-sm-6 col-md-6">
										<div class="form-group" id="otherSkillnameDiv"
											<c:if test="${updateskillList[0].skillnameid==0}">
											style="display:block;"
											</c:if>
											<c:if test="${updateskillList[0].skillnameid!=0}">
											style="display:none;"
											</c:if>>
											<input type="text" class="form-control" name="otherSkillName"
												id="otherSkillName" maxlength="50"
												value="${updateskillList[0].otherSkillName}">
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
								<button id="continue" type="submit"
									class="form-control mycontinuebtn">Continue</button>

							</div>
						</div>
					</c:if>
					<c:if test="${param.edit==true}">
						<div
							class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
							<div class="form-group">
								<button type="button" class="form-control mybackbtn"
									onclick="window.location.href='skillsView.do?edit=true';"
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
	</div>
	<c:if test="${careerId ==0}">
		<script src="resources/js/skilloncareer.js"></script>
	</c:if>
	<c:if test="${careerId == 1}">
		<script src="resources/js/skilloncareerid.js"></script>
	</c:if>
	<script src="resources/js/skillsonready.js"></script>
	<c:if test="${(empty edit) && (mySesProfile[0].usertypeid==1)}">
		<script src="resources/js/skillnonedit.js"></script>
	</c:if>
	<c:if test="${(not empty edit) || (mySesProfile[0].usertypeid==2)}">
		<script src="resources/js/skilledit.js"></script>
	</c:if>
	<script type="text/javascript">
		var completeHandler = function(data) {
			$("#processImg").hide();
			if ('${param.edit}' == "true") {
				window.location.href = "skillsView.do?edit=true";
			} else {
				$("#mainProfileContainer").load('skillsView');
			}
		};
	</script>
	<script type="text/javascript">
		<c:if test="${not empty edit}">
		jQuery(document).ready(function() {
			myflag = 1;
			$('#myedu').data('bootstrapValidator').validate();
			myflag = 0;
		});
		</c:if>
	</script>
	<script type="text/javascript">
		jQuery(document).ready(
				function() {
					<c:if test="${not empty updateskillList}">
					showSkillListProfile('skillname', 'skilltype',
							'otherskillDiv', 'otherSkillnameDiv',
							'${industryId}',
							'${updateskillList[0].skillnameid}');

					</c:if>

				});
	</script>
	<form id="myotherSkills" action="#" method="post" autocomplete="off">
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
								id="othercloseId">Close</button>
						</div>
						<div
							class="col-xs-4 col-xs-push-2 col-sm-4 col-sm-push-2 col-md-4 col-md-push-2">
							<button type="submit" class="form-control mycontinuebtn "
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
							<button type="button" class="form-control mybackbtn"
								data-dismiss="modal" id=closeIdName>Close</button>
						</div>
						<div
							class="col-xs-4 col-xs-push-2 col-sm-4 col-sm-push-2 col-md-4 col-md-push-2">
							<button type="submit" class="form-control mycontinuebtn "
								id="otherSkillNameId">Save</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
	<link rel="stylesheet" href="resources/css/skill.css" />
	<script src="resources/js/skillsmyajax.js"></script>
</body>