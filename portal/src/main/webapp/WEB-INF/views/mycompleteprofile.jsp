<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script src="resources/js/mycompleteprofile.js"></script>
<link rel="stylesheet" href="resources/css/mycompleteprofile.css" />
<body>
	<div
		style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
		<div class="container">
			<img src="resources/images/icon_profile.png">&nbsp;&nbsp;Profile
		</div>
	</div>
	<div class="" style="margin-top: 20px;">
		<div class="container">
			<div class="row">

				<div class="profile-header col-xs-12 col-sm-3 col-md-8">Your
					Profile</div>
					<c:if test="${(eLearningStatus==3 || eLearningStatus==2)&& myprofile[0].usertypeid==2}">
					<div class="pull-right col-xs-6 col-sm-3 col-md-2">
						<button class="form-control mybackbtn"
							onClick="window.open('resources/td_assessment/index.html','mywindow','width=1024,height=700,toolbar=no,scrollbars=yes,location=no,resizable=yes')">Assessment</button>
					</div>
					</c:if>
				<c:if test="${eLearningStatus>=1 && myprofile[0].usertypeid==2}">
					<div class="pull-right col-xs-6 col-sm-3 col-md-2">
						<button class="form-control mybackbtn"
							onClick="window.open('resources/td_module/index.html','mywindow','width=1024,height=700,toolbar=no,scrollbars=yes,location=no,resizable=yes')">E-Learning</button>
					</div>					
				</c:if>

			</div>
			<hr>

			<div class="row">
				<div class="col-md-12">
					<div class="row">
						<div class="col-xs-12 col-sm-3 col-md-2">
							<div class="form-group">
								<img style="width: 130px; height: 150px;"
									src="http://downloads.interviewpandit.com/images/${myprofile[0].photoid}"
									onerror="this.src='resources/images/image_profile.png';">
							</div>
						</div>
						<div class="col-xs-10 col-sm-8 col-md-9">
							<div class="form-group">
								<span
									style="text-transform: capitalize; font-family: robotoregular; font-size: 20px; color: #f36f21; white-space: -moz-pre-wrap !important; white-space: -webkit-pre-wrap; white-space: -pre-wrap; white-space: -o-pre-wrap; white-space: pre-wrap; word-wrap: break-word; word-break: break-all; white-space: normal;">${myprofile[0].firstname}
									${myprofile[0].lastname}</span><br> <a href="#"><u>${myprofile[0].emailid}</u></a><br>
								<c:if
									test="${(not empty myprofile[0].mobileno)&&(not empty myprofile[0].countrycode)}">+${myprofile[0].countrycode}-${myprofile[0].mobileno}<br>
								</c:if>
								Industry:<span
									style="font-family: robotoregular; font-size: 14px; color: #f36f21;">&nbsp;&nbsp;
									<c:if test="${myprofile[0].industryid==0}">${myprofile[0].otherIndustry}</c:if>
									<c:if test="${myprofile[0].industryid!=0}">${myprofile[0].industryname}</c:if>
									<c:if test="${empty myprofile[0].industryid}">Not Added</c:if>
								</span><br>Domain:<span
									style="font-family: robotoregular; font-size: 14px; color: #f36f21;">&nbsp;&nbsp;
									<c:set var="i" value="0" /> <c:forEach var="domLst"
										items="${domainList}">
										<c:if test="${i != '0'}">,</c:if> 
									${domLst.domainname}
										<c:set var="i" value="${i+1}" />
									</c:forEach> <c:if test="${empty domainList}">Not Added</c:if> <br> <c:if
										test="${empty myprofile[0].resumeid}">Resume not uploaded
									</c:if> <c:if test="${not empty myprofile[0].resumeid}">
										<a
											href="http://downloads.interviewpandit.com/images/${myprofile[0].resumeid}"><u>My
												Resume</u></a>
									</c:if>

								</span><br> <span
									style="font-family: robotoregular; font-size: 14px; color: #f36f21;">
									<c:if test="${empty myprofile[0].videoid}">Video not uploaded
									</c:if> <c:if test="${not empty myprofile[0].videoid}">
										<a
											href="http://downloads.interviewpandit.com/images/${myprofile[0].videoid}"><u>Brief
												Intro</u></a>
									</c:if> <br>
									<div class="col-xs-10 col-sm-5 col-md-3"
										style="padding-left: 0px;">
										<a href="changePassword.do">Change Password</a>
										<c:if
											test="${eLearningStatus==2 && myprofile[0].usertypeid==2}">
											<br>
											<a href="myPayment.do">My Payments</a>
										</c:if>
										<c:if test="${myprofile[0].usertypeid==1}">
 										<br>
											<a href="myWallet.do">My Wallet</a> 
										</c:if>
									</div>
								</span>
							</div>
						</div>
						<div class="col-xs-2 col-sm-1 col-md-1">
							<div class="form-group pull-right">
								<a href="profileAboutMe.do?edit=true"><img
									class="visible-lg"
									src="resources/images/icon_profile_edit_SPhone.png"> <img
									class="visible-md"
									src="resources/images/icon_profile_edit_SPhone.png"> <img
									class="visible-sm"
									src="resources/images/icon_profile_edit_SPhone.png"> <img
									class="visible-xs" src="resources/images/icon_profile_edit.png"></a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<hr>
			<div class="col-xs-12">
				<div class="row visible-xs" style="background-color: #59574B;">

					<div class="col-xs-8">
						<h3
							style="font-family: robotoregular; font-size: 20px; color: #ffffff; margin-top: 12px;">
							Education</h3>
					</div>
					<div class="col-xs-4">
						<h3 class="pull-right" style="margin-top: 12px;">
							<a href="studyPage.do?edit=true"><img
								src="resources/images/icon_edit_sphone.png"></a>
						</h3>
					</div>
				</div>
				<br>
			</div>
			<div class="hidden-xs">
				<div class="row">
					<div class="col-sm-6 col-md-6">
						<h3
							style="font-family: robotoregular; font-size: 20px; color: #0095da;">
							Education</h3>
					</div>
					<div class="col-sm-6 col-md-6">
						<h3 class="pull-right">
							<a href="studyPage.do?edit=true"><img
								src="resources/images/icon_profile_edit_SPhone.png"></a>
						</h3>
					</div>
				</div>

				<c:if test="${not empty educationList}">
					<table>
						<thead>
							<tr>
								<th Style="color: #FFF">School/College/University</th>
								<th Style="color: #FFF">Graduated Year</th>
								<th Style="color: #FFF">Degree</th>
								<th Style="color: #FFF">Field of Study</th>
							</tr>
						</thead>
						<tbody>
							<c:set var="maxid" value="0" />
							<c:forEach var="eduLst" items="${educationList}">
								<tr>
									<td>${eduLst.universityname}</td>
									<td>${eduLst.graduateyear}</td>
									<td>${eduLst.degreename}</td>
									<td>${eduLst.fieldofstudy}</td>
								</tr>

								<c:if test="${eduLst.educationid>maxid}">
									<c:set var="maxid" value="${eduLst.educationid}" />
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
				<c:if test="${empty educationList}">
					<a href="applicantStudyView.do?edit=true"><img
						src="resources/images/addeducation.png" /></a>
				</c:if>
			</div>
			<div class="col-xs-12">
				<div class="row visible-xs" style="background-color: #59574B;">

					<div class="col-xs-8">
						<h3
							style="font-family: robotoregular; font-size: 20px; color: #ffffff; margin-top: 12px;">
							Skills</h3>
					</div>
					<div class="col-xs-4">
						<h3 class="pull-right" style="margin-top: 12px;">
							<a href="skillsView.do?edit=true"><img
								src="resources/images/icon_edit_sphone.png"></a>
						</h3>
					</div>
				</div>
				<br>
			</div>
			<div class="hidden-xs">
				<div class="row">
					<div class="col-sm-6 col-md-6">
						<h3
							style="font-family: robotoregular; font-size: 20px; color: #0095da;">
							Skills</h3>
					</div>
					<div class="col-sm-6 col-md-6">
						<h3 class="pull-right">
							<a href="skillsView.do?edit=true"><img
								src="resources/images/icon_profile_edit_SPhone.png"></a>
						</h3>
					</div>
				</div>
				<c:if test="${not empty skillsList}">
					<table>
						<thead>
							<tr>
								<th Style="color: #FFF">Skill Type</th>
								<th Style="color: #FFF">Skill Name</th>
								<th Style="color: #FFF">Rating</th>
								<th Style="color: #FFF">Years of Experience</th>
							</tr>
						</thead>
						<tbody>
							<c:set var="maxid" value="0" />
							<c:forEach var="skilLst" items="${skillsList}">
								<tr>
									<td>${skilLst.skilltypename}</td>
									<td>${skilLst.skillname}</td>
									<td>${skilLst.skillrating}</td>
									<td>${skilLst.year}&nbsp;Years&nbsp;${skilLst.month}&nbsp;months</td>
								</tr>

								<c:if test="${skilLst.skillid>maxid}">
									<c:set var="maxid" value="${skilLst.skillid}" />
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
				<c:if test="${empty skillsList}">
					<a href="skillspage.do?edit=true"><img
						src="resources/images/addskill.png" /></a>
				</c:if>
			</div>
			<div class="col-xs-12">
				<div class="row visible-xs" style="background-color: #59574B;">

					<div class="col-xs-8">
						<h3
							style="font-family: robotoregular; font-size: 20px; color: #ffffff; margin-top: 12px;">
							Work Experience</h3>
					</div>
					<div class="col-xs-4">
						<h3 class="pull-right" style="margin-top: 12px;">
							<a href="careerView.do?edit=true"><img
								src="resources/images/icon_edit_sphone.png"></a>
						</h3>
					</div>
				</div>
				<br>
			</div>
			<div class="hidden-xs">
				<div class="row">
					<div class="col-sm-6 col-md-6">
						<h3
							style="font-family: robotoregular; font-size: 20px; color: #0095da;">
							Work Experience</h3>
					</div>
					<div class="col-sm-6 col-md-6">
						<h3 class="pull-right">
							<a href="careerView.do?edit=true"><img
								src="resources/images/icon_profile_edit_SPhone.png"></a>
						</h3>
					</div>
				</div>
				<c:if test="${not empty careerList}">
					<table>
						<thead>
							<tr>
								<th Style="color: #FFF">Company Name</th>
								<th Style="color: #FFF">Position/Designation</th>
								<th Style="color: #FFF">Location</th>
								<th Style="color: #FFF">Time From</th>
								<th Style="color: #FFF">Time To</th>
							</tr>
						</thead>
						<tbody>
							<c:set var="maxid" value="0" />
							<c:forEach var="careerLst" items="${careerList}">
								<tr>
									<td>${careerLst.companyname}</td>
									<td>${careerLst.designation}</td>
									<td>${careerLst.location}</td>
									<td>${careerLst.months}&nbsp;${careerLst.graduateyear}</td>
									<td><c:if test="${careerLst.tomonth.equals('0')}">
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
				</c:if>
				<c:if test="${empty careerList}">
					<a href="careerpage.do?edit=true"><img
						src="resources/images/addworkexp.png" /></a>
				</c:if>
			</div>
			<!-- </div>
				</div> -->
			<c:if test="${myprofile[0].usertypeid==2}">
				<div class="col-xs-12">
					<div class="row visible-xs" style="background-color: #59574B;">

						<div class="col-xs-8">
							<h3
								style="font-family: robotoregular; font-size: 20px; color: #ffffff; margin-top: 12px;">
								Preferences</h3>
						</div>
						<div class="col-xs-4">
							<h3 class="pull-right" style="margin-top: 12px;">
								<a href="yourpreference.do?edit=true"><img
									src="resources/images/icon_edit_sphone.png"></a>
							</h3>
						</div>
					</div>
					<br>
				</div>
				<div class="hidden-xs">
					<div class="row">
						<div class="col-sm-6 col-md-6">
							<h3
								style="font-family: robotoregular; font-size: 20px; color: #0095da;">
								Preferences</h3>
						</div>
						<div class="col-sm-6 col-md-6">
							<h3 class="pull-right">
								<a href="yourpreference.do?edit=true"><img
									src="resources/images/icon_profile_edit_SPhone.png"></a>
							</h3>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="row">
								<div class="col-xs-12 col-sm-4 col-md-3">
									<div class="form-group">
										<label>Can Conduct</label>
										<ul style="list-style-type: square;">
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
										<ul style="list-style-type: square;">
											<c:set var="interviewmode"
												value="${fn:split(UserPreference[0].interviewmode, ',')}" />
											<c:forEach var="intmod" items="${interviewmode}">
												<c:if test="${intmod==3}">
													<li>Video Interviews</li>
												</c:if>
												<c:if test="${intmod==1}">
													<li>Telephonic Interviews</li>
												</c:if>
											</c:forEach>
										</ul>
									</div>
								</div>
							</div>
							<!-- uncomment below code when location select is needed -->
							<%-- <div class="row">
								<div class="col-xs-12 col-sm-6 col-md-6">
									<div class="form-group">
										<label>Can Interview applicants based on my
											availability in</label>
										<ul style="list-style-type: square;">
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
							</div> --%>
						</div>
					</div>
				</div>
			</c:if>
			<hr>
		</div>
	</div>
	<br>
</body>