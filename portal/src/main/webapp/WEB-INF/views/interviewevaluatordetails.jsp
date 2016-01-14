<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	jQuery(document).ready(function() {
		$("#evalUnavaillink").parent().addClass("active");
		displaydata();
	});
	function displaydata() {
		$(".eval_interviews").load("getDashboardReport", {
			'dashboardId' : '${dashboardId}',
			'enterString' : $('#enterString').val()
		});
	}
	function backInterviewLanding() {
		window.location.href = "tmiAdmCallCenterRepresentativeLanding.do";
	}
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

.modal-dialog {
	width: 648px;
	margin: 30px auto;
}

.modal-profile {
	width: 727px;
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
				<form id="dashboardsearch" action="tmiAdmGetDashboardReport.do"
					method="post" autocomplete="off">
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
									id="enterString" placeholder="Enter a search keyword"
									value="${enterString}" />
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
			<div class="eval_interviews"></div>
			<div class="row">
				<div
					class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-10">
					<div class="form-group">
						<button type="button" class="form-control mybackbtn" id="back" onclick="backInterviewLanding()">Back</button>
					</div>
				</div>
			
			</div>
		</div>
	</div>
	<div class="modal" id="myModal" tabindex="-1" role="dialog"
		data-backdrop="static" aria-labelledby="myModalLabel"
		aria-hidden="true">
		<div class="modal-dialog interview-dialog">
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
		<div class="modal-dialog modal-profile">
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
	<script>
		jQuery(document).ready(
				function() {
					$("#interviewlandLink").parent().addClass("active"), $(
							".myTmi").click(function() {
						var a = $(this).attr("id");
						var res = a.split("_");
						a = res[0];
						a = a.replace("my", "")
						var profile = "TMI ID : ";
						var profileName = profile.concat(res[1]);
						$(".nameOf").text(profileName);
						$(".modal-bodyTmi").load("tmiAdmGetApplicantDetails", {
							'interviewId' : a
						}), $("#myModal").modal({
							show : !0
						})
					})
				});
	</script>
	<script type="text/javascript">
		jQuery(document).ready(function() {
			$(".appid").click(function() {
				var a = $(this).attr("id");
				$(".modal-bodyprofile").load("usersCompleteProfile", {
					'userId' : a.replace("user", "")
				}), $("#myModalprofile").modal({
					show : !0
				})
			});
			$(".evalid").click(function() {
				var a = $(this).attr("id");
				$(".modal-bodyprofile").load("usersCompleteProfile", {
					'userId' : a.replace("user", "")
				}), $("#myModalprofile").modal({
					show : !0
				})
			});
		});
	</script>

</body>