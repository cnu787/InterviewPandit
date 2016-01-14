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
			<h2 class="profile-header">Complete Your Career details</h2>
			<h5 class="profile-text">If you have worked at multiple
				organizations, you can add them now or edit later.</h5>
			<hr>
			<form id="myprofile" action="#" method="post" autocomplete="off">
				<input type="hidden" name="edit" id="edit" value="${edit}">
				<input type="hidden" name="careerid" id="careerid"
					value="${updatecarrerList[0].careerid}"> <input
					type="hidden" name="previousCompanyId" id="previousCompanyId"
					value="${updatecarrerList[0].companyname}">
				<div class="row">
					<div class="col-md-7">
						<div class="row">
							<div class="col-sm-12 col-md-12">
								<div class="form-group">
									<label>Company Name</label> <input type="text"
										name="companyname" class="form-control" id="companyname"
										maxlength="60"
										onkeyup="return getAutoSuggestNames('companyname','companyId','autoSuggestCompanyName.do');"
										value="${updatecarrerList[0].companynametxt}" /> <input
										type="hidden" readonly name="companyId" id="companyId"
										value="${updatecarrerList[0].companyname}" />
									<div class="search_suggest" id="search_companyname"></div>

								</div>


							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 col-md-12">
								<div class="form-group">
									<label>Position/Designation</label> <input type="text"
										class="form-control" name="position" id="position"
										maxlength="50" value="${updatecarrerList[0].designation}">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 col-md-12">
								<div class="form-group">
									<label>Location</label> <input type="text" class="form-control"
										name="location" id="location" maxlength="50"
										value="${updatecarrerList[0].location}">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 col-md-12">
								<div class="row">
									<div class="col-sm-5 col-md-5 ">
										<label>Time From</label>
										<div class="row">
											<div class="col-xs-5 col-sm-5 col-md-5 ">
												<div class="form-group">
													<select class="form-control" name="month" id="month">
														<option value="-1">Month</option>
														<c:forEach var="monthLst" items="${monthsList}">
															<option value="${monthLst.monthid}"
																<c:if test="${monthLst.monthid==updatecarrerList[0].frommonthid}">
													selected="selected"
													</c:if>>
																${monthLst.months}</option>
														</c:forEach>
													</select>

												</div>
											</div>
											<div class="col-xs-5 col-sm-5 col-md-5 ">
												<div class="form-group">
													<select class="form-control" name="year" id="year">
														<option value="-1">Year</option>
														<c:forEach var="yearsLst" items="${yearsList}">
															<option value="${yearsLst.graduateId}"
																<c:if test="${yearsLst.graduateId==updatecarrerList[0].fromyearid}">
													selected="selected"
													</c:if>>
																${yearsLst.graduateyear}</option>
														</c:forEach>
													</select>
												</div>
											</div>
										</div>
									</div>

									<div class="col-sm-5 col-md-5 ">
										<label>Time To</label>
										<div class="row">
											<div class="col-xs-5 col-sm-5 col-md-5 ">
												<div class="form-group">
													<select class="form-control" name="expmonth" id="expmonth">
														<option value="-1">Month</option>
														<c:forEach var="monthLst" items="${monthsList}">
															<option value="${monthLst.monthid}"
																<c:if test="${monthLst.monthid==updatecarrerList[0].tomonthid}">
													selected="selected"
													</c:if>>
																${monthLst.months}</option>
														</c:forEach>
													</select>

												</div>
											</div>
											
											<div class="col-xs-5 col-sm-5 col-md-5">
												<div class="form-group">
													<select class="form-control" name="expryear" id="expryear">
														<option value="-1">Year</option>
														<c:forEach var="yearsLst" items="${yearsList}">
															<option value="${yearsLst.graduateId}"
																<c:if test="${yearsLst.graduateId==updatecarrerList[0].toyearid}">
													selected="selected"
													</c:if>>
																${yearsLst.graduateyear}</option>
														</c:forEach>
													</select>
												</div>
											</div>
											
										</div>
									</div>
									<div class="col-sm-2 col-md-2 ">
										<label>Till date</label>
									<div class="col-xs-2 col-sm-2 col-md-2 ">
											<label> <input type="checkbox" name="tilldate"
												id="tilldate" value="1"> 
											</label>
										</div></div>
								</div>
							</div>
						</div>
						<div class="row">
						<input type="hidden" id="expyear" name="expyear" value="0">
						<input type="hidden" id="exprmnth" name="exprmnth" value="0"> 
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
								<input type="submit" id="continue"
									class="form-control mycontinuebtn" value="Continue" />
							</div>
						</div>
					</c:if>
					<c:if test="${param.edit==true}">
						<div
							class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
							<div class="form-group">
								<button type="button" class="form-control mybackbtn"
									onclick="window.location.href='careerView.do?edit=true';"
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
	<form id="addothercompanyName" action="#" method="post"
		autocomplete="off">
		<div class="modal" id="myothercompanyName" tabindex="-1"
			data-backdrop="static" role="dialog" aria-labelledby="myModalLabel"
			aria-hidden="true">
			<div class="modal-dialog mymodal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							id="closecompanyId" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title " id="myModalLabel">Enter
							other company name</h4>
					</div>
					<div class="modal-body ">
						<div class="row ">
							<div class="col-md-12 ">
								<div class="row">
									<div class="col-sm-12 col-md-12">
										<div class="row">
											<div class="col-sm-7 col-sm-push-3 col-md-7 col-md-push-3">
												<div class="form-group ">
													<label>Enter other company name</label> <input type="text"
														name="othercompanyName" class="form-control"
														id="othercompanyName" maxlength="32">
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
								id="closecompanyname">Close</button>
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
	<script type="text/javascript">
		jQuery(document).ready(function() {
			$('#myprofile').click(function() {
				$('#search_companyname').hide();
			});
			$("#profilelink").parent().addClass("active");
			$("#back").click(function() {
				if ("${mySesProfile[0].usertypeid}" == 1) {
					$("#mainProfileContainer").load('career');
				}
				if ("${mySesProfile[0].usertypeid}" == 2) {
					$("#mainProfileContainer").load('studyPage');
				}

			});

		});
	</script>
	<script src="resources/js/careermaponready.js"></script>
	<c:if test="${(empty edit) && (mySesProfile[0].usertypeid==1)}">
		<script src="resources/js/careermapnonedit.js"></script>
	</c:if>
	<c:if test="${(not empty edit) || (mySesProfile[0].usertypeid==2)}">
		<script src="resources/js/careermapedit.js"></script>
	</c:if>
	<script type="text/javascript">
		var completeHandler = function(data) {
			$("#processImg").hide();
			if ('${param.edit}' == "true") {
				window.location.href = "careerView.do?edit=true";
			} else {
				$("#mainProfileContainer").load('careerView');
			}
		};
		
		function toggleSelection(e){
		    var el = '#' + e.data;
		    console.log(arguments);
		    if (this.checked) {
		        $(el).attr('disabled', 'disabled');
		    } else {
		        $(el).removeAttr('disabled');
		    }
		}

		$(function() {
		    $('#tilldate').click('expryear', toggleSelection);
		    $('#tilldate').click('expmonth', toggleSelection);

		});
	</script>
</body>