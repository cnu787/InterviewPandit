
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	$(document).ready(function() {
		$("#reportslink").parent().addClass("active");
		displaydata();
	});
	
</script>
<style>
* {
	margin: 0;
	padding: 0
}

#page-wrap {
	margin: 50px
}

table {
	width: 100%;
	border-collapse: collapse
}

tr:nth-of-type(odd) {
	background: #FFF
}

tr:nth-of-type(even) {
	background: #f2f2f2
}

th {
	background: #59574B;
	color: #fff;
	font-weight: 700
}

td,th {
	padding: 10px 20px;
	border: 0 solid #ccc;
	text-align: left
}

tr {
	border-bottom: 1px solid #ccc
}

@media only screen and (max-width: 700px) {
	table,thead,tbody,th,td,tr {
		display: block
	}
	td {
		color: #0095da;
		font-size: 14px;
		font-family: "robotoregular"
	}
	tr:nth-of-type(even) {
		background: #f2f2f2
	}
	tr:nth-of-type(odd) {
		background: #f2f2f2
	}
	thead tr {
		position: absolute;
		top: -9999px;
		left: -9999px
	}
	tr {
		border: 1px solid #ccc;
		margin-top: 25px
	}
	td {
		border: none;
		border-bottom: 1px solid #eee;
		position: relative;
		padding-left: 50%
	}
	td:before {
		position: absolute;
		top: 6px;
		left: 6px;
		width: 45%;
		padding-right: 10px;
		white-space: nowrap
	}
	td:nth-of-type(1):before {
		content: "Month Year :";
		font-family: "robotoregular";
		color: #3E454c;
		font-size: 14px
	}
	td:nth-of-type(2):before {
		content: "Amount :";
		font-family: "robotoregular";
		color: #3E454c;
		font-size: 14px
	}
	td:nth-of-type(3):before {
		content: "Payment Status :";
		font-family: "robotoregular";
		color: #3E454c;
		font-size: 14px
	}
}

@media ( min-width : 768px) {
	.interview-dialog {
		width: 750px
	}
}
</style>

<style>
.mydivheader {
	color: #FFF;
	background: none repeat scroll 0% 0% #59574B;
	font-weight: 700;
}

.mydivheader,.mydivrow {
	padding: 10px 20px;
	border-bottom: 1px solid #CCC;
}

.mydivrow:nth-child(even) {
	background: #fff;
}

.mydivrow:nth-child(odd) {
	background: #f2f2f2;
}
</style>
<body>


	<div
		style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
		<div class="container">
			<img src="resources/images/icon_profile.png">&nbsp;&nbsp;Payments
			Request
		</div>
	</div>
	<div class="" style="margin-top: 20px;">
		<div class="container">

			<hr>
			<br>
			<c:if test="${not empty evalPaymentReqList}">
				<div class="row" style="width: 100%;">
					<div class="col-md-12">
						<div class="mydivheader row">
							<div class="col-md-2">Month Year</div>
							<div class="col-md-2">Evaluator Name</div>
							<div class="col-md-2">Applied Date</div>
							<div class="col-md-2">Amount</div>
							<div class="col-md-3">Reference No</div>
							<div class="col-md-1"></div>
						</div>
						<c:set var="count" value="0" />
						<c:forEach var="paylLst" items="${evalPaymentReqList}">
							<form id="${paylLst.evaluserid}-form${count}"
								action="evalPaymentSubmit" method="post" autocomplete="off">
								<input type="hidden" name="evaluserid" value="${paylLst.userid}" />
								<input type="hidden" name="interviewmonth"
									value="${paylLst.interviewmonth}" />
								<c:set var="count" value="${count+1}" />
								<div class="mydivrow row">

									<div class="col-md-2 appTmi"
										id="my-${paylLst.interviewmonth}-${paylLst.evaluserid}">
										<a href="#">${paylLst.interviewmonth}</a>
									</div>
									<div class="col-md-2">${paylLst.fullname}</div>
									<div class="col-md-2">${paylLst.applieddatetime}</div>
									<div class="col-md-2">${paylLst.amount}</div>
									<div class="col-md-3 form-group">
										<input class="form-control" maxlength="50" type="text"
											name="referNo" />
									</div>
									<div class="col-md-1">
										<button type="submit"
											class="col-md-2 form-control mycontinuebtn" id="back">Pay</button>
									</div>

								</div>
							</form>
						</c:forEach>
					</div>
				</div>
			</c:if>
			<c:if test="${empty evalPaymentReqList}">
				<div style="text-align: center;">
					<img style="width: 30px;" src="resources/images/warning.png" />&nbsp;There
					are no payment requests.
				</div>
			</c:if>
			<br>

			<hr>


		</div>
	</div>


	<div class="modal" id="myinterviewFeedBack" tabindex="-1" role="dialog"
		data-backdrop="static" aria-labelledby="myModalLabel"
		aria-hidden="true">
		<div class="modal-dialog interview-dialog ">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myinterview">Interview Detail</h4>
				</div>
				<div class="modal-bodyInterview"></div>
				<div class="modal-footer">
					<div
						class="col-xs-4 col-xs-push-8 col-sm-4 col-sm-push-8 col-md-3 col-md-push-8">
						<button type="button" class="form-control mycontinuebtn"
							data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function getMonth(monthname) {
			var monthname = monthname;
			window.location.href = "requestforPayment.do?monthname="
					+ monthname;
		}
		jQuery(document).ready(function() {
			$(".appTmi").click(function() {

				var a = $(this).attr("id");

				$(".modal-bodyInterview").load("evalPaymentDetailsOnMonth", {
					'monthname' : a.split('-')[1],
					'evalid' : a.split('-')[2],
				}), $("#myinterviewFeedBack").modal({
					show : !0
				})
			})
		});
	</script>
</body>