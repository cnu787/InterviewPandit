<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="resources/js/interviewsuccess.js"></script>
<body>
	<div class="" style="margin-top: 20px;">
		<div class="container">
			<h1 class="header-file">Book Interview</h1>
			<c:if test="${status=='success'}">
				<div
					style="border: 5px solid #0db14b; padding: 2%; text-align: justify;">
					<div class="row">

						<div class="col-xs-2 col-sm-1 col-md-1">
							<img class="visible-lg" src="resources/images/icon_success.png">
							<img class="visible-sm" src="resources/images/icon_success.png">
							<img class="visible-xs"
								src="resources/images/icon_successsmall.png">
						</div>
						<div class="col-xs-10 col-sm-11 col-md-11">
							<span class="successMsg">Your Mock Interview has been
								booked successfully. You will shortly receive an email which
								will explain the next steps that needs to be followed. At any
								point you can login to Interview Pandit website for additional
								information</span>
						</div>
					</div>
				</div>
			</c:if>
			<c:if test="${status=='failure'}">
				<div
					style="border: 5px solid #ff0000; padding: 2%; text-align: justify;">
					<div class="row">

						<div class="col-xs-2 col-sm-1 col-md-1">
							<img class="visible-lg" src="resources/images/warning.png">
							<img class="visible-sm" src="resources/images/warning.png">
							<img class="visible-xs" src="resources/images/warningsmall.png">
						</div>
						<div class="col-xs-10 col-sm-11 col-md-11">
							<span class="successMsg">Payment Failure</span>
						</div>
					</div>
				</div>
			</c:if>
			<hr>
			<div class="row">
				<div class="col-xs-11 col-xs-push-1 col-sm-8 col-md-8">
					<div class="row">
						<div class="col-xs-12 col-sm-4 col-md-4">
							<label>Interview ID</label>
						</div>
						<div class="col-xs-12 col-sm-4 col-md-4">
							<label style="color: #0095DA;">${bookedSlotDetails[0].interviewtmiid}</label>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-4 col-md-4">
							<label style="color: #f36f21;">User Name</label>
						</div>
						<div class="col-xs-12 col-sm-4 col-md-4">
							<label style="color: #f36f21; text-transform: capitalize;">${bookedSlotDetails[0].UserName}</label>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-4 col-md-4">
							<label>Industry</label>
						</div>
						<div class="col-xs-12 col-sm-4 col-md-4">
							<label style="color: #0095DA;">${bookedSlotDetails[0].industryname}</label>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-4 col-md-4">
							<label>Domain</label>
						</div>
						<div class="col-xs-12 col-sm-4 col-md-4">
							<c:set var="i" value="0" />
							<label style="color: #0095DA;"> <c:forEach var="domLst"
									items="${domainList}">
									<c:if test="${i != '0'}">,</c:if> 
						${domLst.domainname}
						<c:set var="i" value="${i+1}" />
								</c:forEach>
							</label>

						</div>
					</div>
					<c:if test="${bookedSlotDetails[0].companyName!=null}">
						<div class="row">
							<div class="col-xs-12 col-sm-4 col-md-4">
								<label>Company Name</label>
							</div>
							<div class="col-xs-12 col-sm-4 col-md-4">
								<label style="color: #0095DA; text-transform: capitalize;">${bookedSlotDetails[0].companyName}</label>
							</div>
						</div>
					</c:if>
					<c:if test="${not empty  bookedSlotDetails[0].designation}">
						<div class="row">
							<div class="col-xs-12 col-sm-4 col-md-4">
								<label>Position</label>
							</div>
							<div class="col-xs-12 col-sm-4 col-md-4">
								<label style="color: #0095DA; text-transform: capitalize;">${bookedSlotDetails[0].designation}</label>
							</div>
						</div>
					</c:if>
					<div class="row">
						<div class="col-xs-12 col-sm-4 col-md-4">
							<label>Role of Interviewer</label>
						</div>
						<div class="col-xs-12 col-sm-4 col-md-4">
							<label style="color: #0095DA;">${bookedSlotDetails[0].interviewerrolename}</label>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-4 col-md-4">
							<label>Location</label>
						</div>
						<div class="col-xs-12 col-sm-4 col-md-4">
							<label style="color: #0095DA;">${bookedSlotDetails[0].locationname}</label>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-4 col-md-4">
							<label>Mode of Interview</label>
						</div>
						<div class="col-xs-12 col-sm-4 col-md-4">
							<label style="color: #0095DA;">${bookedSlotDetails[0].interviewtypename}</label>
						</div>
					</div>
					<hr>
					<div class="row">
						<div class="col-xs-12 col-sm-4 col-md-4">
							<label>Slot Booked</label>
						</div>
						<div class="col-xs-12 col-sm-3 col-md-3">
							<label style="color: #0095DA;">${bookedSlotDetails[0].date}<br>
								${bookedSlotDetails[0].timeslot}
							</label>
						</div>
					</div>
					<hr>
					<div class="row">
						<div class="col-xs-12 col-sm-4 col-md-4">
							<label>Skills for Evaluation</label>
						</div>
						<div class="col-xs-12 col-sm-4 col-md-4">
							<c:forEach var="skillNameLst" items="${skillsNameList}">
								<label style="color: #0095DA;">${skillNameLst.skillname}</label>
								<br>
							</c:forEach>
						</div>
					</div>
					<hr>
					<div class="row">
						<div class="col-xs-12 col-sm-4 col-md-4">
							<label>Pricing</label>
						</div>
						<div class="col-xs-12 col-sm-8 col-md-8">
							<label style="color: #0095DA;">RS.
								${bookedSlotDetails[0].amount} (Including Service tax 14%)</label>
						</div>
					</div>
				</div>
				<div class="col-xs-11 col-xs-push-1 col-sm-3 col-md-3"></div>
			</div>

			<hr>
			<div class="row">
				<div class="col-xs-12 col-sm-3 col-sm-push-6 col-md-3 col-md-push-6">
					<div class="form-group">
						<a href="interviewLanding.do"><button type="button"
								class="form-control mybackbtn" id="back">Back To
								Interview</button></a>
					</div>
				</div>
				<div class="col-xs-12 col-sm-3 col-sm-push-6 col-md-3 col-md-push-6">
					<div class="form-group">
						<a href="interviewBooking.do"><button type="button"
								class="form-control mycontinuebtn">Book Interview</button></a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>