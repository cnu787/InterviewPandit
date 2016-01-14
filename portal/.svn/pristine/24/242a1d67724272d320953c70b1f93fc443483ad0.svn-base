<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
	<div class="" style="margin-top: 20px; margin-bottom: 60px;">
		<div class="container">
			<form id="feedBackform" action="#" autocomplete="off">
				<h1 class="header-file">SKILLS</h1>
				<hr>
				<c:forEach var="feedback" items="${feedbackSection}">
					<c:if
						test="${feedback.feedbacksubsectionname eq 'Ability to develop alternative solutions'}">
						<input type="hidden" name="problemId" id="problemId"
							value="${feedback.feedbacksubsectionid}">
					</c:if>
					<c:if
						test="${feedback.feedbacksubsectionname eq 'Ability to plan & organize tasks'}">
						<input type="hidden" name="taskId" id="taskId"
							value="${feedback.feedbacksubsectionid}">
					</c:if>
					<c:if
						test="${feedback.feedbacksubsectionname eq 'Ability to work smoothly with others'}">
						<input type="hidden" name="othersteamID" id="othersteamID"
							value="${feedback.feedbacksubsectionid}">
					</c:if>
					<c:if
						test="${feedback.feedbacksubsectionname eq 'Ability to maintain good relations'}">
						<input type="hidden" name="relationsId" id="relationsId"
							value="${feedback.feedbacksubsectionid}">
					</c:if>
					<c:if
						test="${feedback.feedbacksubsectionname eq 'Ability to be self-motivated'}">
						<input type="hidden" name="emergencyId" id="emergencyId"
							value="${feedback.feedbacksubsectionid}">
					</c:if>
					<c:if
						test="${feedback.feedbacksubsectionname eq 'Ability to remain calm'}">
						<input type="hidden" name="responsibleId" id="responsibleId"
							value="${feedback.feedbacksubsectionid}">
					</c:if>
				</c:forEach>
				<input type="hidden" name="interviewId" id="interviewId" value="1">

				<div class="row">
					<div class="col-md-12">
						<c:if test="${not empty interviewDetailsSkills}">
							<c:forEach var="skill" items="${interviewDetailsSkills}">
								<c:if test="${skill.feedbacksubsectionid eq 5}">
									<h3 class="header-belowtext">Ability to develop
										alternative solutions and choose the best alternative to a
										problem</h3>
									<div class="row">
										<div class="col-sm-7 col-md-7">
											<div class="form-group">
												<div class="text-instruction">Questions</div>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Describe a time when you were missing something
														you needed to finish a project on your last job.</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Tell me about a time when you had some work to do,
														but you were not exactly sure how to do it.</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Describe a time when you had an idea about a
														different way to make, build, or repair something.</li>
												</ul>
											</div>
											<div class="row">
												<div class="col-sm-6 col-md-6 form-group">
													<div class="form-group text-instruction">Rating</div>
													<div class="form-group">
														<select class="form-control" name="problemRating"
															id="problemRating">
															<option value="-1">Select</option>
															<c:forEach var="skills" items="${skillratinglkp}">
																<option value="${skills.ratingid}"
																	<c:if test="${skills.ratingid==skill.rating}">
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
													style="resize: none" name="problemNotes" id="problemNotes">${skill.notes}</textarea>
											</div>

											<span
												style="font-size: 14px; font-family: robotoregular; color: #3e454c">Max
												150 characters</span>
										</div>
									</div>
									<hr>
								</c:if>
								<c:if test="${skill.feedbacksubsectionid eq 6}">
									<h3 class="header-belowtext">Ability to plan and organize
										tasks to meet deadlines</h3>
									<div class="row">
										<div class="col-sm-7 col-md-7">
											<div class="form-group">
												<div class="text-instruction">Questions</div>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Describe how you went about planning a project
														that you completed from start to finish.</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Tell me about a time when you were proud of the
														way you organized a project.</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Describe a time when you were working on an
														important deadline, but something of higher priority came
														up and prevented you from making the deadline.</li>
												</ul>
											</div>
											<div class="row">
												<div class="col-sm-6 col-md-6 form-group">
													<div class="form-group text-instruction">Rating</div>
													<div class="form-group">
														<select class="form-control" name="taskRating"
															id="taskRating">
															<option value="-1">Select</option>
															<c:forEach var="skills" items="${skillratinglkp}">
																<option value="${skills.ratingid}"
																	<c:if test="${skills.ratingid==skill.rating}">
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
													style="resize: none" name="taskNotes" id="taskNotes">${skill.notes}</textarea>
											</div>
											<span
												style="font-size: 14px; font-family: robotoregular; color: #3e454c">Max
												150 characters</span>
										</div>
									</div>
									<hr>
								</c:if>
								<c:if test="${skill.feedbacksubsectionid eq 7}">
									<h3 class="header-belowtext">Ability to work smoothly with
										others as a team to complete a task</h3>
									<div class="row">
										<div class="col-sm-7 col-md-7">
											<div class="form-group">
												<div class="text-instruction">Questions</div>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Tell me about a time when you had to work with
														three or more people to complete a project.</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Describe a time when you were given a choice of
														doing a group project, or working on your own.</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Describe a team activity that you like to
														participate in.</li>
												</ul>
											</div>
											<div class="row">
												<div class="col-sm-6 col-md-6 form-group">
													<div class="form-group text-instruction">Rating</div>
													<div class="form-group">
														<select class="form-control" name="othersteamRating"
															id="othersteamRating">
															<option value="-1">Select</option>
															<c:forEach var="skills" items="${skillratinglkp}">
																<option value="${skills.ratingid}"
																	<c:if test="${skills.ratingid==skill.rating}">
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
													style="resize: none" name="othersteamNotes"
													id="othersteamNotes">${skill.notes}</textarea>
											</div>
											<span
												style="font-size: 14px; font-family: robotoregular; color: #3e454c">Max
												150 characters</span>
										</div>
									</div>
									<hr>
								</c:if>
								<c:if test="${skill.feedbacksubsectionid eq 8}">
									<h3 class="header-belowtext">Ability to maintain good
										relations with others at place of work/study</h3>
									<div class="row">
										<div class="col-sm-7 col-md-7">
											<div class="form-group">
												<div class="text-instruction">Questions</div>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Tell us about a time at work, when someone really
														got on your nerves.</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Tell us about a time when someone criticized
														something you made, or something you did.</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Describe a time when a co-worker, or friend,
														wanted to do something that you did not agree with.</li>
												</ul>
											</div>
											<div class="row">
												<div class="col-sm-6 col-md-6 form-group">
													<div class="form-group text-instruction">Rating</div>
													<div class="form-group">
														<select class="form-control" name="relationsRating"
															id="relationsRating">
															<option value="-1">Select</option>
															<c:forEach var="skills" items="${skillratinglkp}">
																<option value="${skills.ratingid}"
																	<c:if test="${skills.ratingid==skill.rating}">
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
													style="resize: none" name="relationsNotes"
													id="relationsNotes">${skill.notes}</textarea>
											</div>
											<span
												style="font-size: 14px; font-family: robotoregular; color: #3e454c">Max
												150 characters</span>
										</div>
									</div>
									<hr>
								</c:if>
								<c:if test="${skill.feedbacksubsectionid eq 9}">
									<h3 class="header-belowtext">Ability to remain calm in an
										emergency situation</h3>
									<div class="row">
										<div class="col-sm-7 col-md-7">
											<div class="form-group">
												<div class="text-instruction">Questions</div>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Describe a time when you, a friend, a co-worker,
														or a family member were injured.</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Tell me about a time when an accident happened and
														when you, or someone around you, panicked.</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Tell me about a time at work, when something went
														wrong, quite unexpectedly.</li>
												</ul>
											</div>
											<div class="row">
												<div class="col-sm-6 col-md-6 form-group">
													<div class="form-group text-instruction">Rating</div>
													<div class="form-group">
														<select class="form-control" name="emergencyRating"
															id="emergencyRating">
															<option value="-1">Select</option>
															<c:forEach var="skills" items="${skillratinglkp}">
																<option value="${skills.ratingid}"
																	<c:if test="${skills.ratingid==skill.rating}">
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
													style="resize: none" name="emergencyNotes"
													id="emergencyNotes">${skill.notes}</textarea>
											</div>
											<span
												style="font-size: 14px; font-family: robotoregular; color: #3e454c">Max
												150 characters</span>
										</div>
									</div>
									<hr>
								</c:if>
								<c:if test="${skill.feedbacksubsectionid eq 10}">
									<h3 class="header-belowtext">Ability to be self-motivated,
										responsible, and dependable without close supervision</h3>
									<div class="row">
										<div class="col-sm-7 col-md-7">
											<div class="form-group">
												<div class="text-instruction">Questions</div>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Think of a time when the instructions you received
														from your manager, were not very clear about how to
														complete a specific task.</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Describe a time when you knew that project had to
														get done, but no one was standing over your shoulder
														forcing you to complete it.</li>
												</ul>
											</div>
											<div class="form-group text-knowledge">
												<ul style="list-style-type: square">
													<li>Tell me about the most challenging project you've
														ever done completely on your own.</li>
												</ul>
											</div>
											<div class="row">
												<div class="col-sm-6 col-md-6 form-group">
													<div class="form-group text-instruction">Rating</div>
													<div class="form-group">
														<select class="form-control" name="responsibleRating"
															id="responsibleRating">
															<option value="-1">Select</option>
															<c:forEach var="skills" items="${skillratinglkp}">
																<option value="${skills.ratingid}"
																	<c:if test="${skills.ratingid==skill.rating}">
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
													style="resize: none" name="responsibleNotes"
													id="responsibleNotes">${skill.notes}</textarea>
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


						<c:if test="${ empty interviewDetailsSkills}">
							<h3 class="header-belowtext">Ability to develop alternative
								solutions and choose the best alternative to a problem</h3>
							<div class="row">
								<div class="col-sm-7 col-md-7">
									<div class="form-group">
										<div class="text-instruction">Questions</div>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Describe a time when you were missing something you
												needed to finish a project on your last job.</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Tell me about a time when you had some work to do,
												but you were not exactly sure how to do it.</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Describe a time when you had an idea about a
												different way to make, build, or repair something.</li>
										</ul>
									</div>
									<div class="row">
										<div class="col-sm-6 col-md-6 form-group">
											<div class="form-group text-instruction">Rating</div>
											<div class="form-group">
												<select class="form-control" name="problemRating"
													id="problemRating">
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
											style="resize: none" name="problemNotes" id="problemNotes"></textarea>
									</div>

									<span
										style="font-size: 14px; font-family: robotoregular; color: #3e454c">Max
										150 characters</span>
								</div>
							</div>
							<hr>
							<h3 class="header-belowtext">Ability to plan and organize
								tasks to meet deadlines</h3>
							<div class="row">
								<div class="col-sm-7 col-md-7">
									<div class="form-group">
										<div class="text-instruction">Questions</div>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Describe how you went about planning a project that
												you completed from start to finish.</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Tell me about a time when you were proud of the way
												you organized a project.</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Describe a time when you were working on an
												important deadline, but something of higher priority came up
												and prevented you from making the deadline.</li>
										</ul>
									</div>
									<div class="row">
										<div class="col-sm-6 col-md-6 form-group">
											<div class="form-group text-instruction">Rating</div>
											<div class="form-group">
												<select class="form-control" name="taskRating"
													id="taskRating">
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
											style="resize: none" name="taskNotes" id="taskNotes"></textarea>
									</div>
									<span
										style="font-size: 14px; font-family: robotoregular; color: #3e454c">Max
										150 characters</span>
								</div>
							</div>
							<hr>
							<h3 class="header-belowtext">Ability to work smoothly with
								others as a team to complete a task</h3>
							<div class="row">
								<div class="col-sm-7 col-md-7">
									<div class="form-group">
										<div class="text-instruction">Questions</div>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Tell me about a time when you had to work with three
												or more people to complete a project.</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Describe a time when you were given a choice of
												doing a group project, or working on your own.</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Describe a team activity that you like to
												participate in.</li>
										</ul>
									</div>
									<div class="row">
										<div class="col-sm-6 col-md-6 form-group">
											<div class="form-group text-instruction">Rating</div>
											<div class="form-group">
												<select class="form-control" name="othersteamRating"
													id="othersteamRating">
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
											style="resize: none" name="othersteamNotes"
											id="othersteamNotes"></textarea>
									</div>
									<span
										style="font-size: 14px; font-family: robotoregular; color: #3e454c">Max
										150 characters</span>
								</div>
							</div>
							<hr>
							<h3 class="header-belowtext">Ability to maintain good
								relations with others at place of work/study</h3>
							<div class="row">
								<div class="col-sm-7 col-md-7">
									<div class="form-group">
										<div class="text-instruction">Questions</div>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Tell us about a time at work, when someone really
												got on your nerves.</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Tell us about a time when someone criticized
												something you made, or something you did.</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Describe a time when a co-worker, or friend, wanted
												to do something that you did not agree with.</li>
										</ul>
									</div>
									<div class="row">
										<div class="col-sm-6 col-md-6 form-group">
											<div class="form-group text-instruction">Rating</div>
											<div class="form-group">
												<select class="form-control" name="relationsRating"
													id="relationsRating">
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
											style="resize: none" name="relationsNotes"
											id="relationsNotes"></textarea>
									</div>
									<span
										style="font-size: 14px; font-family: robotoregular; color: #3e454c">Max
										150 characters</span>
								</div>
							</div>
							<hr>
							<h3 class="header-belowtext">Ability to remain calm in an
								emergency situation</h3>
							<div class="row">
								<div class="col-sm-7 col-md-7">
									<div class="form-group">
										<div class="text-instruction">Questions</div>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Describe a time when you, a friend, a co-worker, or
												a family member were injured.</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Tell me about a time when an accident happened and
												when you, or someone around you, panicked.</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Tell me about a time at work, when something went
												wrong, quite unexpectedly.</li>
										</ul>
									</div>
									<div class="row">
										<div class="col-sm-6 col-md-6 form-group">
											<div class="form-group text-instruction">Rating</div>
											<div class="form-group">
												<select class="form-control" name="emergencyRating"
													id="emergencyRating">
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
											style="resize: none" name="emergencyNotes"
											id="emergencyNotes"></textarea>
									</div>
									<span
										style="font-size: 14px; font-family: robotoregular; color: #3e454c">Max
										150 characters</span>
								</div>
							</div>
							<hr>
							<h3 class="header-belowtext">Ability to be self-motivated,
								responsible, and dependable without close supervision</h3>
							<div class="row">
								<div class="col-sm-7 col-md-7">
									<div class="form-group">
										<div class="text-instruction">Questions</div>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Think of a time when the instructions you received
												from your manager, were not very clear about how to complete
												a specific task.</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Describe a time when you knew that project had to
												get done, but no one was standing over your shoulder forcing
												you to complete it.</li>
										</ul>
									</div>
									<div class="form-group text-knowledge">
										<ul style="list-style-type: square">
											<li>Tell me about the most challenging project you've
												ever done completely on your own.</li>
										</ul>
									</div>
									<div class="row">
										<div class="col-sm-6 col-md-6 form-group">
											<div class="form-group text-instruction">Rating</div>
											<div class="form-group">
												<select class="form-control" name="responsibleRating"
													id="responsibleRating">
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
											style="resize: none" name="responsibleNotes"
											id="responsibleNotes"></textarea>
									</div>
									<span
										style="font-size: 14px; font-family: robotoregular; color: #3e454c">Max
										150 characters</span>
								</div>
							</div>
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
				$("#interviewFeedBackContainer").load("evaluatorsknowledge", {
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
															problemRating : {
																validators : {
																	callback : {
																		message : "Please select listed rating .",
																		callback : function(
																				a,
																				b) {
																			var d = b
																					.getFieldElements(
																							"problemRating")
																					.val();
																			return -1 != d
																		}
																	}
																}
															},
															taskRating : {
																validators : {
																	callback : {
																		message : "Please select listed rating.",
																		callback : function(
																				a,
																				b) {
																			var d = b
																					.getFieldElements(
																							"taskRating")
																					.val();
																			return -1 != d
																		}
																	}
																}
															},
															othersteamRating : {
																validators : {
																	callback : {
																		message : "Please select listed rating.",
																		callback : function(
																				a,
																				b) {
																			var d = b
																					.getFieldElements(
																							"othersteamRating")
																					.val();
																			return -1 != d
																		}
																	}
																}
															},
															relationsRating : {
																validators : {
																	callback : {
																		message : "Please select listed rating.",
																		callback : function(
																				a,
																				b) {
																			var d = b
																					.getFieldElements(
																							"relationsRating")
																					.val();
																			return -1 != d
																		}
																	}
																}
															},
															emergencyRating : {
																validators : {
																	callback : {
																		message : "Please select listed rating.",
																		callback : function(
																				a,
																				b) {
																			var d = b
																					.getFieldElements(
																							"emergencyRating")
																					.val();
																			return -1 != d
																		}
																	}
																}
															},
															responsibleRating : {
																validators : {
																	callback : {
																		message : "Please select listed rating.",
																		callback : function(
																				a,
																				b) {
																			var d = b
																					.getFieldElements(
																							"responsibleRating")
																					.val();
																			return -1 != d
																		}
																	}
																}
															},
															problemNotes : {
																validators : {
																	notEmpty : {
																		message : 'The notes is required.'
																	}
																}
															},
															othersteamNotes : {
																validators : {
																	notEmpty : {
																		message : 'The notes is required.'
																	}
																}
															},
															taskNotes : {
																validators : {
																	notEmpty : {
																		message : 'The notes is required.'
																	}
																}
															},
															relationsNotes : {
																validators : {
																	notEmpty : {
																		message : 'The notes is required.'
																	}
																}
															},
															emergencyNotes : {
																validators : {
																	notEmpty : {
																		message : 'The notes is required.'
																	}
																}
															},
															responsibleNotes : {
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
				url : "evaluatorsFeedBackUpdateOnSkills",
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
			$("#interviewFeedBackContainer").load("evaluatorsAttitudes", {
				'interviewId' : interviewId
			})
		};
	</script>
</body>