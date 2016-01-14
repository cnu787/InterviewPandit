<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	jQuery(document).ready(function() {
		$("#employerzonelink").parent().addClass("active");

	});
</script>
<style>
body {
	background-color: #0095DA;
}

.input-group-addon {
	background-color: #59574B;
	color: #FFF;
	border: 1px solid #59574B;
}

hr {
	border-color: #57574B;
}

form {
	color: #3e454c;
	font-size: 12px;
	font-family: robotoregular;
}

@media ( min-width : 768px) {
	.row {
		margin-right: 0px;
		margin-left: 0px;
	}
}
</style>
<body>


	<div class="non-authentic-body">
		<div class="container">
			<div
				style="text-align: center; color: #FFF; font-family: robotocondensedregular;">

				<br>

			</div>

			<div class="row marginauto">
				<div
					class="col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3 myform-body centered">
					<div class="row">
						<div class="col-sm-12 col-md-12" style="margin-top: 10px;">
							<img class="imagemarginauto" style="padding: 5px; width: 53px;"
								src="resources/images/icon_login_login_page.png">
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12 col-md-12">
							<hr>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-6 col-sm-6 col-md-6"
							style="margin-bottom: 10px;">
							<a href="evalLogin.do"><img class="img-responsive"
								src="resources/images/button_login.png" /></a>
						</div>
						<div class="col-xs-6 col-sm-6 col-md-6"
							style="margin-bottom: 10px;">
							<a href="empRegisterView.do"><img class="img-responsive"
								src="resources/images/register today.png" /></a>
						</div>
					</div>
				</div>
			</div>
			<div></div>

		</div>
		<br>
	</div>
</body>
