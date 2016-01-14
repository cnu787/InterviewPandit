<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
table {
	width: 100%;
	border-collapse: collapse
}

tr:nth-of-type(odd) {
	background: #FFF
}

tr:nth-of-type(even) {
	background: #f2f2f2
}

.bgcolor>th {
	background: #59574B;
	color: #fff;
	font-weight: 700
}

td,th {
	padding: 10px 20px;
	border: 0 solid #ccc;
	text-wrap: none;
	white-space: -moz-pre-wrap !important;
	white-space: -webkit-pre-wrap;
	white-space: -pre-wrap;
	white-space: -o-pre-wrap;
	white-space: pre-wrap;
	white-space: normal
}

tr {
	border-bottom: 1px solid #ccc
}

* {
	margin: 0;
	padding: 0
}

#page-wrap {
	margin: 50px
}

table {
	width: 100%;
	border-collapse: collapse
}

.input-disabled {
	background-color: #EBEBE4;
	border: 1px solid #ABADB3;
	padding: 2px 1px;
}
</style>
<body>
	<input type="hidden" name="userId" id="userId" value="${userId}">
	<div class="" style="margin-top: 20px;">
		<div class="container">
			<div class="pull-right col-xs-7 col-sm-3 col-md-2"></div>
			<div class="row">
				<div class="col-md-9">
					<div class="row">
						<div class="col-md-12">
							<div class="row">
								<div class="col-xs-12 col-sm-3 col-md-3">
									<div class="form-group">
										<img style="width: 130px; height: 150px;"
											src="http://downloads.interviewpandit.com/images/${myprofile[0].photoid}"
											onerror="this.src='resources/images/image_profile.png';">
									</div>
								</div>
								<div class="col-xs-10 col-sm-8 col-md-8">
									<div class="form-group">
										<span
											style="text-transform: capitalize; font-family: robotoregular; font-size: 20px; color: #f36f21; white-space: -moz-pre-wrap !important; white-space: -webkit-pre-wrap; white-space: -pre-wrap; white-space: -o-pre-wrap; white-space: pre-wrap; word-wrap: break-word; word-break: break-all; white-space: normal;">${myprofile[0].firstname}
											${myprofile[0].lastname}<br>
										</span>${myprofile[0].emailid} <br>+${myprofile[0].countrycode}-${myprofile[0].mobileno}
										<br> Industry:<span
											style="font-family: robotoregular; font-size: 14px; color: #f36f21;">&nbsp;&nbsp;
											<c:if test="${myprofile[0].industryid!=0}">${myprofile[0].industryname}</c:if>
										</span><br>Domain:<span
											style="font-family: robotoregular; font-size: 14px; color: #f36f21;">&nbsp;&nbsp;
											<c:set var="i" value="0" /> <c:forEach var="domLst"
												items="${domainList}">
												<c:if test="${i != '0'}">,</c:if> 
									${domLst.domainname}
										<c:set var="i" value="${i+1}" />
											</c:forEach> <br> <c:if test="${not empty myprofile[0].resumeid}">
												<a
													href="http://downloads.interviewpandit.com/images/${myprofile[0].resumeid}"><u>My
														Resume</u></a>
											</c:if>

										</span><br> <span
											style="font-family: robotoregular; font-size: 14px; color: #f36f21;">
											<c:if test="${not empty myprofile[0].videoid}">
												<a
													href="http://downloads.interviewpandit.com/images/${myprofile[0].videoid}"><u>Brief
														Intro</u></a>
											</c:if>
										</span>
									</div>
								</div>
							</div>
						</div>
					</div>
					<hr>
					<div>
						<div class="row">
							<div class="col-sm-6 col-md-6">
								<h3
									style="font-family: robotoregular; font-size: 20px; color: #0095da;">
									Education</h3>
							</div>
						</div>

						<c:if test="${not empty educationList}">
							<div class="studyView">
								<table class="studyHistory ">
									<thead class="studythead">
										<tr class="studytr bgcolor">
											<th class="studyth" Style="color: #FFF">School/College/University</th>
											<th class="studyth" Style="color: #FFF">Graduated Year</th>
											<th class="studyth" Style="color: #FFF">Degree</th>
											<th class="studyth" Style="color: #FFF">Field of Study</th>
										</tr>
									</thead>
									<tbody class="studytbody">
										<c:set var="maxid" value="0" />
										<c:forEach var="eduLst" items="${educationList}">
											<tr class="studytr">
												<td class="studytd">${eduLst.universityname}</td>
												<td class="studytd">${eduLst.graduateyear}</td>
												<td class="studytd">${eduLst.degreename}</td>
												<td class="studytd">${eduLst.fieldofstudy}</td>
											</tr>

											<c:if test="${eduLst.educationid>maxid}">
												<c:set var="maxid" value="${eduLst.educationid}" />
											</c:if>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</c:if>
					</div>
					<div>
						<div class="row">
							<div class="col-sm-6 col-md-6">
								<h3
									style="font-family: robotoregular; font-size: 20px; color: #0095da;">
									Skills</h3>
							</div>
						</div>
						<c:if test="${not empty skillsList}">
							<div class="skillview">
								<table class="skillHistory">
									<thead class="skillthead">
										<tr class="skilltr bgcolor">
											<th class="skillth" Style="color: #FFF">Skill Type</th>
											<th class="skillth" Style="color: #FFF">Skill Name</th>
											<th class="skillth" Style="color: #FFF">Rating</th>
											<th class="skillth" Style="color: #FFF">Years of
												Experience</th>
										</tr>
									</thead>
									<tbody class="skilltbody">
										<c:set var="maxid" value="0" />
										<c:forEach var="skilLst" items="${skillsList}">
											<tr class="skilltr">
												<td class="skilltd">${skilLst.skilltypename}</td>
												<td class="skilltd">${skilLst.skillname}</td>
												<td class="skilltd">${skilLst.skillrating}</td>
												<td class="skilltd">${skilLst.year}&nbsp;Years&nbsp;${skilLst.month}&nbsp;months</td>
											</tr>

											<c:if test="${skilLst.skillid>maxid}">
												<c:set var="maxid" value="${skilLst.skillid}" />
											</c:if>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</c:if>
					</div>
					<div>
						<div class="row">
							<div class="col-sm-6 col-md-6">
								<h3
									style="font-family: robotoregular; font-size: 20px; color: #0095da;">
									Work Experience</h3>
							</div>
						</div>
						<c:if test="${not empty careerList}">
							<div class="careerview">
								<table class="careerHistory">
									<thead class="careerthead">
										<tr class="careertr bgcolor">
											<th class="careerth" Style="color: #FFF">Company Name</th>
											<th class="careerth" Style="color: #FFF">Position</th>
											<th class="careerth" Style="color: #FFF">Location</th>
											<th class="careerth" Style="color: #FFF">Time From</th>
											<th class="careerth" Style="color: #FFF">Time To</th>
										</tr>
									</thead>
									<tbody class="careertbody">
										<c:set var="maxid" value="0" />
										<c:forEach var="careerLst" items="${careerList}">
											<tr class="careertr">
												<td class="careertd">${careerLst.companyname}</td>
												<td class="careertd">${careerLst.designation}</td>
												<td class="careertd">${careerLst.location}</td>
												<td class="careertd">${careerLst.months}&nbsp;${careerLst.graduateyear}</td>
												<td class="careertd"><c:if test="${careerLst.tomonth.equals('0')}">
												Till date
												</c:if>
												<c:if test="${careerLst.tomonth!='0'}">
												${careerLst.tomonth}&nbsp;${careerLst.toyear}</c:if></td>
											</tr>

											<c:if test="${careerLst.careerid>maxid}">
												<c:set var="maxid" value="${careerLst.careerid}" />
											</c:if>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</c:if>
					</div>
					<hr>
					<c:if test="${myprofile[0].usertypeid eq 2}">
						<div>
							<div class="row">
								<div class="col-sm-6 col-md-6">
									<h3
										style="font-family: robotoregular; font-size: 20px; color: #0095da;">
										Preferences</h3>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12 col-sm-12 col-md-12">
									<div class="row">
										<div class="col-xs-12 col-sm-4 col-md-3">
											<div class="form-group">
												<label>Can Conduct</label>
												<ul style="list-style-type: square; margin-left: 25px;">
													<c:set var="interviewtype"
														value="${fn:split(UserPreference[0].interviewtype, ',')}" />
													<c:forEach var="inttyp" items="${interviewtype}">
														<c:if test="${inttyp==1}">
															<li>Technical Interviews</li>
														</c:if>
														<c:if test="${inttyp==2}">
															<li>Non-Technical Interviews</li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
										</div>
										<div class="col-xs-12 col-sm-4 col-md-3">
											<div class="form-group">
												<label>Mode of Interviews</label>
												<ul style="list-style-type: square; margin-left: 25px;">
													<c:set var="interviewmode"
														value="${fn:split(UserPreference[0].interviewmode, ',')}" />
													<c:forEach var="intmod" items="${interviewmode}">
														<c:if test="${intmod==3}">
															<li>Video Interviews</li>
														</c:if>
														<c:if test="${intmod==1}">
															<li>Telephonic Interviews</li>
														</c:if>
														<c:if test="${intmod==2}">
															<li>Audio Video Interviews</li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-12 col-sm-6 col-md-6">
											<div class="form-group">
												<label>Can Interview applicants based on my
													availability in</label>
												<ul style="list-style-type: square; margin-left: 25px;">
													<c:set var="interviewlocality"
														value="${fn:split(UserPreference[0].interviewlocality, ',')}" />
													<c:forEach var="intloc" items="${interviewlocality}">
														<c:if test="${intloc==1}">
															<li>India</li>
														</c:if>
														<c:if test="${intloc==2}">
															<li>United States</li>
														</c:if>
														<c:if test="${intloc==3}">
															<li>United Kingdom</li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</body>