<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="resources/css/intlTelInput.css">
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script>
	var date = '${daycount}';
	$(document)
			.ready(
					function() {
						$("#homelink").parent().addClass("active");
						$('#btn-ongoing').click(function() {

						});
						$('#btn-close')
								.click(
										function() {
											window.location.href = "tmiAdmGetClosedInterviewsDetails.do?mydays="
													+ date;

										});
						$('#btn-cancel').click(function() {
							window.location.href = "tmiAdmGetCancelInterviewsDetails.do?mydays="
								+ date;
						});
						$('#btn-eval')
								.click(
										function() {
											window.location.href = "tmiAdmGetEvalUnavailableDetails.do?mydays="+date+"&reschedule=0";
										});
						$('#weekdayimg')
								.click(
										function() {
											$('#mydays').val("0");
											date = $('#mydays').val();
											window.location.href = "tmiAdmCallCenterRepresentativeLanding.do?mydays="
													+ date;
										});
						$('#weekviewimg')
								.click(
										function() {
											$('#mydays').val("7");
											date = $('#mydays').val();
											window.location.href = "tmiAdmCallCenterRepresentativeLanding.do?mydays="
													+ date;
										});
						$('#weekmonthimg')
								.click(
										function() {
											$('#mydays').val("30");
											date = $('#mydays').val();
											window.location.href = "tmiAdmCallCenterRepresentativeLanding.do?mydays="
													+ date;
										});

					});
</script>
<style>
#pagination-right {
	float: right
}

.apply-borderimg {
	border: 5px solid #D3D3D3;
	text-align: center;
	padding-top: 20px;
	height: 140px
}

.badge {
	padding: 5px;
	font-size: 14px;
	color: #FFF;
	background-color: #FF0000;
	border-radius: 50%;
	margin-left: 70px;
	margin-bottom: -10px
}

.apply-color {
	font-family: robotoregular;
	font-size: 14px;
	color: #0095DA;
	text-align: center
}

.added-color {
	font-family: robotoregular;
	font-size: 16px;
	color: #1970F2;
	margin-left: -20px
}

.book-color {
	font-family: robotoregular;
	font-size: 13px;
	color: #808080
}
</style>
<body>
	<div
		style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
		<div class="container">Home</div>
	</div>
	<div class="" style="margin-top: 20px;">
		<div class="container">

			<div
				style="border: 1px solid #D3D3D3; background: none repeat scroll 0% 0% #F2F2F2; padding: 10px 20px">
				<form id="dashboardsearch" action="tmiAdmGetDashboardReport.do"
					method="post" autocomplete="off">
					<label>What are you looking for?</label>
					<div class="row">
						<div class="col-md-3">
							<div class="form-group ">
								<select class="form-control" name="dashboard" id="dashboard">
									<option value="-1">Select</option>
									<c:forEach var="dashboardLst" items="${dashboardsearchtypelkp}">
										<option value="${dashboardLst.dashboardsearchtypeid}"
											<c:if test="${dashboardLst.dashboardsearchtypeid==externalProfile[0].industryid}">
													selected="selected"
													</c:if>>
											${dashboardLst.dashboardsearchname}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group ">
								<input type="text" class="form-control" name="enterString"
									placeholder="Enter a search keyword" id="enterString" />
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

			<div class="row">
				<div class="col-md-8">
					<div class="header-file">
						INTERVIEWS
						<ul class="pagination" id="pagination-right">
							<li><a href="#"
								style="border: 0px; background-color: #fff; padding: 0px 0px;"
								data-toggle="tooltip" data-placement="left" title="Day view"><img
									id="weekdayimg"
									src="resources/images/icon_calendar_day_active.png"></a></li>
							<li><a href="#"
								style="border: 0px; background-color: #fff; padding: 0px 10px;"
								data-toggle="tooltip" data-placement="left" title="Week view"><img
									id="weekviewimg"
									src="resources/images/icon_calendar_week_default.png"></a></li>
							<li><a href="#"
								style="border: 0px; background-color: #fff; padding: 0px 0px;"
								data-toggle="tooltip" data-placement="left" title="Month view"><img
									id="weekmonthimg"
									src="resources/images/icon_calendar_month_default.png"></a></li>
						</ul>
					</div>
					<hr>
				</div>
				<div class="col-md-4">
					<span class="header-file">I want to</span>
					<hr>
				</div>
			</div>
			<div class="row" id="tmi_interviews">
				<input type="hidden" name="mydays" id="mydays">
				<div class="col-md-8">
					<div class="row">
						<div class="col-sm-3">
							<div class="form-group apply-borderimg">
								<div>
									<a href="#"><span class="badge">${ongoingInterviewsCount}</span></a>
								</div>
								<a href="tmiAdmAdminLanding.do" id="btn-ongoing"><img
									src="resources/images/icon_ongoing.png"></a>
							</div>
							<div class="apply-color">ONGOING</div>
						</div>
						<div class="col-sm-3">
							<div class="form-group apply-borderimg">
								<div>
									<a href="#"><span class="badge">${fn:length(closedInterviewsData)}</span></a>
								</div>
								<a href="#" id="btn-close"><img
									src="resources/images/icon_closed.png"></a>
							</div>
							<div class="apply-color">CLOSED</div>
						</div>
						<div class="col-sm-3">
							<div class="form-group apply-borderimg">
								<div>
									<a href="#"><span class="badge">${fn:length(cancelledInterviewsData)}</span></a>
								</div>
								<a href="#" id="btn-cancel"><img
									src="resources/images/icon_cancelled.png"></a>
							</div>
							<div class="apply-color">CANCELLED</div>
						</div>
						<div class="col-sm-3">
							<div class="form-group apply-borderimg">
								<div>
									<a href="#"><span class="badge">${fn:length(evalUnavailableCount)}</span></a>
								</div>
								<a href="#" id="btn-eval"><img
									src="resources/images/icon_evaluator_unavailability.png"></a>
							</div>
							<div class="apply-color">EVALUATOR UNAVAILABILITY</div>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<ul style="list-style-type: circle">
							<li class="added-color"><a
								href="tmiAdmininterviewBooking.do">Book an Interview</a></li>
						</ul>
					</div>
					<div class="form-group">
						<ul style="list-style-type: circle">
							<li class="added-color"><a
								href="tmiAdmininterviewCancelBooking.do">Cancel Interview</a></li>
						</ul>
					</div>
					<div class="form-group">
						<ul style="list-style-type: circle">
							<li class="added-color"><a href="tmiAdmGetEvalUnavailableDetails.do?mydays=30&reschedule=0">Assign an Evaluator</a></li>
						</ul>
					</div>
					<div class="form-group">
						<ul style="list-style-type: circle">
							<li class="added-color"><a href="tmiAdmGetEvalUnavailableDetails.do?mydays=30&reschedule=1">Reschedule an Interview</a></li>
						</ul>
					</div>
				</div>
			</div>
			<h1 class="header-file">Document Repository</h1>
			<hr>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<div class="row">
							<div class="col-md-6">
								<div class="row">
									<div class="col-md-1">
										<a href="#" id="icon-pdf"><img
											src="resources/images/icon_pdf.png"></a>
									</div>
									<div class="col-md-5">
										<div style="padding-left: 10px">
											<span class="apply-color">Contact Book</span>
											<p class="book-color">Adobe Acrobat Document 345 KB</p>
										</div>
									</div>
									<div class="col-md-1">
										<a href="#" id="icon-doc"><img
											src="resources/images/icon_doc.png"></a>
									</div>
									<div class="col-md-5">
										<div style="padding-left: 10px">
											<span class="apply-color">Contact Book</span>
											<p class="book-color">Microsoft Word Document 345 KB</p>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="row">
									<div class="col-md-1">
										<a href="#" id="icon-xls"><img
											src="resources/images/icon_xls.png"></a>
									</div>
									<div class="col-md-5">
										<div style="padding-left: 10px">
											<span class="apply-color">Contact Book</span>
											<p class="book-color">Microsoft Excel Worksheet 345 KB</p>
										</div>
									</div>
									<div class="col-md-1">
										<a href="#" id="icon-xlsimg"><img
											src="resources/images/icon_xls.png"></a>
									</div>
									<div class="col-md-5">
										<div style="padding-left: 10px">
											<span class="apply-color">Contact Book</span>
											<p class="book-color">Microsoft Excel Worksheet 345 KB</p>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<div class="row">
							<div class="col-md-6">
								<div class="row">
									<div class="col-md-1">
										<a href="#" id="icon-pdf"><img
											src="resources/images/icon_pdf.png"></a>
									</div>
									<div class="col-md-5">
										<div style="padding-left: 10px"></div>
									</div>
									<div class="col-md-1">
										<a href="#" id="icon-doc"><img
											src="resources/images/icon_doc.png"></a>
									</div>
									<div class="col-md-5">
										<div style="padding-left: 10px">
											<span class="apply-color">Contact Book</span>
											<p class="book-color">Microsoft Word Document 345 KB</p>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="row">
									<div class="col-md-1">
										<a href="#" id="icon-xls"><img
											src="resources/images/icon_xls.png"></a>
									</div>
									<div class="col-md-5">
										<div style="padding-left: 10px">
											<span class="apply-color">Contact Book</span>
											<p class="book-color">Microsoft Excel Worksheet 345 KB</p>
										</div>
									</div>
									<div class="col-md-1">
										<a href="#" id="icon-xlsimg"><img
											src="resources/images/icon_xls.png"></a>
									</div>
									<div class="col-md-5">
										<div style="padding-left: 10px">
											<span class="apply-color">Contact Book</span>
											<p class="book-color">Microsoft Excel Worksheet 345 KB</p>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		$('[data-toggle="tooltip"]').tooltip();
	</script>
</body>
</html>