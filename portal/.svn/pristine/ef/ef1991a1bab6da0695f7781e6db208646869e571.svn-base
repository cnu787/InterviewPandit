<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<style>
@media ( min-width : 768px) {
	.interview-dialog {
		width: 750px
	}
}
/* Generic Styling, for Desktops/Laptops */
table {
	width: 100%;
	/*border-collapse: collapse;*/
}
/* Zebra striping */
tr:nth-of-type(odd) {
	background: #FFFFFF;
}

tr:nth-of-type(even) {
	background: #f2f2f2;
}

th {
	background: #59574B;
	color: white;
	font-weight: bold;
}

td,th {
	padding: 10px 18px;
	border: 0px solid #ccc;
	text-align: left;
	width: -1em;
	text-wrap: none;
}

td,th {
	padding: 10px 18px;
	border: 0px solid #ccc;
	text-align: left;
	width: -1em;
	text-wrap: none;
	white-space: -moz-pre-wrap !important; /* Mozilla, since 1999 */
	white-space: -webkit-pre-wrap; /*Chrome & Safari */
	white-space: -pre-wrap; /* Opera 4-6 */
	white-space: -o-pre-wrap; /* Opera 7 */
	white-space: pre-wrap; /* css-3 */
	word-wrap: break-word; /* Internet Explorer 5.5+ */
	/*word-break: break-all;*/
	white-space: normal;
}

tr {
	border-bottom: 1px solid #cccccc;
}

.btn-lg {
	padding: 10px 39px;
}
</style>
<script type="text/javascript">
	jQuery(document).ready(function() {
		$("#bulkuploadlink").parent().addClass("active");
	});
</script>
<body>
	<div
		style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
		<div class="container">Bulk Upload Interviews</div>
	</div>
	<div class="container">
	<br>
	<hr>		
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
							<h4 class="modal-title" id="myModalLabel">Interview Summary</h4>
						</div>
						<div class="modal-bodyTmi"></div>
						<div class="modal-footer">
							<div
								class="col-xs-6 col-xs-push-6 col-sm-4 col-sm-push-8 col-md-4 col-md-push-8">
								<button type="button" class="form-control mycontinuebtn"
									data-dismiss="modal">CLOSE</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:if>
		<c:if test="${not empty interviewList}">
		<table>
			<thead>
				<tr>
					<th id="#width1" Style="color: #FFF">Interview ID</th>
					<th id="#width2" Style="color: #FFF">Applicant Name</th>
					<th id="#width2" Style="color: #FFF">Email Id</th>
					<th id="#width5" Style="color: #FFF">Time Slot</th>
					<th id="#width6" Style="color: #FFF">Status</th>
					<th id="#width7" Style="color: #FFF">Feedback</th>
				</tr>
			</thead>
			<c:forEach var="intList" items="${interviewList}">
				<tr>
					<c:set var="string4" value="${fn:split(intList.date, ',')}" />
					<td class="myTmi" id="my${intList.interviewid}"><a href="#">${intList.interviewtmiid}</a></td>
					<td>${intList.firstname}${intList.lastname}</td>
					<td>${intList.emailid}</td>
					<td>${string4[0]}|${intList.timeslot}</td>
					<td><c:if test="${intList.status ==1}">Booked</c:if> <c:if
							test="${intList.status ==2}">
							<c:if test="${intList.evaluatorfeedbackstatus == 0}"> 
										Evaluator Pending Feedback 
											</c:if>
							<c:if
								test="${intList.evaluatorfeedbackstatus == 1 && intList.applicantfeedbackstatus == 0}"> 
									Complete
											</c:if>
							<c:if
								test="${intList.evaluatorfeedbackstatus == 1 && intList.applicantfeedbackstatus == 1}"> 
									Closed
											</c:if>




						</c:if> <c:if test="${intList.status ==3}">Cancelled</c:if> <c:if
							test="${intList.status ==4}">Payment Cancelled</c:if> <c:if
							test="${intList.status ==5}">Payment Failure</c:if></td>
					<td id="test"><img class="imgpadding"
						src="resources/images/icon_evaluators_feedback.png"
						onclick="viewEvalcomm(${intList.interviewid})"></td>
				</tr>
			</c:forEach>

		</table>
		</c:if>
		<c:if test="${empty interviewList}">
		 	
		<div style="text-align: center;">

    <img src="resources/images/warning.png" style="width: 30px;"></img>  
 No records available.
</div>
		
		</c:if>
		<hr>
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
						<h4 class="modal-title" id="myinterview">Interview Feedback</h4>
					</div>
					<div class="modal-bodyInterview modal-bodyTmi"></div>

				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	function reload(){		 
		   location.reload(); 
	}
		jQuery(document).ready(
				function() {
					$("#interviewlandLink").parent().addClass("active"), $(".myTmi")
							.click(function() {
								var a = $(this).attr("id");
								$('#myModal').modal({ keyboard: false})
								$(".modal-bodyTmi").load("myInterviewSummary", {
									'interviewId' : a.replace("my", "")
								}), $("#myModal").modal({
									show : !0
								})
							})
				});
		
		function viewEvalcomm(interviewId){			
			$(".modal-bodyInterview").load("admininterviewfeedback", {
				'interviewId' : interviewId
			}), $("#myinterviewFeedBack").modal({ keyboard: false,show : !0				
			})
			
		}
		
	</script>
</body>