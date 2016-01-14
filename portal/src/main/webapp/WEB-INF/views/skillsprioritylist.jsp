<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" href="resources/css/skillsprioritylist.css" />
<body>
	<div class="" style="margin-top: 20px;">
		<div class="container">
			<h1 class="header-file">Interview</h1>
			<hr>
			<form id="skillspriority" action="#" method="post" autocomplete="off">
				<div class="row">
					<div class="col-md-8">

						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">

								<label>Select Skills and Set Priority for Evaluation</label>
								<c:set var="skill" value="0" />
								<div class="row">

									<c:forEach var="skillslst" items="${skillNamesList}">
										<div class="col-xs-7 col-sm-4 col-md-4 ">
											<div class="checkbox">
												<label> <input type="checkbox"
													name="skills_${skill}" id="skills_${skill}"
													value="${skillslst.skillid}"
													<c:if test="${ skillslst.skillspriority != null}"> checked="true"
													</c:if>>${skillslst.skillname}
												</label><input type="hidden" name="page_${skill}" id="page_${skill}"
													value="${skillslst.pageid}">
											</div>
										</div>
										<div class="col-xs-4 col-sm-2 col-md-2 ">
											<select class="form-control" name="rating_${skill}"
												id="rating_${skill}">
												<option value="-1">Select</option>
												<c:forEach var="i" begin="1"
													end="${fn:length(skillNamesList)}">
													<option value="${i}"
														<c:if test="${i==skillslst.skillspriority}">
													selected="selected"
													</c:if>>${i}</option>
												</c:forEach>
											</select>
										</div>

										<c:set var="skill" value="${skill+1}" />
									</c:forEach>
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
						<div class="form-group">
							<button type="button" class="form-control mybackbtn" id="back">Back</button>
						</div>
					</div>
					<div
						class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
						<div class="form-group">
							<button id="continue" type="button" onclick="myajaxform()"
								class="form-control mycontinuebtn">Continue</button>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<script src="resources/js/skillsprioritylist.js"></script>
</body>