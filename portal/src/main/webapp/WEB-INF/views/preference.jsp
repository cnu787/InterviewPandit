<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
.close {
	color: #fff;
}
</style>
<body>
	<div
		style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
		<div class="container">
			<img src="resources/images/icon_profile.png">&nbsp;&nbsp;Profile
		</div>
	</div>
	<div class="" style="margin-top: 20px;">
		<div class="container">
			<h2 class="profile-header">We want to know your preference</h2>
			<h5 class="profile-text">Please select your preferences on your
				interview style</h5>
			<hr>
			<form id="interviewForm" action="#" method="get" autocomplete="off">
				<div class="row">
					<div class="col-md-10">
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
								<div class="form-group">
									<label style="color: #f36f21;">Can Conduct </label>
									<div class="row">
										<div class="col-sm-4 col-md-4 ">
											<label> <input type="checkbox" name="interviewtype[]"
												id="interviewtype1" value="1"> Technical Interviews
											</label>
										</div>
										<div class="col-sm-4 col-md-4 ">
											<label> <input type="checkbox" id="interviewtype2"
												name="interviewtype[]" value="2"> Non-Technical
												Interviews
											</label>
										</div>

									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								<div class="form-group">
									<label style="color: #f36f21;">Mode Of Interviews </label>
									<div class="row">
										<div class="col-sm-4 col-md-4 ">
											<label> <input type="checkbox" id="interviewmode3"
												name="interviewmode[]" value="3"> Video Interviews
											</label>
										</div>
										<div class="col-sm-4 col-md-4 ">
											<label> <input type="checkbox" id="interviewmode1"
												name="interviewmode[]" value="1"> Telephonic
												Interviews
											</label>
										</div>
										<!-- uncomment below code when location select is needed -->
										<!-- <div class="col-sm-4 col-md-4 ">
											<label> <input type="checkbox" id="interviewmode2"
												name="interviewmode[]" value="2"> Audio Video
												Interviews
											</label>
										</div> -->
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								<div class="form-group">
									<label style="color: #f36f21;">
										<!-- Can Interview Applicants
										based on <a href="#"><u>my availability</u></a> in -->
									</label>
									<div class="row">
										<div class="col-sm-4 col-md-4 ">
											<label> <!-- comment below code when location select is needed -->
												<!-- <input type="hidden" id="interviewlocality1"
												name="interviewlocality[]" value="1"> India --> <input
												type="hidden" id="interviewlocality1" checked="checked"
												name="interviewlocality[]" value="1">
											</label>
										</div>
										<!-- uncomment below code when location select is needed -->
										<!-- <div class="col-sm-4 col-md-4 ">
											<label> <input type="checkbox" id="interviewlocality2"
												name="interviewlocality[]" value="2"> United States
											</label>
										</div>
										<div class="col-sm-4 col-md-4 ">
											<label> <input type="checkbox" id="interviewlocality3"
												name="interviewlocality[]" value="3"> United Kingdom
											</label>
										</div> -->
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<input type="hidden" name="hiddenName" />
				<hr>
				<div class="row">
					<c:if test="${empty param.edit}">
						<div
							class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
							<div class="form-group">
								<button type="button" class="form-control mybackbtn" id="back">Back</button>
							</div>
						</div>
						<div
							class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
							<div class="form-group">
								<input type="submit" id="continue"
									class="form-control mycontinuebtn" value="Done" />
							</div>
						</div>
					</c:if>
					<c:if test="${param.edit==true}">

						<div
							class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
							<div class="form-group">
								<button type="button" class="form-control mybackbtn"
									onclick="window.location.href='myCompleteProfile.do';"
									id="back">Cancel</button>
							</div>
						</div>
						<div
							class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
							<div class="form-group">
								<button type="submit" id="continue"
									class="form-control mycontinuebtn">Save</button>
							</div>
						</div>
					</c:if>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript">
		jQuery(document).ready(function() {
			/* $.ajaxSetup ({cache: false}); */
			$("#profilelink").parent().addClass("active");

			$("#back").click(function() {
				$("#mainProfileContainer").load('skillsView');
			});
		});
	</script>
	<c:if test="${not empty UserPreference}">
		<script type="text/javascript">
			var mySplitFun = function(data, name) {
				var myArr = data.split(',');
				if (myArr.length == 6) {
					document.getElementById('selectall').checked = "checked";
				}
				for (var i = 0; i < myArr.length; i += 1) {
					var b = name + myArr[i];
					document.getElementById(b).checked = "checked";
				}
			};
			var interviewtype = "${UserPreference[0].interviewtype}";
			mySplitFun(interviewtype, 'interviewtype');
			var interviewmode = "${UserPreference[0].interviewmode}";
			mySplitFun(interviewmode, 'interviewmode');
			var interviewlocality = "${UserPreference[0].interviewlocality}";
			mySplitFun(interviewlocality, 'interviewlocality');
		</script>
	</c:if>
	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$('#interviewForm')
									.bootstrapValidator(
											{
												framework : 'bootstrap',
												feedbackIcons : {
													valid : 'glyphicon glyphicon-ok',
													invalid : 'glyphicon glyphicon-remove',
													validating : 'glyphicon glyphicon-refresh'
												},
												fields : {
													'interviewtype[]' : {
														validators : {
															choice : {
																min : 1,
																max : 2,
																message : 'Please select at least one interviews type.'
															}
														}
													},
													'interviewmode[]' : {
														validators : {
															choice : {
																min : 1,
																max : 3,
																message : 'Please select interviewmode '
															}
														}
													},
													'interviewlocality[]' : {
														validators : {
															choice : {
																min : 1,
																max : 3,
																message : 'Please select  at least one my availability.'
															}
														}
													}
												}
											});
							$('#interviewForm').on('success.form.bv',
									function(e) {
										e.preventDefault();
										myajaxform();
									});

						});

		var myajaxform = function() {
			$("#processImg").show();
			var formData = $('form').serialize();
			$.ajax({
				url : 'addPrefence', //Server script to process data
				type : 'GET',
				//Ajax events
				//beforeSend: beforeSendHandler,
				success : completeHandler,
				error : errorHandler,
				// Form data
				data : formData,
				//Options to tell jQuery not to process data or worry about content-type.
				cache : false,
				contentType : false,
				processData : false
			});
		};
		var completeHandler = function(data) {
			$("#processImg").hide();
			window.location.href = "myCompleteProfile.do";
		};
		var errorHandler = function(data) {

		};
		var progressHandlingFunction = function(data) {

		};
	</script>
</body>