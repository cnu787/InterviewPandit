<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	$(document).ready(function() {
		$("#reportslink").parent().addClass("active");
	});
</script>
<body>
	<div
		style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
		<div class="container">Reports</div>
	</div>
	<div class="" style="margin-top: 30px;">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="col-md-4">
						<div class="form-group">
							<a href="tmiAdmOngoingBooking.do">Ongoing Bookings</a><br>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<a href="tmiAdmEvalMonthSlotCount.do">Evaluator Monthly
								Slots List </a>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<a href="tmiAdmApplicantUnavailableSlots.do">No Matching
								Evaluator </a>
						</div>
					</div>

				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-12">
					<div class="col-md-4">
						<div class="form-group">
							<a href="tmiAdmAllInterviewBooking.do">Interview Listing</a><br>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<a href="tmiAdmUsersDetails.do">Registered Users </a>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<a href="tmiAdmEvalPaymentRequest.do">Evaluator Payment
								Request</a>
						</div>
					</div>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-12">
					<div class="col-md-4">
						<div class="form-group">
							<a href="tmiAdmCallCenterRepresentativeLanding.do">CallCenterRepresentative</a><br>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<a href="tmiAdmEvaluatorsLanding.do">EvaluatorsEvaluator</a><br>
						</div>
					</div>	
					<div class="col-md-4">
						<div class="form-group">
							<a href="tmiAdmMockTestResultByList.do">Mock Test Result</a><br>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>