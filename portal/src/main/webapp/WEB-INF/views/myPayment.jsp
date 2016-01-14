
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
jQuery(document).ready(function() {
	$("#profilelink").parent().addClass("active");
});
function walletBack(){
		window.location.href = 'myCompleteProfile.do';
}
	
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
<body>


	<div
		style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
		<div class="container">
			<img src="resources/images/icon_profile.png">&nbsp;&nbsp;Payments
		</div>
	</div>
	<div class="" style="margin-top: 20px;">
		<div class="container">

			<hr>
			<form id="myprofile" method="post" autocomplete="off">
<c:if test="${not empty mypaymentList}">
				<table>
					<thead>
						<tr>
							<th id="width1" Style="color: #FFF">Month Year</th>
							<th id="width2" Style="color: #FFF">Amount</th>
							<th id="width3" Style="color: #FFF">Payment Status</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="paylLst" items="${mypaymentList}">
							<tr>
								<td class="appTmi" id="my${paylLst.month}"><a href="#">${paylLst.month}</a></td>
								<td>${paylLst.amount}</td>
								<td><c:if test="${paylLst.evalpaymentstatus eq 0}">
										<button id="continue" onclick="getMonth('${paylLst.month}')"
											type="button" class="form-control mycontinuebtn">Request
											to pay</button>
									</c:if> <c:if test="${paylLst.evalpaymentstatus eq 1}">
						Request Sent</c:if> <c:if test="${paylLst.evalpaymentstatus eq 2}">
						Processed</c:if></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
</c:if>

				<c:if test="${empty mypaymentList}">
					<div style="text-align: center;">
						<img style="width: 30px;" src="resources/images/warning.png" />&nbsp;There
						are no payments to be made.
					</div>
				</c:if>
				<br>
								<hr>
<div class="row">				
						<div
							class="col-xs-4 col-xs-push-8 col-sm-2 col-sm-push-10 col-md-2 col-md-push-10">
							<div class="form-group">
								<button type="button" onclick="walletBack()"
									class="form-control mybackbtn" >Back</button>
							</div>
						</div>		
				</div>
			</form>
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
				$(".modal-bodyInterview").load("myPaymentDetailsOnMonth", {
					'monthname' : a.replace("my", "")
				}), $("#myinterviewFeedBack").modal({
					show : !0
				})
			})
		});
	</script>
</body>