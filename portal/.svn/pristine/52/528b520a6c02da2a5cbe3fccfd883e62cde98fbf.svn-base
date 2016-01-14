<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
	<div
		style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
		<div class="container">
			<img src="resources/images/icon_profile.png">&nbsp;&nbsp;Profile
		</div>
	</div>
	<div class="" style="margin-top: 20px;">
		<div class="container">
			<h2 class="profile-header">Share your career map</h2>
			<h5 class="profile-text">Are you fresh out of
				school/college/University</h5>
			<hr>
			<form id="myprofile" action="" method="post" autocomplete="off">
				<div class="row">
					<div class="col-md-7">
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
								<div class="form-group">
									<div class="row">
										<div class="col-xs-3 col-sm-3 col-md-2 ">
											<c:if test="${careerId == 1}">
												<input type="radio" name="career" id="careeryes"
													checked="checked" value="1"> Yes	
												</c:if>
											<c:if test="${careerId != 1}">
												<input type="radio" name="career" id="careeryes" value="1"> Yes	
												</c:if>
										</div>
										<div class="col-xs-3 col-sm-3 col-md-2 ">
											<c:if test="${careerId == 0}">
												<input type="radio" name="career" id="careerno"
													checked="checked" value="0"> No	
												</c:if>
											<c:if test="${careerId != 0}">
												<input type="radio" name="career" id="careerno" value="0"> No	
												</c:if>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div style="margin-top: 20%;">
					<hr>
					<div class="row">
						<div
							class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
							<div class="form-group">
								<button type="button" class="form-control mybackbtn" id="back">Back</button>
							</div>
						</div>
						<div
							class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
							<div class="form-group">
								<button type="button" id="continue" onclick="myajaxform()"
									class="form-control mycontinuebtn">Continue</button>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<script src="resources/js/career.js"></script>

</body>