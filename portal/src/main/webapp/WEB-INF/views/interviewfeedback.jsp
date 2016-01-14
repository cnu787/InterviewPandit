<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	jQuery(document).ready(
			function() {
				var interviewId = $("#interviewId").val();
				Window.onload = $("#interviewFeedBackContainer").load(
						"evaluatorInstructions", {
							'interviewId' : interviewId
						});
			});
</script>
<body>
	<div
		style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
		<div class="container">
			<img src="resources/images/icon_interview.png">&nbsp;&nbsp;Interviews
		</div>
	</div>
	<input type="hidden" name="interviewId" id="interviewId" value="1">
	<div id="interviewFeedBackContainer"></div>
</body>