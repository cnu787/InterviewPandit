<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	jQuery(document).ready(function() {
		$("#interviewlandLink").parent().addClass("active")
	});
</script>
<body>
	<input type="hidden" name="interviewId" id="interviewId" value="1">
	<div class="" style="margin-top: 20px; margin-bottom: 60px;">
		<div class="container">
			<h1 class="header-file">Instructions for Evaluator</h1>
			<hr>
			<div class="row">
				<div class="col-md-12">
					<div class="row">
						<div class="col-sm-12 col-md-12">
							<div class="form-group text-instruction">The feedback form
								contains 3 distinct areas for evaluating an applicant based on
								KNOWLEDGE, SKILLS, and ATTITUDES (KSA).</div>
						</div>
						<div class="col-sm-12 col-md-12">
							<div class="form-group text-instruction">
								The first two areas focus on the <a href="#"
									style="color: #0095da;"><u>KNOWLEDGE</u></a> of the applicant
								as relevant for the job that she is applying.

							</div>
						</div>
						<div class="col-sm-12 col-md-12">
							<div class="form-group text-instruction">
								<ul style="list-style-type: square">
									<li>The next seven areas has three different probing
										questions each and they focus on the <a href="#"
										style="color: #0095da;"><u>SKILLS</u></a> of the candidate.
										You may choose to ask one or more of these or similar
										questions (we suggest you ask one depending on the applicant).
									</li>
								</ul>
							</div>
							<div class="form-group text-instruction">
								<ul style="list-style-type: square">
									<li>The final 4 areas focus on the <a href="#"
										style="color: #0095da;"><u>ATTITUDES,</u></a> behaviour and
										communication skiils which are overarching.
									</li>
								</ul>
							</div>
							<div class="form-group text-instruction">
								<ul style="list-style-type: square">
									<li>Listen to the interviewee's response, and then
										highlight your rating by circling number between 1 and 5. if
										you have additional feedback, write it down in the space
										provided.</li>
								</ul>
							</div>
						</div>
						<div class="col-sm-12 col-md-12">
							<div class="form-group text-instruction">Once you have
								completed evaluating the candidate on all the areas, make an
								overall rating of the applicant, taking into account all of the
								information you have received.</div>
						</div>
						<div
							class="col-xs-12 col-sm-4 col-sm-push-3 col-md-4 col-md-push-4">
							<div class="form-group">
								<img class="hidden-xs" src="resources/images/hire_nohire.png">
								<img class="visible-xs" src="resources/images/hire_nohire.png"
									style="width: 100%">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-8 col-md-4">
							<div id="feedback">
								<img name="feedback" src="resources/images/feedback.png" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		jQuery(document).ready(function() {
			$("#feedback").click(function() {
				var interviewId = $("#interviewId").val();
				$("#interviewFeedBackContainer").load('evaluatorsknowledge', {
					'interviewId' : interviewId
				});
			});
		});
	</script>
</body>