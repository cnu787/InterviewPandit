<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="resources/js/bootstrap-datepicker.js"></script>
<link rel="stylesheet" href="resources/css/datepicker.css" />
<link rel="stylesheet" href="resources/css/internalcalender.css">
<script type="text/javascript">
	$(document).ready(function() {			
		$('.pageClick').click(function(e) {
			var a = this.getAttribute('href');			
			$('.register_users').load("tmiAdmInterviewBookingdetails", {				
				'pageNo' : a.replace("#", ""),
				'pageLimit' : '${pageLimit}','interviewTypeId':'${typeofinterviewId}'
			});
			e.preventDefault();
			window.scrollTo(0, 0);
		});	
			
		
	});
	var myPaginationFun = function() {		
		var interviewTypeId='${typeofinterviewId}';		
		$('.register_users').load("tmiAdmInterviewBookingdetails",{
			'pageLimit' : $("#pageLimitId").val(),'interviewTypeId':interviewTypeId
		});
		window.scrollTo(0, 0);

		}
	 
	
	
</script>
<style>
.table-hd {
	height: 61px;
	color: #FFF;
	background: #59574B;
}

.reasonmodal-dialog {
	width: 728px;
	margin: 30px auto;
}

.mymodal-alert {
	width: 400px;
}
</style>
<body>
	<c:if test="${not empty registeredUserList}">
		<div class="row">
			<div class="col-md-4" style="text-align: left">Total No.of
				Records:${registeredListCount}</div>
			<div class="col-md-4" style="margin-top: -4px">
				Show <select id="pageLimitId" onchange="myPaginationFun('#');">
					<c:forEach var="pagLst" items="${paginationList}">
						<option
							<c:if test="${pagLst.paginationlimit==pageLimit}">selected="selected"</c:if>
							value="${pagLst.paginationlimit}">${pagLst.paginationlimit}</option>
					</c:forEach>
				</select>
			</div>
			<div id="" class="col-md-4" style="text-align: right">
				<nav>
					<ul class="pagination" style="margin-top: -2px">${pageNav}</ul>
				</nav>
			</div>
		</div>
		<table>
			<tr>
				<th Style="color: #FFF">Interview ID</th>
				<th Style="color: #FFF">Applicant Name</th>
				<th Style="color: #FFF">Evaluator Name</th>
				<th Style="color: #FFF">Interviewers Role</th>
				<th Style="color: #FFF">Time Slot</th>
				<th Style="color: #FFF">Status</th>
				<th Style="color: #FFF">Feedback</th>
			</tr>
			<c:set var="allInterviewType" value="1,2,3,4,5"/>
			<c:set var="myInterviewType" value="${typeofinterviewId==allInterviewType}"/>
			<c:forEach var="closeLst" items="${registeredUserList}">
				<tr>
					<td style="padding-left: 18px; padding-right: 10px;" class="myTmi"
						id="my${closeLst.interviewid}_${closeLst.interviewtmiid}"><a
						href="#">${closeLst.interviewtmiid}</a></td>
					<td style="padding-left: 18px; padding-right: 10px;" class="appid"
						id="user${closeLst.appuserid}"><a href="#">
							${closeLst.ApplicantName}</a></td>
					<td style="padding-left: 18px; padding-right: 10px;" class="evalid"
						id="user${closeLst.evaluserid}"><a href="#">${closeLst.EvaluatorName}</a></td>
					<td style="padding-left: 18px; padding-right: 10px;">${closeLst.interviewerrolename}</td>
					<td style="padding-left: 18px; padding-right: 10px;">${closeLst.date}
						<br> ${closeLst.timeslot}
					</td>
					<td style="padding-left: 18px; padding-right: 10px;"><c:if
							test="${(closeLst.slotstatus == 5 && typeofinterviewId=='6')|| (closeLst.evalstatus=='rejected' && closeLst.slotstatus == 5 && myInterviewType eq true)}">
			  Reassign </c:if> <c:if test="${closeLst.interviewstatus eq 4}">
			   Payment Cancelled </c:if> <c:if test="${closeLst.interviewstatus eq 5}">Payment Failure
			    </c:if> <c:if
							test="${(closeLst.interviewstatus eq 3 && typeofinterviewId=='3' && closeLst.evalstatus!='rejected') || (closeLst.interviewstatus eq 3 && myInterviewType eq true && closeLst.evalstatus!='rejected')}">
			  Cancelled</c:if> <c:if
							test="${closeLst.slotstatus == 2 && closeLst.interviewstatus ==1 }">
							<a style="color: #0094DA" href="#"
								onclick="accept(${closeLst.slotscheduleid});">Accept</a>&nbsp;
										<a style="color: red;" href="#"
								onclick="reject('${closeLst.slotscheduleid}','${closeLst.interviewid}','${closeLst.starttime}');">Reject</a>
						</c:if>
						<c:if test="${(closeLst.interviewstatus==1 && typeofinterviewId=='1' && closeLst.evalstatus!='notreject' && (closeLst.slotstatus==3 || closeLst.slotstatus==5))||(closeLst.interviewstatus==1 && myInterviewType eq true && closeLst.evalstatus=='notreject' && (closeLst.slotstatus==3 || closeLst.slotstatus==5))}">
						Booked
						</c:if>
						
						<c:if
							test="${(closeLst.slotstatus == 3 && typeofinterviewId=='6') || (closeLst.evalstatus=='rejected' && closeLst.slotstatus == 3 && myInterviewType eq true)}">Rejected</c:if>
						<c:if test="${closeLst.slotstatus == 4}">
							<c:if test="${closeLst.interviewstatus == 1}">Scheduled
										</c:if>
							<c:if test="${closeLst.interviewstatus == 2}">
								<c:if test="${closeLst.evaluatorfeedbackstatus == 0}"> 
										Evaluator Pending Feedback 
											</c:if>
								<c:if
									test="${closeLst.evaluatorfeedbackstatus == 1 && closeLst.applicantfeedbackstatus == 0}"> 
										Complete
											</c:if>
								<c:if
									test="${closeLst.evaluatorfeedbackstatus == 1 && closeLst.applicantfeedbackstatus == 1}"> 
											Closed
											</c:if>

							</c:if>
						</c:if></td>
					<td style="padding-left: 18px; padding-right: 10px;"><c:if
							test="${closeLst.slotstatus == 4}">
							<c:if test="${closeLst.interviewstatus==2}">
								<img class="imgpadding"
									src="resources/images/icon_video_audio_clip.png"
									onclick="myInterviewFiles('${closeLst.interviewtmiid}')">
							</c:if>
							<c:if
								test="${closeLst.interviewstatus == 2 && closeLst.status==0}">
								<img class="imgpadding"
									src="resources/images/icon_evaluators_feedback.png"
									onclick="viewEvalcomm(${closeLst.interviewid})">
							</c:if>
							<c:if
								test="${closeLst.interviewstatus == 2 && empty closeLst.status}">
								<img class="imgpadding"
									src="resources/images/icon_evaluators_feedback.png"
									onclick="viewEvalcomm(${closeLst.interviewid})">
							</c:if>
							<c:if
								test="${closeLst.interviewstatus == 2 && closeLst.status==1}">
								<img class="imgpadding"
									src="resources/images/icon_evaluators_feedback.png"
									onclick="viewEvalcomm(${closeLst.interviewid})">
							</c:if>
						</c:if> <c:if test="${closeLst.slotstatus == 4}">
							<c:if test="${closeLst.interviewstatus == 1}">
								<button type="button" class="form-control myclosedbtn"
									onclick="interviewclosed(${closeLst.interviewid})">Close</button>
							</c:if>
						</c:if> <c:if test="${closeLst.applicantfeedbackstatus == 1}">
							<img onclick="addInterViewFeedBack(${closeLst.interviewid})"
								src="resources/images/icon_eval_interview_accepted.png" />
						</c:if></td>

				</tr>
			</c:forEach>
		</table>
		<br>
	</c:if>
	<c:if test="${empty registeredUserList}">
		<div style="text-align: center;">
			<img style="width: 30px;" src="resources/images/warning.png" />&nbsp;No
			records available.
		</div>
	</c:if>
	<hr>
	<div class="modal" id="myInterviewFiles" tabindex="-1" role="dialog"
		data-backdrop="static" aria-labelledby="myModalLabel"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Interview Files</h4>
				</div>
				<div class="modal-body myInterviewFilesBody text-center"></div>
				<div class="modal-footer">
					<div
						class="col-xs-4 col-xs-push-4 col-sm-4 col-sm-push-4 col-md-4 col-md-push-4">
						<button type="button" data-dismiss="modal"
							class="form-control mycontinuebtn">Close</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal" id="myModal" tabindex="-1" role="dialog"
		data-backdrop="static" aria-labelledby="myModalLabel"
		aria-hidden="true">
		<div class="modal-dialog mymodal-alert ">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" onclick="reload()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel"></h4>
				</div>
				<div class="modal-body text-center"></div>
				<div class="modal-footer">
					<div
						class="col-xs-4 col-xs-push-4 col-sm-4 col-sm-push-4 col-md-4 col-md-push-4">
						<button type="button" class="form-control mycontinuebtn"
							onclick="reload()">Ok</button>
					</div>
				</div>
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
					<h4 class="modal-title" id="myModalLabel">Closing confirmation</h4>
				</div>
				<input type="hidden" name="interviewid" id="interviewid" value="">
				<div class="modal-body text-center ">Are you sure you wish to
					close interview?</div>
				<div class="modal-footer">
					<div
						class="col-xs-4 col-xs-push-2 col-sm-4 col-sm-push-2 col-md-4 col-md-push-2">
						<button type="button" class="form-control mybackbtn"
							data-dismiss="modal">No</button>
					</div>
					<div
						class="col-xs-4 col-xs-push-2 col-sm-4 col-sm-push-2 col-md-4 col-md-push-2">
						<button type="button" id="deleteItem" onclick="closedInterview()"
							class="form-control mycontinuebtn">Yes</button>
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
	<form id="evalRejectSlot" action="#" method="post" autocomplete="off">
		<div class="modal" id="evalRejectSlotModal" tabindex="-1"
			role="dialog" data-backdrop="static" aria-labelledby="myModalLabel"
			aria-hidden="true">
			<div class="modal-dialog reasonmodal-dialog">
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
	<div class="modal" id="myinterviewFeedBackApp" tabindex="-1"
		role="dialog" data-backdrop="static" aria-labelledby="myModalLabel"
		aria-hidden="true">
		<div class="modal-dialog interview-dialog ">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myinterview">Applicant Feedback</h4>
				</div>
				<div class="modal-bodyInterviewapp"></div>
			</div>
		</div>
	</div>
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
											url : "tmiadmrejectInterView/"+slotscheduleid+"/"+reason+"/"+myinterviewid+"/"+starttime,
											async : false,
											success : function(result) {
												$('#reason').val('');
											}
									  }); 
									} 
									
											function reject(slotid,intid,strtime){
											slotscheduleid=slotid;
											myinterviewid=intid;
											starttime=strtime;
											$('#evalRejectSlotModal').modal({keyboard: false,show:true});		
										}
</script>
	<script>
	jQuery(document).ready(
			function() {
				$("#interviewlandLink").parent().addClass("active"), $(".myTmi")
						.click(function() {	
							var a = $(this).attr("id");
							var res = a.split("_");
							a = res[0];
							var profile = "Interview Pandit ID : ";
							var profileName = profile.concat(res[1]);
						$(".nameOf").text(profileName);								
							$(".modal-bodytmi").load("myInterviewSummary", {
								'interviewId' : a.replace("my", "")
							}), $("#myModaltmi").modal({
								show : !0
							})			
							
						})
			});
		</script>
	<script type="text/javascript">
	jQuery(document).ready(
			function() {				
						$(".appid")	.click(function() {
							var a = $(this).attr("id");						
							  $(".modal-bodyprofile").load("usersCompleteProfile", {
								'userId' : a.replace("user", "") 
							}), $("#myModalprofile").modal({
								show : !0
							}) 
						});
						$(".evalid")	.click(function() {
							var a = $(this).attr("id");							
							  $(".modal-bodyprofile").load("usersCompleteProfile", {
								'userId' : a.replace("user", "") 
							}), $("#myModalprofile").modal({
								show : !0
							}) 
						});
			});
	function accept(slotid){		
		$.ajax({
				url : "tmiadmAcceptSlot.do/"+slotid,
				async : false,
				success : function(result) {					
					var modal = $('#myModal').modal({'show':true});			
					  modal.find('.modal-title').text('Alert!');
					  modal.find('.modal-body').text('Thanks for accepting the interview request.');
					
				}
		  });
	}
	function reload(){		 
		   location.reload(); 
	}
	function interviewclosed(interviewid){	
		var interviewid=interviewid;
		 $('#interviewid').val(interviewid);		
		$('#myModalDelete').modal({keyboard: false,show:true});	
	}
	function closedInterview(){
		 var interviewId=$('#interviewid').val();		
		 $.ajax({
				url : "tmiadmClosedInterView.do/"+interviewId,
				async : false,
				success : function() {				
					  location.reload(); 
				}
		  });
	}
	function viewEvalcomm(interviewId){			
		$(".modal-bodyInterview").load("admininterviewfeedback", {
			'interviewId' : interviewId
		}), $("#myinterviewFeedBack").modal({keyboard: false,show : true})
	}
	function addInterViewFeedBack(interviewId){			
		  $(".modal-bodyInterviewapp").load("tmiadmimyInterviewFeedBackApplicant", {
			'interviewId' : interviewId
		}), $("#myinterviewFeedBackApp").modal({
			show : !0
		}); 
	}

	function myInterviewFiles(interviewId){		
		 $.ajax({
				url : "tmiGetInterviewFiles/"+interviewId,
				async : false,
				success : function(result) {					
					  var data="";
					  $.each(result, function(k, v) {
					  $.each(result[k], function(i, j) {						 
						  data+="<p><a target='_blank' href='"+j+"'>"+j+"</a></p>";				          
				        });
					  });					  
					  $('.myInterviewFilesBody').html(data);
				}
		  });
		 $("#myInterviewFiles").modal({
				show : !0
			}); 
		
		}
	</script>

</body>
