<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
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
		font-family: "robotoregular";
		word-wrap: "none";
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
	.skillHistory>tbody>tr>td:nth-of-type(5):before {
		content: "Priorty :";
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

.skillHistory {
	font-family: Helvetica Neue, Helvetica, Arial, sans-serif !important;
	font-size: 14px !important;
	line-height: 1.42857 !important;
	"
}
</style>
<body>
	<div class="" style="margin-top: 0px;">
		<div class="container">
			<div class="row">
				<div class="col-md-9">
					<c:if test="${not empty userDetails[0].resumeid}">
						<label style="float: right; color: #0095DA;"><a
							href="http://downloads.interviewpandit.com/images/${userDetails[0].resumeid}"><u>Resume</u></a>
						</label>
					</c:if>
					<br>
					<div class="row">
						<div class="col-xs-12 col-sm-11 col-md-11">
							<div class="row">
								<div class="col-xs-12 col-sm-4 col-md-4">
									<label>Industry</label>
								</div>
								<div class="col-xs-12 col-sm-4 col-md-4">
									<label style="color: #0095DA;"><c:if
											test="${userDetails[0].industryname =='Others'}">${userDetails[0].otherIndustry}</c:if>
										<c:if test="${userDetails[0].industryname ne 'Others'}">${userDetails[0].industryname}</c:if>
									</label>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12 col-sm-4 col-md-4">
									<label>Domain</label>
								</div>
								<c:set var="i" value="0" />
								<div class="col-xs-12 col-sm-8 col-md-8">
									<label style="color: #0095DA;"> <c:forEach
											var="domianLst" items="${domainList}">
											<c:if test="${i != '0'}">,</c:if> 
								${domianLst.domainname}
								<c:set var="i" value="${i+1}" />
										</c:forEach>
									</label>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12 col-sm-4 col-md-4">
									<label>Interview type</label>
								</div>
								<div class="col-xs-12 col-sm-4 col-md-4">
									<label style="color: #0095DA; text-transform: capitalize;">${userDetails[0].typeofinterview}</label>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12 col-sm-4 col-md-4">
									<label>Career Status</label>
								</div>
								<div class="col-xs-12 col-sm-4 col-md-4">
									<label style="color: #0095DA; text-transform: capitalize;">
										<c:if test="${userDetails[0].careerstatus ==0}">Experience</c:if>
										<c:if test="${userDetails[0].careerstatus ==1}">Fresher/ Campus Grad</c:if>
									</label>
								</div>
							</div>
							<c:if test="${userDetails[0].companyName!=null}">
								<div class="row">
									<div class="col-xs-12 col-sm-4 col-md-4">
										<label>Company Name</label>
									</div>
									<div class="col-xs-12 col-sm-4 col-md-4">
										<label style="color: #0095DA; text-transform: capitalize;">${userDetails[0].companyName}</label>
									</div>
								</div>
							</c:if>
							<c:if test="${not empty userDetails[0].designation}">
								<div class="row">
									<div class="col-xs-12 col-sm-4 col-md-4">
										<label>Position</label>
									</div>
									<div class="col-xs-12 col-sm-4 col-md-4">
										<label style="color: #0095DA; text-transform: capitalize;">${userDetails[0].designation}</label>
									</div>
								</div>
							</c:if>
							<div class="row">
								<div class="col-xs-12 col-sm-4 col-md-4">
									<label>Role of Interviewer</label>
								</div>
								<div class="col-xs-12 col-sm-4 col-md-4">
									<label style="color: #0095DA;">${userDetails[0].interviewerrolename}</label>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12 col-sm-4 col-md-4">
									<label>Location</label>
								</div>
								<div class="col-xs-12 col-sm-4 col-md-4">
									<label style="color: #0095DA;">${userDetails[0].locationname}</label>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12 col-sm-4 col-md-4">
									<label>Mode of Interview</label>
								</div>
								<div class="col-xs-12 col-sm-4 col-md-4">
									<label style="color: #0095DA;">${userDetails[0].interviewtypename}</label>
								</div>
							</div>
						</div>
					</div>
					<c:if test="${not empty userDetails[0].date }">
						<hr>
						<div class="row">
							<div class="col-xs-10 col-sm-11 col-md-11">
								<div class="row">
									<div class="col-xs-12 col-sm-4 col-md-4">
										<label>Slot Booked</label>
									</div>
									<div class="col-xs-12 col-sm-3 col-md-3">
										<label style="color: #0095DA;">${userDetails[0].date}
											<br> ${userDetails[0].timeslot}
										</label>
									</div>
								</div>
							</div>
						</div>
					</c:if>
					<c:if test="${not empty skillsDetails }">
						<hr>
						<div class="row">
							<div class="col-xs-10 col-sm-11 col-md-11">
								<div class="row">
									<div class="col-xs-12 col-sm-4 col-md-4">
										<label>Skills for Evaluation</label>
									</div>
								</div>

							</div>
						</div>

						<div class="row">
							<div class="col-sm-12 col-md-12 ">
								<c:if test="${not empty skillsDetails }">
									<div class="skillview">
										<table class="skillHistory">
											<thead class="skillthead">
												<tr class="skilltr">
													<th class="skillth" Style="color: #FFF">Skill Type</th>
													<th class="skillth" Style="color: #FFF">Skill Name</th>
													<th class="skillth" Style="color: #FFF">Rating</th>
													<th class="skillth" Style="color: #FFF">Years of
														Experience</th>
													<th class="skillth" Style="color: #FFF">Priorty</th>
												</tr>
											</thead>
											<tbody class="skilltbody">
												<c:set var="maxid" value="0" />
												<c:forEach var="skilLst" items="${skillsDetails}">
													<tr class="skilltr">
														<td class="skilltd">${skilLst.skilltypename}</td>
														<td class="skilltd">${skilLst.skillname}</td>
														<td class="skilltd">${skilLst.skillrating}</td>
														<td class="skilltd">${skilLst.year}&nbsp;Years&nbsp;${skilLst.month}&nbsp;months</td>
														<td class="skilltd"><c:if
																test="${not empty skilLst.skillspriority}">${skilLst.skillspriority}</c:if>
															<c:if test="${empty skilLst.skillspriority}">No Priorty</c:if></td>
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
						</div>

					</c:if>
					<hr>
					<c:if test="${mySesProfile[0].usertypeid == 1}">

						<div class="row">
							<div class="col-xs-10 col-sm-11 col-md-11">
								<div class="row">
									<div class="col-xs-12 col-sm-4 col-md-4">
										<label>Pricing</label>
									</div>
									<div class="col-xs-12 col-sm-8 col-md-8">
										<label style="color: #0095DA;">RS.
											${userDetails[0].amount} (Including Service tax 14%)</label>
									</div>
								</div>
							</div>
						</div>
						<hr>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</body>