<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Interview Portal</title>
<link rel="shortcut icon" href="resources/images/favicon.png">
<link rel="stylesheet" href="resources/css/bootstrap.min.css" />
<link rel="stylesheet" href="resources/css/bootstrap.css.map" />
<link rel="stylesheet" href="resources/css/bootstrapValidator.min.css" />
<link rel="stylesheet" type="text/css" href="resources/css/style.css">
<link rel="stylesheet" type="text/css" href="resources/css/mymedia.css">
<link rel="stylesheet" type="text/css" href="resources/css/font.css">
<link rel="stylesheet" href="resources/css/sticky-footer.css" />

<!-- Bootstrap JS -->
<script src="resources/js/jquery-1.11.1.js"></script>
<!-- FormValidation plugin and the class supports validating Bootstrap form -->
<script src="resources/js/bootstrap.js"></script>
<script src="resources/js/bootstrapValidator.js"></script>
<script src="resources/js/ipValidator.js"></script>
<script src="resources/js/tmiUtil.js"></script>
<script src="resources/js/ajaxsetup.js"></script>
<script>
	(function(i, s, o, g, r, a, m) {
		i['GoogleAnalyticsObject'] = r;
		i[r] = i[r] || function() {
			(i[r].q = i[r].q || []).push(arguments)
		}, i[r].l = 1 * new Date();
		a = s.createElement(o), m = s.getElementsByTagName(o)[0];
		a.async = 1;
		a.src = g;
		m.parentNode.insertBefore(a, m)
	})(window, document, 'script', '//www.google-analytics.com/analytics.js',
			'ga');
	ga('create', 'UA-63529317-1', 'auto');
	ga('send', 'pageview');
</script>
<link rel="stylesheet" href="resources/css/portaldecorator.css" />
</head>
<body>
	<c:if test="${empty loggedIn}">
		<c:set var="loggedIn" value="false" />
		<c:set var="homeUrl" value="home.do" />
	</c:if>
	<sec:authorize ifAnyGranted="ROLE_INTERNAL">
		<c:set var="loggedIn" value="true" />
		<c:set var="profileUrl" value="internalHome.do" />
		<c:set var="homeUrl" value="internalLanding.do" />
	</sec:authorize>
	<sec:authorize ifAnyGranted="ROLE_EXTERNAL">
		<c:set var="loggedIn" value="true" />
		<c:set var="profileUrl" value="externalHome.do" />
		<c:set var="homeUrl" value="externalLanding.do" />
	</sec:authorize>
	<!-- Static navbar -->
	<div class="navbar myheader navbar-default navbar-static-top"
		role="navigation">
		<div class="container">
			<div class="navbar-header">
				<div class="mynavbar-toggle pull-right">
					<ul class="nav navbar-right mynavbar-nav">
						<c:if test="${loggedIn eq false }">
							<li style="border-right: 2px solid #fff;"><a
								href="login.do" style="text-align: center;" class="headerright"><img
									class="headerrightimage" src="resources/images/icon_login.png">Log
									In</a></li>
							<li><a href="registerView.do" style="text-align: center;"
								class="headerright"><img class="headerrightimage"
									src="resources/images/icon_register.png">Register</a></li>
						</c:if>
					</ul>
				</div>
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<c:if test="${loggedIn eq true }">
					<a class="navbar-toggle"
						href="<c:url value="j_spring_security_logout" />"
						style="border: 0px;"><img class="headerrightimage"
						src="resources/images/icon_logout.png"></a>


				</c:if>
				<a class="navbar-brand" href="${homeUrl}"> <img
					class="hidden-xs" src="resources/images/testmyinterview_logo2.png"
					alt=""> <img class="visible-xs"
					src="resources/images/testmyinterview_logo2.png" alt="">
				</a>


			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<c:if test="${loggedIn eq false }">
						<li class="active"><a id="homelink" href="${homeUrl}">HOME</a></li>
						<li><a id="aboutlink" href="abouttmi.do">ABOUT US</a></li>
						<li><a id="bloglink" href="http://43.225.52.93/tmiblogs">BLOGS</a></li>
						<li><a id="faqlink" href="faq.do">FAQs</a></li>
						<li><a id="employerzonelink" href="employerZone.do">EVALUATOR
								ZONE</a></li>
					</c:if>
					<c:if test="${loggedIn eq true}">
						<li class="active"><a id="homelink" href="${homeUrl}">HOME</a></li>
					</c:if>
					<c:if test="${loggedIn eq true}">
						<li><a id="profilelink" href="${profileUrl}">PROFILE</a></li>
						<sec:authorize ifAnyGranted="ROLE_INTERNAL">
							<li><a id="calenderlink"
								href="javascript:myprofileStatus(${eLearningStatus},'internalCalender.do');">CALENDAR</a></li>
							<li><a id="interviewhisLink" href="javascript:myprofileStatus(${eLearningStatus},'interviewHistory.do');">INTERVIEWS</a></li>
						</sec:authorize>
						<sec:authorize ifAnyGranted="ROLE_EXTERNAL">
							<li><a id="interviewlandLink" href="interviewLanding.do">INTERVIEWS</a></li>
						</sec:authorize>
						<li><a id="bloglink" href="http://43.225.52.93/tmiblogs">BLOGS</a></li>
						<sec:authorize ifAnyGranted="ROLE_EXTERNAL">
							<li><a id="faqlink" href="faq.do">FAQs</a></li>
						</sec:authorize>
					</c:if>
				</ul>
				<c:if test="${loggedIn eq false }">
					<ul class="nav navbar-nav navbar-right mynavbar-right">
						<li style="border-right: 2px solid #fff;"><a href="login.do"
							style="text-align: center;" class="headerright"><img
								class="headerrightimage" src="resources/images/icon_login.png">Log
								In</a></li>
						<li><a href="registerView.do"
							style="text-align: center; padding-left: 10px; padding-right: 10px;"
							class="headerright"><img class="headerrightimage"
								src="resources/images/icon_register.png">Register</a></li>
					</ul>
				</c:if>

				<c:if test="${loggedIn eq true}">
					<ul class="nav navbar-nav navbar-right mynavbar-right"
						style="font-size: 14px; font-family: robotoregular; color: #3e454c;">
						<li style="text-align: right;">Welcome <span
							style="text-transform: capitalize; color: #F36F21; font-family: robotobold">${mySesProfile[0].screenName}</span>&nbsp;&nbsp;|&nbsp;&nbsp;
							<a href="<c:url value="j_spring_security_logout" />"
							style="padding: 20px 0px 0px; display: inline-block;"><img
								src="resources/images/icon_logout.png" /></a> <br> <span
							style="font-size: 12px;"> <c:if
									test="${not empty userLastSession}">
								Last
							Session: <span id="lastSessionId"></span>
									<script>
										var cDate = convertGmtTOLocal('${userLastSession}');
										$('#lastSessionId').html(
												cDate.getDate()
														+ " "
														+ monthNames[cDate
																.getMonth()]
														+ " , "
														+ cDate.getFullYear()
														+ " "
														+ cDate.getHours()
														+ ":"
														+ cDate.getMinutes()
														+ ":"
														+ cDate.getSeconds()
														+ " HRS");
									</script>
								</c:if>
						</span>
						</li>
						<li><img class="img-circle" id="profileAvatar"
							style="width: 45px; height: 45px; margin: 15px 0px 0px 10px; border: 2px solid #0094DA;"
							src="http://downloads.testmyinterview.com/images/${mySesProfile[0].photoid}"
							onerror="this.src='resources/images/noimage.png';" /></li>
					</ul>
				</c:if>
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>
	<div class="modal" id="myProfileStatus" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog mymodal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Profile Incomplete</h4>
				</div>
				<div class="modal-body">Please take E-Learning course by clicking on the Elearning link in the "Profile" page and complete the assesment before  publishing your available slots.</div>
				<div class="modal-footer">
					<div
						class="col-xs-4 col-xs-push-4 col-sm-4 col-sm-push-4 col-md-4 col-md-push-4">
						<button type="button" class="form-control mybackbtn"
							data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		// binds form submission and fields to the validation engine
		$(".navbar-nav").children().removeClass("active");
	</script>
	<div id="processImg">
		<div class="ano_project">
			<div class="ano_proinn">
				<img src="resources/images/processing1.gif" />
				<h4 align="center" style="color: white; margin-top: 10px;">Processing.....</h4>
			</div>
		</div>
	</div>
	<decorator:body />

	<br>
	<div class="footer">
		<div class="container">
			<p class="text-muted">
				<a href="#">Copyrights</a>&nbsp;&nbsp;|&nbsp; <a
					href="termsandcondition.do">Legal Terms & Conditions</a>&nbsp;&nbsp;|&nbsp;
				<a href="contactus.do">Contact Us</a>
			</p>
		</div>
	</div>
</body>
<script type="text/javascript">
	function myprofileStatus(profileStatus, url) {
		if (profileStatus == 2) {
			window.location.href = url;
		} else {
			$('#myProfileStatus').modal('show');
		}
	}
</script>
</html>