<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script src="resources/js/interviewrolesonready.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('.checkbox').hide();

		$.get("http://ipinfo.io", function(response) {
			if (response.country)
				$("#myCurrentLocation").val(response.country);
			$.ajax({'url':'interviewBookingLocation.do', 'data':{'myCurrentLocation' : response.country}});
		}, "jsonp");

	});
	function addfileName() {
		$('.checkbox').show();
		var filename = document.getElementById('resume').files[0].name;
		document.getElementById('uploadFile').value = filename;

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
				<input type="hidden" value="IN" name="myCurrentLocation"
					id="myCurrentLocation" />
				<div class="row">
					<div class="col-md-8">
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
											<select class="form-control" name="industry" id="industry"
												onchange="showSubCategoryListInterviewnotOthers('domain','industry');showInterviewerRoleWithRateCard('industry','interviewrole','','${externalProfile[0].interviewid}');">
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
									<div class="col-sm-4 col-md-4">
										<label>Interviewer's Role 
										<!-- <span
											class="glyphicon glyphicon-info-sign" data-html="true"
											data-toggle="tooltip" id="tooltip_id" data-placement="bottom"
											title="Select industry to get Rate Card"></span> -->
											</label>
										<div class="form-group">
											<select class="form-control" name="interviewrole"
												id="interviewrole"
												onchange="enableOthersInterviewRoles('interviewrole','otherDomainDiv');">
												<option value="-1">Select</option>
											</select>
										</div>
									</div>
									<div class="col-sm-4 col-md-4">
										<div class="form-group">
											<br><br><a href="#" onClick="showratecard();"><span style="color:blue">Rate Card</span></a>
										</div>
									</div>
									<div class="col-sm-4 col-md-4">
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
													<%-- <option value="${interlocationLst.locationid}"													
													<c:if test="${interlocationLst.locationid==externalProfile[0].interviewerlocationid}">
													selected="selected"
													</c:if>>
														${interlocationLst.locationname}</option> --%>
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
				<div class="row">
					<div
						class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
						<div class="form-group">
							<a href="interviewLanding.do"><button type="button"
									class="form-control mybackbtn" id="back">Back</button></a>
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
	<c:if test="${empty externalProfile[0].resume}">
		<script src="resources/js/interviewrolesemptyresume.js"></script>
	</c:if>
	<c:if test="${not empty externalProfile[0].resume}">
		<script src="resources/js/interviewrolesnonemptyresume.js"></script>
	</c:if>
	<script type="text/javascript">
		$(function() {
			$('[data-toggle="tooltip"]').tooltip();
		});
		var completeHandler = function(data) {
			$("#processImg").hide();
			nextstep();
			$("#interviewBookingContainer").load('interviewTechSkills');
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
					showInterviewerRoleWithRateCard('industry', 'interviewrole',
							'${externalProfile[0].interviewerroleid}',
							'${externalProfile[0].interviewid}');
					</c:if>

				});
		function showratecard(){
			 $("#myModalrate").modal({
				show : !0
			})
	
	}
	</script>
	<div id="myModalrate" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header" style="background:#000;height:10%">
       <button type="button" class="close" data-dismiss="modal" style="font-size:20px;opacity:1;hover:#fff">&times;close</button>
        <h4 class="modal-title">RateCard</h4>
      </div><br>
      <div class="modal-bodyrate">
        <table border="1" style="width:90%;margin:auto;">
        <tr bgcolor="#000" style="color:#fff">
        <th width="20%">Role</th><th width="20%">Rate per Interview</th><th width="40%">Description</th>
        </tr>
      	<c:forEach var="roleList" items="${rolelist}">
				<c:if test="${roleList.currencytypeid eq 1}"><tr>
					<td>${roleList.interviewerrolename}</td>
					<td><img src="resources\images\rupee.png" />&nbsp;${roleList.amount}</td>
					<td>${roleList.description}</td></tr></c:if></c:forEach>
       
        </table>
      </div>
      <div class="modal-footer">
      
      </div>
    </div>

  </div>
</div>
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
						<h4 class="modal-title " id="myModalLabel">Enter other
							industry name</h4>
					</div>
					<div class="modal-body ">
						<div class="row ">
							<div class="col-md-12 ">
								<div class="row">
									<div class="col-sm-12 col-md-12">
										<div class="row">
											<div class="col-sm-7 col-sm-push-3 col-md-7 col-md-push-3">
												<div class="form-group ">
													<label>Enter other industry name</label> <input type="text"
														class="form-control" name="otherIndustry"
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
								<div class="row">
									<div class="col-sm-12 col-md-12">
										<div class="row">
											<div class="col-sm-7 col-sm-push-3 col-md-7 col-md-push-3">
												<div class="form-group ">
													<label>Enter other interview role</label> <input
														type="text" name="otherinterviewroles"
														class="form-control" id="otherinterviewroles"
														maxlength="32">
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
						<h4 class="modal-title " id="myModalLabel">Enter other domain
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
	<form id="addotherinterviewrole" action="#" method="post"
		autocomplete="off">
		<div class="modal" id="myotherinterviewrole" tabindex="-1"
			data-backdrop="static" role="dialog" aria-labelledby="myModalLabel"
			aria-hidden="true">
			<div class="modal-dialog mymodal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							id="closeinterviewId" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title " id="myModalLabel">Enter other
							interview role</h4>
					</div>
					<div class="modal-body ">
						<div class="row ">
							<div class="col-md-12 ">
								<div class="row">
									<div class="col-sm-12 col-md-12">
										<div class="row">
											<div class="col-sm-7 col-sm-push-3 col-md-7 col-md-push-3">
												<div class="form-group ">
													<label>Enter other interview role</label> <input
														type="text" name="otherinterviewrole" class="form-control"
														id="otherinterviewrole" maxlength="32">
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
								id=closeinterviewrole>Close</button>
						</div>
						<div
							class="col-xs-4 col-xs-push-2 col-sm-4 col-sm-push-2 col-md-4 col-md-push-2">
							<button type="submit" class="form-control mycontinuebtn"
								id="saveotherinterviewrole">Save</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
	<link rel="stylesheet" href="resources/css/interviewroles.css" />
	<script src="resources/js/interviewroles.js"></script>
</body>