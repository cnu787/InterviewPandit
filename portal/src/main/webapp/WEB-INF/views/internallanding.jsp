<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
	<div class="modal" id="evalLandingModal" tabindex="-1" role="dialog"
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
	<div
		style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
		<div class="container">
			<img src="resources/images/icon_profile.png">&nbsp;&nbsp;Profile
		</div>
	</div>
	<div class="" style="margin-top: 20px; margin-bottom: 60px;">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="row">
						<div class="col-sm-6 col-md-3">
							<div class="form-group profile-reader">
								<div style="text-align: center; margin-top: 30px;">
									<img src="resources/images/icon_profile_main.png">
								</div>
								<div style="height: 180px;">
									<h3 class="head-profile">My Profile</h3>
									<div
										style="text-align: center; margin-left: 10px; margin-right: 10px;">
										<span class="groom-profile"> <c:if
												test="${mySesProfile[0].profilestatus==0}">
												<!-- Build your profile to help us groom you better -->
										Please provide your complete information for us to match you with relevant interview slots
										</c:if> <c:if
												test="${(mySesProfile[0].profilestatus!=0 && eLearningStatus==0) || (eLearningStatus==2)}">
										Hi <span style="">${mySesProfile[0].screenName}</span>, consider updating
										your profile
										</c:if> <c:if test="${eLearningStatus==1}">
										You are just a step away from taking interviews. Finish online training module and you can start conducting interviews
										</c:if>
										</span>
									</div>
									<div align="center">
										<c:if test="${profileReportCount>0}">
											<div class="progress"
												style="width: 110px; margin-top: 10px; margin-bottom: 0px;">
												<div class="progress-bar" role="progressbar"
													aria-valuenow="50" aria-valuemin="0" aria-valuemax="100"
													style="width: ${profileReportCount}%;"></div>
											</div>
											<div class="progress-value"
												style="padding: 0px 15px; margin-top: -15px;">${profileReportCount}%</div>
										</c:if>
									</div>
								</div>
								<div style="clear: both;"></div>
								<div style="text-align: center; margin-bottom: 30px;">
									<c:if test="${mySesProfile[0].profilestatus==0}">
										<a href="internalHome.do"><button type="button"
												class="btn btn-primary btn-sm"
												style="font-family: robotoregular; font-size: 16px; color: #d44434; background-color: #FFF; border: 1px solid #d44434;">
												Create Profile</button></a>
									</c:if>
									<c:if test="${mySesProfile[0].profilestatus!=0}">
										<a href="internalHome.do"><button type="button"
												class="btn btn-primary btn-sm"
												style="font-family: robotoregular; font-size: 16px; color: #d44434; background-color: #FFF; border: 1px solid #d44434;">
												Update Profile</button></a>
									</c:if>
								</div>
							</div>
						</div>
						<div class="col-sm-6 col-md-3">
							<div class="form-group calendar-page">
								<div style="text-align: center; margin-top: 30px;">
									<img src="resources/images/icon_calendar.png">
								</div>
								<div style="height: 180px;">
									<h3 class="head-profile">Calendar&nbsp;</h3>
									<div
										style="text-align: center; margin-left: 10px; margin-right: 10px;">
										<span class="groom-profile"> <c:if
												test="${calendarCount eq 0}">
										Helps you plan your day better for interviews
										</c:if> <c:if test="${calendarCount ne 0}">
										 ${calendarCount} new requests to conduct interviews
										</c:if>
										</span>
									</div>
								</div>
								<div style="text-align: center; margin-bottom: 30px;">
									<a
										href="javascript:myprofileStatus(${eLearningStatus},'internalCalender.do');"><button
											type="button" class="btn btn-primary btn-sm"
											style="font-family: robotoregular; font-size: 16px; color: #293139; background-color: #FFF; border: 1px solid #293139;">Set
											Your Availability</button></a>

								</div>
							</div>
						</div>
						<div class="col-sm-6 col-md-3">
							<div class="form-group interview-book">
								<div style="text-align: center; margin-top: 30px;">
									<img src="resources/images/icon_interviews.png">
								</div>
								<div style="height: 180px;">
									<h3 class="head-profile">
										Interviews&nbsp;
										<c:if test="${calendarCount ne 0}">
											<span class="badge">${calendarCount}</span>
										</c:if>
									</h3>
									<div
										style="text-align: center; margin-left: 10px; margin-right: 10px;">
										<span class="groom-profile"> <c:if
												test="${(interviewCount eq 0)&&(feedbackCount eq 0)}">
										View interviews scheduled for you and share your experience/ feedback on the applicant
										</c:if> <c:if test="${(interviewCount eq 0)&&(feedbackCount ne 0)}">
										You have ${feedbackCount} feedback to share
										</c:if> <c:if test="${(interviewCount ne 0)&&(feedbackCount eq 0)}">
										You have ${interviewCount} interviews lined up today to conduct
										</c:if> <c:if test="${(interviewCount ne 0)&&(feedbackCount ne 0)}">
										You have ${interviewCount} interviews lined up today to conduct and
										${feedbackCount} to share feedback on
										</c:if>
										</span>
									</div>
								</div>
								<div style="text-align: center; margin-bottom: 30px;">
									<a
										href="javascript:myprofileStatus(${eLearningStatus},'interviewHistory.do');"><button
											type="button" class="btn btn-primary btn-sm"
											style="font-family: robotoregular; font-size: 16px; color: #4d4645; background-color: #FFF; border: 1px solid #4d4645;">
											View Interviews</button></a>
								</div>
							</div>
						</div>
						<div class="col-sm-6 col-md-3">
							<div class="form-group referral-page">
								<div style="text-align: center; margin-top: 30px;">
									<img src="resources/images/icon_referrals.png">
								</div>
								<div style="height: 180px;">
									<h3 class="head-profile">
										Referrals&nbsp;
										<c:if test="${referralCount > 0}">
											<span class="badge">${referralCount}</span>
										</c:if>
									</h3>
									<div
										style="text-align: center; margin-left: 10px; margin-right: 10px;">
										<span class="groom-profile"> <c:if
												test="${referralCount==0}">
										Refer your friends with our
											Referral Program and earn Rich Rewards
										</c:if> <c:if test="${referralCount>0}">
										You referrals are giving
											you brownie points. keep reffering.
										</c:if>
										</span>
									</div>
								</div>
								<div style="text-align: center; margin-bottom: 30px;">
									<a href="referfriend.do"><button type="button"
											class="btn btn-primary btn-sm"
											style="font-family: robotoregular; font-size: 16px; color: #0078b0; background-color: #FFF; border: 1px solid #0078b0;">Refer
											a Friend</button></a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		jQuery(document).ready(function() {
			$("#homelink").parent().addClass("active");
		});
	</script>
</body>