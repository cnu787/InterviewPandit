<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
</style>
<body>
	<div class="" style="margin-top: 20px;">
		<div class="container">
			<h1 class="header-file">
				Interview: <span class="changecolor">Interview Pandit 9948765</span>
			</h1>
			<hr>
			<form id="modal-form" action="interviewModals.do">
				<button type="button" class="btn btn-primary" data-toggle="modal"
					data-target="#myModal">Click Interview</button>

				<!-- Modal -->
				<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
					data-backdrop="static" aria-labelledby="myModalLabel"
					aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times; </span>
								</button>
								<h4 class="modal-title" id="myModalLabel">Interview: Interview Pandit
									9948765</h4>
							</div>
							<div class="modal-body">
								<div class="row">
									<div class="col-md-12">
										<div class="row">
											<div class="col-md-5">
												<label>Applicant Name</label>
											</div>
											<div class="col-md-5">
												<label style="color: #0095DA;">Prince</label>
											</div>
										</div>
										<div class="row">
											<div class="col-md-5">
												<label>Industry</label>
											</div>
											<div class="col-md-5">
												<label>IT Company</label>
											</div>
										</div>
										<div class="row">
											<div class="col-md-5">
												<label>Domain</label>
											</div>
											<div class="col-md-5">
												<label>Banking Sector</label>
											</div>
										</div>
										<div class="row">
											<div class="col-md-5">
												<label>Position</label>
											</div>
											<div class="col-md-5">
												<label>Associate Consultant</label>
											</div>
										</div>
										<div class="row">
											<div class="col-md-5">
												<label>Role of Interviewer</label>
											</div>
											<div class="col-md-5">
												<label>Lead Consultant</label>
											</div>
										</div>
										<div class="row">
											<div class="col-md-5">
												<label>Location</label>
											</div>
											<div class="col-md-5">
												<label>India</label>
											</div>
										</div>
										<div class="row">
											<div class="col-md-5">
												<label>Type of Interview</label>
											</div>
											<div class="col-md-5">
												<label>Telephonic</label>
											</div>
										</div>
										<div class="row">
											<div class="col-md-5">
												<label>Slot Booked</label>
											</div>
											<div class="col-md-5">
												<label>20 Feb, 2015 | 3PM - 4PM</label>
											</div>
										</div>
										<div class="row">
											<div class="col-md-5">
												<label>Skills for Evaluation</label>
											</div>
											<div class="col-md-5">
												<label>Savings</label>
											</div>
										</div>
										<div class="row">
											<div class="col-md-5 col-md-push-5">
												<label>Wealth Management</label>
											</div>
										</div>
										<div class="row">
											<div class="col-md-5 col-md-push-5">
												<label>Business Analysis</label>
											</div>
										</div>
										<div class="row">
											<div class="col-md-5">
												<label>Evaluator</label>
											</div>
											<div class="col-md-5">
												<label>Suresh Ranjan</label>
											</div>
										</div>
										<div class="row">
											<div class="form-group">
												<div class="col-md-5">
													<label>Meeting Invite<span style="color: #F00;">*</span></label>
												</div>
												<div class="col-md-5 selectContainer">
													<label><select class="form-control" name="genre">
															<option value="">Select</option>
															<option value="accept">Accepted</option>
															<option value="reject">Rejected</option>
													</select></label>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="form-group">
												<div class="col-md-5">
													<label>Comments<span style="color: #F00;">*</span></label>
												</div>
												<div class="col-md-5">
													<label><textarea class="form-control"
															name="Comments" rows="3" maxlength="80"
															style="resize: none"></textarea><span
														style="float: right;">Max 80 characters</span></label>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">Close</button>
								<button type="button" class="btn btn-primary">Save</button>
							</div>
						</div>
					</div>
				</div>
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
							<input id="continue" type="submit"
								class="form-control mycontinuebtn" value="Cancel Booking" />
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
							$('#modal-form')
									.bootstrapValidator(
											{
												framework : 'bootstrap',
												feedbackIcons : {
													valid : 'glyphicon glyphicon-ok',
													invalid : 'glyphicon glyphicon-remove',
													validating : 'glyphicon glyphicon-refresh'
												},
												fields : {
													genre : {
														validators : {
															notEmpty : {
																message : 'The genre is required'
															}
														}
													},
													Comments : {
														validators : {
															notEmpty : {
																message : 'The content is required and cannot be empty'
															},
															stringLength : {
																max : 80,
																message : 'The content must be less than 80 characters long'
															}
														}
													}
												}
											});
						});
	</script>
</body>