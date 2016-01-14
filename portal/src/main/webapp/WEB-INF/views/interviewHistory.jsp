<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="resources/css/intlTelInput.css">
<link rel="stylesheet" href="resources/css/internalcalender.css">
<script src="resources/js/intlTelInput.js"></script>
<script type="text/javascript">
var slotscheduleid=null;
var myinterviewid=null;
var mystarttime=null;
	jQuery(document).ready(
			function() {						
				$("#interviewhisLink").parent().addClass("active");
				$(".appTmi").click(function() {
							var a = $(this).attr("id");
							$(".modal-bodytmi").load("myInterviewSummary", {
								'interviewId' : a.replace("my", "")
							}), $("#myModaltmi").modal({
								show : !0
							})
						});
				$(".appid")	.click(function() {
					var a = $(this).attr("id");
					$(".modal-bodyprofile").load("completeProfile", {
						'userId' : a.replace("user", "")
					}), $("#myModalprofile").modal({
						show : !0
					})
				});
			});
	function reload(){		 
		   location.reload(); 
	}
	function accept(slotid){
		  $.ajax({
				url : "acceptInterView.do/"+slotid,
				async : false,
				success : function(result) {
					var modal = $('#myModal').modal({keyboard: false,show:true});
					  modal.find('.modal-title').text('Alert!');
					  modal.find('.modal-body').text('Thanks for accepting the interview request.');
					
				}
		  });
		  
	}
function reject(stime,slotid,intid){
		slotscheduleid=slotid;
		myinterviewid=intid;
		mystarttime=stime
		$('#evalRejectSlotModal').modal({keyboard: false,show:true});		
	}
	function viewEvalcomm(interviewId){		
		$(".modal-bodyInterview").load("evaluatorSummary", {
			'interviewId' : interviewId
		}), $("#myinterviewFeedBack").modal({keyboard: false,show : true})
	}
	</script>
<style>
.tmilejend {
	height: 16px;
	width: 16px;
	margin-right: 10px;
}

hr {
	border-color: #57574B;
}

@media ( min-width : 768px) {
	.interview-dialog {
		width: 750px
	}
}

@media ( min-width : 767px) {
	#pagination-right {
		float: right;
	}
	.form-grouping {
		margin-top: 10px;
	}
}
</style>
<body>
	<div
		style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
		<div class="container">
			<img src="resources/images/icon_calendar_logo.png">&nbsp;&nbsp;My
			Interview History
		</div>
	</div>

	<div id="myInterviewHistorySection" style="margin-top: 20px;">
		<div class="container">
			<c:if test="${not empty myScheduleBookings}">
				<div class="modal" id="myModaltmi" tabindex="-1" role="dialog"
					data-backdrop="static" aria-labelledby="myModalLabel"
					aria-hidden="true">
					<div class="modal-dialog interview-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title" id="myModalLabel">Interview Summary</h4>
							</div>
							<div class="modal-bodytmi"></div>
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
				<div class="modal" id="myModalprofile" tabindex="-1" role="dialog"
					data-backdrop="static" aria-labelledby="myModalLabel"
					aria-hidden="true">
					<div class="modal-dialog interview-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title" id="myModalLabel">Profile Summary</h4>
							</div>
							<div class="modal-bodyprofile"></div>
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
				<div class="interView">
					<table class="interviewHistory">
						<thead class="interviewthead">
							<tr class="interviewtr">
								<th class="interviewth" id="width1" Style="color: #FFF">Interview
									ID</th>
								<th class="interviewth" id="width2" Style="color: #FFF;">Screen
									Name</th>
								<th class="interviewth" id="width3" Style="color: #FFF">Applied
									Position</th>
								<th class="interviewth" id="width4" Style="color: #FFF">Interview
									Type</th>
								<th class="interviewth" id="width5" Style="color: #FFF">Time
									Slot</th>
								<th class="interviewth" id="width6" Style="color: #FFF">Status</th>
								<th class="interviewth" id="width7" Style="color: #FFF">Feedback</th>
							</tr>
						</thead>
						<tbody class="interviewttbody">
							<c:forEach var="schedule" items="${myScheduleBookings}">
								<tr class="interviewtr">
									<td class="interviewtd appTmi" id="my${schedule.interviewid}"><a
										href="#">${schedule.interviewtmiid}</a></td>
									<td class="interviewtd appid" id="user${schedule.userid}"><a
										href="#">${schedule.screenName}</a></td>
									<td class="interviewtd">${schedule.designation}</td>
									<td class="interviewtd">${schedule.interviewtypename}</td>
									<td class="interviewtd">${schedule.slottime}</td>
									<td class="interviewtd"><c:if test="${schedule.slotstatus == 2 && schedule.interviewstatus !=3}">
											<a style="color: #0094DA" href="#"
												onclick="accept(${schedule.slotscheduleid});">Accept</a>&nbsp;
										<a style="color: red;" href="#"
												onclick="reject('${schedule.starttime}','${schedule.slotscheduleid}','${schedule.interviewid}');">Reject</a>
										</c:if> <c:if test="${schedule.slotstatus == 3}">Rejected</c:if> <c:if
											test="${schedule.slotstatus == 4}">
											<c:if test="${schedule.interviewstatus == 1}">Scheduled
										</c:if>
											<c:if test="${schedule.interviewstatus == 2}">
												<c:if test="${schedule.evaluatorfeedbackstatus == 0}"> 
										Evaluator Pending Feedback 
											</c:if>
												<c:if
													test="${schedule.evaluatorfeedbackstatus == 1 && schedule.applicantfeedbackstatus == 0}"> 
										Complete
											</c:if>
												<c:if
													test="${schedule.evaluatorfeedbackstatus == 1 && schedule.applicantfeedbackstatus == 1}"> 
											Closed
											</c:if>

											</c:if>
										</c:if></td>
									<td class="interviewtd"><c:if
											test="${schedule.slotstatus == 4}">
											<c:if
												test="${schedule.interviewstatus == 2 && schedule.status==0}">
												<img class="imgpadding"
													src="resources/images/icon_evaluators_feedback.png"
													onclick="viewEvalcomm(${schedule.interviewid})">
											</c:if>
											<c:if
												test="${schedule.interviewstatus == 2 && empty schedule.status}">
												<img class="imgpadding"
													src="resources/images/icon_evaluators_feedback.png"
													onclick="viewEvalcomm(${schedule.interviewid})">
											</c:if>
											<c:if
												test="${schedule.interviewstatus == 2 && schedule.status==1}">
												<img class="imgpadding"
													src="resources/images/icon_evaluators_feedback.png"
													onclick="viewEvalcomm(${schedule.interviewid})">
											</c:if>
										</c:if> <c:if test="${schedule.slotstatus == 4}">
											<c:if test="${schedule.interviewstatus == 1}">
												<button type="button" class="form-control myclosedbtn"
													onclick="interviewclosed(${schedule.interviewid})">Close</button>
											</c:if>
										</c:if></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<br>
				<div class="row">

					<div class="col-sm-4 col-md-4">
						<img class="tmilejend"
							src="resources/images/icon_evaluators_feedback.png">Evaluator
						Feedback
					</div>
					<div class="col-sm-12 col-md-12">
						<hr>
					</div>
				</div>

			</c:if>
			<c:if test="${empty myScheduleBookings}">
			<div class="col-sm-12 col-md-12">
						<hr>					
				<div style="text-align: center;">
					<img style="width: 30px;" src="resources/images/warning.png" />&nbsp;There
					are no records available.
				</div>
					<hr>
			</div>
			</c:if>
			<br> <br>
			<div class="modal" id="myModal" tabindex="-1" role="dialog"
				data-backdrop="static" aria-labelledby="myModalLabel"
				aria-hidden="true">
				<div class="modal-dialog mymodal-dialog ">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								onclick="reload()" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="myModalLabel"></h4>
						</div>
						<div class="modal-body"></div>
						<div class="modal-footer">
							<div
								class="col-xs-4 col-xs-push-4 col-sm-4 col-sm-push-4 col-md-4 col-md-push-4">
								<button type="button" class="form-control mycontinuebtn"
									onclick="reload()" data-dismiss="modal">Ok</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal" id="myinterviewFeedBack" tabindex="-1"
				role="dialog" data-backdrop="static" aria-labelledby="myModalLabel"
				aria-hidden="true">
				<div class="modal-dialog interview-dialog ">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" onclick="reload()"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="myinterview">Evaluator Feedback</h4>
						</div>
						<div class="modal-bodyInterview modal-bodyTmi"></div>

					</div>
				</div>
			</div>
			<div class="modal" id="myModalDelete" tabindex="-1" role="dialog"
				data-backdrop="static" aria-labelledby="myModalLabel"
				aria-hidden="true">
				<div class="modal-dialog mymodal-alert">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">Closing
								confirmation</h4>
						</div>
						<input type="hidden" name="interviewid" id="interviewid" value="">
						<div class="modal-body text-center ">Are you sure you wish
							to close interview?</div>
						<div class="modal-footer">
							<div
								class="col-xs-4 col-xs-push-2 col-sm-4 col-sm-push-2 col-md-4 col-md-push-2">
								<button type="button" class="form-control mybackbtn"
									data-dismiss="modal">No</button>
							</div>
							<div
								class="col-xs-4 col-xs-push-2 col-sm-4 col-sm-push-2 col-md-4 col-md-push-2">
								<button type="button" id="deleteItem"
									onclick="closedInterview()" class="form-control mycontinuebtn">Yes</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<form id="evalRejectSlot" action="#" method="post" autocomplete="off">
				<div class="modal" id="evalRejectSlotModal" tabindex="-1"
					role="dialog" data-backdrop="static" aria-labelledby="myModalLabel"
					aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title" id="myModalLabel">Reason for Reject
									Slot</h4>
							</div>
							<div class="modal-body modal-bodyrejectslot">
								<div class="col-sm-12 col-md-12">

									<div class="form-group">
										<textarea class="form-control" rows="4" name="reason"
											id="reason" maxlength="100" style="resize: none"></textarea>
									</div>
									<hr>
								</div>
							</div>
							<div class="modal-footer">

								<div class="col-md-12 ">

									<div
										class="col-xs-6   col-sm-3 col-sm-push-6 col-md-3 col-md-push-6">
										<button type="button" class="form-control mybackbtn"
											data-dismiss="modal">Cancel</button>
									</div>
									<div
										class="col-xs-6  col-sm-3 col-sm-push-6 col-md-3 col-md-push-6">
										<button type="submit" class="form-control mycontinuebtn"
											id="rejectSlot">Reject</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
			<script>
$(document).ready(
		function() {
					$("#evalRejectSlot")
							.bootstrapValidator(
									{ 
										feedbackIcons : {
											valid : "glyphicon glyphicon-ok",
											invalid : "glyphicon glyphicon-remove",
											validating : "glyphicon glyphicon-refresh"
										},
										fields : {													
											reason : {
												validators : {
													notEmpty : {
														message : "reason  is required."
													}
												}
											}
										}
									})
									}), $("#evalRejectSlot").on(
											"success.form.bv", function(a) {
												rejectSlotUpdate();
													
											});
									function rejectSlotUpdate(){
										var reason =$('#reason').val();
										 $.ajax({
											url : "rejectInterView.do/"+slotscheduleid+"/"+reason+"/"+myinterviewid+"/"+mystarttime,
											async : false,
											success : function(result) {
												$('#reason').val('');
											}
									  }); 
									}
						

									function interviewclosed(interviewid){	
										var interviewid=interviewid;
										 $('#interviewid').val(interviewid);		
										$('#myModalDelete').modal({keyboard: false,show:true})	
									}
									function closedInterview(){
										 var interviewId=$('#interviewid').val();		
										 $.ajax({
												url : "evalClosedInterView.do/"+interviewId,
												async : false,
												success : function() {				
													  location.reload(); 
												}
										  });
									}
</script>
		</div>
	</div>
</body>