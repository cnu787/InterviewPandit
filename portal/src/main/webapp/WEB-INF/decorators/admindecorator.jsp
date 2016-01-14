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
<title>Interview Portal</title>
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
<link rel="stylesheet" href="resources/css/portaldecorator.css" />
<style>
.container {
	width: 970px !important;
}
.nav.active {
	background: url(../images/navigation_hover.png) no-repeat scroll center
		bottom/14px auto transparent !important
}
 .navbar-nav>.open>a,.navbar-nav>.open>a:focus,.navbar-nav>.open>a:hover{
 background-color :transparent !important;
}
.dropdown-menu{
background-color :#F7F7F7; 
}
</style>
</head>
<body>
	<c:if test="${empty loggedIn}">
		<c:set var="loggedIn" value="false" />
		<c:set var="homeUrl" value="home.do" />
	</c:if>
	<sec:authorize ifAnyGranted="ROLE_ADMIN">
		<c:set var="adminloggedIn" value="true" />
		<c:set var="loggedIn" value="true" />
		<c:set var="homeUrl" value="tmiAdmAdminLanding.do" />
	</sec:authorize>	
	<sec:authorize ifAnyGranted="ROLE_CCR">
		<c:set var="adminloggedIn" value="true" />
		<c:set var="loggedIn" value="true" />
		<c:set var="homeUrl" value="tmiAdmCallCenterRepresentativeLanding.do" />
	</sec:authorize>	
	<sec:authorize ifAnyGranted="ROLE_EVAL_EVAL">
		<c:set var="adminloggedIn" value="true" />
		<c:set var="loggedIn" value="true" />
		<c:set var="homeUrl" value="tmiAdmEvaluatorsLanding.do" />
	</sec:authorize>
	<!-- Static navbar -->
	<div class="navbar myheader navbar-default navbar-static-top"
		role="navigation">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="${homeUrl}"> <img
					src="resources/images/testmyinterview_logo2.png" alt="">
				</a>
			</div>
			<div>
				<ul class="nav navbar-nav">
					<c:if test="${loggedIn eq true}">
						<li class="active"><a id="homelink" href="${homeUrl}">HOME</a></li>
					</c:if>
					<sec:authorize ifAnyGranted="ROLE_CCR">					
				 <li  class="dropdown ">
          			<a id="evalUnavaillink"  href="#" class=" dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">INTERVIEWS <span class="caret"></span></a>
        			  <ul class="dropdown-menu">
           				 <li><a href="tmiAdmAdminLanding.do">ONGOING</a></li>
          				  <li><a href="tmiAdmGetClosedInterviewsDetails.do?mydays=0">CLOSED</a></li>
          				  <li><a href="tmiAdmGetCancelInterviewsDetails.do?mydays=0">CANCELLED</a></li>            
          				  <li><a href="tmiAdmGetEvalUnavailableDetails.do?mydays=0&reschedule=0">EVALUATOR UNAVAILABILITY</a></li> 
         			 </ul>
       			 </li>
							
							
					</sec:authorize>
					<sec:authorize ifAnyGranted="ROLE_ADMIN">
						<li><a id="createuserlink" href="tmiAdmCreateAdminUser.do">CREATE
								USER</a></li>
					</sec:authorize>
					<sec:authorize ifAnyGranted="ROLE_ADMIN">
						<li><a id="bulkuploadlink" href="tmiAdmuploadEmails.do">BULK
								UPLOAD</a></li>
					</sec:authorize>
					<sec:authorize ifAnyGranted="ROLE_ADMIN">
						<li><a id="mocktestlink" href="tmiAdmMockTest.do">MOCK
								TEST</a></li>
					</sec:authorize>
					<sec:authorize ifAnyGranted="ROLE_ADMIN">
						<li><a id="reportslink" href="tmiAdmReportsPage.do">REPORTS</a></li>
					</sec:authorize>
				</ul>
				<c:if test="${loggedIn eq true && adminloggedIn eq true}">
					<ul class="nav navbar-nav navbar-right mynavbar-right"
						style="font-size: 14px; font-family: robotoregular; color: #3e454c;">
						<li style="text-align: right;">Welcome <span
							style="text-transform: capitalize; color: #F36F21; font-family: robotobold">${mySesProfile[0].firstname}</span>&nbsp;&nbsp;|&nbsp;&nbsp;
							<a href="<c:url value="j_spring_security_logout" />"
							style="padding: 30px 0px 0px; display: inline-block;"><img
								src="resources/images/icon_logout.png" /></a> <br>
						</li>
					</ul>
				</c:if>
			</div>
			<!--/.nav-collapse -->
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
				<a href="copyright.do">Copyrights</a>&nbsp;&nbsp;|&nbsp; <a
					href="termsandcondition.do">Legal Terms & Conditions</a>&nbsp;&nbsp;|&nbsp;
				<a href="contactus.do">Contact Us</a>
			</p>
		</div>
	</div>
</body>
</html>