<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="resources/css/intlTelInput.css">
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script>
	$(document).ready(function() {
		$("#reportslink").parent().addClass("active");
		displaydata();
	});
	function displaydata() {
		$(".register_users").load("tmiAdmGetallRegisteredUsers")
	}
	function emailIdSearch() {
		var emailId=$('#enterString').val();
		$(".register_users").load("tmiAdmGetallRegisteredUsers",{'emailId':emailId})}
	
</script>
<style>
/* Generic Styling, for Desktops/Laptops */
table {
	width: 100%;
	/*border-collapse: collapse;*/
}
/* Zebra striping */
tr:nth-of-type(odd) {
	background: #FFFFFF;
}

tr:nth-of-type(even) {
	background: #f2f2f2;
}

th {
	background: #59574B;
	color: white;
	font-weight: bold;
}

td,th {
	padding: 10px 18px;
	border: 0px solid #ccc;
	text-align: left;
	width: -1em;
	text-wrap: none;
}

td,th {
	padding: 10px 18px;
	border: 0px solid #ccc;
	text-align: left;
	width: -1em;
	text-wrap: none;
	white-space: -moz-pre-wrap !important; /* Mozilla, since 1999 */
	white-space: -webkit-pre-wrap; /*Chrome & Safari */
	white-space: -pre-wrap; /* Opera 4-6 */
	white-space: -o-pre-wrap; /* Opera 7 */
	white-space: pre-wrap; /* css-3 */
	word-wrap: break-word; /* Internet Explorer 5.5+ */
	/*word-break: break-all;*/
	white-space: normal;
}

tr {
	border-bottom: 1px solid #cccccc;
}

.ev-btn {
	padding: 8px 8px;
	margin-bottom: 5px;
	border: 1px solid #999393;
}

.mycontinuebtn {
	background-image: linear-gradient(to bottom, #37B2EB, #37B2EB);
	border-color: #37B2EB;
	background-color: #37B2EB !important;
}

.interview-tmi {
	width: 646px;
	margin: 30px auto;
}
</style>
<body>
	<div
		style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
		<div class="container">Registered Users</div>
	</div>
	<div class="" style="margin-top: 20px;">
		<div class="container">
<div
				style="border: 1px solid #D3D3D3; background: none repeat scroll 0% 0% #F2F2F2; padding: 10px 20px">
				 <form id="basedOnEmailsearch"
					action="javascript:emailIdSearch()" method="post"
					autocomplete="off">	
						<br>		
					<div class="row">
						<div class="col-md-6">
							<div class="form-group ">
								<input type="text" class="form-control" name="enterString"
									id="enterString" placeholder="Enter Email Address" />
							</div>
						</div>
						<div class="col-md-2">
							<button type="submit" class="form-control mybackbtn">Search</button>
						</div>
					</div>
				</form>
				<script type="text/javascript">
					$(document)
							.ready(
									function() {
										$("#basedOnEmailsearch")
												.bootstrapValidator(
														{
															feedbackIcons : {
																valid : "glyphicon glyphicon-ok",
																invalid : "glyphicon glyphicon-remove",
																validating : "glyphicon glyphicon-refresh"
															},
															fields : {															
																enterString : {
																	validators : {
																		notEmpty : {
																			message : "Email id is required."
																		}
																	}
																}
															}
														})
									});
				</script>
			</div>

			<hr>
			<div class="register_users"></div>

		</div>
	</div>
	<div class="modal" id="myModal" tabindex="-1" role="dialog"
		data-backdrop="static" aria-labelledby="myModalLabel"
		aria-hidden="true">
		<div class="interview-tmi">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title nameOf" id="myModalLabel"></h4>
				</div>
				<div class="modal-bodyTmi"></div>
			</div>
		</div>
	</div>
	<div class="modal" id="myModalprofile" tabindex="-1" role="dialog"
		data-backdrop="static" aria-labelledby="myModalLabel"
		aria-hidden="true">
		<div class="modal-dialog interview-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Profile Summary</h4>
				</div>
				<div class="modal-bodyprofile"></div>
				<div class="modal-footer">
					<div
						class="col-xs-6 col-xs-push-6 col-sm-4 col-sm-push-8 col-md-4 col-md-push-8">
						<button type="button" class="form-control mybackbtn"
							data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>