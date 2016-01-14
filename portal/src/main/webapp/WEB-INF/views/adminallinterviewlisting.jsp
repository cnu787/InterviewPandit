
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="resources/css/intlTelInput.css">
<script type="text/javascript">
	<c:if test="${empty count}">
	jQuery(document).ready(function() {
		$("#homelink").parent().addClass("active");
	});
	</c:if>
	<c:if test="${not empty count}">
	jQuery(document).ready(function() {
		$("#evalUnavaillink").parent().addClass("active");
	});
	</c:if>
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

td, th {
	padding: 10px 18px;
	border: 0px solid #ccc;
	text-align: left;
	width: -1em;
	text-wrap: none;
}

td, th {
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

.btn-lg {
	padding: 10px 39px;
}

.ongoing {
	width: 18.8%;
	padding: 2px;
	border: 5px solid #D3D3D3;
	margin: 5px;
	height: 80Px;
	color: #0094DA;
}

.h1, .h2, .h3, h1, h2, h3 {
	margin-top: 9px;
	margin-bottom: 10px;
}

.text-size {
	margin-top: 9px;
	margin-bottom: 10px;
	font-size: 36px;
}

.onload {
	color: #F36F21;
	border: 5px solid #F36F21;
}

.interview-tmi {
	width: 646px;
	margin: 30px auto;
}

.modal-dialog {
	width: 728px;
	margin: 30px auto;
}
</style>
<body>
	<div
		style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
		<div class="container">Home > Interviews</div>
	</div>
	<div class="" style="margin-top: 20px;">
		<div class="container">
			<div
				style="border: 1px solid #D3D3D3; background: none repeat scroll 0% 0% #F2F2F2; padding: 10px 20px">
				<form id="dashboardsearch"
					action="javascript:displaydatabasedOnsearch()" method="post"
					autocomplete="off">
					<label>What are you looking for?</label>
					<div class="row">
						<div class="col-md-3">
							<div class="form-group ">
								<select class="form-control" name="dashboard" id="dashboard">
									<option value="-1">Select</option>
									<c:forEach var="dashboardLst" items="${dashboardsearchtypelkp}">
										<option value="${dashboardLst.dashboardsearchtypeid}"
											<c:if test="${dashboardLst.dashboardsearchtypeid==dashboardId}">
													selected="selected"
													</c:if>>
											${dashboardLst.dashboardsearchname}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group ">
								<input type="text" class="form-control" name="enterString"
									id="enterString" placeholder="Enter a search keyword" />
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
										$("#dashboardsearch")
												.bootstrapValidator(
														{
															feedbackIcons : {
																valid : "glyphicon glyphicon-ok",
																invalid : "glyphicon glyphicon-remove",
																validating : "glyphicon glyphicon-refresh"
															},
															fields : {
																dashboard : {
																	validators : {
																		callback : {
																			message : "Please select listed looking type.",
																			callback : function(
																					a,
																					b) {
																				var d = b
																						.getFieldElements(
																								"dashboard")
																						.val();
																				return -1 != d
																			}
																		}
																	}
																},
																enterString : {
																	validators : {
																		notEmpty : {
																			message : "Enter Name is required."
																		}
																	}
																}
															}
														})
									});
				</script>
			</div>
			<br>
			<h1 class="header-file">
				<span style="color: #F26F21">Ongoing Interviews:</span>
			</h1>
			<hr>
			<div>
				<div class="row">
					<div class="col-md-12 ongoingcalls ">
						<div class="ongoing col-md-2 onload" id="all-1,2,5,6,9">
							<h1 align="center">${ongoingCount+evalNoAnswerCount+appNoAnswerCount+successCount}</h1>
							<br>
							<div style="font-size: 14px;" class="textcolor" align="center">Total</div>
						</div>
						<div class="ongoing col-md-2" id="ongoing-1,2">
							<h1 align="center">${ongoingCount}</h1>
							<br>
							<div style="font-size: 14px;" class="textcolor" align="center">On
								going</div>
						</div>
						<div class="ongoing col-md-2" id="evalNoAnswer-5">
							<h1 align="center">${evalNoAnswerCount}</h1>
							<br>
							<div style="font-size: 14px;" class="textcolor" align="center">Evaluator
								No Answer</div>
						</div>
						<div class="ongoing col-md-2" id="appNoAnswer-6">
							<h1 align="center">${appNoAnswerCount}</h1>
							<br>
							<div style="font-size: 14px;" class="textcolor" align="center">Applicant
								No Answer</div>
						</div>
						<div class="ongoing col-md-2" id="success-9">
							<h1 align="center">${successCount}</h1>
							<br>
							<div style="font-size: 14px;" class="textcolor" align="center">Success</div>
						</div>
					</div>
				</div>
			</div>
			<br> <br />
		</div>
		<div class="" style="margin-top: 10px;">
			<div class="container" align="center"
				style="padding-left: 0px; padding-right: 0px;">
				<div class="connectdetails"></div>
			</div>
		</div>
		<br /> <br />
		<script>
			function myDropdownEvalnameCheckCheck() {

				$("#dashboardsearch").bootstrapValidator(
						"enableFieldValidators", "dashboard", !1), $(
						"#dashboardsearch").bootstrapValidator(
						"enableFieldValidators", "dashboard", !0);
				$("#dashboardsearch").bootstrapValidator(
						"enableFieldValidators", "enterString", !1), $(
						"#dashboardsearch").bootstrapValidator(
						"enableFieldValidators", "enterString", !0);
			}

			$('.ongoing').click(function() {
				$(".ongoingcalls div").removeClass("onload");
				$(this).addClass('onload');
				displaydata(this.id.split("-")[1]);
			});

			function displaydata(connectstatus) {
				myDropdownEvalnameCheckCheck();
				var evaluatorName = $('#enterString').val();
				var dashboard = $('#dashboard').val();
				$(".ongoingcalls div").removeClass("onload");
				$('#dashboard').val('-1');
				$('#enterString').val('');
				$(".connectdetails").load("tmiAdmAdmincallDetails", {
					'connectstatus' : connectstatus,
					'evaluatorName' : evaluatorName,
					'dashboard' : dashboard
				}), $("#myModal").modal({
					show : !0
				})
			}
			function displaydatabasedOnsearch(connectstatus) {
				$(".ongoingcalls div").removeClass("onload");
				var evaluatorName = $('#enterString').val();
				var dashboard = $('#dashboard').val();
				$(".connectdetails").load(
						"tmiAdmAdmincallDetailsBasedOnSearch", {
							'evaluatorName' : evaluatorName,
							'dashboard' : dashboard
						});
				$("#myModal").modal({
					show : !0
				})
			}
			$(document).ready(function() {
				displaydata("2,5,6,9");
			});
		</script>
	</div>

	<div class="modal" id="myModal1" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
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
		aria-labelledby="myModalLabel" aria-hidden="true">
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