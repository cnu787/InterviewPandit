<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" href="resources/css/interViewLand.css">
<style>
@media ( min-width : 768px) {
	.interview-dialog {
		width: 750px
	}
}
</style>
<body>
	<div
		style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
		<div class="container">
			<img src="resources/images/icon_interview.png">&nbsp;&nbsp;Interviews
		</div>
	</div>
	
				
	
	<div class="" style="margin-top: 20px;">
		<div class="container">
		
		
		<div class="row">			
					<div class="col-xs-6  col-sm-3  col-md-3">
				<c:if test="${not empty interviewList}">
					<a href="interviewBooking.do"><img
						src="resources/images/button_book_another2.png" /></a>
				</c:if>
				<c:if test="${empty interviewList}">
					<a href="interviewBooking.do"><img
						src="resources/images/button_book_now2.png" /></a>
				</c:if>			
			    </div>
				<c:if test="${mocktestStatus ne 0}">
				<div class="col-xs-7 col-sm-offset-6 col-sm-3 col-md-offset-6 col-md-3 topmargin">
					<a href="mockTestResultList.do"><button
							class="form-control mybackbtn">MOCK TEST RESULTS</button></a>
				</div> </c:if>
			</div>
			<input type="hidden" name="intervi" id="intervi"
				value="${interviewCount}">
				
			<hr>
			<div class="row">
				<div class="col-md-12">
					<c:if test="${ not empty interviewList}">
						<div class="modal" id="myModal" tabindex="-1" role="dialog"
							data-backdrop="static" aria-labelledby="myModalLabel"
							aria-hidden="true">
							<div class="modal-dialog interview-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title" id="myModalLabel">Interview
											Summary</h4>
									</div>
									<div class="modal-bodyTmi"></div>
									<div class="modal-footer">
										<div
											class="col-xs-6 col-xs-push-6 col-sm-4 col-sm-push-8 col-md-4 col-md-push-8">
											<button type="button" class="form-control mycontinuebtn"
												data-dismiss="modal">Close</button>
										</div>
									</div>
								</div>
							</div>
						</div>
						<br>
						<div class="row">
							<div class="col-md-4">
								<label><B>Click on</B>&nbsp;</label><img src="resources/images/icon_evaluators_feedback.png">&nbsp;<label>for Evaluator
									Feedback </label>
							</div>
							<div class="col-md-4">
								<img src="resources/images/icon_eval_interview_accepted.png">&nbsp;<label>Applicant
									Feedback </label>
							</div>
													
						</div>
						<br>
	
	
						<div class="row">
							<div class="col-md-12">
								<table>
									<thead>
										<tr>
											<th id="width1" Style="color: #FFF">Interview ID</th>
											<th id="width2" Style="color: #FFF">Industry</th>
											<th id="width3" Style="color: #FFF">Applied Position</th>
											<th id="width4" Style="color: #FFF">Interviewers Role</th>
											<th id="width5" Style="color: #FFF">Time Slot</th>
											<th id="width6" Style="color: #FFF">Status</th>
											<th id="width7" Style="color: #FFF">Feedback</th>
										</tr>
									</thead>
									<c:forEach var="intList" items="${interviewList}">
										<tr>
											<c:set var="string4" value="${fn:split(intList.date, ',')}" />
											<td class="myTmi" id="my${intList.interviewid}"><a
												href="#">${intList.interviewtmiid}</a></td>
											<td>${intList.industryname}</td>
											<td>${intList.designation}</td>
											<td>${intList.interviewerrolename}</td>
											<td>${string4[0]}|${intList.timeslot}</td>
											<td><c:if test="${intList.status ==1}">Booked</c:if> <c:if
													test="${intList.status == 2}">
													<c:if
														test="${intList.evaluatorfeedbackstatus ==1 && intList.applicantfeedbackstatus == 0}"> 
												Complete							
											</c:if>
													<c:if
														test="${intList.evaluatorfeedbackstatus == 0 && intList.applicantfeedbackstatus == 0}"> 
										Evaluator Pending Feedback 
											</c:if>
													<c:if
														test="${intList.evaluatorfeedbackstatus == 1 && intList.applicantfeedbackstatus == 1}"> 
											Closed
											</c:if>

												</c:if> <c:if test="${intList.status ==3}">Cancelled</c:if> <c:if
													test="${intList.status ==4}">Payment Cancelled</c:if> <c:if
													test="${intList.status ==5}">Payment Failure</c:if></td>
											<td id="test"><c:if test="${intList.status ==2}">
													<c:if test="${intList.evaluatorfeedbackstatus ==1}">
														<img src="resources/images/icon_evaluators_feedback.png"
															onclick="viewEvalcomm(${intList.interviewid})" />
														<img
															onclick="addInterViewFeedBack(${intList.interviewid})"
															src="resources/images/icon_eval_interview_accepted.png" />
													</c:if>
												</c:if></td>
										</tr>
									</c:forEach>

								</table>
							</div>
						</div>
						<br>
						<div class="row">
							<div class="col-md-4">
								<img src="resources/images/icon_evaluators_feedback.png">&nbsp;<label>Evaluator
									Feedback </label>
							</div>
							<div class="col-md-4">
								<img src="resources/images/icon_eval_interview_accepted.png">&nbsp;<label>Applicant
									Feedback </label>
							</div>
						</div>
					</c:if>
					<c:if test="${not empty interviewList}">
						<hr>
					</c:if>
					<div class="row">
						<div
							class="col-xs-8 col-xs-push-2 col-sm-4 col-sm-push-2 col-md-3 col-md-push-3">
							<div class="form-group" style="text-align: center;">
								<img src="resources/images/telephonic.png">
								<h3 class="interview-heading">TELEPHONIC INTERVIEWS</h3>
							</div>
						</div>
						<div
							class="col-xs-8 col-xs-push-2 col-sm-4 col-sm-push-2 col-md-3 col-md-push-3">
							<div class="form-group" style="text-align: center;">
								<img src="resources/images/video_video.png">
								<h3 class="interview-heading">VIDEO INTERVIEW</h3>
							</div>
						</div>
					</div>
					<hr>


				</div>
			</div>
		</div>
	</div>
	<script src="resources/js/interviewLand.js"></script>
	<div class="modal" id="myProfile" tabindex="-1" role="dialog"
		data-backdrop="static" aria-labelledby="myModalLabel"
		aria-hidden="true">
		<div class="modal-dialog mymodal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Profile details</h4>
				</div>
				<div class="modal-body">Please update your profile details</div>
				<div class="modal-footer">
					<div
						class="col-xs-4 col-xs-push-4 col-sm-4 col-sm-push-4 col-md-4 col-md-push-4">
						<button type="button" class="form-control mybackbtn"
							data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal" id="myinterviewFeedBack" tabindex="-1" role="dialog"
		data-backdrop="static" aria-labelledby="myModalLabel"
		aria-hidden="true">
		<div class="modal-dialog interview-dialog ">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myinterview">Evaluator Feedback</h4>
				</div>
				<div class="modal-bodyInterview"></div>
				<div class="modal-footer">
					<div
						class="col-xs-4 col-xs-push-8 col-sm-4 col-sm-push-8 col-md-4 col-md-push-8">
						<button type="button" class="form-control mycontinuebtn"
							data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal" id="interviewFeedBackApp" tabindex="-1"
		role="dialog" data-backdrop="static" aria-labelledby="myModalLabel"
		aria-hidden="true">
		<div class="modal-dialog interview-dialog ">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myinterview">Interview Feedback</h4>
				</div>
				<div class="modal-Interviewapp"></div>
				<div class="modal-footer">
					<div
						class="col-xs-4 col-xs-push-8 col-sm-4 col-sm-push-8 col-md-4 col-md-push-8">
						<button type="button" class="form-control mycontinuebtn"
							data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal" id="myinterviewFeedBackApp" tabindex="-1"
		role="dialog" data-backdrop="static" aria-labelledby="myModalLabel"
		aria-hidden="true">
		<div class="modal-dialog interview-dialog ">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" onclick="reload()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myinterview">Applicant Feedback</h4>
				</div>
				<div class="modal-bodyInterviewapp"></div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	function viewEvalcomm(interviewId){	
		$(".modal-bodyInterview").load("myInterviewFeedBack", {
			'interviewId' : interviewId
		}), $("#myinterviewFeedBack").modal({
			show : !0
		})
	}
	function addInterViewFeedBack(interviewId){		
		 $(".modal-bodyInterviewapp").load("applicantFeebBackSummary", {
			'interviewId' : interviewId
		}), $("#myinterviewFeedBackApp").modal({
			show : !0
		})
	}
	function reload(){
		 location.reload();
	}
	</script>
</body>