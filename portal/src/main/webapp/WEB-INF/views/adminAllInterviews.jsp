<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="resources/css/intlTelInput.css">
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script>
	$(document).ready(function() {
		$("#reportslink").parent().addClass("active");
	});
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
	width: 730px;
	margin: 30px auto;
}

.ongoing {
	width: 13.2%;
	padding: 2px;
	border: 5px solid #D3D3D3;
	margin: 5px;
	height: 80Px;
	color: #0094DA;
}

.h1,.h2,.h3,h1,h2,h3 {
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
</style>
<body>
	<div
		style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
		<div class="container">Interview Bookings</div>
	</div>
	<div class="" style="margin-top: 20px;">
		<div class="container">
			<hr>
			<div>
				<div class="row">
				<a href="#">
					<div class="col-md-12 ongoingcalls ">
						<div class="ongoing col-md-2 onload" id="all-1,2,3,4,5">
							<h1 align="center">${booked+closed+cancelled+paymentcancelled+paymentfailure+rejectInterviews}</h1>
							<br>
							<div style="font-size: 14px;" class="textcolor" align="center">Total</div>
						</div>
						<div class="ongoing col-md-2 " id="booking-1">
							<h1 align="center">${booked}</h1>
							<br>
							<div style="font-size: 14px;" class="textcolor" align="center">Booked</div>
						</div>
						<div class="ongoing col-md-2" id="closed-2">
							<h1 align="center">${closed}</h1>
							<br>
							<div style="font-size: 14px;" class="textcolor" align="center">Closed</div>
						</div>
						<div class="ongoing col-md-2" id="Cancelled-3">
							<h1 align="center">${cancelled}</h1>
							<br>
							<div style="font-size: 14px;" class="textcolor" align="center">Cancelled</div>
						</div>
						<div class="ongoing col-md-2" id="PaymentCancelled-4">
							<h1 align="center">${paymentcancelled}</h1>
							<br>
							<div style="font-size: 14px;" class="textcolor" align="center">Payment
								Cancelled</div>
						</div>
						<div class="ongoing col-md-2" id="PaymentFailure-5">
							<h1 align="center">${paymentfailure}</h1>
							<br>
							<div style="font-size: 14px;" class="textcolor" align="center">Payment
								Failure</div>
						</div>
						<div class="ongoing col-md-2" id="RejectSlots-6">
							<h1 align="center">${rejectInterviews}</h1>
							<br>
							<div style="font-size: 14px;" class="textcolor" align="center">Reject</div>
						</div>
					</div>
					</a>
				</div>
			</div>
			<br>
			<br>
			<hr>
			<div class="register_users"></div>

		</div>
	</div>
	<div class="modal" id="myModaltmi" tabindex="-1" role="dialog"
		data-backdrop="static" aria-labelledby="myModalLabel"
		aria-hidden="true">
		<div class="modal-dialog interview-dialog">
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
					<div
						class="col-xs-6 col-xs-push-6 col-sm-4 col-sm-push-8 col-md-4 col-md-push-8">
						<button type="button" class="form-control mycontinuebtn"
							data-dismiss="modal">Close</button>
					</div>
				</div>
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
	<script type="text/javascript">
		$(document).ready(function() {
			displaydata("1,2,3,4,5");
		});
		function displaydata1() {
			$(".register_users").load("tmiAdmInterviewBookingdetails")
		}
		$('.ongoing').click(function() {
			$(".ongoingcalls div").removeClass("onload");
			$(this).addClass('onload');
			displaydata(this.id.split("-")[1]);
		});

		function displaydata(interviewTypeId) {
			$(".register_users").load("tmiAdmInterviewBookingdetails", {
				'interviewTypeId' : interviewTypeId
			})
		}
	</script>
</body>

</html>