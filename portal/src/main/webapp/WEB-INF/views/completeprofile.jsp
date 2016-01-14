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
/* for Study css */
.studyHistory>tbody>tr:nth-of-type(odd) {
	background: #FFF
}

.studyHistory>tbody>tr:nth-of-type(even) {
	background: #f2f2f2
}

.studyHistory>thead>tr>th {
	background: #59574B;
	color: #fff;
	font-weight: 700
}

.studyHistory>tbody>tr>td,th {
	padding: 10px 20px;
	border: 0 solid #ccc;
	text-align: left
}

.studyHistory>tbody>tr {
	border-bottom: 1px solid #ccc
}

@media only screen and (max-width: 700px) {
	.studyHistory,.studythead,.studytbody,.studyth,.studytd,.studytr {
		display: block
	}
	.studyHistory>tbody>tr>td {
		color: #0095da;
		font-size: 14px;
		font-family: "robotoregular"
	}
	.studyHistory>tbody>tr:nth-of-type(even) {
		background: #f2f2f2
	}
	.studyHistory>tbody>tr:nth-of-type(odd) {
		background: #f2f2f2
	}
	.studyView>table thead tr {
		position: absolute;
		top: -9999px;
		left: -9999px
	}
	.studyHistory>tbody>tr {
		border: 1px solid #ccc;
		margin-top: 25px
	}
	.studyHistory>tbody>tr>td {
		border: none;
		border-bottom: 1px solid #eee;
		position: relative;
		padding-left: 50%
	}
	.studyHistory>tbody>tr>td:before {
		position: absolute;
		top: 6px;
		left: 6px;
		width: 45%;
		padding-right: 10px;
		white-space: nowrap
	}
	.studyHistory>tbody>tr>td:nth-of-type(1):before {
		content: "College :";
		font-family: "robotoregular";
		color: #3E454c;
		font-size: 14px
	}
	.studyHistory>tbody>tr>td:nth-of-type(2):before {
		content: "Graduated Year :";
		font-family: "robotoregular";
		color: #3E454c;
		font-size: 14px
	}
	.studyHistory>tbody>tr>td:nth-of-type(3):before {
		content: "Degree :";
		font-family: "robotoregular";
		color: #3E454c;
		font-size: 14px
	}
	.studyHistory>tbody>tr>td:nth-of-type(4):before {
		content: "Field of Study :";
		font-family: "robotoregular";
		color: #3E454c;
		font-size: 14px
	}
}

/* for Skill css */
.skillHistory>tbody>tr:nth-of-type(odd) {
	background: #FFF
}

.skillHistory>tbody>tr:nth-of-type(even) {
	background: #f2f2f2
}

.skillHistory>thead>tr>th {
	background: #59574B;
	color: #fff;
	font-weight: 700
}

.skillHistory>tbody>tr>td,th {
	padding: 10px 20px;
	border: 0 solid #ccc;
	text-align: left
}

.skillHistory>tbody>tr {
	border-bottom: 1px solid #ccc
}

@media only screen and (max-width: 700px) {
	.skillHistory,.skillthead,.skilltbody,.skillth,.skilltd,.skilltr {
		display: block
	}
	.skillHistory>tbody>tr>td {
		color: #0095da;
		font-size: 14px;
		font-family: "robotoregular"
	}
	.skillHistory>tbody>tr:nth-of-type(even) {
		background: #f2f2f2
	}
	.skillHistory>tbody>tr:nth-of-type(odd) {
		background: #f2f2f2
	}
	.skillview>table thead tr {
		position: absolute;
		top: -9999px;
		left: -9999px
	}
	.skillHistory>tbody>tr {
		border: 1px solid #ccc;
		margin-top: 25px
	}
	.skillHistory>tbody>tr>td {
		border: none;
		border-bottom: 1px solid #eee;
		position: relative;
		padding-left: 50%
	}
	.skillHistory>tbody>tr>td:before {
		position: absolute;
		top: 6px;
		left: 6px;
		width: 45%;
		padding-right: 10px;
		white-space: nowrap
	}
	.skillHistory>tbody>tr>td:nth-of-type(1):before {
		content: "Skill Type :";
		font-family: "robotoregular";
		color: #3E454c;
		font-size: 14px
	}
	.skillHistory>tbody>tr>td:nth-of-type(2):before {
		content: "Skill Name :";
		font-family: "robotoregular";
		color: #3E454c;
		font-size: 14px
	}
	.skillHistory>tbody>tr>td:nth-of-type(3):before {
		content: "Rating :";
		font-family: "robotoregular";
		color: #3E454c;
		font-size: 14px
	}
	.skillHistory>tbody>tr>td:nth-of-type(4):before {
		content: "Years of Experience :";
		font-family: "robotoregular";
		color: #3E454c;
		font-size: 14px
	}
}

/* for Skill css */
.careerHistory>tbody>tr:nth-of-type(odd) {
	background: #FFF
}

.careerHistory>tbody>tr:nth-of-type(even) {
	background: #f2f2f2
}

.careerHistory>thead>tr>th {
	background: #59574B;
	color: #fff;
	font-weight: 700
}

.careerHistory>tbody>tr>td,th {
	padding: 10px 20px;
	border: 0 solid #ccc;
	text-align: left
}

.careerHistory>tbody>tr {
	border-bottom: 1px solid #ccc
}

@media only screen and (max-width: 700px) {
	.careerHistory,.careerthead,.careertbody,.careerth,.careertd,.careertr {
		display: block
	}
	.careerHistory>tbody>tr>td {
		color: #0095da;
		font-size: 14px;
		font-family: "robotoregular"
	}
	.careerHistory>tbody>tr:nth-of-type(even) {
		background: #f2f2f2
	}
	.careerHistory>tbody>tr:nth-of-type(odd) {
		background: #f2f2f2
	}
	.careerview>table thead tr {
		position: absolute;
		top: -9999px;
		left: -9999px
	}
	.careerHistory>tbody>tr {
		border: 1px solid #ccc;
		margin-top: 25px
	}
	.careerHistory>tbody>tr>td {
		border: none;
		border-bottom: 1px solid #eee;
		position: relative;
		padding-left: 50%
	}
	.careerHistory>tbody>tr>td:before {
		position: absolute;
		top: 6px;
		left: 6px;
		width: 45%;
		padding-right: 10px;
		white-space: nowrap
	}
	.careerHistory>tbody>tr>td:nth-of-type(1):before {
		content: "Company Name :";
		font-family: "robotoregular";
		color: #3E454c;
		font-size: 14px
	}
	.careerHistory>tbody>tr>td:nth-of-type(2):before {
		content: "Position :";
		font-family: "robotoregular";
		color: #3E454c;
		font-size: 14px
	}
	.careerHistory>tbody>tr>td:nth-of-type(3):before {
		content: "Location :";
		font-family: "robotoregular";
		color: #3E454c;
		font-size: 14px
	}
	.careerHistory>tbody>tr>td:nth-of-type(4):before {
		content: "Time From :";
		font-family: "robotoregular";
		color: #3E454c;
		font-size: 14px
	}
	.careerHistory>tbody>tr>td:nth-of-type(5):before {
		content: "Time To :";
		font-family: "robotoregular";
		color: #3E454c;
		font-size: 14px
	}
}

@media only screen and (min-device-width : 320px) and (max-device-width
	: 480px) {
	body {
		padding: 0;
		margin: 0;
		width: auto
	}
	.footer {
		position: relative;
		font-size: 14px;
		bottom: 0;
		width: 100%;
		height: 80px;
		background-color: #3E454D
	}
}

@media only screen and (min-device-width: 768px) {
	#width1 {
		width: 13%
	}
	#width2 {
		width: 12%
	}
	#width3 {
		width: 20%
	}
	#width4 {
		width: 16%
	}
	#width5 {
		width: 20%
	}
	#width6 {
		width: 15%
	}
}
</style>
<body>
	<sec:authorize ifAnyGranted="ROLE_EXTERNAL">
		<form name="testForm"
			action="http://tmiblogs.elasticbeanstalk.com/tmitest/tmiProcessor.php"
			method="post" target="newwindow">
			<input type="hidden" name="pid" value="9" /> <input type="hidden"
				name="id" value="${myTmiTestId}" />
		</form>
	</sec:authorize>


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
											style="text-transform: capitalize; font-family: robotoregular; font-size: 20px; color: #f36f21; white-space: -moz-pre-wrap !important; white-space: -webkit-pre-wrap; white-space: -pre-wrap; white-space: -o-pre-wrap; white-space: pre-wrap; word-wrap: break-word; word-break: break-all; white-space: normal;">
											<sec:authorize
												ifAnyGranted="ROLE_ADMIN,ROLE_CCR,ROLE_EVAL_EVAL">
									  ${myprofile[0].firstname}	${myprofile[0].lastname}
									  </sec:authorize> <sec:authorize ifAnyGranted="ROLE_INTERNAL">
									  ${myprofile[0].screenName}	
									  </sec:authorize>
										</span><br> Industry:<span
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
													href="http://downloads.testmyinterview.com/images/${myprofile[0].resumeid}"><u>My
														Resume</u></a>
											</c:if>

										</span><br> <span
											style="font-family: robotoregular; font-size: 14px; color: #f36f21;">
											<c:if test="${not empty myprofile[0].videoid}">
												<a
													href="http://downloads.testmyinterview.com/images/${myprofile[0].videoid}"><u>Brief
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
												<td class="careertd">${careerLst.tomonth}&nbsp;${careerLst.toyear}</td>
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
				</div>
			</div>
		</div>
	</div>
	<br>
</body>