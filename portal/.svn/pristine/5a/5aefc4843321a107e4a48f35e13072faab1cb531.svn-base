<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="resources/css/intlTelInput.css">
<script src="resources/js/intlTelInput.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		displaydata();
	});

	function displaydata() {
		$(".applicantSlotDetails").load("applicantSlotbooking");

	}
	function backbutton() {
		backstep();
		$("#interviewBookingContainer").load('skillspriority');
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
	/*border-collapse: collapse;*/
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
</style>
<body>

	<div class="" style="margin-top: 20px;">
		<div class="container">
			<h1 class="header-file">Book Slot</h1>
			<h4 class="header-text" style="color: #0066cc;">Below are the
				slots available for interviews Click on the one you would like to
				avail</h4>
			<br>
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
							onClick="checkAvailabilitySlots();"
							class="form-control mycontinuebtn" value="Continue" />
					</div>
				</div>
			</div>
			<div class="modal" id="myModal" tabindex="-1" role="dialog"
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
		</div>
	</div>
</body>