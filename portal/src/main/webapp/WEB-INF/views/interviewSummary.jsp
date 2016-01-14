<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<body>
	<div class="" style="margin-top: 20px;">
		<div class="container">
			<h1 class="header-file">Interview Summary</h1>
			<hr>
			<c:if test="${finalAmount eq '0'}">
				<c:set var="submitUrl" value="paymentSuccess.do" />
			</c:if>
			<c:if test="${finalAmount ne '0'}">
				<c:set var="submitUrl" value="${paymentGateWayURl}" />
			</c:if>
			<form id="makePayment" action="${submitUrl}" method="post">
				<c:if test="${finalAmount eq '0'}">
					<input type="hidden" name="myToken" value='${myToken}' />
				</c:if>
				<input type="hidden" name="firstname" value='${firstname}' /> <input
					type="hidden" name="surl" value="${surl}" /> <input type="hidden"
					name="phone" value='${phone}' /> <input type="hidden" name="key"
					value='${key}' /> <input type="hidden" name="hash" value='${hash}' />
				<input type="hidden" name="curl" value="${curl}" /> <input
					type="hidden" name="furl" value="${furl}" /> <input type="hidden"
					name="txnid" value='${txnid}' /> <input type="hidden"
					name="productinfo" value='${productinfo}' /> <input type="hidden"
					name="amount" value='${finalAmount}' /> <input type="hidden"
					name="email" value='${email}' /><input type="hidden" name="pg"
					value='DC' /><input type="hidden" name="udf1"
					value='${actualAmount}' /><input type="hidden" name="udf2"
					value='${evalAmount}' />
					
				<div class="row">
					<div class="col-xs-10 col-sm-11 col-md-11">
						<div class="row">
							<div class="col-xs-12 col-sm-4 col-md-4">
								<label>Industry</label>
							</div>
							<div class="col-xs-12 col-sm-4 col-md-4">
								<label style="color: #0095DA;"><c:if
										test="${userDetails[0].industryname =='Others'}">${userDetails[0].otherIndustry}</c:if>
									<c:if test="${userDetails[0].industryname ne 'Others'}">${userDetails[0].industryname}</c:if>
								</label>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-4 col-md-4">
								<label>Domain</label>
							</div>
							<c:set var="i" value="0" />
							<div class="col-xs-12 col-sm-3 col-md-3">
								<label style="color: #0095DA;"> <c:forEach
										var="domianLst" items="${domainList}">
										<c:if test="${i != '0'}">,</c:if> 
								${domianLst.domainname}
								<c:set var="i" value="${i+1}" />
									</c:forEach>
								</label>
							</div>
						</div>
						<c:if test="${userDetails[0].companyName!=null}">
							<div class="row">
								<div class="col-xs-12 col-sm-4 col-md-4">
									<label>Company Name</label>
								</div>
								<div class="col-xs-12 col-sm-4 col-md-4">
									<label style="color: #0095DA; text-transform: capitalize;">${userDetails[0].companyName}</label>
								</div>
							</div>
						</c:if>
						<c:if test="${not empty userDetails[0].designation}">
							<div class="row">
								<div class="col-xs-12 col-sm-4 col-md-4">
									<label>Position</label>
								</div>
								<div class="col-xs-12 col-sm-4 col-md-4">
									<label style="color: #0095DA; text-transform: capitalize;">${userDetails[0].designation}</label>
								</div>
							</div>
						</c:if>
						<div class="row">
							<div class="col-xs-12 col-sm-4 col-md-4">
								<label>Role of Interviewer</label>
							</div>
							<div class="col-xs-12 col-sm-4 col-md-4">
								<label style="color: #0095DA;">${userDetails[0].interviewerrolename}</label>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-4 col-md-4">
								<label>Location</label>
							</div>
							<div class="col-xs-12 col-sm-4 col-md-4">
								<label style="color: #0095DA;">${userDetails[0].locationname}</label>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-4 col-md-4">
								<label>Mode of Interview</label>
							</div>
							<div class="col-xs-12 col-sm-4 col-md-4">
								<label style="color: #0095DA;">${userDetails[0].interviewtypename}</label>
							</div>
						</div>
					</div>
					<div class="col-xs-2 col-sm-1 col-md-1">
						<img class="img-responsive" onclick="interviewBooking()"
							src="resources/images/icon_edit.png">

					</div>
				</div>
				<hr>
				<div class="row">
					<div class="col-xs-10 col-sm-11 col-md-11">
						<div class="row">
							<div class="col-xs-12 col-sm-4 col-md-4">
								<label>Slot Booked</label>
							</div>
							<div class="col-xs-12 col-sm-3 col-md-3">
								<label style="color: #0095DA;">${userDetails[0].date} <br>
									${userDetails[0].timeslot}
								</label>
							</div>
						</div>
					</div>
					<div class="col-xs-2 col-sm-1 col-md-1">
						<img class="img-responsive" onclick="interviewSlotBooking()"
							src="resources/images/icon_edit.png">

					</div>
				</div>
				<hr>
				<div class="row">
					<div class="col-xs-10 col-sm-11 col-md-11">
						<c:set var="i" value="0" />
						<c:forEach var="skilllst" items="${skillsDetails}">
							<div class="row">
								<div class="col-xs-12 col-sm-4 col-md-4">
									<c:if test="${i==0}">
										<label>Skills for Evaluation</label>
									</c:if>
								</div>

								<div class="col-xs-12 col-sm-4 col-md-4">
									<label style="color: #0095DA;">${skilllst.skillname}</label>
								</div>
							</div>
							<c:set var="i" value="${i+1}" />
						</c:forEach>
					</div>
					<div class="col-xs-2 col-sm-1 col-md-1">
						<img class="img-responsive" onclick="interviewTechSkills()"
							src="resources/images/icon_edit.png">

					</div>
				</div>
				<hr>
				<div class="row">
					<div class="col-xs-10 col-sm-11 col-md-11">
						<div class="row">
							<div class="col-xs-12 col-sm-4 col-md-4">
								<label>Pricing</label>
							</div>
							<div class="col-xs-12 col-sm-8 col-md-8">
								<label style="color: #0095DA;">RS. ${actualAmount}
									(Inclusive of all taxes)</label> <br> <label
									style="color: #0095DA;">Wallet Balance RS.
									${myWalletAmount} </label> <br> <label style="color: #0095DA;">Final
									Pricing RS. ${finalAmount} </label>

							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-11 col-md-11">
						<div class="row">
							<div
								class="col-xs-12 col-sm-8 col-sm-push-4 col-md-8 col-md-push-4">
								<div class="checkbox">
									<label><input type="checkbox" id="terms" name="terms">I
										accept to the <span style="color: #0095DA"><a
											href="javascript:termsandcondition()">terms & conditions</a></span></label>
								</div>
							</div>
						</div>
					</div>
				</div>
				<hr>
				<input type="hidden" name="_dontcare">
				<div class="row">
					<div class="col-xs-6 col-sm-3 col-sm-push-6 col-md-2 col-md-push-8">
						<div class="form-group">
							<button type="button" class="form-control mybackbtn" id="back">Back</button>
						</div>
					</div>
					<div class="col-xs-6 col-sm-3 col-sm-push-6 col-md-2 col-md-push-8">
						<div class="form-group">
							<button id="continue" type="submit"
								class="form-control mycontinuebtn">Make Payment</button>
						</div>
					</div>
				</div>
			</form>
			<div class="modal " id="termsandcondition" tabindex="-1"
				role="dialog" data-backdrop="static" aria-labelledby="myModalLabel"
				aria-hidden="true">
				<div class="modal-dialog mymodal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">Terms and
								Condition</h4>
						</div>
						<div class="modal-bodytermsandcondition"></div>
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
			<div class="modal " id="interviewmodification" tabindex="-1"
				role="dialog" data-backdrop="static" aria-labelledby="myModalLabel"
				aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">Warning</h4>
						</div>
						<div class="modal-body">
						Please note that your selected slot will be removed if you choose to go back.
						</div>
						<div class="modal-footer">
							<div
								class="col-xs-4 col-xs-push-2 col-sm-4 col-sm-push-2 col-md-4 col-md-push-2">
								<button type="button" class="form-control mybackbtn"
									data-dismiss="modal">Cancel</button>
							</div>
							<div
								class="col-xs-4 col-xs-push-2 col-sm-4 col-sm-push-2 col-md-4 col-md-push-2">
								<button type="button" id="interviewmodificationok" class="form-control mycontinuebtn"
									data-dismiss="modal">Ok</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="resources/js/interviewSummary.js"></script>

	<script type="text/javascript">
		function termsandcondition() {
			$(".modal-bodytermsandcondition").load("termsandcondition"), $(
					"#termsandcondition").modal({
				show : !0
			})
		}
	</script>
	<style>
@media ( min-width : 768px) {
	.mymodal-dialog {
		width: 1000px;
	}
}
</style>
</body>