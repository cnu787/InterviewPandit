<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="resources/css/intlTelInput.css">
<script src="resources/js/intlTelInput.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		displaydata();
	});

	function displaydata() {
		$(".applicantSlotDetails").load("tmiadminapplicantSlotbooking");

	}
	function backbutton() {
		backstep();
		$("#interviewBookingContainer").load("tmiadmininterviewRole");
	}
	function reloaddata(){
		$(".applicantSlotDetails").load("tmiadminapplicantSlotbooking");
	}
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
}

th {
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

table-hd {
	height: 61px;
	color: #FFF;
	background: #59574B;
}

.ev-btn {
	padding: 8px 8px;
	margin-bottom: 5px;
	border: 1px solid #999393;
}

.interview-reschedule {
	width: 90%;
}
</style>
<body>

	<div class="" style="margin-top: 20px;">
		<div class="container">
			<h1 class="header-file">Book Slot</h1>
			<h4 class="header-text" style="color: #0066cc;">Below are the
				slots available for interviews Click on the one you would like to
				avail</h4>
			<br>
			<!-- <div
				class="col-sm-2 col-md-2"> -->
			<button type="button" style="width: 84px;"
				class="form-control mycontinuebtn" onclick="tmieSlot1()">
				Add Slot</button>
			<!-- </div>		 -->
			<hr>
			<div class="applicantSlotDetails"></div>
			<br>
			<hr>
			<div class="row">
				<div
					class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
					<div class="form-group">
						<button type="button" class="form-control mybackbtn"
							onclick="backbutton()" id="back">Back</button>
					</div>
				</div>
				<div
					class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
					<div class="form-group">
						<input id="continue" type="button"
							onClick="checkAvailabilitySlotss();"
							class="form-control mycontinuebtn" value="Continue" />
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal" id="myModaltmi" tabindex="-1" role="dialog"
		data-backdrop="static" aria-labelledby="myModalLabel"
		aria-hidden="true">

		<div class="modal-dialog interview-tmiid">
			<div class="modal-content">

				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Interview Summary</h4>
				</div>
				<div class="modal-bodytmi"></div>
				<div class="modal-footer">
					<div class="col-md-4 col-md-push-8">
						<button type="button" class="form-control mycontinuebtn"
							data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal" id=myModal tabindex="-1" role="dialog"
		data-backdrop="static" aria-labelledby="myModalLabel"
		aria-hidden="true">
		<div class="modal-dialog interview-reschedule">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" onclick ="reloaddata()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title my-modal-title" id="myModalLabel">Add
						Slot For Interview</h4>
				</div>
				<div class="modal-body modal-bodyreschedule"></div>
				<div class="modal-footer">
					<hr>
					<div class="col-md-12 ">
						<div class="col-md-2 col-md-push-8">
							<button type="button" class="form-control mybackbtn" onclick ="reloaddata()"
								data-dismiss="modal">Cancel</button>
						</div>
						<div class="col-md-2 col-md-push-8">
							<button type="button" class="form-control mycontinuebtn" id=""
								onclick="addSlotToInterview1();">Assign</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal" id="myModalAlert" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog mymodal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel"></h4>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<div
						class="col-xs-4 col-xs-push-4 col-sm-4 col-sm-push-4 col-md-4 col-md-push-4">
						<button type="button" class="form-control mycontinuebtn"
							data-dismiss="modal">Ok</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function tmieSlot1() {
			$("#processImg").show();
			var ids = bookedslotid;
			$('#' + ids).css("background-color", "#FFFFFF");
			$('#' + ids).children().children("#selectcolor").prop("checked",
					false);
			$(".modal-bodyreschedule").load("addInterviewSlot");
			$("#myModal").modal({
				show : !0
			})
		}

		function checkAvailabilitySlotss() {

			if (bookedslotid != null) {
				$("#processImg").show();
				var status = false;
				$.ajax({
					url : "tmiadminupdateSlotScheduleIdStatus.do/"
							+ bookedslotid + "/" + $("#interviewId").val(),
					async : false,
					success : function(result) {
						status = result;
					}
				});
				if (status == true) {
					nextstep();

					$("#processImg").hide();					
					$("#interviewBookingContainer").load(
							'tmiadmininterviewSummary');
				} else {
					$("#processImg").hide();
					var modal = $('#myModalAlert').modal({
						'show' : true
					});
					modal.find('.modal-title').text('Alert!');
					modal.find('.modal-body')
							.text('Please select another slot');

				}
			} else {

				var modal = $('#myModalAlert').modal({
					'show' : true
				});
				modal.find('.modal-title').text('Alert!');
				modal.find('.modal-body').text('Please select a slot');
			}

		}
		function addSlotToInterview1() {
			if (bookedslot != null) {
				var status1 = false;
				$("#processImg").show();
				$.ajax({
					url : "tmiadminaddSlotScheduleId.do/"
							+ $('#selectDate').val() + "/"
							+ $('#interviewSlotId').val() + "/" + bookedslot,
					async : false,
					success : function(result) {
						completeHandler(result);
					}
				});

			} else {

				alert("Please select Any Slot ");
			}

			function completeHandler(result) {
				if (result == true) {
					nextstep();
					$("#processImg").hide();
					$('#myModal').modal('hide');				
					$("#interviewBookingContainer").load(
							'tmiadmininterviewSummary');
				} else {

					$("#processImg").hide();
					$('#myModal').modal('hide');
					var modal = $('#myModalAlert').modal({
						'show' : true
					});
					modal.find('.modal-title').text('Alert!');
					modal.find('.modal-body')
							.text('Please select another slot');

				}
			}
		}
	</script>
</body>