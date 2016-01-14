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
			<c:if test="${mockTestStatus==1 && myprofile[0].usertypeid==1}">
				<div class="pull-right col-xs-7 col-sm-3 col-md-2">
					<a href="mockTest.do"><button
							class="form-control mycontinuebtn">TAKE MOCK TEST</button></a>							
				</div>
			</c:if>
		</div>
	</div>
	<div class="" style="margin-top: 20px; margin-bottom: 60px;">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="row">
						<div class="col-sm-6 col-md-4">
							<div class="form-group profile-reader">
								<div style="text-align: center; margin-top: 30px;">
									<img src="resources/images/icon_profile_main.png">
								</div>
								<div style="height: 150px;">
									<h3 class="head-profile">My Profile</h3>
									<div
										style="text-align: center; margin-left: 10px; margin-right: 10px;">
										<span class="groom-profile"> <c:if
												test="${profileReportCount==25}">
								 Complete your profile so that we can train you better
								</c:if> <c:if test="${profileReportCount>25}">
								Hi <span style="">${mySesProfile[0].screenName}</span>, consider updating
										your profile to help us groom you better 
									
								</c:if>

										</span>
									</div>
									<div align="center">
										<c:if test="${profileReportCount>25}">
											<div class="progress">
												<div class="progress-bar" role="progressbar"
													aria-valuenow="50" aria-valuemin="0" aria-valuemax="100"
													style="width: ${profileReportCount}%;"></div>
											</div>
											<div class="progress-value">${profileReportCount}%</div>
										</c:if>
									</div>
								</div>
								<div style="text-align: center; margin-bottom: 30px;">
									<c:if test="${profileReportCount==25}">
										<a href="externalHome.do"><button type="button"
												class="btn btn-primary btn-sm"
												style="font-family: robotoregular; font-size: 16px; color: #d44434; background-color: #FFF; border: 1px solid #d44434;">
												Create Profile</button></a>
									</c:if>
									<c:if test="${profileReportCount>25}">
										<a href="externalHome.do"><button type="button"
												class="btn btn-primary btn-sm"
												style="font-family: robotoregular; font-size: 16px; color: #d44434; background-color: #FFF; border: 1px solid #d44434;">
												Update Profile</button></a>
									</c:if>
								</div>
							</div>
						</div>
						<div class="col-sm-6 col-md-4">
							<div class="form-group interview-book">
								<div style="text-align: center; margin-top: 30px;">
									<img src="resources/images/icon_interviews.png">
								</div>
								<div style="height: 150px;">
									<h3 class="head-profile">
										<a style="color: #ffffff;" href="interviewLanding.do">Interviews&nbsp;</a>
										<c:if test="${interviewCount!=0}">
											<span class="badge">${interviewBookingCount}</span>
										</c:if>
									</h3>
									<div
										style="text-align: center; margin-left: 10px; margin-right: 10px;">
										<span class="groom-profile"> <c:if
												test="${interviewCount!=0}">
									You have booked ${interviewBookingCount} interviews
									</c:if> <c:if test="${interviewCount==0}">
									Book a slot for the mock interview to get trained by experts
									<br><a href="#" onClick="showratecard();"><div style="color:#fff"><u>at a nominal rate</u></div></a>
									</c:if>
										</span>
									</div>
								</div>
								<div style="text-align: center; margin-bottom: 30px;">
									<button onclick="bookInterview();" type="button"
										class="btn btn-primary btn-sm"
										style="font-family: robotoregular; font-size: 16px; color: #4d4645; background-color: #FFF; border: 1px solid #4d4645;">Book
										an Interview</button>
								</div>
							</div>
						</div>
						<div class="col-sm-6 col-md-4 referral-head">
							<div class="form-group referral-page">
								<div style="text-align: center; margin-top: 30px;">
									<img src="resources/images/icon_referrals.png">
								</div>
								<div style="height: 150px;">
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
									Have a friend who needs guidance? Refer with our referral program and earn great rewards.
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
			<div id="myModalrate" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header" style="background:#000;height:10%">
       <button type="button" class="close" data-dismiss="modal" style="font-size:20px;opacity:1;hover:#fff">&times;close</button>
        <h4 class="modal-title">RateCard</h4>
      </div><br>
      <div class="modal-bodyrate">
        <table border="1" style="width:90%;margin:auto;">
        <tr bgcolor="#000" style="color:#fff">
        <th width="20%">Role</th><th width="20%">Rate per Interview</th><th width="40%">Description</th>
        </tr>
      	<c:forEach var="roleList" items="${rolelist}">
				<c:if test="${roleList.currencytypeid eq 1}"><tr>
					<td>${roleList.interviewerrolename}</td>
					<td><img src="resources\images\rupee.png" />&nbsp;${roleList.amount}</td>
					<td>${roleList.description}</td></tr></c:if></c:forEach>
       
        </table>
      </div>
      <div class="modal-footer">
      
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
		function bookInterview() {
			window.location.href = "interviewBooking.do";
		}
		function showratecard(){
				 $("#myModalrate").modal({
					show : !0
				})
		
		}
		
	</script>
</body>