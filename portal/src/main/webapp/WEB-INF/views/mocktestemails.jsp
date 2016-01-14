<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- shyam added on 12-05-2015 - Start -->
<link rel="stylesheet" href="resources/css/datepicker.css" />
<script src="resources/js/bootstrap-datepicker.js"></script>
<!-- shyam added on 12-05-2015 - End -->
<link rel="stylesheet" href="resources/css/intlTelInput.css">
<link rel="stylesheet" href="resources/css/internalcalender.css">
<script src="resources/js/intlTelInput.js"></script>
<script type="text/javascript">
	jQuery(document).ready(function() {
		$("#mocktestlink").parent().addClass("active");
	});
</script>
<body>
	<div
		style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
		<div class="container">Mock Test</div>
	</div>
	<script type="text/javascript">
		function addfileName() {
			var filename = document.getElementById('BrowserHidden').files[0].name;
			document.getElementById('uploadFile').value = filename;
		}
	</script>
	<div class="" style="margin-top: 20px;">
		<div class="container">
			<br>
			<form id="myinterview" class="bv-form" enctype="multipart/form-data"
				action="tmiAdmMockuploadFiles.do" method="post" autocomplete="off">
				<div class="row">

					<div class="col-md-8">
						<c:if test="${count ==1}">
							<label><img style="width: 30px;"
								src="resources/images/ok.png" />&nbsp;Mock Test has been
								successfully <c:if test="${interviewStatus eq 1}">Enabled</c:if>
								<c:if test="${interviewStatus eq 0}">Disabled</c:if> for the
								applicants listed in the uploaded file.</label>
						</c:if>
						<div class="bg-danger">
							<c:if test="${not empty unregisteredMembersApp}">
								<strong><img style="width: 30px;"
									src="resources/images/warning.png" />&nbsp; Following
									Applicants are not registered with Interview Pandit. Please rectify the file
									and uploaded again.<br></strong>
								<c:forTokens items="${unregisteredMembersApp}" delims=","
									var="appname">
									<c:set var="appName1"
										value="${fn:replace(appname, 
                                '[', '')}" />
									<c:set var="appmembers"
										value="${fn:replace(appName1, 
                                ']', '')}" />
									<label> <c:out value="${appmembers}" /></label>
									<br>
								</c:forTokens>
							</c:if>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
								<label>Upload Bulk Email</label>
								<div class="row">
									<div class="col-xs-7 col-sm-6 col-md-6">
										<div class="form-group">
											<input id="uploadFile" name="uploadFile" data-required="true"
												readonly class="form-control" />
										</div>
									</div>
									<div class="col-xs-6 col-sm-3 col-md-3">
										<div
											style='background: url("resources/images/resumebrowse.png") no-repeat scroll 0% 0% transparent;'>
											<div class="form-group">
												<input type='file' class="form-control btn upploadbtn"
													name="BrowserHidden" id="BrowserHidden"
													onchange="addfileName();" />
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-sm-12 col-md-12">
								<div class="form-group">
									<label>Mock Interview Status</label>
									<div class="row" id="intertype">
										<div class="col-xs-6 col-sm-3 col-md-3 ">
											<input type="radio" id="enable" name="interviewStatus"
												value="1" checked="checked"> Enable

										</div>
										<div class="col-xs-6 col-sm-3 col-md-3 ">
											<input type="radio" id="disable" name="interviewStatus"
												value="0">Disable

										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<hr>
				<input type="hidden" name="_dontcare">
				<div class="row">
					<div
						class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
					</div>
					<div
						class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
						<div class="form-group">
							<button id="continue" type="submit"
								class="form-control mycontinuebtn">Upload</button>

						</div>
					</div>
				</div>

			</form>
		</div>
	</div>
	<script type="text/javascript">
		$(document)
				.ready(
						function() {

							$("#myinterview")
									.bootstrapValidator(
											{
												feedbackIcons : {
													valid : "glyphicon glyphicon-ok",
													invalid : "glyphicon glyphicon-remove",
													validating : "glyphicon glyphicon-refresh"
												},
												fields : {
													BrowserHidden : {
														validators : {
															notEmpty : {
																message : "Bulk Email Excel is required."
															},
															file : {
																extension : "xls,xlsx",
																type : "application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
																maxSize : 5e6,
																message : "The selected file is not valid, it should be (xls,xlsx) and 5 MB at maximum."
															}
														}
													}

												}
											})
						});
	</script>
</body>
</html>