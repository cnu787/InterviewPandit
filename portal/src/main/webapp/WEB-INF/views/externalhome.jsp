<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<body>


	<div id="mainProfileContainer">
		My profile<br> Your profile is not yet created. <a href="#"
			id="create">Create One</a> <br>
		<sec:authorize ifAnyGranted="ROLE_EXTERNAL">
			<a href="interviewLanding.do">Skip Profile</a>
		</sec:authorize>
	</div>
	<script type="text/javascript">
		jQuery(document).ready(function() {
			$("#profilelink").parent().addClass("active");
			$("#create").click(function() {
				$("#mainProfileContainer").load('profileAboutMe');
			});

		});
	</script>
</body>

