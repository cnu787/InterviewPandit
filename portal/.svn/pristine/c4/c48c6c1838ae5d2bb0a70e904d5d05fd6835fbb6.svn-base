<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
	<div class="" style="margin-top: 20px; margin-bottom: 60px;">
		<div class="container">
			<form id="feedBackform" action="#" autocomplete="off">
				<h1 class="header-file">Knowledge</h1>
				<hr>
				<c:forEach var="feedback" items="${feedbackSection}">
					<c:if
						test="${feedback.feedbacksubsectionname eq 'Education Qualification'}">
						<input type="hidden" name="educationalId" id="educationalId"
							value="${feedback.feedbacksubsectionid}">
					</c:if>
					<c:if
						test="${feedback.feedbacksubsectionname eq 'Interest in Job'}">
						<input type="hidden" name="interestId" id="interestId"
							value="${feedback.feedbacksubsectionid}">
					</c:if>
					<c:if
						test="${feedback.feedbacksubsectionname eq 'Technical Knowledge'}">
						<input type="hidden" name="technicalId" id="technicalId"
							value="${feedback.feedbacksubsectionid}">
					</c:if>
					<c:if
						test="${feedback.feedbacksubsectionname eq 'General Knowledge about Technical Industry'}">
						<input type="hidden" name="generalId" id="generalId"
							value="${feedback.feedbacksubsectionid}">
					</c:if>
				</c:forEach>
				<input type="hidden" name="interviewId" id="interviewId" value="1">
				<c:if test="${not empty interviewDetailsKnowledge}">
					<c:forEach var="knowledge" items="${interviewDetailsKnowledge}">

						<div class="row">
							<div class="col-md-12">
								<c:if test="${knowledge.feedbacksubsectionid eq 1}">
									<h3 class="header-belowtext">Educational Qualifications</h3>
									<div class="row">
										<div class="col-sm-7 col-md-7">
											<div class="form-group">
												<div class="text-instruction">Questions</div>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Tell me more about you and your education and job
														experience.</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Tell me succinctly about the projects have you
														done during your career?</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>What other areas interest you?</li>
												</ul>
											</div>

											<div class="row">
												<div class="col-sm-6 col-md-6 form-group">
													<div class="form-group text-instruction">Rating</div>
													<div class="form-group">
														<select class="form-control" name="educationalRating"
															id="educationalRating">
															<option value="-1">Select</option>
															<c:forEach var="skills" items="${skillratinglkp}">
																<option value="${skills.ratingid}"
																	<c:if test="${skills.ratingid==knowledge.rating}">
																selected="selected"
													</c:if>>
																	${skills.rating}</option>
															</c:forEach>
														</select>
													</div>
												</div>
											</div>

										</div>
										<div class="col-sm-5 col-md-5">
											<div class="form-group text-instruction">Notes</div>
											<div class="form-group">
												<textarea class="form-control" rows="5" maxlength="150"
													style="resize: none" name="educationalNotes"
													id="educationalNotes">${knowledge.notes}</textarea>
											</div>
											<span
												style="font-size: 14px; font-family: robotoregular; color: #3e454c">Max
												150 characters</span>
										</div>
									</div>
									<hr>
								</c:if>
								<c:if test="${knowledge.feedbacksubsectionid eq 2}">
									<h3 class="header-belowtext">Interest in Job</h3>
									<div class="row">
										<div class="col-sm-7 col-md-7">
											<div class="form-group">
												<div class="text-instruction">Questions</div>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Can you tell me some of the reasons why you
														decided to apply for this job?</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>What are some of the experiences you have had in
														the past that increased your interest in this job?</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>What knowledge do you have about what would you do
														on this job?</li>
												</ul>
											</div>
											<div class="row">
												<div class="col-sm-6 col-md-6 form-group">
													<div class="form-group text-instruction">Rating</div>
													<div class="form-group">
														<select class="form-control" name="interestRating"
															id="InterestRating">
															<option value="-1">Select</option>
															<c:forEach var="skills" items="${skillratinglkp}">
																<option value="${skills.ratingid}"
																	<c:if test="${skills.ratingid==knowledge.rating}">
																selected="selected"
													</c:if>>
																	${skills.rating}</option>
															</c:forEach>
														</select>
													</div>
												</div>
											</div>

										</div>
										<div class="col-sm-5 col-md-5">
											<div class="form-group text-instruction">Notes</div>
											<div class="form-group">
												<textarea class="form-control" rows="5" maxlength="150"
													style="resize: none" name="interestNotes"
													id="interestNotes">${knowledge.notes}</textarea>
											</div>
											<span
												style="font-size: 14px; font-family: robotoregular; color: #3e454c">Max
												150 characters</span>
										</div>

									</div>
									<hr>
								</c:if>
								<c:if test="${knowledge.feedbacksubsectionid eq 3}">
									<h3 class="header-belowtext">Technical Knowledge</h3>
									<div class="row">
										<div class="col-sm-7 col-md-7">
											<div class="form-group">
												<div class="text-instruction">Questions</div>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Before coming for the mock interview scan the
														resume for the technical experiences, prepare 8 to 10
														questions.</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>First check with the candidate about the technical
														area he is conversant with, and then ask 4 to 5 relevant
														questions from the hand-out.</li>
												</ul>
											</div>

											<div class="row">
												<div class="col-sm-6 col-md-6 form-group">
													<div class="form-group text-instruction">Rating</div>
													<div class="form-group">
														<select class="form-control" name="technicalRating"
															id="technicalRating">
															<option value="-1">Select</option>
															<c:forEach var="skills" items="${skillratinglkp}">
																<option value="${skills.ratingid}"
																	<c:if test="${skills.ratingid==knowledge.rating}">
																selected="selected"
													</c:if>>
																	${skills.rating}</option>
															</c:forEach>
														</select>
													</div>
												</div>
											</div>
										</div>
										<div class="col-sm-5 col-md-5">
											<div class="form-group text-instruction">Notes</div>
											<div class="form-group">
												<textarea class="form-control" rows="5" maxlength="150"
													style="resize: none" name="technicalNotes"
													id="technicalNotes">${knowledge.notes}</textarea>
											</div>
											<span
												style="font-size: 14px; font-family: robotoregular; color: #3e454c">Max
												150 characters</span>
										</div>

									</div>
									<hr>
								</c:if>
								<c:if test="${knowledge.feedbacksubsectionid eq 4}">
									<h3 class="header-belowtext">General Knowledge about
										Technical Industry</h3>
									<div class="row">
										<div class="col-sm-7 col-md-7">
											<div class="form-group">
												<div class="text-instruction">Questions</div>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Before coming for the mock interview scan the
														resume for the interest areas, and prepare 5 to 6
														questions.</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Fist check with the candidate about the language
														he is conersant with, and then ask 3 to 4 relevant
														questions from the hand-out.</li>
												</ul>
											</div>

											<div class="row">
												<div class="col-sm-6 col-md-6 form-group">
													<div class="form-group text-instruction">Rating</div>
													<div class="form-group">
														<select class="form-control" name="generalRating"
															id="generalRating">
															<option value="-1">Select</option>
															<c:forEach var="skills" items="${skillratinglkp}">
																<option value="${skills.ratingid}"
																	<c:if test="${skills.ratingid==knowledge.rating}">
																selected="selected"
													</c:if>>
																	${skills.rating}</option>
															</c:forEach>
														</select>
													</div>
												</div>
											</div>
										</div>
										<div class="col-sm-5 col-md-5">
											<div class="form-group text-instruction">Notes</div>
											<div class="form-group">
												<textarea class="form-control" rows="5" maxlength="150"
													style="resize: none" name="generalNotes" id="generalNotes">${knowledge.notes}</textarea>
											</div>
											<span
												style="font-size: 14px; font-family: robotoregular; color: #3e454c">Max
												150 characters</span>
										</div>
									</div>
									<hr>
								</c:if>
							</div>
						</div>
					</c:forEach>
				</c:if>
				<c:if test="${empty interviewDetailsKnowledge}">
					<h3 class="header-belowtext">Educational Qualifications</h3>
					<div class="row">
						<div class="col-md-12">
							<div class="row">
								<div class="col-sm-7 col-md-7">
									<div class="form-group">
										<div class="text-instruction">Questions</div>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Tell me more about you and your education and job
												experience.</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Tell me succinctly about the projects have you done
												during your career?</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>What other areas interest you?</li>
										</ul>
									</div>
									<div class="row">
										<div class="col-sm-6 col-md-6 form-group">
											<div class="form-group text-instruction">Rating</div>
											<div class="form-group">
												<select class="form-control" name="educationalRating"
													id="educationalRating">
													<option value="-1">Select</option>
													<c:forEach var="skills" items="${skillratinglkp}">
														<option value="${skills.ratingid}">
															${skills.rating}</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>
								</div>

								<div class="col-sm-5 col-md-5">
									<div class="form-group text-instruction">Notes</div>
									<div class="form-group">
										<textarea class="form-control" rows="5" maxlength="150"
											style="resize: none" name="educationalNotes"
											id="educationalNotes"></textarea>
									</div>
									<span
										style="font-size: 14px; font-family: robotoregular; color: #3e454c">Max
										150 characters</span>
								</div>

							</div>

							<hr>
							<h3 class="header-belowtext">Interest in Job</h3>
							<div class="row">
								<div class="col-sm-7 col-md-7">
									<div class="form-group">
										<div class="text-instruction">Questions</div>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Can you tell me some of the reasons why you decided
												to apply for this job?</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>What are some of the experiences you have had in the
												past that increased your interest in this job?</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>What knowledge do you have about what would you do
												on this job?</li>
										</ul>
									</div>
									<div class="row">
										<div class="col-sm-6 col-md-6 form-group">
											<div class="form-group text-instruction">Rating</div>
											<div class="form-group">
												<select class="form-control" name="interestRating"
													id="InterestRating">
													<option value="-1">Select</option>
													<c:forEach var="skills" items="${skillratinglkp}">
														<option value="${skills.ratingid}">
															${skills.rating}</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>
								</div>
								<div class="col-sm-5 col-md-5">
									<div class="form-group text-instruction">Notes</div>
									<div class="form-group">
										<textarea class="form-control" rows="5" maxlength="150"
											style="resize: none" name="interestNotes" id="interestNotes"></textarea>
									</div>
									<span
										style="font-size: 14px; font-family: robotoregular; color: #3e454c">Max
										150 characters</span>
								</div>
							</div>
							<hr>
							<h3 class="header-belowtext">Technical Knowledge</h3>
							<div class="row">
								<div class="col-sm-7 col-md-7">
									<div class="form-group">
										<div class="text-instruction">Questions</div>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Before coming for the mock interview scan the resume
												for the technical experiences, prepare 8 to 10 questions.</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>First check with the candidate about the technical
												area he is conversant with, and then ask 4 to 5 relevant
												questions from the hand-out.</li>
										</ul>
									</div>
									<div class="row">
										<div class="col-sm-6 col-md-6 form-group">
											<div class="form-group text-instruction">Rating</div>
											<div class="form-group">
												<select class="form-control" name="technicalRating"
													id="technicalRating">
													<option value="-1">Select</option>
													<c:forEach var="skills" items="${skillratinglkp}">
														<option value="${skills.ratingid}">
															${skills.rating}</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>
								</div>
								<div class="col-sm-5 col-md-5">
									<div class="form-group text-instruction">Notes</div>
									<div class="form-group">
										<textarea class="form-control" rows="5" maxlength="150"
											style="resize: none" name="technicalNotes"
											id="technicalNotes"></textarea>
									</div>
									<span
										style="font-size: 14px; font-family: robotoregular; color: #3e454c">Max
										150 characters</span>
								</div>
							</div>
							<hr>
							<h3 class="header-belowtext">General Knowledge about
								Technical Industry</h3>
							<div class="row">
								<div class="col-sm-7 col-md-7">
									<div class="form-group">
										<div class="text-instruction">Questions</div>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Before coming for the mock interview scan the resume
												for the interest areas, and prepare 5 to 6 questions.</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Fist check with the candidate about the language he
												is conersant with, and then ask 3 to 4 relevant questions
												from the hand-out.</li>
										</ul>
									</div>
									<div class="row">
										<div class="col-sm-6 col-md-6 form-group">
											<div class="form-group text-instruction">Rating</div>
											<div class="form-group">
												<select class="form-control" name="generalRating"
													id="generalRating">
													<option value="-1">Select</option>
													<c:forEach var="skills" items="${skillratinglkp}">
														<option value="${skills.ratingid}">
															${skills.rating}</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>
								</div>
								<div class="col-sm-5 col-md-5">
									<div class="form-group text-instruction">Notes</div>
									<div class="form-group">
										<textarea class="form-control" rows="5" maxlength="150"
											style="resize: none" name="generalNotes" id="generalNotes"></textarea>
									</div>
									<span
										style="font-size: 14px; font-family: robotoregular; color: #3e454c">Max
										150 characters</span>
								</div>
							</div>
							<hr>
						</div>
					</div>

				</c:if>
				<div
					class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
					<div class="form-group">
						<button type="button" class="form-control mybackbtn"
							data-dismiss="modal" id="back">Back</button>
					</div>
				</div>
				<div
					class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
					<div class="form-group">
						<button id="continue1" type="submit"
							class="form-control mycontinuebtn">Continue</button>

					</div>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript">
		jQuery(document).ready(function() {
			$("#back").click(function() {
				$("#interviewFeedBackContainer").load("evaluatorInstructions")
			});
		});

		$(document)
				.ready(
						function() {
							myflag = 0;
									$("#feedBackform")
											.bootstrapValidator(
													{
														feedbackIcons : {
															valid : "glyphicon glyphicon-ok",
															invalid : "glyphicon glyphicon-remove",
															validating : "glyphicon glyphicon-refresh"
														},
														fields : {
															educationalRating : {
																validators : {
																	callback : {
																		message : "Please select listed rating.",
																		callback : function(
																				a,
																				b) {
																			var d = b
																					.getFieldElements(
																							"educationalRating")
																					.val();
																			return -1 != d
																		}
																	}
																}
															},
															interestRating : {
																validators : {
																	callback : {
																		message : "Please select listed rating.",
																		callback : function(
																				a,
																				b) {
																			var d = b
																					.getFieldElements(
																							"interestRating")
																					.val();
																			return -1 != d
																		}
																	}
																}
															},
															technicalRating : {
																validators : {
																	callback : {
																		message : "Please select listed rating.",
																		callback : function(
																				a,
																				b) {
																			var d = b
																					.getFieldElements(
																							"technicalRating")
																					.val();
																			return -1 != d
																		}
																	}
																}
															},
															generalRating : {
																validators : {
																	callback : {
																		message : "Please select listed rating.",
																		callback : function(
																				a,
																				b) {
																			var d = b
																					.getFieldElements(
																							"generalRating")
																					.val();
																			return -1 != d
																		}
																	}
																}
															},
															educationalNotes : {
																validators : {
																	notEmpty : {
																		message : 'The notes is required.'
																	}
																}
															},
															interestNotes : {
																validators : {
																	notEmpty : {
																		message : 'The notes is required.'
																	}
																}
															},
															technicalNotes : {
																validators : {
																	notEmpty : {
																		message : 'The notes is required.'
																	}
																}
															},
															generalNotes : {
																validators : {
																	notEmpty : {
																		message : 'The notes is required.'
																	}
																}
															}

														}
													}), $("#feedBackform").on(
											"success.form.bv",
											function(a) {
												a.preventDefault(), 0 == myflag
														&& myajaxform()
											})
						});

		function myajaxform() {
			myflag = 1;
			var a = $("form").serialize();
			$.ajax({
				url : "evaluatorsFeedBackUpdateOnKnowledge",
				type : "GET",
				success : completeHandler,
				error : errorHandler,
				data : a,
				cache : !1,
				contentType : !1,
				processData : !1
			})
		}
		jQuery(document).ready(function() {

		});
		var errorHandler = function() {
		}, progressHandlingFunction = function() {
		}, completeHandler = function() {
			var interviewId = $("#interviewId").val();
			$("#interviewFeedBackContainer").load("evaluatorsSkills", {
				'interviewId' : interviewId
			})
		};
	</script>
</body>