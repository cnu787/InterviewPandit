<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
	<div class="" style="margin-top: 20px;">
		<div class="container">
			<c:if test="${not empty userDetails}">
				<form id="modal-form" action="interviewModals.do">
					<!-- Modal -->



					<div class="row">
						<div class="col-sm-12 col-md-8">
							<div class="row">
								<div class="col-md-5">
									<label>Applicant Name</label>
								</div>
								<div class="col-md-6">
									<label style="color: #0095DA;">${userDetails[0].UserName}</label>
								</div>
							</div>
							<div class="row">
								<div class="col-md-5">
									<label>Industry</label>
								</div>
								<div class="col-md-6">
									<label>${userDetails[0].industryname}</label>
								</div>
							</div>
							<c:if test="${not empty domainList}">
								<div class="row">
									<div class="col-md-5">
										<label>Domain</label>
									</div>
									<c:set var="i" value="0" />
									<div class="col-md-6">
										<label> <c:forEach var="domLst" items="${domainList}">
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
								<div class="col-md-5">
									<label>Position</label>
								</div>
								<div class="col-md-6">
									<label>${userDetails[0].designation}</label>
								</div>
							</div>
							</c:if>
							<div class="row">
								<div class="col-md-5">
									<label>Role of Interviewer</label>
								</div>
								<div class="col-md-6">
									<label>${userDetails[0].typeofinterview}</label>
								</div>
							</div>
							<div class="row">
								<div class="col-md-5">
									<label>Location</label>
								</div>
								<div class="col-md-6">
									<label>${userDetails[0].locationname}</label>
								</div>
							</div>
							<div class="row">
								<div class="col-md-5">
									<label>Type of Interview</label>
								</div>
								<div class="col-md-6">
									<label>${userDetails[0].interviewtypename}</label>
								</div>
							</div>
							<div class="row">
								<div class="col-md-5">
									<label>Slot Booked</label>
								</div>
								<div class="col-md-6">
									<label>${userDetails[0].date}|${userDetails[0].timeslot}</label>
								</div>
							</div>
							<c:if test="${not empty skillsDetails}">
								<div class="row">
									<div class="col-md-5">
										<label>Skills for Evaluation</label>
									</div>
									<div class="col-md-6">
										<c:set var="j" value="0" />
										<label> <c:forEach var="skillLst"
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
							<hr>
						</div>

					</div>
					<div class="row">
						<div
							class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-4 col-md-2 col-md-push-5">
							<div class="form-group">
								<button type="button" class="form-control mybackbtn"
									data-dismiss="modal">Close</button>
							</div>
						</div>
					</div>

				</form>
			</c:if>
		</div>
	</div>
	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$('#modal-form')
									.bootstrapValidator(
											{
												framework : 'bootstrap',
												feedbackIcons : {
													valid : 'glyphicon glyphicon-ok',
													invalid : 'glyphicon glyphicon-remove',
													validating : 'glyphicon glyphicon-refresh'
												},
												fields : {
													genre : {
														validators : {
															notEmpty : {
																message : 'The Meeting Invite is required'
															}
														}
													},
													Comments : {
														validators : {
															notEmpty : {
																message : 'The Comments is required.'
															},
															stringLength : {
																max : 80,
																message : 'The content must be less than 80 characters long'
															}
														}
													}
												}
											});
						});
	</script>
</body>