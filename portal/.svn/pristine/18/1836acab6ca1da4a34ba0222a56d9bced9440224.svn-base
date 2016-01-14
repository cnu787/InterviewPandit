<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
	<div class="" style="margin-top: 20px; margin-bottom: 60px;">
		<div class="container">
			<form id="feedBackform" action="#" autocomplete="off">
				<h1 class="header-file">ATTITUDES</h1>
				<hr>
				<c:forEach var="feedback" items="${feedbackSection}">
					<c:if
						test="${feedback.feedbacksubsectionname eq 'First Impression & Apperance'}">
						<input type="hidden" name="apperanceId" id="apperanceId"
							value="${feedback.feedbacksubsectionid}">
					</c:if>
					<c:if
						test="${feedback.feedbacksubsectionname eq 'Behaviour,Body Language(Non-Verbal)'}">
						<input type="hidden" name="bodyId" id="bodyId"
							value="${feedback.feedbacksubsectionid}">
					</c:if>
					<c:if
						test="${feedback.feedbacksubsectionname eq 'Verbal Communication'}">
						<input type="hidden" name="verbalId" id="verbalId"
							value="${feedback.feedbacksubsectionid}">
					</c:if>
					<c:if
						test="${feedback.feedbacksubsectionname eq 'Closing the Interview'}">
						<input type="hidden" name="closingId" id="closingId"
							value="${feedback.feedbacksubsectionid}">
					</c:if>
				</c:forEach>
				<input type="hidden" name="interviewId" id="interviewId" value="1">
				<div class="row">
					<div class="col-md-12">
						<c:if test="${not empty interviewDetailsAttitudes}">
							<c:forEach var="att" items="${interviewDetailsAttitudes}">
								<c:if test="${att.feedbacksubsectionid eq 11}">
									<h3 class="header-belowtext">First Impression and
										Appearance</h3>
									<div class="row">
										<div class="col-sm-7 col-md-7">
											<div class="form-group">
												<div class="text-instruction">Questions</div>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Applicant submits an updated, targeted and
														professional looking resume prior to the interview.</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Applicant expresses optimism and energy in initial
														greeting; offers a solid handshake</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Applicant is well groomed, has good hygiene and is
														appropriately dressed</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Applicant smiles and speaks clearly and
														distinctly; words are not mumbled</li>
												</ul>
											</div>
											<div class="row">
												<div class="col-sm-6 col-md-6 form-group">
													<div class="form-group text-instruction">Rating</div>
													<div class="form-group">
														<select class="form-control" name="apperanceRating"
															id="apperanceRating">
															<option value="-1">Select</option>
															<c:forEach var="skills" items="${skillratinglkp}">
																<option value="${skills.ratingid}"
																	<c:if test="${skills.ratingid==att.rating}">
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
													style="resize: none" name="apperanceNotes"
													id="apperanceNotes">${att.notes}</textarea>
											</div>
											<span
												style="font-size: 14px; font-family: robotoregular; color: #3e454c">Max
												150 characters</span>
										</div>
									</div>
									<hr>
								</c:if>
								<c:if test="${att.feedbacksubsectionid eq 12}">
									<h3 class="header-belowtext">Behaviour, Body Language
										(Non-Verbal)</h3>
									<div class="row">
										<div class="col-sm-7 col-md-7">
											<div class="form-group">
												<div class="text-instruction">Questions</div>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Applicant demonstrates professionalism; sits
														squarely in chair; has good posture</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Applicant demonstrates openness and receptiveness;
														open position (arms are not crossed)</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Applicant demonstrates interest and enthusiasm;
														leans slightly forward; uses facial expressions</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Applicant demonstrates confidence and
														attentiveness; maintains good eye contact</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Applicant maintains poise; appears relaxed;
														doesn't shift and fidget excessively</li>
												</ul>
											</div>
											<div class="row">
												<div class="col-sm-6 col-md-6 form-group">
													<div class="form-group text-instruction">Rating</div>
													<div class="form-group">
														<select class="form-control" name="bodyRating"
															id="bodyRating">
															<option value="-1">Select</option>
															<c:forEach var="skills" items="${skillratinglkp}">
																<option value="${skills.ratingid}"
																	<c:if test="${skills.ratingid==att.rating}">
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
													style="resize: none" name="bodyNotes" id="bodyNotes">${att.notes}</textarea>
											</div>
											<span
												style="font-size: 14px; font-family: robotoregular; color: #3e454c">Max
												150 characters</span>
										</div>
									</div>
									<hr>
								</c:if>
								<c:if test="${att.feedbacksubsectionid eq 13}">
									<h3 class="header-belowtext">Verbal Communication</h3>
									<div class="row">
										<div class="col-sm-7 col-md-7">
											<div class="form-group">
												<div class="text-instruction">Questions</div>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Applicant responds with concise, organized and
														well thought-out answers</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Applicant articulates ideas clearly and uses
														proper grammar and appropriate vocabulary</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Applicant demonstrates research and understanding
														of the desired career, position & employer</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Applicant demonstrates self awareness of work
														values and personal motivators</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Applicant illustrates strengths and skills through
														specific examples</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Applicant relates past achievements to skills used
														in the job</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Applicant avoids flat "yes" or "no" answers</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Applicant discusses a weakness honestly but
														neutralizes it by explaining steps taken to improve</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Applicant asks appropriate questions about the job
														and employer</li>
												</ul>
											</div>



											<div class="row">
												<div class="col-sm-6 col-md-6 form-group">
													<div class="form-group text-instruction">Rating</div>
													<div class="form-group">
														<select class="form-control" name="verbalRating"
															id="verbalRating">
															<option value="-1">Select</option>
															<c:forEach var="skills" items="${skillratinglkp}">
																<option value="${skills.ratingid}"
																	<c:if test="${skills.ratingid==att.rating}">
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
													style="resize: none" name="verbalNotes" id="verbalNotes">${att.notes}</textarea>
											</div>
											<span
												style="font-size: 14px; font-family: robotoregular; color: #3e454c">Max
												150 characters</span>
										</div>
									</div>
									<hr>
								</c:if>
								<c:if test="${att.feedbacksubsectionid eq 14}">
									<h3 class="header-belowtext">Closing the Interview</h3>
									<div class="row">
										<div class="col-sm-7 col-md-7">
											<div class="form-group">
												<div class="text-instruction">Questions</div>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Applicant inquires about timeframe and next
														actions to be taken</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Applicant thanks interviewer by name and acquires
														appropriate contact info. for follow-up</li>
												</ul>
											</div>
											<div class="row">
												<div class="col-sm-6 col-md-6 form-group">
													<div class="form-group text-instruction">Rating</div>
													<div class="form-group">
														<select class="form-control" name="closingRating"
															id="closingRating">
															<option value="-1">Select</option>
															<c:forEach var="skills" items="${skillratinglkp}">
																<option value="${skills.ratingid}"
																	<c:if test="${skills.ratingid==att.rating}">
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
													style="resize: none" name="closingNotes" id="closingNotes">${att.notes}</textarea>
											</div>
											<span
												style="font-size: 14px; font-family: robotoregular; color: #3e454c">Max
												150 characters</span>
										</div>
									</div>
									<hr>
								</c:if>
							</c:forEach>
						</c:if>
						<c:if test="${empty interviewDetailsAttitudes}">
							<h3 class="header-belowtext">First Impression and Appearance</h3>
							<div class="row">
								<div class="col-sm-7 col-md-7">
									<div class="form-group">
										<div class="text-instruction">Questions</div>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Applicant submits an updated, targeted and
												professional looking resume prior to the interview.</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Applicant expresses optimism and energy in initial
												greeting; offers a solid handshake</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Applicant is well groomed, has good hygiene and is
												appropriately dressed</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Applicant smiles and speaks clearly and distinctly;
												words are not mumbled</li>
										</ul>
									</div>
									<div class="row">
										<div class="col-sm-6 col-md-6 form-group">
											<div class="form-group text-instruction">Rating</div>
											<div class="form-group">
												<select class="form-control" name="apperanceRating"
													id="apperanceRating">
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
											style="resize: none" name="apperanceNotes"
											id="apperanceNotes"></textarea>
									</div>
									<span
										style="font-size: 14px; font-family: robotoregular; color: #3e454c">Max
										150 characters</span>
								</div>
							</div>
							<hr>
							<h3 class="header-belowtext">Behaviour, Body Language
								(Non-Verbal)</h3>
							<div class="row">
								<div class="col-sm-7 col-md-7">
									<div class="form-group">
										<div class="text-instruction">Questions</div>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Applicant demonstrates professionalism; sits
												squarely in chair; has good posture</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Applicant demonstrates openness and receptiveness;
												open position (arms are not crossed)</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Applicant demonstrates interest and enthusiasm;
												leans slightly forward; uses facial expressions</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Applicant demonstrates confidence and attentiveness;
												maintains good eye contact</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Applicant maintains poise; appears relaxed; doesn't
												shift and fidget excessively</li>
										</ul>
									</div>
									<div class="row">
										<div class="col-sm-6 col-md-6 form-group">
											<div class="form-group text-instruction">Rating</div>
											<div class="form-group">
												<select class="form-control" name="bodyRating"
													id="bodyRating">
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
											style="resize: none" name="bodyNotes" id="bodyNotes"></textarea>
									</div>
									<span
										style="font-size: 14px; font-family: robotoregular; color: #3e454c">Max
										150 characters</span>
								</div>
							</div>
							<hr>
							<h3 class="header-belowtext">Verbal Communication</h3>
							<div class="row">
								<div class="col-sm-7 col-md-7">
									<div class="form-group">
										<div class="text-instruction">Questions</div>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Applicant responds with concise, organized and well
												thought-out answers</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Applicant articulates ideas clearly and uses proper
												grammar and appropriate vocabulary</li>
										</ul>
									</div>




									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Applicant demonstrates research and understanding of
												the desired career, position & employer</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Applicant demonstrates self awareness of work values
												and personal motivators</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Applicant illustrates strengths and skills through
												specific examples</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Applicant relates past achievements to skills used
												in the job</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Applicant avoids flat "yes" or "no" answers</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Applicant discusses a weakness honestly but
												neutralizes it by explaining steps taken to improve</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Applicant asks appropriate questions about the job
												and employer</li>
										</ul>
									</div>



									<div class="row">
										<div class="col-sm-6 col-md-6 form-group">
											<div class="form-group text-instruction">Rating</div>
											<div class="form-group">
												<select class="form-control" name="verbalRating"
													id="verbalRating">
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
											style="resize: none" name="verbalNotes" id="verbalNotes"></textarea>
									</div>
									<span
										style="font-size: 14px; font-family: robotoregular; color: #3e454c">Max
										150 characters</span>
								</div>
							</div>
							<hr>
							<h3 class="header-belowtext">Closing the Interview</h3>
							<div class="row">
								<div class="col-sm-7 col-md-7">
									<div class="form-group">
										<div class="text-instruction">Questions</div>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Applicant inquires about timeframe and next actions
												to be taken</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Applicant thanks interviewer by name and acquires
												appropriate contact info. for follow-up</li>
										</ul>
									</div>
									<div class="row">
										<div class="col-sm-6 col-md-6 form-group">
											<div class="form-group text-instruction">Rating</div>
											<div class="form-group">
												<select class="form-control" name="closingRating"
													id="closingRating">
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
											style="resize: none" name="closingNotes" id="closingNotes"></textarea>
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
				var interviewId = $("#interviewId").val();
				$("#interviewFeedBackContainer").load("evaluatorsSkills", {
					'interviewId' : interviewId
				})
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
															apperanceRating : {
																validators : {
																	callback : {
																		message : "Please select listed rating .",
																		callback : function(
																				a,
																				b) {
																			var d = b
																					.getFieldElements(
																							"apperanceRating")
																					.val();
																			return -1 != d
																		}
																	}
																}
															},
															bodyRating : {
																validators : {
																	callback : {
																		message : "Please select listed rating.",
																		callback : function(
																				a,
																				b) {
																			var d = b
																					.getFieldElements(
																							"bodyRating")
																					.val();
																			return -1 != d
																		}
																	}
																}
															},
															verbalRating : {
																validators : {
																	callback : {
																		message : "Please select listed rating.",
																		callback : function(
																				a,
																				b) {
																			var d = b
																					.getFieldElements(
																							"verbalRating")
																					.val();
																			return -1 != d
																		}
																	}
																}
															},
															closingRating : {
																validators : {
																	callback : {
																		message : "Please select listed rating.",
																		callback : function(
																				a,
																				b) {
																			var d = b
																					.getFieldElements(
																							"closingRating")
																					.val();
																			return -1 != d
																		}
																	}
																}
															},
															apperanceNotes : {
																validators : {
																	notEmpty : {
																		message : 'The notes is required.'
																	}
																}
															},
															bodyNotes : {
																validators : {
																	notEmpty : {
																		message : 'The notes is required.'
																	}
																}
															},
															verbalNotes : {
																validators : {
																	notEmpty : {
																		message : 'The notes is required.'
																	}
																}
															},
															closingNotes : {
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
				url : "evaluatorsFeedBackUpdateOnAttitudes",
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
			$("#interviewFeedBackContainer").load("evaluatorImpression", {
				'interviewId' : interviewId
			})
		};
	</script>
</body>