<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="resources/css/intlTelInput.css">
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script>
	$(document).ready(function() {
		$("#evalUnavaillink").parent().addClass("active");
		displaydata();
	});
	function displaydata() {
		$(".eval_unavailable").load("evalUnavailableDetailsbasedonName",{
			'dashboard' :'0',
			'evaluatorName' : '',
			'mydays':'${mydays}',
			'reschedule':'${reschedule}'
		});
	}
	function unavailabledisplaydata() {
		var evaluatorName = $('#enterString').val();
		var dashboard = $('#dashboard').val();
		$(".eval_unavailable").load("evalUnavailableDetailsbasedonName", {
			'evaluatorName' : evaluatorName,
			'dashboard' : dashboard,
			'mydays':'${mydays}',
			'reschedule':'${reschedule}'
		});
	}
	function assignEvalSlots(interviewid, slotscheduleid, evalid) {
		var status = false;
		$.ajax({
			url : "assignEvaluatorToApplicantSlot.do/" + interviewid + "/"
					+ slotscheduleid + "/" + evalid,
			async : false,
			success : function(result) {
				status = result;
			}
		});
		if (status == true) {
			location.reload();
		} else {
			alert('Please select another slot');
		}
	}
</script>
<style>
/* Generic Styling, for Desktops/Laptops */
table {
	width: 100%;
	/*border-collapse: collapse;*/
}
/* Zebra striping */
tr:nth-of-type(odd) {
	/* background: #FFFFFF; */
	
}

tr:nth-of-type(even) {
	/* background: #f2f2f2; */
	
}

th {
	/* 	background: #59574B;
	color: white; */
	font-weight: bold;
}

.table-hd {
	color: #FFF;
	background: #59574B;
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

.interview-profile {
	width: 727px;
}

.interview-tmi {
	width: 646px;
	margin: 30px auto;
}
</style>
<body>
	<div
		style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
		<div class="container">Interviews</div>
	</div>
	<div class="" style="margin-top: 20px;">
		<div class="container">
			<div
				style="border: 1px solid #D3D3D3; background: none repeat scroll 0% 0% #F2F2F2; padding: 10px 20px">
				<form id="dashboardsearch"
					action="javascript:unavailabledisplaydata()" method="post"
					autocomplete="off">
					<label>What are you looking for?</label>
					<div class="row">
						<div class="col-md-3">
							<div class="form-group ">
								<select class="form-control" name="dashboard" id="dashboard">
									<option value="-1">Select</option>
									<option value="3">Evaluator Name</option>
								</select>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group ">
								<input type="text" class="form-control" name="enterString"
									id="enterString" placeholder="Enter a search keyword" />
							</div>
						</div>
						<div class="col-md-2">
							<button type="submit" class="form-control mybackbtn">Search</button>
						</div>
					</div>
				</form>
				<script type="text/javascript">
					$(document)
							.ready(
									function() {
										$("#dashboardsearch")
												.bootstrapValidator(
														{
															feedbackIcons : {
																valid : "glyphicon glyphicon-ok",
																invalid : "glyphicon glyphicon-remove",
																validating : "glyphicon glyphicon-refresh"
															},
															fields : {
																dashboard : {
																	validators : {
																		callback : {
																			message : "Please select listed looking type.",
																			callback : function(
																					a,
																					b) {
																				var d = b
																						.getFieldElements(
																								"dashboard")
																						.val();
																				return -1 != d
																			}
																		}
																	}
																},
																enterString : {
																	validators : {
																		notEmpty : {
																			message : "Enter Name is required."
																		}
																	}
																}
															}
														})
									});
				</script>
			</div>
			<h1 class="header-file">
				<span style="color: #F26F21"><c:if test="${reschedule ==0}">Evaluator Reassign</c:if>
				<c:if test="${reschedule ==1}">Evaluator Reschedule</c:if></span>
			</h1>
			<br />

			<div class="modal" id="myModalprofile" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog interview-profile">
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
								<button type="button" class="form-control mybackbtn"
									data-dismiss="modal">Close</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal" id="myModal1" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="interview-tmi">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title nameOf" id="myModalLabel"></h4>
						</div>
						<div class="modal-bodyTmi"></div>
					</div>
				</div>
			</div>
			<div class="eval_unavailable"></div>
		</div>
	</div>
</body>
</html>