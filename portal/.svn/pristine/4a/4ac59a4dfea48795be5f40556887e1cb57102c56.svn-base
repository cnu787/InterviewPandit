<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	window.onload = function() {
		var status = "new";
		displaydata(status);
	};

	jQuery(document).ready(function() {
		$("#homelink").parent().addClass("active");
	});
</script>
<style>
@media ( min-width : 768px) {
	.interview-dialog {
		width: 750px
	}
}
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

.btn-lg {
	padding: 10px 39px;
}

.ongoing {
	width: 18.8%;
	padding: 2px;
	border: 5px solid #D3D3D3;
	margin: 5px;
	height: auto;
	color: #0094DA;
}

h1 {
	margin-top: 10px;
}

.text {
	text-align: center;
	margin-bottom: 8px;
	margin-top: 15px;
}

.onload {
	color: #F36F21;
	border: 5px solid #F36F21;
}
</style>
<body>
	<div
		style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
		<div class="container">Home</div>
	</div>
	<div class="" style="margin-top: 20px;">
		<div class="container">

			<div
				style="border: 1px solid #D3D3D3; background: none repeat scroll 0% 0% #F2F2F2; padding: 10px 20px">
				<form id="dashboardsearch" action="#" method="post"
					autocomplete="off">
					<label>What are you looking for?</label>
					<div class="row">
						<div class="col-md-3">
							<div class="form-group ">
								<select class="form-control" name="dashboard" id="dashboard">
									<option value="-1">Select</option>
									<option value="3">Evaluator Name</option>
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
														});
										$("#dashboardsearch").on(
												"success.form.bv", function(a) {
													a.preventDefault()
													displaydataOnName()
												})
									});
				</script>
			</div>
			<h1 class="header-file">
				<span style="color: #F26F21">Evaluators</span>
			</h1>
			<hr>
			<div class="row">
				<div class="col-md-12">

					<div class="row ongoingcalls">
						<a href="#">
							<div class="ongoing col-md-1" style="margin-left: 10px" id="all"
								onclick="displaydata('all')">
								<h1 align="center">${allMembers}</h1>
								<div class="text">All</div>

							</div>
						
							<div class="ongoing col-md-1" id="new"
								onclick="displaydata('new')">
								<h1 align="center">${newMembers}</h1>
								<div class="text">New</div>
							</div>
						
							<div class="ongoing col-md-1" id="underTraining"
								onclick="displaydata('underTraining')">
								<h1 align="center">${trainingMembers}</h1>
								<div class="text">Yet to Complete Training</div>
							</div>						
							<div class="ongoing col-md-1" id="mappedToTmi"
								onclick="displaydata('mappedToTmi')">
								<h1 align="center">${mappedMembers}</h1>
								<div class="text">Certified Evaluators</div>
							</div>
						
							<div class="ongoing col-md-1" id="unsuitable"
								onclick="displaydata('unsuitable')">
								<h1 align="center">${unsuitableMembers}</h1>
								<div class="text">Unsuitable</div>
							</div>
						</a>
					</div>

				</div>
			</div>
			<hr>
			<div class="" style="margin-top: 10px;">
				<div class="container" align="center"
					style="padding-left: 0px; padding-right: 0px;">
					<div class="evaldetails"></div>
				</div>
			</div>
			<hr>
		</div>
	</div>

	<script type="text/javascript">
		function myDropdownEvalnameCheckCheck() {

			$("#dashboardsearch").bootstrapValidator("enableFieldValidators",
					"dashboard", !1), $("#dashboardsearch").bootstrapValidator(
					"enableFieldValidators", "dashboard", !0);
			$("#dashboardsearch").bootstrapValidator("enableFieldValidators",
					"enterString", !1), $("#dashboardsearch")
					.bootstrapValidator("enableFieldValidators", "enterString",
							!0);
		}
		function displaydata(status) {			
			myDropdownEvalnameCheckCheck();
			$('#dashboard').val('-1');
			$('#enterString').val('');
			$(".ongoingcalls div").removeClass("onload");
			var id = status;
			$('#' + id).addClass('onload ');
			$(".evaldetails").load("evalDetails", {
				'status' : status
			});
		}
		$("#myModal").modal({
			show : !0
		})

		function displaydataOnName() {
			$(".ongoingcalls div").removeClass("onload");
			var selectType = $('#dashboard').val();
			var enterName = $('#enterString').val();
			$(".evaldetails").load("evalDetails", {
				'selectType' : selectType,
				'enterName' : enterName,
				'status':''
			});
		}		
	</script>
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
					<h4 class="modal-title nameOf" id="myModalLabel">Profile:</h4>
				</div>
				<div class="modal-bodyprofile"></div>
				<div class="modal-footer"></div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#tmiRoles").bootstrapValidator({
				feedbackIcons : {
					valid : "glyphicon glyphicon-ok",
					invalid : "glyphicon glyphicon-remove",
					validating : "glyphicon glyphicon-refresh"
				},
				fields : {
					roles : {
						validators : {
							callback : {
								message : "Please select listed industry.",
								callback : function(a, b) {
									var d = b.getFieldElements("roles").val();
									return -1 != d
								}
							}
						}
					}
				}
			}), $("#tmiRoles").on("success.form.bv", function(a) {
				a.preventDefault(), myotherajaxform()
			})
		});
		var myotherajaxform = function() {
			var a = $("input[name=roles]").val(), b = $("input[name=comment]")
					.val();
		};
	</script>
</body>